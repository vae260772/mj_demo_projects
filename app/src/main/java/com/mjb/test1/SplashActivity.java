package com.mjb.test1;

import static com.mjb.test1.DemoApplication.mPreferences;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.mjb.test1.mj_b_mian.AppsFlyerLibUtil;
import com.mjb.test1.mj_b_mian.BWebMainActivity;

/**
 * 可以放到A面BWebMainActivity里面
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                //首次打开广告流量true才进入B面
                // 或者之前已经是广告流量了，保存到文件true，直接 进入B
                if (AppsFlyerLibUtil.is_ad_af_status || mPreferences.getBoolean("is_ad_af_status", false)) {
                    startActivity(new Intent(SplashActivity.this, BWebMainActivity.class));
                } else {
                    //默认A面
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));

                }
                finish();
            }
        }, 3000);

    }
}