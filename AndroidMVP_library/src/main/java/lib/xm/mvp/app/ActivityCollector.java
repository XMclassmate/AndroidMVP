package lib.xm.mvp.app;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Activity管理类
 */
public class ActivityCollector {

    private static ActivityCollector activityCollector;

    public static ActivityCollector getInstance() {
        if (activityCollector == null) {
            synchronized (ActivityCollector.class) {
                if (activityCollector == null) {
                    activityCollector = new ActivityCollector();
                }
            }
        }
        return activityCollector;
    }

    /**
     * Activity 列表
     * - LinkedList 内部以链表的形式保存集合的元素，插入和删除比较出色；
     * - Collections.synchronizedXxx() 将集合包装成线程同步的，解决多
     * 线程并发访问集合时的线程安全问题。
     */
    private List<Activity> mActivityList = Collections.synchronizedList(new ArrayList<Activity>());

    public List<Activity> getmActivityList() {
        return mActivityList;
    }

    /**
     * Activity入栈
     */
    public void pushActivity(Activity activity) {
        mActivityList.add(activity);
    }

    /**
     * Activity出栈
     */
    public void popActivity(Activity activity) {
        mActivityList.remove(activity);
    }


    /**
     * 获取当前(栈顶)Activity
     */
    public Activity currentActivity() {
        if (mActivityList == null || mActivityList.isEmpty()) {
            return null;
        }
        return mActivityList.get(mActivityList.size() - 1);
    }

    /**
     * 结束当前(栈顶)Activity
     */
    public void finishCurrentActivity() {
        if (mActivityList == null || mActivityList.isEmpty()) {
            return;
        }
        Activity activity = mActivityList.get(mActivityList.size() - 1);
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        // 从列表中移除
        mActivityList.remove(activity);
        // finish掉Activity
        if (!activity.isFinishing()) {
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束列表中指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        if (mActivityList == null || mActivityList.isEmpty()) {
            return;
        }
        for (Activity activity : mActivityList) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        if (mActivityList == null) {
            return;
        }
        for (Activity activity : mActivityList) {
            activity.finish();
            activity = null;
        }
        mActivityList.clear();
    }

    /**
     * 结束除了***以外的所有Activity
     */
    public void finishAllActivityExcept(Class<?> cls) {
        if (mActivityList == null) {
            return;
        }
        for (Activity activity : mActivityList) {
            if (!activity.getClass().equals(cls)) {
                activity.finish();
                activity = null;
            }
        }
    }

    /**
     * 退出应用程序
     */
    public void exitApp() {
        // 结束所有界面
        finishAllActivity();
        // 杀掉当前进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
