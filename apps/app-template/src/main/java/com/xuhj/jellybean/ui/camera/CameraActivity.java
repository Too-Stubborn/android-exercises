package com.xuhj.jellybean.ui.camera;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Camera;
import android.os.Bundle;
import android.provider.Contacts;
import android.support.v7.app.AppCompatActivity;
import android.transition.Scene;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xuhj.jellybean.R;
import com.xuhj.jellybean.service.TestService;
import com.xuhj.jellybean.util.LogUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CameraActivity extends AppCompatActivity {

    @BindView(R.id.surface_view)
    SurfaceView surfaceView;
    @BindView(R.id.text_view)
    TextView textView;
    @BindView(R.id.open_camera)
    Button openCamera;
    @BindView(R.id.close_camera)
    Button closeCamera;
    @BindView(R.id.take_photo)
    Button takePhoto;
    private Scene mScene;

    private Camera mCamera = null;
    private SurfaceHolder mSurfaceHolder = null;
    private boolean isPreview = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ButterKnife.bind(this);

        initData();
        initView();

    }

    private void initData() {

    }

    private void initView() {
        mSurfaceHolder = surfaceView.getHolder();
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });

    }

    private void openCamera() {
        if (!isPreview) {
            mCamera = Camera.open();
        }
        if (mCamera != null && !isPreview) {
            try {
                mCamera.setPreviewDisplay(mSurfaceHolder);
                mCamera.setDisplayOrientation(90);
                mCamera.startPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
            isPreview = true;
        }
    }

    private void closeCamera() {
        if (mCamera != null && isPreview) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
            isPreview = false;
        }
    }

    private void startService() {
        Intent intent = new Intent(this, TestService.class);
        startService(intent);
    }

    private void sendBroadToService() {
    }

    private void writeFile(String fileName, String message) {
        try {

            FileOutputStream fos = openFileOutput(fileName, MODE_PRIVATE);

            byte[] bytes = message.getBytes();
            fos.write(bytes);
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getContact() {
        String[] columns = new String[]{Contacts.People._ID, Contacts.People.NAME};
        ContentResolver crv = getContentResolver();
        Cursor cursor = crv.query(Contacts.People.CONTENT_URI, columns, null, null, null);
        while (cursor.moveToNext()) {
            LogUtils.show(cursor.getString(cursor.getColumnIndex("name")));
        }
        cursor.close();
    }

    private String readFile(String fileName) {
        String result = "";
        try {
            FileInputStream fis = openFileInput(fileName);
            int length = fis.available();
            byte[] buffer = new byte[length];
            fis.read(buffer);
//            result = EncodingUtils.getString(buffer, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //处理扫描结果（在界面上显示）
        if (resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("result");
            Toast.makeText(this, scanResult, Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.take_photo, R.id.open_camera, R.id.close_camera})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.take_photo:
                break;
            case R.id.open_camera:
                openCamera();
                break;
            case R.id.close_camera:
                closeCamera();
                break;
        }
    }
}
