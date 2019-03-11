package lib.xm.mvp.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;

import com.xm.mvp.BuildConfig;
import com.xm.mvp.R;

import lib.xm.mvp.app.ActivityCollector;
import lib.xm.mvp.util.log.LogUtils;


/**
 * Created by XMclassmate on 2018/5/16.
 */

public abstract class AbstractApplication extends MultiDexApplication {

    private static Context appContext;
    private static ActivityCollector collector;
    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        LogUtils.init(BuildConfig.DEBUG,getString(R.string.app_name));
        collector = new ActivityCollector();
        registerActivityLifecycleCallbacks(callbacks);
    }

    private ActivityLifecycleCallbacks callbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            collector.pushActivity(activity);
            LogUtils.printStack(false).tag("ActivityStack").e(activity.getClass().getSimpleName()+" onCreated "+ collector.getmActivityList().size());
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            collector.popActivity(activity);
            LogUtils.printStack(false).tag("ActivityStack").e(activity.getClass().getSimpleName()+" onDestroyed "+ collector.getmActivityList().size());
        }
    };

    public static Context getAppContext(){
        return appContext;
    }

}
