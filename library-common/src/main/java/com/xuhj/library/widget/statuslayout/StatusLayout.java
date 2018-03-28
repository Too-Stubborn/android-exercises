package com.xuhj.library.widget.statuslayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xuhj.library.R;
import com.xuhj.library.R2;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 多状态布局
 *
 * @author xuhj
 */
public class StatusLayout extends FrameLayout implements IStatusLayout {
    private static final String TAG = "StatusLayout";

    public static final int STATUS_CONTENT = 0x00;
    public static final int STATUS_LOADING = 0x01;
    public static final int STATUS_EMPTY_DATA = 0x02;
    public static final int STATUS_ERROR = 0x03;
    public static final int STATUS_NETWORK_ERROR = 0x04;

    @BindView(R2.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R2.id.tv_loading_msg)
    TextView tvLoadingMsg;
    @BindView(R2.id.layout_status_loading)
    LinearLayout layoutStatusLoading;
    @BindView(R2.id.iv_empty_data)
    ImageView ivEmptyData;
    @BindView(R2.id.tv_empty_data_msg)
    TextView tvEmptyDataMsg;
    @BindView(R2.id.layout_status_empty_data)
    LinearLayout layoutStatusEmptyData;
    @BindView(R2.id.iv_error)
    ImageView ivError;
    @BindView(R2.id.tv_error_msg)
    TextView tvErrorMsg;
    @BindView(R2.id.layout_status_error)
    LinearLayout layoutStatusError;
    @BindView(R2.id.iv_network_error)
    ImageView ivNetworkError;
    @BindView(R2.id.tv_network_error_msg)
    TextView tvNetworkErrorMsg;
    @BindView(R2.id.layout_status_network_error)
    LinearLayout layoutStatusNetworkError;

    // 当前View显示状态
    private int mViewStatus = STATUS_CONTENT;
    private LayoutInflater mInflater;

    public StatusLayout(Context context) {
        this(context, null);
    }

    public StatusLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle) {
        mInflater = LayoutInflater.from(getContext());
        mInflater.inflate(R.layout.layout_status, this);
        ButterKnife.bind(this);

        // Load attributes
//        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.StatusLayout, defStyle, 0);
//        a.recycle();

        invalidateView();
    }

    /**
     * 重绘布局状态
     */
    private void invalidateView() {
        layoutStatusLoading.setVisibility(View.GONE);
        layoutStatusEmptyData.setVisibility(View.GONE);
        layoutStatusError.setVisibility(View.GONE);
        layoutStatusNetworkError.setVisibility(View.GONE);

        switch (mViewStatus) {
            case STATUS_LOADING:
                layoutStatusLoading.bringToFront();
                layoutStatusLoading.setVisibility(View.VISIBLE);
                break;
            case STATUS_EMPTY_DATA:
                layoutStatusEmptyData.bringToFront();
                layoutStatusEmptyData.setVisibility(View.VISIBLE);
                break;
            case STATUS_ERROR:
                layoutStatusError.bringToFront();
                layoutStatusError.setVisibility(View.VISIBLE);
                break;
            case STATUS_NETWORK_ERROR:
                layoutStatusNetworkError.bringToFront();
                layoutStatusNetworkError.setVisibility(View.VISIBLE);
                break;
            case STATUS_CONTENT:
                break;
            default:
                break;
        }
    }

    //------------------------- 提供外部调用方法 ------------------------------------

    /**
     * 显示正常页面
     */
    @Override
    public void showContent() {
        mViewStatus = STATUS_CONTENT;
        invalidateView();
    }

    /**
     * 显示加载进度条页面
     */
    @Override
    public void showLoading() {
        mViewStatus = STATUS_LOADING;
        invalidateView();
    }

    /**
     * 显示无数据页面
     */
    @Override
    public void showEmptyData() {
        mViewStatus = STATUS_EMPTY_DATA;
        invalidateView();
    }

    /**
     * 显示异常页面
     */
    @Override
    public void showError() {
        mViewStatus = STATUS_ERROR;
        invalidateView();
    }

    /**
     * 显示网络异常页面
     */
    @Override
    public void showNetworkError() {
        mViewStatus = STATUS_NETWORK_ERROR;
        invalidateView();
    }

    /**
     * 显示无数据页面
     */
    @Override
    public void showEmptyData(int icon, String msg) {
        mViewStatus = STATUS_EMPTY_DATA;
        ivEmptyData.setImageResource(icon);
        tvEmptyDataMsg.setText(msg);
        invalidateView();
    }

    /**
     * 显示异常页面
     */
    @Override
    public void showError(int icon, String msg) {
        mViewStatus = STATUS_ERROR;
        ivError.setImageResource(icon);
        tvErrorMsg.setText(msg);
        invalidateView();
    }

    /**
     * 显示网络异常页面
     */
    @Override
    public void showNetworkError(int icon, String msg) {
        mViewStatus = STATUS_NETWORK_ERROR;
        ivNetworkError.setImageResource(icon);
        tvNetworkErrorMsg.setText(msg);
        invalidateView();
    }

    @OnClick({R2.id.layout_status_network_error})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R2.id.layout_status_network_error:
                if (mOnEventListener != null) {
                    mOnEventListener.onRetry();
                }
                break;
        }
    }

    private OnEventListener mOnEventListener;

    public void setOnEventListener(OnEventListener mOnEventListener) {
        this.mOnEventListener = mOnEventListener;
    }


    public interface OnEventListener {
        void onRetry();
    }
}
