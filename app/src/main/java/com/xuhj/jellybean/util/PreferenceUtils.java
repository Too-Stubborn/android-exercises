package com.xuhj.jellybean.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.xuhj.jellybean.JBApplication;
import com.xuhj.jellybean.common.Settings;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * SharedPreference工具类
 */
public class PreferenceUtils {

    public final static String PACKAGE_NAME = Settings.PACKAGE_NAME;
    private static Gson mGson = new Gson();

    //SharedPreferences名字
    public final static String PREFS_NAME = "PrefsFile";
    public final static String PK_VERSION_CODE = PACKAGE_NAME + ".PK_VERSION_CODE";
    public final static String PK_GUIDE_INITED = PACKAGE_NAME + ".PK_GUIDE_INITED";

    public static SharedPreferences getSharedPrefs() {
        return JBApplication.getInstance().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static Editor getSharedEditor() {
        return JBApplication.getInstance().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
    }

    public static boolean putString(final String key, final String value) {
        return getSharedPrefs().edit().putString(key, value).commit();
    }

    public static String getString(String key, final String defaultValue) {
        return getSharedPrefs().getString(key, defaultValue);
    }

    public static boolean putBoolean(final String key, final boolean value) {
        return getSharedPrefs().edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(final String key, final boolean defaultValue) {
        return getSharedPrefs().getBoolean(key, defaultValue);
    }

    public static boolean putInt(final String key, final int value) {
        return getSharedPrefs().edit().putInt(key, value).commit();
    }

    public static int getInt(final String key, final int defaultValue) {
        return getSharedPrefs().getInt(key, defaultValue);
    }

    public static boolean putFloat(final String key, final float value) {
        return getSharedPrefs().edit().putFloat(key, value).commit();
    }

    public static float getFloat(final String key, final float defaultValue) {
        return getSharedPrefs().getFloat(key, defaultValue);
    }

    public static boolean putLong(final String key, final long value) {
        return getSharedPrefs().edit().putLong(key, value).commit();
    }

    public static long getLong(final String key, final long defaultValue) {
        return getSharedPrefs().getLong(key, defaultValue);
    }

    //------------------------------- Object处理 ------------------------------------
    public static boolean putObject(String key, Object obj) {
        return putObject(key, obj, null);
    }

    /**
     * 用于泛型转换
     */
    public static boolean putObject(String key, Object obj, Type type) {
        if (obj == null)
            return false;
        try {
            if (mGson == null)
                mGson = new Gson();
            String userJson = null;
            if (type == null)
                userJson = mGson.toJson(obj);
            else
                userJson = mGson.toJson(obj, type);
            return PreferenceUtils.putString(key, userJson);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static <T extends Object> T getObject(String key, Class<T> clazz) {
        Object object = null;
        String userJson = PreferenceUtils.getString(key, null);
        if (mGson == null)
            mGson = new Gson();
        try {
            object = mGson.fromJson(userJson, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return (T) object;
        }
    }

    /**
     * 用于泛型转换
     */
    public static <T extends Object> T getObject(String key, Type type) {
        Object object = null;
        String userJson = PreferenceUtils.getString(key, null);
        if (mGson == null)
            mGson = new Gson();
        try {
            object = mGson.fromJson(userJson, type);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return (T) object;
        }
    }

    //------------------------- 存储list数据管理 --------------------------
    public static <T> boolean putList(String key, List<T> list) {
        String toJson = new Gson().toJson(list);
        return putString(key, toJson);
    }

    public static <T> boolean putList(String key, List<T> list, Type type) {
        String toJson = new Gson().toJson(list, type);
        return putString(key, toJson);
    }

    public static <T> List<T> getList(String key, Class<T> clazz) {
        String fromJson = getSharedPrefs().getString(key, null);
        List<T> list = new ArrayList<>();
        if (fromJson == null)
            return list;
        JsonArray array = new JsonParser().parse(fromJson).getAsJsonArray();
        if (mGson == null)
            mGson = new Gson();
        for (final JsonElement elem : array) {
            list.add(mGson.fromJson(elem, clazz));
        }
        return list;
    }

    /**
     * 用于泛型转换
     */
    public static <T> List<T> getList(String key, Type type) {
        String fromJson = getSharedPrefs().getString(key, null);
        List<T> list = new ArrayList<>();
        if (fromJson == null)
            return list;
        if (mGson == null)
            mGson = new Gson();
        list = mGson.fromJson(fromJson, type);
        return list;
    }

    public static boolean remove(String key) {
        return getSharedEditor().remove(key).commit();
    }

}
