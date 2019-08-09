package com.xm.mvptest.modules.launch;

import lib.xm.mvp.base.BaseView;

/**
 * Created by XMclassmate on 2019/8/9 14:33
 * Description:
 */
public class WelcomeContract {

    interface IView extends BaseView{

    }

    interface IPresenter{

        void launchOver();

    }
}
