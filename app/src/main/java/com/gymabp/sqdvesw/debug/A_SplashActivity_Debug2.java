//package com.gymabp.sqdvesw;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.os.Handler;
//import android.util.Log;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.appsflyer.AppsFlyerConversionListener;
//import com.appsflyer.AppsFlyerLib;
//import com.appsflyer.attribution.AppsFlyerRequestListener;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class A_SplashActivity_Debug2 extends AppCompatActivity {
//
//    String TAG = "ABaseActivity";
//    public static String[] datasObj;
//    Context context;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        context = this;
//        initAppsFlyer("k2UrCtuTW7qirzSsDVbFhU");
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                AppsFlyerLib.getInstance().logEvent(context, "af_login", new HashMap<>(), new AppsFlyerRequestListener() {
////                    @Override
////                    public void onSuccess() {
////                        Log.d(TAG, "=onSuccess=");
////                    }
////
////                    @Override
////                    public void onError(int i, String s) {
////
////                    }
////                });
////                AppsFlyerLib.getInstance().logEvent(context, "af_ complete_registration", new HashMap<>(), new AppsFlyerRequestListener() {
////                    @Override
////                    public void onSuccess() {
////                        Log.d(TAG, "=onSuccess=");
////                    }
////
////                    @Override
////                    public void onError(int i, String s) {
////
////                    }
////                });
//                AppsFlyerLib.getInstance().logEvent(context, "af_first_purchase", new HashMap<>(), new AppsFlyerRequestListener() {
//                    @Override
//                    public void onSuccess() {
//                        Log.d(TAG, "=onSuccess=");
//                    }
//
//                    @Override
//                    public void onError(int i, String s) {
//
//                    }
//                });
////                AppsFlyerLib.getInstance().logEvent(context, "af_purchase", new HashMap<>(), new AppsFlyerRequestListener() {
////                    @Override
////                    public void onSuccess() {
////                        Log.d(TAG, "=onSuccess=");
////                    }
////
////                    @Override
////                    public void onError(int i, String s) {
////
////                    }
////                });
//            }
//        }, 5000);
//    }
//
//    /**
//     * 初始化AppsFlyer
//     */
//    /// private static boolean not_organic = false;
//    private void initAppsFlyer(String afKey) {
//        Log.d(TAG, "initAppsFlyer afKey=" + afKey);
//        AppsFlyerLib.getInstance().setMinTimeBetweenSessions(0);
//        AppsFlyerLib.getInstance().setDebugLog(true);
//
//        // app flay初始化
//        AppsFlyerLib.getInstance().init(afKey, new AppsFlyerConversionListener() {
//            @Override
//            public void onConversionDataSuccess(Map<String, Object> map) {
//                //map={install_time=2023-09-25 13:27:12.578, af_status=Organic, af_message=organic install, is_first_launch=true}
//                Log.d(TAG, "onConversionDataSuccess map=" + map);
////                if (!TextUtils.equals((String) map.get("af_status"), "Organic")) {
////                    not_organic = true;
////                }
//            }
//
//            @Override
//            public void onConversionDataFail(String s) {
//                Log.d(TAG, "onConversionDataFail=" + s);
//
//            }
//
//            @Override
//            public void onAppOpenAttribution(Map<String, String> map) {
//                Log.d(TAG, "onAppOpenAttribution=" + map);
//
//            }
//
//            @Override
//            public void onAttributionFailure(String s) {
//                Log.d(TAG, "onAttributionFailure=" + s);
//
//            }
//        }, getApplicationContext());
//
//
//        AppsFlyerLib.getInstance().start(getApplicationContext(), afKey, new AppsFlyerRequestListener() {
//            @Override
//            public void onSuccess() {
//                Log.d(TAG, "Launch sent successfully, got 200 response code from server");
//            }
//
//            @Override
//            public void onError(int i, @NonNull String s) {
//                Log.d(TAG, "Launch failed to be sent:\n" + "Error code: " + i + "\n" + "Error description: " + s);
//            }
//        });
//    }
//}