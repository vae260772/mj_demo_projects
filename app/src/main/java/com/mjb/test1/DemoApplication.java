package com.mjb.test1;

import static com.mjb.test1.mj_b_mian.AppsFlyerLibUtil.initAppsFlyer;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class DemoApplication extends Application {
    public static Context appContext;
    String TAG = "DemoApplication";
    public static SharedPreferences mPreferences;
    public static String appId = BuildConfig.APPLICATION_ID;
    public static int version = BuildConfig.VERSION_CODE;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        mPreferences = getSharedPreferences(appId, MODE_PRIVATE);
        initAppsFlyer();
    }
}
