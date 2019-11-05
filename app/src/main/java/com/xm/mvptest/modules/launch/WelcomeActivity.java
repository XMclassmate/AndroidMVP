package com.xm.mvptest.modules.launch;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.xm.mvptest.R;
import com.xm.mvptest.app.BaseActivity;
import com.xm.mvptest.modules.test.TestActivity;

import lib.xm.mvp.util.log.LogUtils;

/**
 * Created by XMclassmate on 2019/8/9 14:29
 * Description:
 */
public class WelcomeActivity extends BaseActivity<WelcomePresenter> implements WelcomeContract.IView {

    TextView tv_content;
    int count = 5;
    CountDownTimer timer;
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

        tv_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, TestActivity.class));
                if (timer != null) timer.cancel();
            }
        });


        timer = new CountDownTimer(5*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                LogUtils.e((count)+"   "+ millisUntilFinished);
                tv_content.setText((count--)+"   "+ millisUntilFinished);
            }

            @Override
            public void onFinish() {
                tv_content.setText("");
                startActivity(new Intent(WelcomeActivity.this, TestActivity.class));
                if (timer != null)
                timer.cancel();
            }
        }.start();
    }

}
