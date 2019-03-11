package lib.xm.mvp.util;

import android.support.annotation.NonNull;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.BackpressureStrategy;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import lib.xm.mvp.util.log.LogUtils;

/**
 * Created by XMclassmate on 2018/5/31.
 */

public class RxBus {
    private HashMap<String, CompositeDisposable> mSubscriptionMap;
    private static volatile RxBus mRxBus;
    private ConcurrentHashMap<String, List<Subject>> subjectsMap = new ConcurrentHashMap<>();

    public static RxBus getIntance() {
        if (mRxBus == null) {
            synchronized (RxBus.class) {
                if (mRxBus == null) {
                    mRxBus = new RxBus();
                }
            }
        }
        return mRxBus;
    }

    private RxBus() {
    }

    /**
     * 发送事件
     * @param tag
     * @param o
     */
    public void post(@NonNull String tag, @NonNull Object o) {
        List<Subject> subjectList = subjectsMap.get(tag);
        if (subjectList == null) {
            return;
        }
        for (Subject subject : subjectList) {
            subject.onNext(o);
        }
    }

    /**
     * 订阅
     * @param tag
     * @param clzz
     * @param consumer
     * @param <T>
     * @return
     */
    public <T> Disposable register(@NonNull String tag, @NonNull Class<T> clzz, Consumer<T> consumer) {
        List<Subject> subjectList = subjectsMap.get(tag);
        if (null == subjectList) {
            subjectList = new ArrayList<>();
        }
        Subject<T> subject = (Subject<T>) PublishSubject.create().toSerialized();
        subjectList.add(subject);
        subjectsMap.put(tag, subjectList);
        Disposable disposable = subject.toFlowable(BackpressureStrategy.BUFFER)
                .ofType(clzz)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(consumer, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtils.e(throwable);
                    }
                });

        addSubscription(tag, disposable);
        return disposable;
    }

    /**
     * 保存订阅后的disposable
     *
     * @param disposable
     */
    private void addSubscription(String tag, Disposable disposable) {
        if (mSubscriptionMap == null) {
            mSubscriptionMap = new HashMap<>();
        }
        if (mSubscriptionMap.get(tag) != null) {
            mSubscriptionMap.get(tag).add(disposable);
        } else {
            CompositeDisposable disposables = new CompositeDisposable();
            disposables.add(disposable);
            mSubscriptionMap.put(tag, disposables);
        }
    }

    /**
     * 取消订阅
     */
    public void unSubscribe(@NonNull String tag, @NonNull Disposable disposable) {
        if (disposable == null){
            return;
        }
        disposable.dispose();
        if (mSubscriptionMap == null) {
            return;
        }

        if (!mSubscriptionMap.containsKey(tag)) {
            return;
        }
        if (mSubscriptionMap.get(tag) != null) {
            mSubscriptionMap.get(tag).remove(disposable);
        }
    }

    /**
     * 取消订阅
     */
    public void unSubscribe(@NonNull List<HashMap<String, Disposable>> disposableMapList) {
        if (disposableMapList.isEmpty()) {
            return;
        }
        for (HashMap<String, Disposable> disposableMap : disposableMapList) {
            Iterator<Map.Entry<String, Disposable>> iterator = disposableMap.entrySet().iterator();
            Map.Entry<String, Disposable> entry = null;
            while (iterator.hasNext()) {
                entry = iterator.next();
                unSubscribe(entry.getKey(), entry.getValue());
            }
            disposableMap.clear();
        }
        disposableMapList.clear();
    }
}
