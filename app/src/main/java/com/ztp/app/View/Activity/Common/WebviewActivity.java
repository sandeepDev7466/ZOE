package com.ztp.app.View.Activity.Common;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.R;

public class WebviewActivity extends AppCompatActivity {

    WebView webView;
    MyProgressDialog progressDialog;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        webView = findViewById(R.id.webView);
        progressDialog = new MyProgressDialog(this);

        if (getIntent() != null) {
            String ext = getIntent().getStringExtra("url").substring(getIntent().getStringExtra("url").lastIndexOf('.') + 1);
            if (ext.equalsIgnoreCase("pdf") || ext.equalsIgnoreCase("doc") || ext.equalsIgnoreCase("docx") || ext.equalsIgnoreCase("pptx") || ext.equalsIgnoreCase("ppt")) {
                //webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + getIntent().getStringExtra("url"));
                startWebView("http://docs.google.com/gview?embedded=true&url=" + getIntent().getStringExtra("url"));
            } else {
                //webView.loadUrl(getIntent().getStringExtra("url"));
                startWebView(getIntent().getStringExtra("url"));
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void startWebView(String url) {

        WebSettings settings = webView.getSettings();

        settings.setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);

        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);

        progressDialog.show(getString(R.string.please_wait));

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(WebviewActivity.this, "Error:" + description, Toast.LENGTH_SHORT).show();

            }
        });
        webView.loadUrl(url);
    }

}
