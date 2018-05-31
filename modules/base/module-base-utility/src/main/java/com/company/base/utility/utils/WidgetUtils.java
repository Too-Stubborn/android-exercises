package com.company.base.utility.utils;

import android.text.InputFilter;
import android.text.InputType;
import android.widget.EditText;

/**
 * 控件工具类
 *
 * @author xuhj
 */
public class WidgetUtils {

    /**
     * 限制文本输入长度
     */
    public static void setInputLengthFilter(EditText editText, int length) {
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
    }

    /**
     * 显示输入框密码
     */
    public static void showPassword(EditText editText) {
        editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }

    /**
     * 隐藏输入框密码
     */
    public static void hidePassword(EditText editText) {
        editText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
    }
}
