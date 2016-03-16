package com.example.administrator.endaily;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.yyj.ui.ChangeTheme;

/**
 * Created by 草帽儿 on 2016/2/18.
 */
public class WebViewActivity extends Activity{
    private WebView webView;
    private TextView titleTV;
    private ImageView backIv,reloadIv;
    private static ChangeTheme changeTheme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        changeTheme = new ChangeTheme(this);
        changeTheme.initTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_webview);
        Intent intent =getIntent();
        final String url = intent.getStringExtra("url");
        String title = intent.getStringExtra("title");
        if (url!=null) {
            webView= (WebView) findViewById(R.id.webview);
            titleTV= (TextView) findViewById(R.id.webview_title);

            backIv= (ImageView) findViewById(R.id.webview_backIv);
            reloadIv= (ImageView) findViewById(R.id.webview_reload);
            backIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (webView!=null&&webView.canGoBack()) {
                        webView.goBack();
                        return;
                    }
                    finish();
                }
            });
            reloadIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (webView!=null&&url!=null) {
                        webView.reload();
                    }
                }
            });
            titleTV.setText(title);
            webView.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
            WebSettings webSettings = webView.getSettings();
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//不使用缓存
            webSettings.setAllowFileAccess(true);// 若html是一个文件框的话,就可以浏览本地文件
            webSettings.setBuiltInZoomControls(true);
            webSettings.setDatabaseEnabled(true);
            webSettings.setSupportZoom(true); //支持缩放
            //WebView双击变大，再双击后变小，当手动放大后，双击可以恢复到原始大小
            webSettings.setUseWideViewPort(true);//
            webSettings.setDefaultZoom(WebSettings.ZoomDensity.FAR);
            if (webView!=null&&url!=null) {
                webView.loadUrl(url);
            }

        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK&&webView!=null&&
                webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        finish();
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        changeTheme.clearTheme();
        super.onDestroy();
    }

}