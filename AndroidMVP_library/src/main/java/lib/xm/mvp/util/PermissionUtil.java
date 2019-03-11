package lib.xm.mvp.util;
import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.concurrent.ConcurrentLinkedQueue;

import lib.xm.mvp.base.AbstractApplication;
import lib.xm.mvp.util.log.LogUtils;


/**
 * Created by XMclassmate on 2018/4/20.
 * 动态申请Android危险权限工具类，注意Google官方关于危险权限分组的变更
 */

public class PermissionUtil {

    public static final int permissionRequestCode = 1111;

    public static final int GROUP_CALENDAR = 0;
    public static final int GROUP_CAMERA = 1;
    public static final int GROUP_CONTACTS = 2;
    public static final int GROUP_LOCATION = 3;
    public static final int GROUP_MICROPHONE = 4;
    public static final int GROUP_CALL_PHONE = 5;
    public static final int GROUP_SMS = 6;
    public static final int GROUP_STORAGE = 7;
    private static final String[][] PERMISSION_GROUPS = {
            {Manifest.permission.READ_CALENDAR},
            {Manifest.permission.CAMERA},
            {Manifest.permission.READ_CONTACTS},
            {Manifest.permission.ACCESS_COARSE_LOCATION},
            {Manifest.permission.RECORD_AUDIO},
            {Manifest.permission.READ_PHONE_STATE,Manifest.permission.CALL_PHONE},
            {Manifest.permission.SEND_SMS},
            {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}};

    //App需要的敏感权限
    private static int[] appPermisions = new int[]{GROUP_CALL_PHONE,GROUP_STORAGE,GROUP_CAMERA,GROUP_LOCATION};
    //App缺失的敏感权限
    public static ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue();

    private static synchronized boolean checkPermission(String permission) {
        if (Build.VERSION.SDK_INT >= 23 && StringUtil.isNotEmpty(permission)) {
            return ContextCompat.checkSelfPermission(AbstractApplication.getAppContext(), permission) == PackageManager.PERMISSION_GRANTED;
        }
        return true;
    }

    /**
     * 检查权限组
     *
     * @param permissionGroup
     * @return
     */
    public static synchronized boolean checkPermissionGroup(int permissionGroup) {
        if (permissionGroup < GROUP_CALENDAR || permissionGroup > GROUP_STORAGE) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            String[] group = PERMISSION_GROUPS[permissionGroup];
            boolean flag = true;
            for (String permission : group) {
                if (!checkPermission(permission)) {
                    flag = false;
                    LogUtils.printStack(false).e("没有权限:" + permission);
                }
            }
            return flag;
        }
        return true;
    }

    /**
     * 申请权限组
     *
     * @param activity
     * @param permissionGroup
     */
    public static synchronized boolean requestPermission(Activity activity, int permissionGroup, int requestCode) {
        if (checkPermissionGroup(permissionGroup)){
            return false;
        }
        ActivityCompat.requestPermissions(activity, PERMISSION_GROUPS[permissionGroup], requestCode);
        return true;
    }


    public static boolean requestAllPermisions(Activity activity){
        queue.clear();
        for (int index : appPermisions) {
            if (!checkPermissionGroup(index)){
                queue.add(index);
            }
        }
        boolean temp = queue.isEmpty();
        requestNext(activity);
        return temp;
    }

    public static void requestNext(Activity activity){
        if (queue.isEmpty()){
            return;
        }
        if (!requestPermission(activity, queue.poll(), permissionRequestCode)){
            requestNext(activity);
        }
    }

    /**
     * 检查权限申请结果
     *
     * @param permissions
     * @param grantResults
     * @return
     */
    public static synchronized boolean checkResult(String[] permissions, int[] grantResults) {
        boolean flag = true;
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                flag = false;
                LogUtils.printStack(false).e(permissions[i] + "申请失败");
            }
        }
        return flag;
    }
}
