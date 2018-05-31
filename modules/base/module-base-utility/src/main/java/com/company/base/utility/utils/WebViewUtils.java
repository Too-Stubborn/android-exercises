package com.company.base.utility.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Picture;
import android.os.Build;
import android.webkit.WebView;

/**
 * WebView工具类
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2017/11/17
 */
public class WebViewUtils {

    /**
     * WebView快照截图
     *
     * @param webView webview
     * @return bitmap
     */
    public static Bitmap captureWebView(WebView webView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return captureWebViewLollipop(webView);
        } else {
            return captureWebViewKitKat(webView);
        }
    }

    /**
     * WebView快照截图，< API21
     *
     * @param webView webview
     * @return bitmap
     */
    private static Bitmap captureWebViewKitKat(WebView webView) {
        Picture picture = webView.capturePicture();
        int width = picture.getWidth();
        int height = picture.getHeight();
        if (width > 0 && height > 0) {
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            Canvas canvas = new Canvas(bitmap);
            picture.draw(canvas);
            return bitmap;
        }
        return null;
    }

    /**
     * WebView快照截图，>= API21
     *
     * @param webView webview
     * @return bitmap
     */
    private static Bitmap captureWebViewLollipop(WebView webView) {
        float scale = webView.getScale();
        int width = webView.getWidth();
        int height = (int) (webView.getContentHeight() * scale + 0.5);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        webView.draw(canvas);
        return bitmap;
    }

}
