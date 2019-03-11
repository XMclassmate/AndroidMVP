package lib.xm.mvp.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;


import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lib.xm.mvp.base.AbstractApplication;

/**
 * 应用工具类用于全局操作app的版本信息提取，退出等
 */

public class AppUtil {

    /**
     * 退出app
     */
    public static void exitApp(){
        System.gc();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * 获取安卓设备信息
     * @return
     */
    public static String getDeviceInfo(){
        PackageManager pm = AbstractApplication.getAppContext().getPackageManager();
        StringBuilder sb = new StringBuilder();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(AbstractApplication.getAppContext().getPackageName(), PackageManager.GET_ACTIVITIES);
            sb.append("App VersionName:").append(packageInfo.versionName)
                    .append("\n")
                    .append("VersionCode:").append(packageInfo.versionCode)
                    .append("\n")
                    .append("OS Version:").append(Build.VERSION.RELEASE).append("_").append(Build.VERSION.SDK_INT)
                    .append("\n")
                    .append("Vendor:").append(Build.MANUFACTURER)
                    .append("\n")
                    .append("Model:").append(Build.MODEL)
                    .append("\n")
                    .append("CPU ABI:").append(Build.CPU_ABI);
            return sb.toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean isAppInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        List<String> pName = new ArrayList<String>();
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);
    }

    /**
     * 利用系统安装界面安装APK
     *
     * @param file .apk文件路径
     */
    public static void installAPK(Activity activity, File file) {
        try {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            activity.startActivityForResult(intent, 200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getVersionName(){
        PackageManager pm = AbstractApplication.getAppContext().getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(AbstractApplication.getAppContext().getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getVersionCode(){
        PackageManager pm = AbstractApplication.getAppContext().getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(AbstractApplication.getAppContext().getPackageName(), 0);
            return packageInfo.versionCode+"";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
