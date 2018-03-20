package com.xuhj.jellybean.view.webview;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.widget.ProgressBar;

/**
 * 系统内核webview
 */
public class NativeWebView extends WebView {

    private Context mContext;
    private ProgressBar mProgressBar;

    public NativeWebView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public NativeWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        init();
    }

    private void init() {

//        WebSettings webSettings = this.getSettings();
//        //先加载文字后显示图片
//        webSettings.setBlockNetworkImage(true);
//        webSettings.setAllowFileAccess(true);
//        //缩放设置
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        webSettings.setUseWideViewPort(true);
//        webSettings.setLoadWithOverviewMode(true);
//        webSettings.setSupportZoom(true);
//        webSettings.setBuiltInZoomControls(true);
//        webSettings.setDisplayZoomControls(false);
//        webSettings.setSupportMultipleWindows(false);
//        //缓存路径
//        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
//        webSettings.setAppCacheMaxSize(Long.MAX_VALUE);
//        webSettings.setDomStorageEnabled(true);
//        webSettings.setAppCacheEnabled(true);
//        webSettings.setDatabaseEnabled(true);
//        webSettings.setGeolocationEnabled(true);
//        webSettings.setAppCachePath(getContext().getDir("appcache", 0).getPath());
//        webSettings.setDatabasePath(getContext().getDir("databases", 0).getPath());
//        webSettings.setGeolocationDatabasePath(getContext().getDir("geolocation", 0).getPath());
//        //插件设置
//        webSettings.setPluginState(WebSettings.PluginState.ON);
//        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
//        // js相关
//        webSettings.setSaveFormData(false);
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//
//        CookieSyncManager.createInstance(getContext());
//        CookieSyncManager.getInstance().sync();

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (mProgressBar != null) {
            LayoutParams lp = (LayoutParams) mProgressBar.getLayoutParams();
            lp.x = l;
            lp.y = t;
            mProgressBar.setLayoutParams(lp);
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    public ProgressBar getProgressBar() {
        return mProgressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.mProgressBar = progressBar;
    }
}
