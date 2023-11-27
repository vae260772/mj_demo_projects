package com.mjb.test1.mj_b_mian;

import static com.mjb.test1.DemoApplication.appContext;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.attribution.AppsFlyerRequestListener;

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

    //af_status=Organic，表示是自然流量，谷歌商店下载
    public static boolean isOrganic = true;
    private static final String TAG = "AppsFlyerLibUtil";

    /**
     * 初始化AppsFlyer
     */
    public static void initAppsFlyer(String afKey) {
        Log.d(TAG, "initAppsFlyer");
        AppsFlyerLib.getInstance().setMinTimeBetweenSessions(0);
        AppsFlyerLib.getInstance().setDebugLog(true);

        // app flay初始化
        AppsFlyerLib.getInstance().init(afKey, new AppsFlyerConversionListener() {
            @Override
            public void onConversionDataSuccess(Map<String, Object> map) {
                //map={install_time=2023-09-25 13:27:12.578, af_status=Organic, af_message=organic install, is_first_launch=true}
                Log.d(TAG, "onConversionDataSuccess map=" + map);
                if (TextUtils.equals((String) map.get("af_status"), "Organic")) {
                    isOrganic = true;
                } else {
                    isOrganic = false;
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
                Log.d(TAG, "Launch sent successfully, got 200 response code from server");
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
        Map maps = (Map) JSON.parse(data);
        AppsFlyerLib.getInstance().logEvent(context, name, maps);

        Toast.makeText(context, name + "\n" + data, Toast.LENGTH_LONG).show();
    }
}