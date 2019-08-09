package com.xm.mvptest.app.tasks;

import lib.xm.mvp.launchstarter.task.MainTask;
import lib.xm.mvp.util.log.LogUtils;

/**
 * Created by XMclassmate on 2019/8/9 15:05
 * Description:
 */
public class DelayTaskA extends MainTask {

    @Override
    public void run() {
        LogUtils.tag("record-time").e("DelayTaskA-start");
        try {
            Thread.sleep(150);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LogUtils.tag("record-time").e("DelayTaskA-end");
    }
}
