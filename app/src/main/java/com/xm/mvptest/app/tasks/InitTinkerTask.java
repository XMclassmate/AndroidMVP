package com.xm.mvptest.app.tasks;

import android.os.Build;

import com.tencent.tinker.entry.ApplicationLike;
import com.tencent.tinker.lib.service.PatchResult;
import com.tinkerpatch.sdk.TinkerPatch;
import com.tinkerpatch.sdk.loader.TinkerPatchApplicationLike;
import com.tinkerpatch.sdk.server.callback.RollbackCallBack;
import com.tinkerpatch.sdk.tinker.callback.ResultCallBack;
import com.xm.mvptest.BuildConfig;

import lib.xm.mvp.launchstarter.task.MainTask;
import lib.xm.mvp.util.log.LogUtils;

/**
 * Created by XMclassmate on 2019/8/8 17:51
 * Description:
 */
public class InitTinkerTask extends MainTask {
    private ApplicationLike tinkerApplicationLike;

    @Override
    public boolean needWait() {
        return true;
    }

    @Override
    public void run() {
        // 我们可以从这里获得Tinker加载过程的信息
        if (BuildConfig.TINKER_ENABLE) {
            tinkerApplicationLike = TinkerPatchApplicationLike.getTinkerPatchApplicationLike();
            // 初始化TinkerPatch SDK
            TinkerPatch.init(
                    tinkerApplicationLike
            )
                    .reflectPatchLibrary()
                    .setPatchRestartOnSrceenOff(true)
                    .setPatchRollbackOnScreenOff(true)
                    .setFetchPatchIntervalByHours(1)
                    .setPatchCondition("Vendor", Build.MANUFACTURER)
                    .setPatchRollBackCallback(new RollbackCallBack() {
                        @Override
                        public void onPatchRollback() {
                            LogUtils.e("现在版本已经回退了!!!");
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
}
