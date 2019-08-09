package com.xm.mvptest.app;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.xm.mvptest.R;

import lib.xm.mvp.app.ProgressDialog;
import lib.xm.mvp.base.AbstractActivity;
import lib.xm.mvp.base.AbstractPresenter;
import lib.xm.mvp.base.BaseView;
import lib.xm.mvp.util.CommonUtil;

/**
 * Created by XMclassmate on 2018/7/25.
 */

public abstract class BaseActivity<T extends AbstractPresenter> extends AbstractActivity<T> implements BaseView {

    private Dialog progressDialog;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            final View v = getCurrentFocus();
            if (isShouldHideInput(v, ev) && (v != null)) {
                hideSoftInput(v);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void showProgressPage(@Nullable Object obj) {
        showProgressDialog(obj.toString());
    }

    @Override
    public void hideProgressPage() {
        hideProgressDialog();
    }

    /**
     * 进度条颜色，需要改变颜色可重写此方法
     * @return
     */
    protected int getProgressColor(){
        return CommonUtil.getColor(R.color.colorPrimary);
    }
    /**
     * 显示进度，需要自定义的进度弹窗可重写此方法
     */
    private void showProgressDialog(@Nullable String msg) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this, R.style.TransleteTheme, getProgressColor(), msg);
        }
        if (progressDialog.isShowing()) {
            return;
        }
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog.show();
            }
        });
    }

    /**
     * 隐藏进度
     */
    private void hideProgressDialog() {
        if (progressDialog != null) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();
                    progressDialog = null;
                }
            });
        }
    }

    /**
     * 判定是否需要隐藏软键盘
     */
    protected boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 隐藏软件盘
     * @param view 界面上的任意一个View，用于获取token:getWindowToken()
     */
    protected void hideSoftInput(View view) {
        if (view != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 显示软件盘
     * @param view 界面上的任意一个View，用于获取token:getWindowToken()
     */
    protected void showSoftInput(View view) {
        if (view != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.showSoftInput(view, 0);
        }
    }

}
