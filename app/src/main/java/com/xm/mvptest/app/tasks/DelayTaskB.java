package com.xm.mvptest.app.tasks;

import lib.xm.mvp.launchstarter.task.MainTask;
import lib.xm.mvp.util.log.LogUtils;

/**
 * Created by XMclassmate on 2019/8/9 15:11
 * Description:
 */
public class DelayTaskB extends MainTask {

    @Override
    public void run() {
        LogUtils.tag("record-time").e("DelayTaskB-start");
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LogUtils.tag("record-time").e("DelayTaskB-end");
    }
}
