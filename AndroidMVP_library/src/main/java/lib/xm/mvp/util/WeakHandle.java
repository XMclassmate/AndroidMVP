package lib.xm.mvp.util;

import android.os.Handler;
import android.os.Message;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import lib.xm.mvp.listener.IWeakHandMsg;
import lib.xm.mvp.util.log.LogUtils;


/**
 * Created by boka_lyp on 2015/10/24.
 */
public class WeakHandle extends Handler {

    private WeakReference<IWeakHandMsg> activity;
    private volatile List<Integer> tags;
    private volatile List<Boolean> isRemovedCallBack;

    public WeakHandle(IWeakHandMsg activity) {
        this.activity = new WeakReference(activity);
        tags = new ArrayList<>();
        isRemovedCallBack = new ArrayList<>();
    }

//    public BaseActivity getBaseActivity() {
//        return (BaseActivity) activity.get();
//    }

//    public BaseFragment getBaseFragment() {
//        return (BaseFragment) activity.get();
//    }

    @Override
    public void handleMessage(Message msg) {

        super.handleMessage(msg);
        if (activity!=null&&activity.get()!=null) {
            activity.get().handleMessageCallBack(msg);
        }
    }

    public void sendMyMessage(Message message, boolean canCancel) {
        setCanCancel(canCancel, message.what);
        LogUtils.e("activity name=" + activity.get().getClass().getSimpleName());
        sendMessage(message);
    }

    private void setCanCancel(boolean canCancel, int what) {
        if (canCancel) {
            if (tags != null) {
                tags.add(what);
            }
        }
        if (isRemovedCallBack != null) {
            isRemovedCallBack.add(false);
        }
    }

    public void sendMyEmptyMessage(int what, boolean canCancel) {
        setCanCancel(canCancel, what);
        sendEmptyMessage(what);
    }

    public void sendMyEmptyMessageAtTime(int what, boolean canCancel, long uptimeMillis) {
        setCanCancel(canCancel, what);
        sendEmptyMessageAtTime(what, uptimeMillis);
    }

    public void sendMyEmptyMessageDelayed(int what, boolean canCancel, long uptimeMillis) {
        setCanCancel(canCancel, what);
        sendEmptyMessageDelayed(what, uptimeMillis);
    }

    public WeakReference<IWeakHandMsg> getActivity() {
        return activity;
    }

    public void setActivity(WeakReference<IWeakHandMsg> activity) {
        this.activity = activity;
    }

    public List<Integer> getTags() {
        return tags;
    }

    public void setTags(List<Integer> tags) {
        this.tags = tags;
    }

    public List<Boolean> getIsRemovedCallBack() {
        return isRemovedCallBack;
    }

    public void setIsRemovedCallBack(List<Boolean> isRemovedCallBack) {
        this.isRemovedCallBack = isRemovedCallBack;
    }
}
