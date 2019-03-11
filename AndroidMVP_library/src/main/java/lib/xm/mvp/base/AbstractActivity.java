package lib.xm.mvp.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import lib.xm.mvp.util.RxBus;

/**
 * Created by XMclassmate on 2018/5/30.
 */

public abstract class AbstractActivity<T extends AbstractPresenter> extends AppCompatActivity {

    protected T presenter;
    protected String tag;
    protected List<HashMap<String,Disposable>> disposableMapList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tag = getClass().getSimpleName();
        disposableMapList = new ArrayList<>();
        setContentView(getLayoutId());
        presenter = initPresenter();
        initView();
        setListener();
    }

    protected abstract int getLayoutId();

    /**
     * 初始化presenter
     *
     * @return
     */
    protected abstract T initPresenter();

    /**
     * 初始化view
     */
    protected abstract void initView();

    /**
     * 设置监听器
     */
    protected abstract void setListener();

    /**
     * 注册rxbus监听器
     * @param tag
     * @param clzz
     * @param consumer
     * @param <T>
     */
    protected <T> void registerEventListener(@NonNull String tag, Class<T> clzz, Consumer<T> consumer){
        Disposable disposable = RxBus.getIntance().register(tag,clzz,consumer);
        HashMap<String,Disposable> map = new HashMap<>();
        map.put(tag,disposable);
        disposableMapList.add(map);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestory();
        RxBus.getIntance().unSubscribe(disposableMapList);
    }
}
