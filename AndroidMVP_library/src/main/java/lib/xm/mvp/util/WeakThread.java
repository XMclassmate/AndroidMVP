package lib.xm.mvp.util;


import java.lang.ref.WeakReference;

import lib.xm.mvp.listener.IWeakThread;

/**
 * Created by boka_lyp on 2015/10/24.
 */
public class WeakThread extends Thread {

    private WeakReference<IWeakThread> weakThread;
    private int tag;
    private boolean canDestroy;
    private volatile boolean isOver = false;

    public WeakThread(IWeakThread weakThread, int tag, boolean canDestroy) {
        this.weakThread = new WeakReference<IWeakThread>(weakThread);
        this.tag = tag;
        this.canDestroy = canDestroy;
    }

    @Override
    public void run() {
        if (!isOver && !isInterrupted()) {
            weakThread.get().runThread(tag);
        }
    }

    public void clearResources() {

    }

    public void destroyCallBack() {

    }

    public synchronized void before() {

    }

    public synchronized void after() {

    }

    @Override
    public synchronized void start() {
        before();
        super.start();
        after();
    }

    public WeakReference<IWeakThread> getWeakThread() {
        return weakThread;
    }

    public void setWeakThread(WeakReference<IWeakThread> weakThread) {
        this.weakThread = weakThread;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public boolean isCanDestroy() {
        return canDestroy;
    }

    public void setCanDestroy(boolean canDestroy) {
        this.canDestroy = canDestroy;
    }

    public synchronized boolean isOver() {
        return isOver;
    }

    public synchronized void setIsOver(boolean isOver) {
        this.isOver = isOver;
    }
}
