package lib.xm.mvp.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import lib.xm.mvp.base.AbstractApplication;
import lib.xm.mvp.enumtype.NetState;
import lib.xm.mvp.util.log.LogUtils;


/**
 *
 * 公共的工具类
 */
public class CommonUtil {

    /* ===================================== 获取资源 ========================================= */
    /**
     * 获取颜色
     * - 最新的API：getColor需要提供Theme不方便，低版本的已经过时，
     * 所以这里直接使用兼容的获取颜色的方法。
     */
    public static int getColor(int colorId) {
        return ContextCompat.getColor(AbstractApplication.getAppContext(), colorId);
    }

    /**
     * 获取字符串
     *
     * @param stringId id
     * @param args     参数
     */
    public static String getString(int stringId, Object... args) {
        return String.format(AbstractApplication.getAppContext().getString(stringId), args);
    }

    /**
     * 获取资源
     *
     * @param id 资源id
     */
    public static Drawable getDrawable(int id) {
        return ContextCompat.getDrawable(AbstractApplication.getAppContext(), id);
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /* ===================================== 字体 ========================================= */

    public static final String FONT_HELVETICA_NEUELT_PRO = "fonts/HelveticaNeueLTPro-Lt.otf";

    /**
     * 设置字体
     * - 给TextView设置字体，调用的还是其自带的setTypeface()方法，来给TextView设置字体，
     * 其中，字体文件放在asset目录下。
     *
     * @param context  上下文
     * @param root     需要设置字体的View
     * @param fontName 字体名称
     */
    public static void setTypeFace(final Context context, final View root, final String fontName) {

        try {
            if (root instanceof TextView) {
                // 给单个的TextView设置字体
                ((TextView) root).setTypeface(Typeface.createFromAsset(context.getAssets(), fontName));
            } else if (root instanceof ViewGroup) {
                // 给整个ViewGroup里的所有TextView设置字体
                ViewGroup viewGroup = (ViewGroup) root;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    setTypeFace(context, viewGroup.getChildAt(i), fontName);
                }
            }
        } catch (Exception e) {
            LogUtils.e(String.format("给View %s 设置字体 %s 的时候出错!", root, fontName));
            e.printStackTrace();
        }
    }

    /* ===================================== Window信息 ========================================= */

    /**
     * 获取状态栏高度
     */
    public static int getStateBarHeight() {

        int height = 0;
        int resourceId = AbstractApplication.getAppContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = AbstractApplication.getAppContext().getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }

    private static int[] windowInfo = new int[2];

    /**
     * 获取屏幕的信息
     * - 如果使用了AutoLayout，可以使用他的帮助类AutoUtil.java，获取百分比高度和宽度，更加方便
     *
     * @return int[] windowInfo: 0-宽度 1-高度
     */
    public static int[] getWindowWidth(Activity activity) {
        if (windowInfo[0] == 0) {
            WindowManager wm = activity.getWindowManager();
            Display defaultDisplay = wm.getDefaultDisplay();
            DisplayMetrics outMetrics = new DisplayMetrics();
            defaultDisplay.getMetrics(outMetrics);
            windowInfo[0] = outMetrics.widthPixels;
            windowInfo[1] = outMetrics.heightPixels;
        }
        return windowInfo;
    }

    /**
     * 打印Window信息
     *
     * @param activity
     */
    public static void printWindowInfo(Activity activity) {

        WindowManager wm = activity.getWindowManager();
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            LogUtils.e("屏幕信息："
                    + "\nheightPixels:" + metrics.heightPixels + ", widthPixels:" + metrics.widthPixels
                    + "\ndensity:" + metrics.density + ", densityDpi:" + metrics.densityDpi + ", scaledDensity:" + metrics.scaledDensity
                    + "\nxdpi:" + metrics.xdpi + "ydpi:" + metrics.ydpi
            );
        }
    }

    /**
     * Action是否允许
     */
    public static boolean isActionAvailable(String action) {
        Intent intent = new Intent(action);
        return AbstractApplication.getAppContext().getPackageManager().resolveActivity(intent, 0) != null;
    }

    /* ===================================== Wind ========================================= */
    /**
     * 赋值时处理空值
     *
     * @param tv        --TextView控件
     * @param content   --填入内容，将进行非空判断
     * @param strReturn --填入内容为空时你想显示的内容
     */
    public static void setText(TextView tv, String content, String strReturn) {
        try {
            if (StringUtil.isEmpty(strReturn)) {
                strReturn = "";
            }
            if (StringUtil.isNotEmpty(content)) {
                strReturn = content;
            }
            tv.setText(strReturn);
        } catch (Exception e) {
            e.printStackTrace();
            tv.setText(strReturn);
        }
    }

    /**
     * 处理Integer为null报错的问题
     */
    public static int FormatInteger(Integer intValue) {
        int ret = 0;
        if (intValue != null) {
            ret = intValue.intValue();
        }
        return ret;
    }

    /**
     * 处理Double为null报错的问题
     */
    public static double FormatDouble(Double dValue) {
        double ret = 0.0;
        if (dValue != null) {
            ret = dValue.doubleValue();
        }
        return ret;
    }

    public static int dip2px(Context context, int dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static NetState getNetState(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        NetState mNetWorkType = NetState.NETWORKTYPE_INVALID;
        if (networkInfo != null && networkInfo.isConnected()) {
            String type = networkInfo.getTypeName();
            if (type.equalsIgnoreCase("WIFI")) {
                mNetWorkType = NetState.NETWORKTYPE_WIFI;
            } else if (type.equalsIgnoreCase("MOBILE")) {
                String proxyHost = android.net.Proxy.getDefaultHost();
                mNetWorkType = TextUtils.isEmpty(proxyHost)
                        ? (DeviceUtils.isFastMobileNetwork(context) ? NetState.NETWORKTYPE_3G : NetState.NETWORKTYPE_2G)
                        : NetState.NETWORKTYPE_WAP;
            }
        }
        return mNetWorkType;
    }

    /**
     * 获取某个类的第i个泛型参数的类型
     *
     * @param <T>
     * @param o
     * @param i
     * @return
     */
    public static <T> Class<T> getTClass(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i]);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建某个类的第i个泛型参数的对象
     *
     * @param o
     * @param i
     * @param <T>
     * @return
     */
    public static <T> T getT(Object o, int i) {
        try {
            return (T) getTClass(o, i).newInstance();
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取clazz子类的第一个泛型参数的类型
     *
     * @param clazz
     * @return
     */
    public static Class getSuperClassGenricType(Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    /**
     * 获取clazz子类的第index个泛型参数的类型
     *
     * @param clazz
     * @return
     */
    public static Class getSuperClassGenricType(Class clazz, int index) throws IndexOutOfBoundsException {

        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }
}