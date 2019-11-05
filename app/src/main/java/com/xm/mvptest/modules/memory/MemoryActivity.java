package com.xm.mvptest.modules.memory;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.xm.mvptest.R;
import com.xm.mvptest.app.BaseActivity;
import com.xm.mvptest.modules.memory.util.Callback;
import com.xm.mvptest.modules.memory.util.CallbackManager;

/**
 * Created by XMclassmate on 2019/8/21 13:54
 * Description:
 */
public class MemoryActivity extends BaseActivity<MemoryPresenter> implements MemoryContract.IView, Callback {

    Button btn1;
    ImageView iv1;
    Bitmap bitmap;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_memory;
    }

    /**
     * 初始化presenter
     *
     * @return
     */
    @Override
    protected MemoryPresenter initPresenter() {
        return new MemoryPresenter(this);
    }

    /**
     * 初始化view
     */
    @Override
    protected void initView() {
        btn1 = findViewById(R.id.btn_1);
        iv1 = findViewById(R.id.iv1);
        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.huodong_bg);
        iv1.setImageBitmap(bitmap);
        CallbackManager.addCallback(this);
    }

    /**
     * 设置监听器
     */
    @Override
    protected void setListener() {
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.sendEmptyMessage(0);
            }
        });

    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            presenter.createArray();
            handler.sendEmptyMessageDelayed(0,30);
        }
    };

    @Override
    public void doOperate() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CallbackManager.removeCallback(this);
        bitmap.recycle();
    }
}
