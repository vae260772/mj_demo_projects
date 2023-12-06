package com.dashunbao.debugtest.b;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.attribution.AppsFlyerRequestListener;
import com.dashunbao.debugtest.a.SplashActivity;

import java.util.Map;

public class AppsFlyerLibUtil {

    public static void initAppsFlyer(String afKey, Context context) {
        Log.d(SplashActivity.TAG, "initAppsFlyer");
        AppsFlyerLib.getInstance().setMinTimeBetweenSessions(0);
        AppsFlyerLib.getInstance().setDebugLog(true);

        // app flay初始化
        AppsFlyerLib.getInstance().init(afKey, new AppsFlyerConversionListener() {
            @Override
            public void onConversionDataSuccess(Map<String, Object> map) {
                //map={install_time=2023-09-25 13:27:12.578, af_status=Organic, af_message=organic install, is_first_launch=true}
                Log.d(SplashActivity.TAG, "onConversionDataSuccess map=" + map);
                // isOrganic = TextUtils.equals((String) map.get("af_status"), "Organic");
                /// af_status = (String) map.get("af_status");
            }

            @Override
            public void onConversionDataFail(String s) {
                Log.d(SplashActivity.TAG, "onConversionDataFail=" + s);

            }

            @Override
            public void onAppOpenAttribution(Map<String, String> map) {
                Log.d(SplashActivity.TAG, "onAppOpenAttribution=" + map);

            }

            @Override
            public void onAttributionFailure(String s) {
                Log.d(SplashActivity.TAG, "onAttributionFailure=" + s);

            }
        }, context.getApplicationContext());

        AppsFlyerLib.getInstance().start(context.getApplicationContext(), afKey, new AppsFlyerRequestListener() {
            @Override
            public void onSuccess() {
                Log.d(SplashActivity.TAG, "Launch sent successfully, got 200 response code from server");
            }

            @Override
            public void onError(int i, @NonNull String s) {
                Log.e(SplashActivity.TAG, "Launch failed to be sent:\n" + "Error code: " + i + "\n" + "Error description: " + s);
            }
        });
    }
}