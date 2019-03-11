package lib.xm.mvp.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import lib.xm.mvp.util.RxBus;
import lib.xm.mvp.util.log.LogUtils;

/**
 * Created by XMclassmate on 2018/5/30.
 */

public abstract class AbstractFragment<T extends AbstractPresenter> extends Fragment {

    protected T presenter;
    protected Activity activity;
    protected View contentView;
    /**
     * 是否懒加载
     */
    private boolean isLazyMode;
    /**
     * 视图是否已初始化
     */
    protected boolean isViewCreated;
    /**
     * 是否已加载过数据
     */
    protected boolean isLoadData;
    protected String tag;
    protected List<HashMap<String,Disposable>> disposableMapList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tag = getClass().getSimpleName();
        disposableMapList = new ArrayList<>();
        activity = getActivity();
        LogUtils.e(tag + " onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (contentView == null) {
            contentView = inflater.inflate(getLayoutId(), null);
        }
        initView(contentView);
        presenter = initPresenter();
        setListener();
        isViewCreated = true;
        LogUtils.e(tag + "isViewCreated=" + isViewCreated);
        if (!isLazyMode) {
            LogUtils.e(tag + "正常加载数据");
            initData();
            isLoadData = true;
        }else{
            //由于setUserVisibleHint可能在onCreateView之前调用导致不加载数据，这里在懒加载模式下手动调用一次
            lazyLoadData();
        }
        return contentView;
    }

    protected abstract int getLayoutId();

    protected abstract T initPresenter();

    protected abstract void initView(View contentView);

    protected abstract void setListener();

    protected abstract void initData();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        lazyLoadData();
    }

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

    /**
     * 懒加载数据
     */
    protected void lazyLoadData(){
        if (!isLazyMode || !getUserVisibleHint()) {
            return;
        }
        if (isViewCreated && !isLoadData) {
            LogUtils.e(tag + "懒加载数据");
            initData();
            isLoadData = true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewCreated = false;
        isLoadData = false;
        LogUtils.e(tag + "isViewCreated=" + isViewCreated);
        presenter.onDestory();
    }

    /**
     * 设置是否懒加载
     * @param lazyMode
     */
    public void setLazyMode(boolean lazyMode) {
        isLazyMode = lazyMode;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getIntance().unSubscribe(disposableMapList);
    }
}
