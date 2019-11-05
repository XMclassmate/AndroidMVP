package com.xm.mvptest.modules.launch;

import android.content.Intent;

import com.xm.mvptest.app.tasks.DelayTaskA;
import com.xm.mvptest.app.tasks.DelayTaskB;
import com.xm.mvptest.modules.test.TestActivity;

import lib.xm.mvp.base.AbstractPresenter;
import lib.xm.mvp.launchstarter.DelayInitDispatcher;
import lib.xm.mvp.util.log.LogUtils;

/**
 * Created by XMclassmate on 2019/8/9 14:34
 * Description:
 */
public class WelcomePresenter extends AbstractPresenter<WelcomeContract.IView> implements WelcomeContract.IPresenter {

    public WelcomePresenter(WelcomeContract.IView iView) {
        super(iView);

    }

    @Override
    protected void start() {

    }

    @Override
    public void launchOver() {
        DelayInitDispatcher mdDelayInitDispatcher = new DelayInitDispatcher();
        mdDelayInitDispatcher.addTask(new DelayTaskA())
                .addTask(new DelayTaskB())
                .start();
        LogUtils.endRecord("launchOver");
    }
}
