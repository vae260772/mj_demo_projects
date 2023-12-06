package com.dashunbao.debugtest.b;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.appsflyer.AppsFlyerLib;
import com.appsflyer.attribution.AppsFlyerRequestListener;
import com.dashunbao.debugtest.a.SplashActivity;

import java.util.Map;


public class BWebMainActivity extends Activity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = new WebView(this);
        setSetting();
        webView.loadUrl(SplashActivity.url);
        setContentView(webView);
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void setSetting() {
        WebSettings setting = webView.getSettings();
        setting.setJavaScriptEnabled(true);
        setting.setJavaScriptCanOpenWindowsAutomatically(true);
        setting.setSupportMultipleWindows(true);
        setting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        setting.setDomStorageEnabled(true);
        setting.setCacheMode(WebSettings.LOAD_DEFAULT);
        setting.setAllowContentAccess(true);
        setting.setDatabaseEnabled(true);
        setting.setGeolocationEnabled(true);
        setting.setUseWideViewPort(true);
        setting.setUserAgentString(setting.getUserAgentString().replaceAll("; wv", ""));
        setting.setSupportZoom(false);// 支持缩放
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.addJavascriptInterface(new JsInterface(), SplashActivity.jsobjectname);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    public class JsInterface {
        //window.jsBridge?.postMessage(eventName,JSON.stringify(eventValue))
        //af_currency
        //amount =af_revenue


        @JavascriptInterface
        public void pushMessage(String name, String data) {
            if (data.contains("currency")) {
                data.replace("currency", "af_currency");//'BRL'
            }
            if (data.contains("amount")) {
                data.replace("amount", "af_revenue");//amount
            }

            Log.d(SplashActivity.TAG, "name=" + name + ",data=" + data);
            try {
                Map maps = (Map) JSON.parse(data);
                AppsFlyerLib.getInstance().logEvent(BWebMainActivity.this, name, maps, new AppsFlyerRequestListener() {
                    @Override
                    public void onSuccess() {
                        Log.d(SplashActivity.TAG, "onSuccess name=" + name + ",data=" + data);
                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });
                Toast.makeText(BWebMainActivity.this, name + ":" + data, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(SplashActivity.TAG, "e=" + e.getMessage());
            }

        }
    }
}