package com.company.base.utility.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.company.base.utility.R;

/**
 * 状态栏工具类
 *
 * @author xuhj
 */
public class StatusBarUtils {
    private static final String TAG = "StatusBarUtils";

    private static final int FAKE_STATUS_BAR_VIEW_ID = R.id.fake_status_bar_view;
    private static final int FAKE_TRANSLUCENT_VIEW_ID = R.id.fake_translucent_view;
    private static final int TAG_STATUS_BAR_OFFSET_ID = R.id.tag_status_bar_offset;
    private static final int TAG_NAVIGATION_BAR_OFFSET_ID = R.id.tag_navigation_bar_offset;

    public static final int DEFAULT_STATUS_BAR_ALPHA = 112;


    /**
     * 设置状态栏：颜色模式
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     * @param alpha    状态栏透明度
     */

    public static void setColor(Activity activity, @ColorInt int color, @IntRange(from = 0, to = 255) int alpha) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

            activity.getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

            activity.getWindow().setStatusBarColor(setColorAlpha(color, alpha));
            activity.getWindow().setNavigationBarColor(Color.BLACK);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            setFakeStatusBarView(activity, color, alpha);
            setRootViewFitSystemWindows(activity);
        }
    }

    /**
     * 设置沉浸式模式下的状态栏颜色，顶部会有一层半透明阴影
     *
     * @param activity
     * @param color
     * @param alpha
     */
    public static void setTranslucentColor(Activity activity, @ColorInt int color, @IntRange(from = 0, to = 255) int alpha) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            setFakeStatusBarView(activity, color, alpha);
            setRootViewFitSystemWindows(activity);
        }
    }


//    /**
//     * 为滑动返回界面设置状态栏颜色
//     *
//     * @param activity 需要设置的activity
//     * @param color    状态栏颜色值
//     */
//    public static void setColorForSwipeBack(Activity activity, int color) {
//        setColorForSwipeBack(activity, color, DEFAULT_STATUS_BAR_ALPHA);
//    }

//    /**
//     * 为滑动返回界面设置状态栏颜色
//     *
//     * @param activity       需要设置的activity
//     * @param color          状态栏颜色值
//     * @param statusBarAlpha 状态栏透明度
//     */
//    public static void setColorForSwipeBack(Activity activity, @ColorInt int color,
//                                            @IntRange(from = 0, to = 255) int statusBarAlpha) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//
//            ViewGroup contentView = ((ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT));
//            View rootView = contentView.getChildAt(0);
//            int statusBarHeight = getStatusBarHeight(activity);
//            if (rootView != null && rootView instanceof CoordinatorLayout) {
//                final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) rootView;
//                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//                    coordinatorLayout.setFitsSystemWindows(false);
//                    contentView.setBackgroundColor(setColorAlpha(color, statusBarAlpha));
//                    boolean isNeedRequestLayout = contentView.getPaddingTop() < statusBarHeight;
//                    if (isNeedRequestLayout) {
//                        contentView.setPadding(0, statusBarHeight, 0, 0);
//                        coordinatorLayout.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                coordinatorLayout.requestLayout();
//                            }
//                        });
//                    }
//                } else {
//                    coordinatorLayout.setStatusBarBackgroundColor(setColorAlpha(color, statusBarAlpha));
//                }
//            } else {
//                contentView.setPadding(0, statusBarHeight, 0, 0);
//                contentView.setBackgroundColor(setColorAlpha(color, statusBarAlpha));
//            }
//            setTransparentForWindow(activity);
//        }
//    }

    /**
     * 设置View偏移至状态栏以下
     *
     * @param activity       需要设置的activity
     * @param needOffsetView 需要向下偏移的 View
     */
    public static void setStatusBarOffsetView(Activity activity, ViewGroup needOffsetView) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        // 设置布局MarginTop属性，值为状态栏高度
        if (needOffsetView != null) {
            Object haveSetOffset = needOffsetView.getTag(TAG_STATUS_BAR_OFFSET_ID);
            if (haveSetOffset != null && (Boolean) haveSetOffset) {
                return;
            }
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) needOffsetView.getLayoutParams();
            layoutParams.setMargins(layoutParams.leftMargin,
                    layoutParams.topMargin + getStatusBarHeight(activity),
                    layoutParams.rightMargin,
                    layoutParams.bottomMargin);
            needOffsetView.setTag(TAG_STATUS_BAR_OFFSET_ID, true);
        }
    }

    /**
     * 设置View偏移至导航栏以上
     *
     * @param activity       需要设置的activity
     * @param needOffsetView 需要向上偏移的 View
     */
    public static void setNavigationBarOffsetView(Activity activity, ViewGroup needOffsetView) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return;
        }
        // 设置布局MarginTop属性，值为状态栏高度
        if (needOffsetView != null) {
            Object haveSetOffset = needOffsetView.getTag(TAG_NAVIGATION_BAR_OFFSET_ID);
            if (haveSetOffset != null && (Boolean) haveSetOffset) {
                return;
            }
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) needOffsetView.getLayoutParams();
            layoutParams.setMargins(layoutParams.leftMargin,
                    layoutParams.topMargin,
                    layoutParams.rightMargin,
                    layoutParams.bottomMargin + getNavigationBarHeight(activity));
            needOffsetView.setTag(TAG_NAVIGATION_BAR_OFFSET_ID, true);
        }
    }

    /**
     * 设置状态栏：沉浸式模式，4.4-5.0为透明渐变，5.0以上为半透明
     *
     * @param activity 需要设置的activity
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 设置状态栏全透明，即颜色模式下的全透明状态栏
     *
     * @param activity 需要设置的activity
     */
    public static void setTransparent(Activity activity) {
        setColor(activity, Color.BLACK, 0);
    }

//    /**
//     * 针对根布局是 CoordinatorLayout, 使状态栏半透明
//     * <p>
//     * 适用于图片作为背景的界面,此时需要图片填充到状态栏
//     *
//     * @param activity       需要设置的activity
//     * @param statusBarAlpha 状态栏透明度
//     */
//    public static void setTranslucentForCoordinatorLayout(Activity activity, @IntRange(from = 0, to = 255) int statusBarAlpha) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
//            return;
//        }
//        setTransparent(activity);
//        setFakeStatusBarView(activity, Color.BLACK, statusBarAlpha);
//    }


//    /**
//     * 为DrawerLayout 布局设置状态栏变色
//     *
//     * @param activity     需要设置的activity
//     * @param drawerLayout DrawerLayout
//     * @param color        状态栏颜色值
//     */
//    public static void setColorForDrawerLayout(Activity activity, DrawerLayout drawerLayout, @ColorInt int color) {
//        setColorForDrawerLayout(activity, drawerLayout, color, DEFAULT_STATUS_BAR_ALPHA);
//    }

//    /**
//     * 为DrawerLayout 布局设置状态栏颜色,纯色
//     *
//     * @param activity     需要设置的activity
//     * @param drawerLayout DrawerLayout
//     * @param color        状态栏颜色值
//     */
//    public static void setColorNoTranslucentForDrawerLayout(Activity activity, DrawerLayout drawerLayout, @ColorInt int color) {
//        setColorForDrawerLayout(activity, drawerLayout, color, 0);
//    }

//    /**
//     * 为DrawerLayout 布局设置状态栏变色
//     *
//     * @param activity       需要设置的activity
//     * @param drawerLayout   DrawerLayout
//     * @param color          状态栏颜色值
//     * @param statusBarAlpha 状态栏透明度
//     */
//    public static void setColorForDrawerLayout(Activity activity, DrawerLayout drawerLayout, @ColorInt int color,
//                                               @IntRange(from = 0, to = 255) int statusBarAlpha) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
//            return;
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
//        } else {
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
//        // 生成一个状态栏大小的矩形
//        // 添加 statusBarView 到布局中
//        ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
//        View fakeStatusBarView = contentLayout.findViewById(FAKE_STATUS_BAR_VIEW_ID);
//        if (fakeStatusBarView != null) {
//            if (fakeStatusBarView.getVisibility() == View.GONE) {
//                fakeStatusBarView.setVisibility(View.VISIBLE);
//            }
//            fakeStatusBarView.setBackgroundColor(color);
//        } else {
//            contentLayout.addView(createStatusBarView(activity, color), 0);
//        }
//        // 内容布局不是 LinearLayout 时,设置padding top
//        if (!(contentLayout instanceof LinearLayout) && contentLayout.getChildAt(1) != null) {
//            contentLayout.getChildAt(1)
//                    .setPadding(contentLayout.getPaddingLeft(), getStatusBarHeight(activity) + contentLayout.getPaddingTop(),
//                            contentLayout.getPaddingRight(), contentLayout.getPaddingBottom());
//        }
//        // 设置属性
//        setDrawerLayoutProperty(drawerLayout, contentLayout);
//        setFakeStatusBarView(activity, statusBarAlpha);
//    }

//    /**
//     * 设置 DrawerLayout 属性
//     *
//     * @param drawerLayout              DrawerLayout
//     * @param drawerLayoutContentLayout DrawerLayout 的内容布局
//     */
//    private static void setDrawerLayoutProperty(DrawerLayout drawerLayout, ViewGroup drawerLayoutContentLayout) {
//        ViewGroup drawer = (ViewGroup) drawerLayout.getChildAt(1);
//        drawerLayout.setFitsSystemWindows(false);
//        drawerLayoutContentLayout.setFitsSystemWindows(false);
//        drawerLayoutContentLayout.setClipToPadding(true);
//        drawer.setFitsSystemWindows(false);
//    }
//
//    /**
//     * 为DrawerLayout 布局设置状态栏变色(5.0以下无半透明效果,不建议使用)
//     *
//     * @param activity     需要设置的activity
//     * @param drawerLayout DrawerLayout
//     * @param color        状态栏颜色值
//     */
//    @Deprecated
//    public static void setColorForDrawerLayoutDiff(Activity activity, DrawerLayout drawerLayout, @ColorInt int color) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            // 生成一个状态栏大小的矩形
//            ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
//            View fakeStatusBarView = contentLayout.findViewById(FAKE_STATUS_BAR_VIEW_ID);
//            if (fakeStatusBarView != null) {
//                if (fakeStatusBarView.getVisibility() == View.GONE) {
//                    fakeStatusBarView.setVisibility(View.VISIBLE);
//                }
//                fakeStatusBarView.setBackgroundColor(setColorAlpha(color, DEFAULT_STATUS_BAR_ALPHA));
//            } else {
//                // 添加 statusBarView 到布局中
//                contentLayout.addView(createStatusBarView(activity, color), 0);
//            }
//            // 内容布局不是 LinearLayout 时,设置padding top
//            if (!(contentLayout instanceof LinearLayout) && contentLayout.getChildAt(1) != null) {
//                contentLayout.getChildAt(1).setPadding(0, getStatusBarHeight(activity), 0, 0);
//            }
//            // 设置属性
//            setDrawerLayoutProperty(drawerLayout, contentLayout);
//        }
//    }

//    /**
//     * 为 DrawerLayout 布局设置状态栏透明
//     *
//     * @param activity     需要设置的activity
//     * @param drawerLayout DrawerLayout
//     */
//    public static void setTranslucentForDrawerLayout(Activity activity, DrawerLayout drawerLayout) {
//        setTranslucentForDrawerLayout(activity, drawerLayout, DEFAULT_STATUS_BAR_ALPHA);
//    }

//    /**
//     * 为 DrawerLayout 布局设置状态栏透明
//     *
//     * @param activity     需要设置的activity
//     * @param drawerLayout DrawerLayout
//     */
//    public static void setTranslucentForDrawerLayout(Activity activity, DrawerLayout drawerLayout,
//                                                     @IntRange(from = 0, to = 255) int statusBarAlpha) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
//            return;
//        }
//        setTransparentForDrawerLayout(activity, drawerLayout);
//        setFakeStatusBarView(activity, statusBarAlpha);
//    }

//    /**
//     * 为 DrawerLayout 布局设置状态栏透明
//     *
//     * @param activity     需要设置的activity
//     * @param drawerLayout DrawerLayout
//     */
//    public static void setTransparentForDrawerLayout(Activity activity, DrawerLayout drawerLayout) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
//            return;
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
//        } else {
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
//
//        ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
//        // 内容布局不是 LinearLayout 时,设置padding top
//        if (!(contentLayout instanceof LinearLayout) && contentLayout.getChildAt(1) != null) {
//            contentLayout.getChildAt(1).setPadding(0, getStatusBarHeight(activity), 0, 0);
//        }
//
//        // 设置属性
//        setDrawerLayoutProperty(drawerLayout, contentLayout);
//    }

//    /**
//     * 为 DrawerLayout 布局设置状态栏透明(5.0以上半透明效果,不建议使用)
//     *
//     * @param activity     需要设置的activity
//     * @param drawerLayout DrawerLayout
//     */
//    @Deprecated
//    public static void setTranslucentForDrawerLayoutDiff(Activity activity, DrawerLayout drawerLayout) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            // 设置状态栏透明
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            // 设置内容布局属性
//            ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
//            contentLayout.setFitsSystemWindows(true);
//            contentLayout.setClipToPadding(true);
//            // 设置抽屉布局属性
//            ViewGroup vg = (ViewGroup) drawerLayout.getChildAt(1);
//            vg.setFitsSystemWindows(false);
//            // 设置 DrawerLayout 属性
//            drawerLayout.setFitsSystemWindows(false);
//        }
//    }

//    /**
//     * 为头部是 ImageView 的界面设置状态栏全透明
//     *
//     * @param activity       需要设置的activity
//     * @param needOffsetView 需要向下偏移的 View
//     */
//    public static void setTransparentForImageView(Activity activity, View needOffsetView) {
//        setTranslucentForImageView(activity, 0, needOffsetView);
//    }

//    /**
//     * 为头部是 ImageView 的界面设置状态栏透明(使用默认透明度)
//     *
//     * @param activity       需要设置的activity
//     * @param needOffsetView 需要向下偏移的 View
//     */
//    public static void setTranslucentForImageView(Activity activity, View needOffsetView) {
//        setTranslucentForImageView(activity, DEFAULT_STATUS_BAR_ALPHA, needOffsetView);
//    }

//    /**
//     * 为头部是 ImageView 的界面设置状态栏透明
//     *
//     * @param activity       需要设置的activity
//     * @param statusBarAlpha 状态栏透明度
//     * @param needOffsetView 需要向下偏移的 View
//     */
//    public static void setTranslucentForImageView(Activity activity, @IntRange(from = 0, to = 255) int statusBarAlpha,
//                                                  View needOffsetView) {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
//            return;
//        }
//        setTransparentForWindow(activity);
//        setFakeStatusBarView(activity, Color.BLACK, statusBarAlpha);
//        if (needOffsetView != null) {
//            Object haveSetOffset = needOffsetView.getTag(TAG_KEY_HAVE_SET_OFFSET);
//            if (haveSetOffset != null && (Boolean) haveSetOffset) {
//                return;
//            }
//            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) needOffsetView.getLayoutParams();
//            layoutParams.setMargins(layoutParams.leftMargin,
//                    layoutParams.topMargin + getStatusBarHeight(activity),
//                    layoutParams.rightMargin,
//                    layoutParams.bottomMargin);
//            needOffsetView.setTag(TAG_KEY_HAVE_SET_OFFSET, true);
//        }
//    }

//    /**
//     * 为 fragment 头部是 ImageView 的设置状态栏透明
//     *
//     * @param activity       fragment 对应的 activity
//     * @param needOffsetView 需要向下偏移的 View
//     */
//    public static void setTranslucentForImageViewInFragment(Activity activity, View needOffsetView) {
//        setTranslucentForImageViewInFragment(activity, DEFAULT_STATUS_BAR_ALPHA, needOffsetView);
//    }

//    /**
//     * 为 fragment 头部是 ImageView 的设置状态栏透明
//     *
//     * @param activity       fragment 对应的 activity
//     * @param needOffsetView 需要向下偏移的 View
//     */
//    public static void setTransparentForImageViewInFragment(Activity activity, View needOffsetView) {
//        setTranslucentForImageViewInFragment(activity, 0, needOffsetView);
//    }

//    /**
//     * 为 fragment 头部是 ImageView 的设置状态栏透明
//     *
//     * @param activity       fragment 对应的 activity
//     * @param statusBarAlpha 状态栏透明度
//     * @param needOffsetView 需要向下偏移的 View
//     */
//    public static void setTranslucentForImageViewInFragment(Activity activity, @IntRange(from = 0, to = 255) int statusBarAlpha,
//                                                            View needOffsetView) {
//        setTranslucentForImageView(activity, statusBarAlpha, needOffsetView);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
//                && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//            clearPreviousSettings(activity);
//        }
//    }


    // ---------------------------------------- 辅助方法 --------------------------------------

    /**
     * 清除样式
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static void clearAllFlag(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        } else {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 移除自定义的StatusBarView
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static void clearPreviousSettings(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            View fakeStatusBarView = decorView.findViewById(FAKE_STATUS_BAR_VIEW_ID);
            if (fakeStatusBarView != null) {
                decorView.removeView(fakeStatusBarView);
                ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT)).getChildAt(0);
                rootView.setPadding(0, 0, 0, 0);
            }
        }
    }

    /**
     * 设置自定义状态栏属性
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     * @param alpha    透明值
     */
    private static void setFakeStatusBarView(Activity activity, @ColorInt int color, @IntRange(from = 0, to = 255) int alpha) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        View fakeTranslucentView = decorView.findViewById(FAKE_STATUS_BAR_VIEW_ID);
        if (fakeTranslucentView != null) {
            if (fakeTranslucentView.getVisibility() == View.GONE) {
                fakeTranslucentView.setVisibility(View.VISIBLE);
            }
            fakeTranslucentView.setBackgroundColor(setColorAlpha(color, alpha));
        } else {
            decorView.addView(createStatusBarView(activity, color, alpha));
        }
    }

    /**
     * 隐藏伪状态栏 View
     *
     * @param activity 调用的 Activity
     */
    private static void hideFakeStatusBarView(Activity activity) {
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        View fakeStatusBarView = decorView.findViewById(FAKE_STATUS_BAR_VIEW_ID);
        if (fakeStatusBarView != null) {
            fakeStatusBarView.setVisibility(View.GONE);
        }
    }

    /**
     * 生成和状态栏大小一样的自定义伪状态栏
     *
     * @param activity 需要设置的activity
     * @param color    状态栏颜色值
     * @param alpha    透明值
     * @return 状态栏矩形条
     */
    private static View createStatusBarView(Activity activity, @ColorInt int color, @IntRange(from = 0, to = 255) int alpha) {
        // 绘制一个和状态栏一样高的矩形
        View statusBarView = new View(activity);
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(setColorAlpha(color, alpha));
        statusBarView.setId(FAKE_STATUS_BAR_VIEW_ID);
        return statusBarView;
    }

    /**
     * 设置根布局参数
     */
    private static void setRootViewFitSystemWindows(Activity activity) {
        ViewGroup parent = (ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT);
        for (int i = 0, count = parent.getChildCount(); i < count; i++) {
            View childView = parent.getChildAt(i);
            if (childView instanceof ViewGroup) {
                childView.setFitsSystemWindows(true);
                ((ViewGroup) childView).setClipToPadding(true);
            }
        }
    }

//    /**
//     * 设置透明
//     */
//    private static void setTransparentForWindow(Activity activity) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
//            activity.getWindow()
//                    .getDecorView()
//                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            activity.getWindow()
//                    .setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
//    }


    /**
     * 获取状态栏高度
     */
    private static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    /**
     * 获取导航栏高度
     */
    private static int getNavigationBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    /**
     * 计算状态栏颜色
     *
     * @param color color值
     * @param alpha alpha值
     * @return 最终的状态栏颜色
     */
    private static int setColorAlpha(@ColorInt int color, @IntRange(from = 0, to = 255) int alpha) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

}