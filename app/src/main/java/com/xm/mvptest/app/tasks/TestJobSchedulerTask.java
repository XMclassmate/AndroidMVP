package com.xm.mvptest.app.tasks;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;

import com.xm.mvptest.service.JobSchedulerService;

import lib.xm.mvp.launchstarter.task.MainTask;

/**
 * Created by XMclassmate on 2019/11/13 15:12
 * Description:
 */
public class TestJobSchedulerTask extends MainTask {
    @Override
    public void run() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            JobScheduler jobScheduler = (JobScheduler) mContext.getApplicationContext().getSystemService(Context.JOB_SCHEDULER_SERVICE);
            JobInfo.Builder builder = new JobInfo.Builder(111, new ComponentName(mContext.getPackageName(),JobSchedulerService.class.getName()))
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                    .setOverrideDeadline(15000)
                    .setRequiresCharging(true);
            jobScheduler.schedule(builder.build());
        }
    }
}
