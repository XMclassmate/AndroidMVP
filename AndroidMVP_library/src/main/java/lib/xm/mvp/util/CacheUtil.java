package lib.xm.mvp.util;

import android.text.TextUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import lib.xm.mvp.base.AbstractApplication;
import lib.xm.mvp.util.log.LogUtils;


/**
 * @author XMclassmate
 * @date 2017/12/7
 */

public class CacheUtil {
    /**
     * 缓存字符串
     *
     * @param key
     * @param json
     */
    public static void putString(final String key, final String json) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                LogUtils.i("putString:" + key);
                ACache.get(AbstractApplication.getAppContext()).put(key, json);
                e.onNext("putString:" + key + ":success");
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        LogUtils.i(s);
                    }
                });
    }

    /**
     * 保存字符串设置有效时间
     *
     * @param key
     * @param json
     * @param time
     */
    public static void putString(final String key, final String json, final int time) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                LogUtils.i("putString:" + key);
                ACache.get(AbstractApplication.getAppContext()).put(key, json, time);
                e.onNext("putString:" + key + ":success");
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        LogUtils.i(s);
                    }
                });
    }

    /**
     * 提取字符串
     *
     * @param key
     * @param listener
     */
    public static void getString(final String key, final CacheListener listener) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                String json = ACache.get(AbstractApplication.getAppContext()).getAsString(key);
                e.onNext(json);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        LogUtils.i("getString:" + key + ":" + s);
                        if (TextUtils.isEmpty(s)) {
                            if (listener != null) {
                                listener.fail();
                            }
                        } else {
                            if (listener != null) {
                                listener.success(s);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.i("getString:" + key + ":failed");
                        if (listener != null) {
                            listener.fail();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 保存byte数组并设置有效时间
     *
     * @param key
     * @param bs
     * @param time
     */
    public static void putBytes(final String key, final byte[] bs, final int time) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                LogUtils.i("putBytes:" + key);
                ACache.get(AbstractApplication.getAppContext()).put(key, bs, time);
                e.onNext(new String());
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        LogUtils.i("putBytes:" + key + ":success");
                    }
                });
    }

    /**
     * 提取byte数组
     *
     * @param key
     * @param listener
     */
    public static void getBytes(final String key, final CacheListener listener) {
        Observable.create(new ObservableOnSubscribe<byte[]>() {
            @Override
            public void subscribe(ObservableEmitter<byte[]> e) throws Exception {
                byte[] bs = ACache.get(AbstractApplication.getAppContext()).getAsBinary(key);
                e.onNext(bs);
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<byte[]>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(byte[] bytes) {
                        LogUtils.i("getString:" + key + ":" + bytes);
                        if (bytes != null && bytes.length != 0) {
                            if (listener != null) {
                                listener.success(bytes);
                            }
                        } else {
                            if (listener != null) {
                                listener.fail();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.i("getBytes:" + key + ":failed");
                        if (listener != null) {
                            listener.fail();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 根据key来删除缓存数据
     *
     * @param key
     * @return
     */
    public static boolean remove(String key) {
        LogUtils.i("removeCache:" + key);
        return ACache.get(AbstractApplication.getAppContext()).remove(key);
    }

    public interface CacheListener {
        /**
         * 成功
         *
         * @param obj
         */
        void success(Object obj);

        /**
         * 失败
         */
        void fail();
    }
}
