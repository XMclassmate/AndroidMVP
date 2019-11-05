package com.xm.mvptest.modules.memory;

import lib.xm.mvp.base.AbstractPresenter;

/**
 * Created by XMclassmate on 2019/8/21 13:53
 * Description:
 */
public class MemoryPresenter extends AbstractPresenter<MemoryContract.IView> implements MemoryContract.IPresenter {

    public MemoryPresenter(MemoryContract.IView iView) {
        super(iView);
    }

    @Override
    protected void start() {

    }

    @Override
    public void createArray() {
        for (int i = 0; i < 1000; i++) {
            String[] strs = new String[100000];
        }
    }
}
