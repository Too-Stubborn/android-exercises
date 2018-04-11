package com.xuhj.library.widget.statuslayout;

/**
 * 布局状态定义
 */
public interface IStatusLayout {

    void showContent();

    // -------------------- all status view ----------------------------------
    void showLoading();

    void showEmptyData();

    void showError();

    void showNetworkError();

    // -------------------- all status view with params ----------------------------------
    void showEmptyData(int icon, String msg);

    void showError(int icon, String msg);

    void showNetworkError(int icon, String msg);
}