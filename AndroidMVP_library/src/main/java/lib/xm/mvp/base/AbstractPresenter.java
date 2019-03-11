package lib.xm.mvp.base;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by XMclassmate on 2018/5/11.
 */

public abstract class AbstractPresenter<V extends BaseView> {
    protected V iView;
    protected CompositeDisposable compositeDisposable;

    public AbstractPresenter(V iView) {
        this.iView = iView;
        this.compositeDisposable = new CompositeDisposable();
        start();
    }

    protected abstract void start();

    protected void onDestory() {
        iView = null;
        compositeDisposable.dispose();
    }
}
