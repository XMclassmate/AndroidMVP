package com.xm.mvptest.modules.test;

import android.app.Activity;
import android.support.annotation.NonNull;

import lib.xm.mvp.base.BaseView;


/**
 * Created by XMclassmate on 2018/5/29.
 */

public class TestContract {

    interface IView extends BaseView {
        void setText(@NonNull String str);
    }

    interface IPresenter {
        void loadData();

        void testLog();

        void testPermission(Activity context);

        void sigin();

        void icona();

        void iconb();
    }
}
