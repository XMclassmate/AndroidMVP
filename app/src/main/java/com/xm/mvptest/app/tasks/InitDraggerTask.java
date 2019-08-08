package com.xm.mvptest.app.tasks;

import com.xm.mvptest.app.AppComponent;
import com.xm.mvptest.app.AppModule;
import com.xm.mvptest.app.DaggerAppComponent;
import com.xm.mvptest.app.MyApplication;

import lib.xm.mvp.launchstarter.task.Task;

/**
 * Created by XMclassmate on 2019/8/8 17:27
 * Description:
 */
public class InitDraggerTask extends Task {

    @Override
    public boolean needWait() {
        return true;
    }

    @Override
    public void run() {
        AppComponent appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule((MyApplication) mContext))
                .build();
        MyApplication.setAppComponent(appComponent);
    }
}
