package com.xm.mvptest.modules.memory;

import lib.xm.mvp.base.BaseView;

/**
 * Created by XMclassmate on 2019/8/21 12:00
 * Description:
 */
public class MemoryContract {

    interface IView extends BaseView{

    }

    interface IPresenter{

        void createArray();
    }
}
