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

import lib.xm.mvp.base.AbstractApplication;
import lib.xm.mvp.util.log.LogUtils;

/**
 * @author XMclassmate
 * @date 2018/2/11
 */

public class MyApplication extends AbstractApplication {

    private String buglyAppId = "f9f70f0d9a";
    private ApplicationLike tinkerApplicationLike;
    private static AppComponent appComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponent();
        LogUtils.e("初始化腾讯bugly");
        CrashReport.initCrashReport(getApplicationContext(),buglyAppId, BuildConfig.DEBUG);
        initTinkerPatch();
    }

    private void initAppComponent(){
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

    private void initTinkerPatch() {
        // 我们可以从这里获得Tinker加载过程的信息
        if (BuildConfig.TINKER_ENABLE) {
            tinkerApplicationLike = TinkerPatchApplicationLike.getTinkerPatchApplicationLike();
            // 初始化TinkerPatch SDK
            TinkerPatch.init(
                    tinkerApplicationLike
//                new TinkerPatch.Builder(tinkerApplicationLike)
//                    .requestLoader(new OkHttp3Loader())
//                    .build()
            )
                    .reflectPatchLibrary()
                    .setPatchRestartOnSrceenOff(true)
                    .setPatchRollbackOnScreenOff(true)
                    .setFetchPatchIntervalByHours(1)
                    .setPatchCondition("Vendor", Build.MANUFACTURER)
                    .setPatchRollBackCallback(new RollbackCallBack() {
                        @Override
                        public void onPatchRollback() {
                            Log.e("wutong", "现在版本已经回退了!!!");
                        }
                    })
                    .fetchPatchUpdate(true)
                    .setPatchResultCallback(new ResultCallBack() {
                        @Override
                        public void onPatchResult(PatchResult patchResult) {
                            if (BuildConfig.DEBUG) {
//                                showDialog();
                            }
                            LogUtils.e("补丁应用成功!");
                        }
                    })
                    .setFetchDynamicConfigIntervalByHours(-1);
            // 获取当前的补丁版本
            LogUtils.e( "Current patch version is " + TinkerPatch.with().getPatchVersion());
            // fetchPatchUpdateAndPollWithInterval 与 fetchPatchUpdate(false)
            // 不同的是，会通过handler的方式去轮询
            TinkerPatch.with().fetchPatchUpdateAndPollWithInterval();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
