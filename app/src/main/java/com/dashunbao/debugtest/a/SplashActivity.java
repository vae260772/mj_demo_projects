package com.dashunbao.debugtest.a;

import static com.dashunbao.debugtest.b.BWebMainActivity.firebaseStr2;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dashunbao.debugtest.BuildConfig;
import com.dashunbao.debugtest.R;
import com.dashunbao.debugtest.b.AppsFlyerLibUtil;
import com.dashunbao.debugtest.b.BWebMainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.Locale;

public class SplashActivity extends AppCompatActivity {

    public static final String TAG = "Debug";

    private long setDays(long days) {
        return 3600 * 24 * days;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(setDays(30))//本地数据保存至少30天，可以自动定义>30即可
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);

        //正式版本，用firebase返回参数
        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(Task<Boolean> task) {
                        try {
                            String[] appids = BuildConfig.APPLICATION_ID.split("\\.");
                            String key1 = appids[appids.length - 1];
                            String firebaseStr1 = mFirebaseRemoteConfig.getString(key1);
                            Log.d(SplashActivity.TAG, "firebaseStr1=" + firebaseStr1);

                            if (firebaseStr1.isEmpty()) {
                                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                finish();
                            } else {
                                //http://test.brlpk.com+o95M3dWpRnFnyw6cKSULp5+jsBridge+1+pt+br
                                url = firebaseStr1.split("\\+")[0];//"https://rspg.bet"
                                appsflyerkey = firebaseStr1.split("\\+")[1];
                                jsobjectname = firebaseStr1.split("\\+")[2];
                                force2B = firebaseStr1.split("\\+")[3];
                                mlanguage = firebaseStr1.split("\\+")[4];
                                mcountryiso = firebaseStr1.split("\\+")[5];
                                Log.d(SplashActivity.TAG, "url=" + url);
                                Log.d(SplashActivity.TAG, "afkey=" + appsflyerkey);
                                Log.d(SplashActivity.TAG, "jsobjectname=" + jsobjectname);
                                Log.d(SplashActivity.TAG, "force2B=" + force2B);
                                Log.d(SplashActivity.TAG, "mlanguage=" + mlanguage);
                                Log.d(SplashActivity.TAG, "mcountryiso=" + mcountryiso);

                                //str2
                                firebaseStr2 = mFirebaseRemoteConfig.getString(key1 + "2").split("\\+");
                                Log.d(SplashActivity.TAG, "firebaseStr2=" + firebaseStr2);
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

    public static String url = "";
    public static String appsflyerkey = "";
    public static String jsobjectname = "";
    public static String force2B = "";
    public static String mlanguage = "";
    public static String mcountryiso = "";

    private void jumpPage() {
        TelephonyManager tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
        String countryIso = tm.getNetworkCountryIso();//br、cn
        Locale locale = getResources().getConfiguration().locale;
        String language = locale.getLanguage();//pt、zh、en
        AppsFlyerLibUtil.initAppsFlyer(appsflyerkey, this);
        Toast.makeText(this, "countryIso=" + countryIso +
                ",language=" + language, Toast.LENGTH_LONG).show();
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