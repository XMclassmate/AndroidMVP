package com.xm.mvptest.modules.memory.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XMclassmate on 2019/8/21 17:08
 * Description:
 */
public class CallbackManager {

    private static List<Callback> mCallbacks = new ArrayList<>();

    public static void addCallback(Callback callback) {
        mCallbacks.add(callback);
    }

    public static void removeCallback(Callback callback) {
        mCallbacks.remove(callback);
    }
}
