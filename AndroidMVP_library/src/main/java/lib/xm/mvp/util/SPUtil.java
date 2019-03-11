package lib.xm.mvp.util;

import android.app.Activity;
import android.content.SharedPreferences;

import lib.xm.mvp.base.AbstractApplication;
import lib.xm.mvp.util.log.LogUtils;


/**
 *
 * @author XMclassmate
 * @date 2016/9/2
 */
public class SPUtil {

    private static SharedPreferences sp = null;

    private static void getInstance(){
        if (sp == null){
            sp = AbstractApplication.getAppContext().getSharedPreferences(AbstractApplication.getAppContext().getPackageName().substring(AbstractApplication.getAppContext().getPackageName().lastIndexOf(".")), Activity.MODE_PRIVATE);
        }
    }

    /**
     * 清空sp所有数据
     */
    public static void clearSP() {
        getInstance();
        sp.edit().clear().apply();
        LogUtils.e("清空SharedPreferences数据成功!");
    }

    /**
     * 保存boolean信息
     */
    public static void putBoolean(String key, boolean value) {
        getInstance();
        sp.edit().putBoolean(key, value).apply();
    }

    /**
     * 获取boolean信息
     */
    public static boolean getBoolean(String key, boolean defValue) {
        getInstance();
        return sp.getBoolean(key, defValue);
    }

    /**
     * 保存String信息
     */
    public static void putString(String key, String value) {
        getInstance();
        sp.edit().putString(key, value).apply();
    }

    /**
     * 获取String信息
     */
    public static String getString(String key, String defValue) {
        getInstance();
        return sp.getString(key, defValue);
    }

    /**
     * 保存int信息
     */
    public static void putInt(String key, int value) {
        getInstance();
        sp.edit().putInt(key, value).apply();
    }

    /**
     * 获取int信息
     */
    public static int getInt(String key, int defValue) {
        getInstance();
        return sp.getInt(key, defValue);
    }

    /**
     * 保存long信息
     */
    public static void putLong(String key, long value) {
        getInstance();
        sp.edit().putLong(key, value).apply();
    }

    /**
     * 获取long信息
     */
    public static long getLong(String key, long defValue) {
        getInstance();
        return sp.getLong(key, defValue);
    }
}
