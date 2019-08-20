package com.xm.mvptest.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import com.xm.mvptest.IMyAidlInterface;
import com.xm.mvptest.utils.ShellUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import lib.xm.mvp.util.DeviceUtils;
import lib.xm.mvp.util.RxBus;
import lib.xm.mvp.util.ThreadUtil;
import lib.xm.mvp.util.log.LogUtils;

/**
 * Created by XMclassmate on 2018/12/4
 */
public class SiginService extends Service {

    private Disposable disposable;

    private IMyAidlInterface.Stub binder = new IMyAidlInterface.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public String testGetString() throws RemoteException {
            return null;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        disposable = RxBus.getIntance().register(this.getClass().getSimpleName(), String.class, new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                LogUtils.e("收到事件："+s);
                if ("sigin".equals(s)){
                    sigin();
                }
            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private void sigin(){
        LogUtils.e(DeviceUtils.getPhoneIp());
        ThreadUtil.start(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<String> commandList = new ArrayList<>();
                commandList.add("input tap 558 1017");
                commandList.add("input tap 545 1716");
                commandList.add("input tap 206 1012");
                commandList.add("input tap 220 1508");
                commandList.add("input tap 843 1497");
                commandList.add("input tap 558 1017");
                commandList.add("input tap 943 1342");
                commandList.add("input tap 687 1849");
                commandList.add("input tap 566 525");
                for (String command: commandList) {
                    ShellUtils.CommandResult result = ShellUtils.execCommand(new String[]{"ps"}, false);
                    LogUtils.e(result.result + "   errorMsg:"+ result.errorMsg + "   successMsg:"+ result.successMsg);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getIntance().unSubscribe(SiginService.class.getSimpleName(), disposable);
    }
}
