package com.xm.mvptest.app.tasks;

import com.tencent.bugly.crashreport.CrashReport;
import com.xm.mvptest.BuildConfig;

import lib.xm.mvp.launchstarter.task.Task;

/**
 * Created by XMclassmate on 2019/8/8 17:37
 * Description:
 */
public class InitBuglyTask extends Task {
    private final String buglyAppId = "f9f70f0d9a";
    @Override
    public void run() {
        CrashReport.initCrashReport(mContext,buglyAppId, BuildConfig.DEBUG);
    }
}
