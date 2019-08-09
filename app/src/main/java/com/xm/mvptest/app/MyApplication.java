package com.xm.mvptest.app;

import android.content.Context;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.tinker.entry.ApplicationLike;
import com.tencent.tinker.lib.service.PatchResult;
import com.tinkerpatch.sdk.TinkerPatch;
import com.tinkerpatch.sdk.loader.TinkerPatchApplicationLike;
import com.tinkerpatch.sdk.server.callback.RollbackCallBack;
import com.tinkerpatch.sdk.tinker.callback.ResultCallBack;
import com.xm.mvptest.BuildConfig;
import com.xm.mvptest.app.tasks.GetDeviceIdTask;
import com.xm.mvptest.app.tasks.InitBuglyTask;
import com.xm.mvptest.app.tasks.InitDraggerTask;
import com.xm.mvptest.app.tasks.InitTinkerTask;

import lib.xm.mvp.base.AbstractApplication;
import lib.xm.mvp.launchstarter.TaskDispatcher;
import lib.xm.mvp.util.log.LogUtils;

/**
 * @author XMclassmate
 * @date 2018/2/11
 */

public class MyApplication extends AbstractApplication {


    private static AppComponent appComponent;
    private static MyApplication myApplication;
    private String deviceId;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this;

        TaskDispatcher.init(this);
        TaskDispatcher taskDispatcher = TaskDispatcher.createInstance();
        taskDispatcher.addTask(new InitDraggerTask())
                .addTask(new InitBuglyTask())
                .addTask(new GetDeviceIdTask())
                .addTask(new InitTinkerTask())
                .start();
        taskDispatcher.await();
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        LogUtils.startRecord();
        MultiDex.install(this);
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    public static void setAppComponent(AppComponent appComponent) {
        MyApplication.appComponent = appComponent;
    }

    public static MyApplication getMyApplication() {
        return myApplication;
    }

    public void setDeviceId(String mDeviceId) {
        deviceId = mDeviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }
}
