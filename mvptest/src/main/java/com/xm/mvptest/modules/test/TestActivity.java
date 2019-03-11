package com.xm.mvptest.modules.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xm.mvptest.R;
import com.xm.mvptest.service.SiginService;
import com.xm.mvptest.utils.ShellUtils;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

import lib.xm.mvp.base.AbstractActivity;
import lib.xm.mvp.util.PermissionUtil;
import lib.xm.mvp.util.ToastUtil;
import lib.xm.mvp.util.log.LogUtils;


/**
 * Created by XMclassmate on 2018/4/20.
 */

public class TestActivity extends AbstractActivity<TestPresenter> implements TestContract.IView, View.OnClickListener {
    Button btn_http;
    Button btn_log;
    Button btn_permission;
    Button btn_toMain;
    Button btn_sign;
    TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PermissionUtil.getNoPermisions(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected TestPresenter initPresenter() {
        return new TestPresenter(this);
    }

    @Override
    public void initView() {
        btn_http = findViewById(R.id.btn_http);
        btn_log = findViewById(R.id.btn_log);
        btn_permission = findViewById(R.id.btn_permission);
        btn_toMain = findViewById(R.id.btn_to_main);
        btn_sign = findViewById(R.id.btn_sigin);
        tv = findViewById(R.id.tv_content);

        Intent intent1 = new Intent(this, SiginService.class);
        startService(intent1);
    }

    @Override
    protected void setListener() {
        btn_http.setOnClickListener(this);
        btn_log.setOnClickListener(this);
        btn_permission.setOnClickListener(this);
        btn_toMain.setOnClickListener(this);
        btn_sign.setOnClickListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PermissionUtil.permissionRequestCode) {
            PermissionUtil.requestNext(this);
//            boolean result = PermissionUtil.checkResult(permissions, grantResults);
//            if (result) {
//                ToastUtil.toast("申请权限成功");
//            } else {
//                ToastUtil.toast("申请权限失败");
//            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_http:
                presenter.loadData();
                break;
            case R.id.btn_log:
                presenter.testLog();
                break;
            case R.id.btn_permission:
                presenter.testPermission(TestActivity.this);
                break;
            case R.id.btn_to_main:
                presenter.icona();
                break;
            case R.id.btn_sigin:
//                presenter.sigin();
                presenter.iconb();
                break;
            default:
                break;
        }
    }

    @Override
    public void setText(String str) {
        tv.setText(str);
    }

    @Override
    public void showProgressPage(Object obj) {

    }

    @Override
    public void hideProgressPage() {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.e(event.getX()+"  "+ event.getY());
        return super.onTouchEvent(event);
    }
}
