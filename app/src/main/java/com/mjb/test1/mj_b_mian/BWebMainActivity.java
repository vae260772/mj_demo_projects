package com.mjb.test1.mj_b_mian;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.appsflyer.AppsFlyerLib;

import java.util.Map;


public class BWebMainActivity extends Activity {
    private static final String TAG = "BWebMainActivity";
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
        @JavascriptInterface
        public void postMessage(String name, String data) {
            Log.d(TAG, "name = " + name + "    data = " + data);
            if (TextUtils.isEmpty(name) || TextUtils.isEmpty(data)) {
                return;
            }
            Map maps = (Map) JSON.parse(data);
            AppsFlyerLib.getInstance().logEvent(BWebMainActivity.this, name, maps);
            Toast.makeText(BWebMainActivity.this, name + ":" + data, Toast.LENGTH_LONG).show();
        }
    }
}