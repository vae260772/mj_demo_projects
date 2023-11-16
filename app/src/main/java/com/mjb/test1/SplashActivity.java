package com.mjb.test1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.mjb.test1.mj_b_mian.AppsFlyerLibUtil;
import com.mjb.test1.mj_b_mian.BWebMainActivity;

import java.util.Random;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(0)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);


        ////////////////////测试阶段可以写死
        // String appsflyerkey = "Dp89DfWmmL78B9unRrYXdc";
        //   String url = "https://www.5g88.com/?cid=226292";

        //正式版本，用firebase返回参数
//        String url = mFirebaseRemoteConfig.getString("url");
//        String appsflyerkey = mFirebaseRemoteConfig.getString("key");
//        Log.d(TAG, "1url=" + url);
//        Log.d(TAG, "1appsflyerkey=" + appsflyerkey);
        ////////////////////////////////////

        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        boolean updated = false;
                        if (task.isSuccessful()) {
                            updated = task.getResult();
                            Log.d(TAG, "true, Config params updated: " + updated);
//                            Toast.makeText(MainActivity.this, "Fetch and activate succeeded",
//                                    Toast.LENGTH_SHORT).show();

                        } else {
                            Log.d(TAG, "false,Config params updated: " + updated);

//                            Toast.makeText(MainActivity.this, "Fetch failed",
//                                    Toast.LENGTH_SHORT).show();
                        }
                        //    displayWelcomeMessage();

                        String url = mFirebaseRemoteConfig.getString("url");
                        String appsflyerkey = mFirebaseRemoteConfig.getString("key");
                        Log.d(TAG, "2 url=" + url);
                        Log.d(TAG, "2 appsflyerkey=" + appsflyerkey);
                        jumpPage(appsflyerkey, url);
                    }
                });

//        mFirebaseRemoteConfig.addOnConfigUpdateListener(new ConfigUpdateListener() {
//            @Override
//            public void onUpdate(ConfigUpdate configUpdate) {
//                Log.d(TAG, "Updated keys: " + configUpdate.getUpdatedKeys());
//
//                mFirebaseRemoteConfig.activate().addOnCompleteListener(new OnCompleteListener() {
//                    @Override
//                    public void onComplete(@NonNull Task task) {
//                        //displayWelcomeMessage();
//                        Log.d(TAG, "onComplete: " + task);
//                        String url = mFirebaseRemoteConfig.getString("url");
//                        String appsflyerkey = mFirebaseRemoteConfig.getString("key");
//                        Log.d(TAG, "3url=" + url);
//                        Log.d(TAG, "3appsflyerkey=" + appsflyerkey);
//                    }
//                });
//            }
//
//            @Override
//            public void onError(FirebaseRemoteConfigException error) {
//                Log.w(TAG, "Config update error with code: " + error.getCode(), error);
//            }
//        });

        /**
         * 包名：com.Fascinating5G88Music.Tiger
         * KEY：Dp89DfWmmL78B9unRrYXdc
         *
         * APP名称：Fascinating-Music Tiger
         *
         * 固定链接：https://www.5g88.com/?cid=226292
         */


//        Log.d(TAG, "appsflyerkey: " + appsflyerkey);
//        Log.d(TAG, "url: " + url);

//        Toast.makeText(this, "appsflyerkey=" + appsflyerkey +
//                ",url=" + url, Toast.LENGTH_LONG).show();


    }


    private void jumpPage(String appsflyerkey, String url) {

        if (!TextUtils.isEmpty(appsflyerkey)) {
            AppsFlyerLibUtil.initAppsFlyer(appsflyerkey);
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (TextUtils.isEmpty(url)) {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                } else {
                    BWebMainActivity.mLoadUrl = url;
                    startActivity(new Intent(SplashActivity.this, BWebMainActivity.class));
                    finish();
                }
            }
        }, 2000 + new Random().nextInt(3000));
    }
}