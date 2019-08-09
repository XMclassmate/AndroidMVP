package com.xm.mvptest.modules.launch;

import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.xm.mvptest.R;
import com.xm.mvptest.app.BaseActivity;

import lib.xm.mvp.util.log.LogUtils;

/**
 * Created by XMclassmate on 2019/8/9 14:29
 * Description:
 */
public class WelcomeActivity extends BaseActivity<WelcomePresenter> implements WelcomeContract.IView {

    TextView tv_content;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_welcome;
    }

    /**
     * 初始化presenter
     *
     * @return
     */
    @Override
    protected WelcomePresenter initPresenter() {
        return new WelcomePresenter(this);
    }

    /**
     * 初始化view
     */
    @Override
    protected void initView() {
        tv_content = findViewById(R.id.tv_content);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    /**
     * 设置监听器
     */
    @Override
    protected void setListener() {
        tv_content.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                tv_content.getViewTreeObserver().removeOnPreDrawListener(this);
                presenter.launchOver();
                return true;
            }
        });
        tv_content.setText("广发卡过IE如过过过过过");

    }

}
