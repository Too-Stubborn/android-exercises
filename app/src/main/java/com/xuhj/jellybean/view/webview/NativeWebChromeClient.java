package com.xuhj.jellybean.view.webview;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.xuhj.jellybean.util.CameraUtils;

import java.io.File;

public class NativeWebChromeClient extends WebChromeClient {

    private File mCameraFile;
    private ValueCallback<Uri> mUploadMessage;
    private Context mContext;
    private ProgressBar mProgressBar;

    public ValueCallback<Uri> getUploadMessage() {
        return mUploadMessage;
    }

    public void setUploadMessage(ValueCallback<Uri> mUploadMessage) {
        this.mUploadMessage = mUploadMessage;
    }

    public NativeWebChromeClient(Context context) {
        this.mContext = context;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if (mProgressBar != null) {
            if (newProgress == 100) {
                mProgressBar.setVisibility(View.GONE);
            } else {
                if (mProgressBar.getVisibility() == View.GONE)
                    mProgressBar.setVisibility(View.VISIBLE);
                mProgressBar.setProgress(newProgress);
            }
        }
    }

    // js上传文件的<input type="file" name="fileField" id="fileField" />事件捕获
    // Android > 4.1.1 调用这个方法
    public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                String acceptType, String capture) {
        mUploadMessage = uploadMsg;
        showDialog("操作");

    }

    // 3.0 + 调用这个方法
    public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                String acceptType) {
        mUploadMessage = uploadMsg;
        showDialog("操作");
    }

    // Android < 3.0 调用这个方法
    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        mUploadMessage = uploadMsg;
        showDialog("操作");
    }

    /**
     * 弹出对话框选择相册或者拍照
     *
     * @param title
     */
    private void showDialog(String title) {
        String[] items = new String[]{"拍照", "相册"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext).setTitle(title)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            mCameraFile = CameraUtils.openCamera((Activity) mContext, CameraUtils.REQUEST_CODE_CAMERA, CameraUtils.getPhotoFileName());
                        } else if (i == 1) {
                            CameraUtils.openAlbum((Activity) mContext, CameraUtils.REQUEST_CODE_ALBUM);
                        }
                    }
                });
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                mUploadMessage.onReceiveValue(null);
            }
        });
        dialog.show();
    }

    public ProgressBar getProgressBar() {
        return mProgressBar;
    }

    public void setProgressBar(ProgressBar progressBar) {
        this.mProgressBar = progressBar;
    }

    public File getCameraFile() {
        return mCameraFile;
    }

    public void setCameraFile(File mCaremaFile) {
        this.mCameraFile = mCaremaFile;
    }
}
