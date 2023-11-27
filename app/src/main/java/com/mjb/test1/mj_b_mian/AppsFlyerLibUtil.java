//package com.mjb.test1.mj_b_mian;
//
//import static com.mjb.test1.MainActivity.datasObj;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.text.TextUtils;
//import android.widget.Toast;
//
//import com.alibaba.fastjson.JSON;
//import com.appsflyer.AFInAppEventParameterName;
//import com.appsflyer.AppsFlyerLib;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 登录：“login”
// * 登出：“logout”
// * 点击注册：“registerClick”
// * 注册成功 ：“register”
// * 点击充值：“rechargeClick” 需要解析处理
// * *首充成功 ：“firstrecharge” 需要解析处理
// * *复充成功 ：“recharge” 需要解析处理
// * 提现点击：“withdrawClick”
// * *提现成功 ：“withdrawOrderSuccess”
// * 进入游戏(包含三方与自营)：“enterGame”
// * 领取vip奖励：“vipReward”
// * 领取每日奖励：“dailyReward”
// * 新增交互事件
// * 1. 活动中心（进入页面）：“enterEventCenter”
// * 2. 任务中心（进入页面）：“enterTask”
// * 3. 实时返水（进入页面）：“enterCashback”
// * 4. 推广赚钱（进入页面）：“enterPromote”
// * 5. 6张banner图（每张图的点击事件）：“bannerClick”
// * 存取款的事件内容有额外带上金额以及币种
// * 例：
// * 存款: {\"amount\":\"200\",\"currency\":\"PHP\",\"isFirst\":0,\"success\":1}
// * 取款: {\"amount\":\"104\",\"currency\":\"PHP\",\"success\":1}
// */
//
//public class AppsFlyerLibUtil {
//
//    //af_status=Organic，表示是自然流量，谷歌商店下载
//    public static boolean isOrganic = true;
//    private static final String TAG = "AppsFlyerLibUtil";
//
//
//    /***
//
//}