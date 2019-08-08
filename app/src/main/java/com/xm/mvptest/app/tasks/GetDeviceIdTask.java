package com.xm.mvptest.app.tasks;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import com.xm.mvptest.app.MyApplication;

import lib.xm.mvp.launchstarter.task.Task;


public class GetDeviceIdTask extends Task {
    private String mDeviceId;

    @Override
    public boolean needWait() {
        return true;
    }

    @Override
    public void run() {
        // 真正自己的代码
        TelephonyManager tManager = (TelephonyManager) mContext.getSystemService(
                Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mDeviceId = tManager.getDeviceId();
        MyApplication app = (MyApplication) mContext;
        app.setDeviceId(mDeviceId);
    }
}
