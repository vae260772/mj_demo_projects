package com.mjb.test1.mj_b_mian;

import static com.mjb.test1.DemoApplication.appContext;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.appsflyer.AFInAppEventParameterName;
import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.attribution.AppsFlyerRequestListener;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录：“login”
 * 登出：“logout”
 * 点击注册：“registerClick”
 * 注册成功 ：“register”
 * 点击充值：“rechargeClick” 需要解析处理
 * *首充成功 ：“firstrecharge” 需要解析处理
 * *复充成功 ：“recharge” 需要解析处理
 * 提现点击：“withdrawClick”
 * *提现成功 ：“withdrawOrderSuccess”
 * 进入游戏(包含三方与自营)：“enterGame”
 * 领取vip奖励：“vipReward”
 * 领取每日奖励：“dailyReward”
 * 新增交互事件
 * 1. 活动中心（进入页面）：“enterEventCenter”
 * 2. 任务中心（进入页面）：“enterTask”
 * 3. 实时返水（进入页面）：“enterCashback”
 * 4. 推广赚钱（进入页面）：“enterPromote”
 * 5. 6张banner图（每张图的点击事件）：“bannerClick”
 * 存取款的事件内容有额外带上金额以及币种
 * 例：
 * 存款: {\"amount\":\"200\",\"currency\":\"PHP\",\"isFirst\":0,\"success\":1}
 * 取款: {\"amount\":\"104\",\"currency\":\"PHP\",\"success\":1}
 */
public class AppsFlyerLibUtil {
    private static final String TAG = "AppsFlyerLibUtil";
    private static final String afKey = "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
    public static boolean is_ad_af_status = false;//默认非广告流量，进A面

    /**
     * 初始化AppsFlyer
     */
    public static void initAppsFlyer() {
        Log.d(TAG, "initAppsFlyer");
        AppsFlyerLib.getInstance().setMinTimeBetweenSessions(0);
        AppsFlyerLib.getInstance().setDebugLog(true);

        // app flay初始化
        AppsFlyerLib.getInstance().init(afKey, new AppsFlyerConversionListener() {
            @Override
            public void onConversionDataSuccess(Map<String, Object> map) {
                //map={install_time=2023-09-25 13:27:12.578, af_status=Organic, af_message=organic install, is_first_launch=true}
                Log.d(TAG, "onConversionDataSuccess map=" + map);
                String afType = (String) map.get("af_status");
                if (TextUtils.equals(afType, "Organic")) {//Organic自然流量，可以用static方法，或者文件存储：is_ad_af_status
                    is_ad_af_status = false;
                } else {
                    //广告流量
                    is_ad_af_status = true;
                }
            }

            @Override
            public void onConversionDataFail(String s) {
                Log.d(TAG, "onConversionDataFail=" + s);

            }

            @Override
            public void onAppOpenAttribution(Map<String, String> map) {
                Log.d(TAG, "onAppOpenAttribution=" + map);

            }

            @Override
            public void onAttributionFailure(String s) {
                Log.d(TAG, "onAttributionFailure=" + s);

            }
        }, appContext);

        AppsFlyerLib.getInstance().start(appContext, afKey, new AppsFlyerRequestListener() {
            @Override
            public void onSuccess() {
                Log.e(TAG, "Launch sent successfully, got 200 response code from server");
            }

            @Override
            public void onError(int i, @NonNull String s) {
                Log.e(TAG, "Launch failed to be sent:\n" + "Error code: " + i + "\n" + "Error description: " + s);
            }
        });

    }

    /***
     * 上报AF数据
     */
    public static void event(Activity context, String name, String data) {
        Map<String, Object> eventValue = new HashMap<String, Object>();
        /***
         * 开启新窗口跳转
         */
        if ("openWindow".equals(name)) {
            Intent intent = new Intent(context, BWebChildAct.class);
            intent.putExtra("url", data);
            context.startActivityForResult(intent, 1);
        } else if ("firstrecharge".equals(name) || "recharge".equals(name)) {
            try {
                Map maps = (Map) JSON.parse(data);
                for (Object map : maps.entrySet()) {
                    String key = ((Map.Entry) map).getKey().toString();
                    if ("amount".equals(key)) {
                        eventValue.put(AFInAppEventParameterName.REVENUE, ((Map.Entry) map).getValue());
                    } else if ("currency".equals(key)) {
                        eventValue.put(AFInAppEventParameterName.CURRENCY, ((Map.Entry) map).getValue());
                    }
                }
            } catch (Exception e) {

            }
        } else if ("withdrawOrderSuccess".equals(name)) {
            // 提现成功
            try {
                Map maps = (Map) JSON.parse(data);
                for (Object map : maps.entrySet()) {
                    String key = ((Map.Entry) map).getKey().toString();
                    if ("amount".equals(key)) {
                        float revenue = 0;
                        String value = ((Map.Entry) map).getValue().toString();
                        if (!TextUtils.isEmpty(value)) {
                            revenue = Float.valueOf(value);
                            revenue = -revenue;
                        }
                        eventValue.put(AFInAppEventParameterName.REVENUE, revenue);

                    } else if ("currency".equals(key)) {
                        eventValue.put(AFInAppEventParameterName.CURRENCY, ((Map.Entry) map).getValue());
                    }
                }
            } catch (Exception e) {

            }
        } else {
            eventValue.put(name, data);
        }
        AppsFlyerLib.getInstance().logEvent(context, name, eventValue);

        Toast.makeText(context, name, Toast.LENGTH_SHORT).show();
    }
}