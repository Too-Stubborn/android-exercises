package com.xuhj.jellybean.view.webview;//package com.projectmanager.view.webview;

//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.View;
//import android.view.ViewGroup;
//import android.webkit.CookieSyncManager;
//import android.webkit.DownloadListener;
//import android.webkit.JavascriptInterface;
//import android.webkit.WebSettings;
//import android.widget.FrameLayout;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import com.example.taojinzi_seller.R;
//import com.taojinzi.seller.activity.base.BaseActivity;
//import com.taojinzi.seller.activity.order.ConfigOrderActivity;
//import com.taojinzi.seller.common.IntentConsts;
//import com.taojinzi.seller.model.OrderConfigQuickEntity;
//import com.taojinzi.seller.model.ShareEntity;
//import com.taojinzi.seller.util.CameraUtils;
//import com.taojinzi.seller.util.CommonUtils;
//import com.taojinzi.seller.util.DisplayUtil;
//import com.taojinzi.seller.util.UrlUtils;
//
//import java.io.File;
//
//import butterknife.ButterKnife;
//import butterknife.InjectView;
//import butterknife.OnClick;
//
///**
// * 网页浏览
// */
//public class NativeWebViewActivity extends BaseActivity {
//
//    @InjectView(R.id.title_center_text)
//    TextView title_center_text;
//    @InjectView(R.id.webview)
//    NativeWebView webview;
//    @InjectView(R.id.core_text)
//    TextView core_text;
//    @InjectView(R.id.share_tv)
//    TextView share_tv;
//    @InjectView(R.id.close_btn)
//    ImageView close_btn;
//    @InjectView(R.id.back_btn)
//    ImageView back_btn;
//
//
//    //图片裁剪宽度
//    private static int cropX = 400;
//    //图片裁剪高度
//    private static int cropY = 400;
//    //标题最长显示长度
//    private static final int MAX_LENGTH = 14;
//
//    private NativeWebViewClient mWebViewClient;
//    private NativeWebChromeClient mWebChromeClient;
//
//    //网址
//    private String mHomeUrl;
//    //标题
//    private String mTitleStr;
//    //选择相册或拍照后，保存的本地文件
//    private File mCameraFile;
//
//    //是否是html代码还是url
//    private boolean isHtml;
//    //是否可以关闭
//    private boolean hasClose;
//    //是否可以分享
//    private boolean hasShare;
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK) {
//            if (requestCode == CameraUtils.REQUEST_CODE_CAMERA) {//拍照
//                File temp = mWebChromeClient.getCameraFile();
//                mCameraFile = CameraUtils.cropPicture(this, CameraUtils.REQUEST_CODE_CROP, temp, CameraUtils.getPhotoFileName(),
//                        cropX, cropY);
//            } else if (requestCode == CameraUtils.REQUEST_CODE_ALBUM) {//相册
//                String picturePath = CameraUtils.getPhotoPathByLocalUri(this, data.getData());
//                File temp = new File(picturePath);
//                mCameraFile = CameraUtils.cropPicture(this, CameraUtils.REQUEST_CODE_CROP, temp, CameraUtils.getPhotoFileName(),
//                        cropX, cropY);
//            } else if (requestCode == CameraUtils.REQUEST_CODE_CROP) {//裁剪
//                mWebChromeClient.getUploadMessage().onReceiveValue(Uri.fromFile(mCameraFile));
//            } else {
//                mWebChromeClient.getUploadMessage().onReceiveValue(null);
//            }
//
//        } else {
//            mWebChromeClient.getUploadMessage().onReceiveValue(null);
//        }
//    }
//
//    @Override
//    public void create(Bundle savedInstanceState) {
//        setContentView(R.layout.activity_webview_native);
//        ButterKnife.inject(this);
//
//        mTitleStr = this.getIntent().getStringExtra(IntentConsts.EXTRA_TITLE);
//        mHomeUrl = this.getIntent().getStringExtra(IntentConsts.EXTRA_WEBVIEW_URL);
//        isHtml = this.getIntent().getBooleanExtra(IntentConsts.EXTRA_WEBVIEW_FLAG_HTML, false);
//        hasClose = this.getIntent().getBooleanExtra(IntentConsts.EXTRA_WEBVIEW_FLAG_CLOSE, true);
//        hasShare = this.getIntent().getBooleanExtra(IntentConsts.EXTRA_WEBVIEW_FLAG_SHARE, true);
//
//    }
//
//
//    @Override
//    public void init() {
//        initWebView();
//
//        //是否可以关闭
//        back_btn.setVisibility(hasClose ? View.GONE : View.VISIBLE);
//        //是否可以关闭
//        close_btn.setVisibility(hasClose ? View.VISIBLE : View.GONE);
//        //是否可以分享
//        share_tv.setVisibility(hasShare ? View.VISIBLE : View.GONE);
//        //设置标题
//        title_center_text.setText(mTitleStr);
//
//        if (isHtml) {
//            //加载html代码
//            webview.loadData(mHomeUrl, "text/html;charset=utf-8", null);
//        } else {
//            //解析网址
//            mHomeUrl = UrlUtils.resolvValidUrl(mHomeUrl);
//            //加载网址
//            if (mHomeUrl != null)
//                webview.loadUrl(mHomeUrl);
//        }
//    }
//
//    private void initWebView() {
//
//        // 设置Client
//        mWebViewClient = new NativeWebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(android.webkit.WebView view, String url) {
//                view.loadUrl(url);
//                return true;
//            }
//        };
//        webview.setWebViewClient(mWebViewClient);
//
//        // 设置WebChromeClient
//        mWebChromeClient = new NativeWebChromeClient(this) {
//            @Override
//            public void onReceivedTitle(android.webkit.WebView view, String title) {
//                if (!hasClose)
//                    return;
//
////                if (!webview.getUrl().equalsIgnoreCase(mHomeUrl)) {
////                    if (title != null && title.length() > MAX_LENGTH)
////                        NativeWebViewActivity.this.title_center_text.setText(title.subSequence(0, MAX_LENGTH) + "...");
////                    else
////                        NativeWebViewActivity.this.title_center_text.setText(title);
////                } else {
////                    NativeWebViewActivity.this.title_center_text.setText("");
////                }
//            }
//        };
//
//        //添加顶部加载进度条
//        if (hasClose) {
//            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(5));
//            ProgressBar progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
//            progressBar.setLayoutParams(params);
//            webview.addView(progressBar);
//            webview.setProgressBar(progressBar);
//            mWebChromeClient.setProgressBar(progressBar);
//        }
//        webview.setWebChromeClient(mWebChromeClient);
//
//        //下载监听
//        webview.setDownloadListener(new DownloadListener() {
//
//            @Override
//            public void onDownloadStart(String arg0, String arg1, String arg2,
//                                        String arg3, long arg4) {
//            }
//        });
//
//        WebSettings webSettings = webview.getSettings();
//        //先加载文字后显示图片
//        webSettings.setBlockNetworkImage(true);
//        webSettings.setAllowFileAccess(true);
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        webSettings.setUseWideViewPort(true);
//        webSettings.setLoadWithOverviewMode(true);
//        webSettings.setSupportZoom(true);
//        webSettings.setBuiltInZoomControls(true);
//        webSettings.setDisplayZoomControls(false);
//        webSettings.setSupportMultipleWindows(false);
//        webSettings.setAppCacheEnabled(true);
//        webSettings.setDatabaseEnabled(true);
//        webSettings.setDomStorageEnabled(true);
//        webSettings.setGeolocationEnabled(true);
//        webSettings.setAppCacheMaxSize(Long.MAX_VALUE);
//        webSettings.setAppCachePath(this.getDir("appcache", 0).getPath());
//        webSettings.setDatabasePath(this.getDir("databases", 0).getPath());
//        webSettings.setGeolocationDatabasePath(this.getDir("geolocation", 0).getPath());
//        webSettings.setPluginState(WebSettings.PluginState.ON);
//        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
//        //js相关
//        webSettings.setSaveFormData(false);
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        //设置js监听
//        webview.addJavascriptInterface(new CommonScriptInterface(), "taojinzi");
//
//        CookieSyncManager.createInstance(this);
//        CookieSyncManager.getInstance().sync();
//    }
//
//
//    private int count = 0;
//
//    class CommonScriptInterface {
//        CommonScriptInterface() {
//        }
//
//        @JavascriptInterface
//        public void register(String str) {
//            if (count > 0) {
//                return;
//            }
//            count++;
//            String[] array = str.split("-");
//            OrderConfigQuickEntity mOrderData = new OrderConfigQuickEntity();
//            mOrderData.money = Float.valueOf(array[0]);
//            mOrderData.productId = array[1];
//            mOrderData.skuValuesText = array[2];
//            mOrderData.productNumber = Integer.valueOf(array[3]);
//            mOrderData.activityType = Integer.valueOf(array[4]);
//            mOrderData.activityId = Integer.valueOf(array[5]);
//
//            Intent intent = new Intent(NativeWebViewActivity.this, ConfigOrderActivity.class);
//            Bundle bundle = new Bundle();
//            bundle.putParcelable(IntentConsts.EXTRA_ORDER_OPERATE_PARAMS, mOrderData);
//            bundle.putInt(IntentConsts.EXTRA_ORDER_OPERATE_CODE, 2);
//            bundle.putInt(IntentConsts.EXTRA_ORDER_GIGT, 1);
//            intent.putExtras(bundle);
//            startActivity(intent);
//            count = 0;
//
//        }
//
//    }
//
//    /**
//     * 点击事件
//     *
//     * @param v
//     */
//    @OnClick({R.id.back_btn, R.id.close_btn, R.id.share_tv, R.id.go_back, R.id.go_forward, R.id.np_refresh_nor})
//    void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.back_btn:
//                onBackPressed();
//                break;
//            case R.id.close_btn:
//                finish();
//                break;
//            case R.id.share_tv:
//                ShareEntity shareEntity = new ShareEntity();
//                shareEntity.title = mTitleStr;
//                shareEntity.content = mTitleStr;
//                shareEntity.url = mHomeUrl;
//                shareEntity.shareType = 1;
//                CommonUtils.gotoShareActivity(this, shareEntity, -1);
//                break;
//            case R.id.go_back:
//                if (webview != null && webview.canGoBack())
//                    webview.goBack();
//                break;
//            case R.id.go_forward:
//                if (webview != null && webview.canGoForward())
//                    webview.goForward();
//                break;
//            case R.id.home_btn:
//                webview.loadUrl(mHomeUrl);
//                break;
//            case R.id.np_refresh_nor:
//                webview.loadUrl(mHomeUrl);
//                break;
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        if (webview != null)
//            webview.destroy();
//        super.onDestroy();
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (webview != null && webview.canGoBack())
//            webview.goBack();
//        else
//            super.onBackPressed();
//    }
//
//}
