package com.xm.mvptest.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.xm.mvptest.service.SiginService;

/**
 * Created by XMclassmate on 2018/12/4
 */
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (Intent.ACTION_BOOT_COMPLETED.equals(action)){
            Intent intent1 = new Intent(context, SiginService.class);
            context.startService(intent1);
        }
    }
}
