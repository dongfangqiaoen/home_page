package com.sun.webview_gesture;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Created by sun on 2017/12/26.
 */

public class JsActivity extends AppCompatActivity {
    WebView mWv;
    private String LOGTAG ="JsActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUI();
    }

    private void setupUI() {
        setContentView(R.layout.js_layout);
        mWv = findViewById(R.id.js_webview);
        WebSettings wSet = mWv.getSettings();
        wSet.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        wSet.setJavaScriptCanOpenWindowsAutomatically(true);
        mWv.addJavascriptInterface(new JsInteration(), "control");
        mWv.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                testMethod(mWv);
            }

        });
        mWv.loadUrl("file:///android_asset/index.html");


        // 由于设置了弹窗检验调用结果,所以需要支持js对话框
        // webview只是载体，内容的渲染需要使用webviewChromClient类去实现
        // 通过设置WebChromeClient对象处理JavaScript的对话框
        //设置响应js 的Alert()函数
        mWv.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                AlertDialog.Builder b = new AlertDialog.Builder(JsActivity.this);
                b.setTitle("Alert");
                b.setMessage(message);
                b.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });
                b.setCancelable(false);
                b.create().show();
                return true;
            }

        });


    }

    private void testMethod(WebView webView) {
        String call = "javascript:sayHello()";

//        String  content="content";

//        call = "javascript:alertMessage(\"" + content + "\")";
//
//        call = "javascript:toastMessage(\"" + "content" + "\")";

//        call = "javascript:sumToJava(1,2)";
        webView.loadUrl(call);

    }


    public class JsInteration {

        @JavascriptInterface
        public String toastMessage(final String message, final String f) {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            Log.d(LOGTAG,"toast");
            final int a=1;
            final int b=2;

//            mWv.loadUrl("javascript:say()");



            mWv.post(new Runnable() {
                public void run() {
                    Log.d(LOGTAG,"run start  "+ f);

//                    mWv.loadUrl("javascript:\"" + callback + "\"(\""+a+",\"\"" + b +"\" ) ");
                    mWv.loadUrl("javascript:"+f+"");

                    Log.d(LOGTAG,"run end ");
                }
            });
            return "a";
        }

        @JavascriptInterface
        public String onSumResult(int result) {
            Log.i(LOGTAG, "onSumResult result=" + result);
            return "b";
        }
    }




}
