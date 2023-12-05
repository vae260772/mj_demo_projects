//package com.gymabp.sqdvesw;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
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
//public class A_SplashActivity_Debug extends AppCompatActivity {
//
//    String TAG = "ABaseActivity";
//    public static String[] datasObj;
//    Context context;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);
//        context = this;
//        initAppsFlyer("QMTmMgGU2pfeLS33aAVdKS");
//        findViewById(R.id.pb).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                /**
//                 * https://www.leah.bet/?id=41736066|nRhcAXDYSrtBTzd94nRf8Z|jsBridge|openWindow|
//                 * firstrecharge|recharge|amount|currency|withdrawOrderSuccess|true|cn|zh
//                 */
////                AppsFlyerLib.getInstance().logEvent(getApplicationContext(), "login", new HashMap<>(), new AppsFlyerRequestListener() {
////                    @Override
////                    public void onSuccess() {
////                        Log.d(TAG, "Event sent successfully");
////                        //   Toast.makeText(getApplicationContext(), "login============", Toast.LENGTH_LONG).show();
////                    }
////
////                    @Override
////                    public void onError(int i, String s) {
////                        Log.d(TAG, "Event failed to be sent:\n" +
////                                "Error code: " + i + "\n"
////                                + "Error description: " + s);
////                    }
////                });
////                //2
////                AppsFlyerLib.getInstance().logEvent(getApplicationContext(), "logout", new HashMap<>(), new AppsFlyerRequestListener() {
////                    @Override
////                    public void onSuccess() {
////                        Log.d(TAG, "Event sent successfully");
////                    }
////
////                    @Override
////                    public void onError(int i, String s) {
////                        Log.d(TAG, "Event failed to be sent:\n" +
////                                "Error code: " + i + "\n"
////                                + "Error description: " + s);
////                    }
////                });
////                //3
////                AppsFlyerLib.getInstance().logEvent(getApplicationContext(), "registerClick", new HashMap<>(), new AppsFlyerRequestListener() {
////                    @Override
////                    public void onSuccess() {
////                        Log.d(TAG, "Event sent successfully");
////                    }
////
////                    @Override
////                    public void onError(int i, String s) {
////                        Log.d(TAG, "Event failed to be sent:\n" +
////                                "Error code: " + i + "\n"
////                                + "Error description: " + s);
////                    }
////                });
//                //4
////                AppsFlyerLib.getInstance().logEvent(getApplicationContext(), "register", new HashMap<>(), new AppsFlyerRequestListener() {
////                    @Override
////                    public void onSuccess() {
////                        Log.d(TAG, "Event sent successfully");
////                    }
////
////                    @Override
////                    public void onError(int i, String s) {
////                        Log.d(TAG, "Event failed to be sent:\n" +
////                                "Error code: " + i + "\n"
////                                + "Error description: " + s);
////                    }
////                });
//
//                //5
////                AppsFlyerLib.getInstance().logEvent(getApplicationContext(), "rechargeClick", new HashMap<>(), new AppsFlyerRequestListener() {
////                    @Override
////                    public void onSuccess() {
////                        Log.d(TAG, "Event sent successfully");
////                    }
////
////                    @Override
////                    public void onError(int i, String s) {
////                        Log.d(TAG, "Event failed to be sent:\n" +
////                                "Error code: " + i + "\n"
////                                + "Error description: " + s);
////                    }
////                });
//                AppsFlyerLib.getInstance().logEvent(getApplicationContext(), "firstrecharge", new HashMap<>());
//                // AppsFlyerLib.getInstance().logEvent(getApplicationContext(), "recharge", new HashMap<>());
//                //    AppsFlyerLib.getInstance().logEvent(getApplicationContext(), "withdrawClick", new HashMap<>());
////                AppsFlyerLib.getInstance().logEvent(getApplicationContext(), "withdrawOrderSuccess", new HashMap<>());
////                AppsFlyerLib.getInstance().logEvent(getApplicationContext(), "enterGame", new HashMap<>());
//            }
//        });
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