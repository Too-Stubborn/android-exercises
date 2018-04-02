package com.xuhj.jellybean.view.webview;


import android.webkit.WebView;
import android.webkit.WebViewClient;

public class NativeWebViewClient extends WebViewClient {

    @Override
    public void onPageFinished(WebView view, String url) {
        view.getSettings().setBlockNetworkImage(false);
        super.onPageFinished(view, url);
    }
}
