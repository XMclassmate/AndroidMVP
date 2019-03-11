package lib.xm.mvp.util;

import android.widget.Toast;

import com.xm.mvp.BuildConfig;

import lib.xm.mvp.base.AbstractApplication;


/**
 * toast工具类
 */

public class ToastUtil {

    /**
     * Toast
     */
    public static void toast(String content) {
        Toast.makeText(AbstractApplication.getAppContext(), content, Toast.LENGTH_SHORT).show();
    }

    /**
     * Toast
     */
    public static void toastLong(String content) {
        Toast.makeText(AbstractApplication.getAppContext(), content, Toast.LENGTH_LONG).show();
    }

    /**
     * Toast
     * - 测试的时候打的log，等到上线的时候就会关掉
     */
    public static void toastTest(String content) {
        if (BuildConfig.DEBUG) {
            Toast.makeText(AbstractApplication.getAppContext(), "Test：" + content, Toast.LENGTH_SHORT).show();
        }
    }
}
