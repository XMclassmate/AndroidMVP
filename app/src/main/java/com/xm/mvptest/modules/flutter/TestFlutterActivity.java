package com.xm.mvptest.modules.flutter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.xm.mvptest.R;

import io.flutter.facade.Flutter;
import io.flutter.view.FlutterView;

/**
 * Created by XMclassmate on 2019/3/14 17:35
 * Description:
 */
public class TestFlutterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_flutter);
        FrameLayout container = findViewById(R.id.flutter_view_container);
        final FlutterView flutterView = Flutter.createView(this, getLifecycle(), "route2");
        FlutterView.FirstFrameListener[] listeners = new FlutterView.FirstFrameListener[1];
        listeners[0] = new FlutterView.FirstFrameListener() {
            @Override
            public void onFirstFrame() {
                container.setVisibility(View.VISIBLE);
            }
        };
        flutterView.addFirstFrameListener(listeners[0]);
        container.addView(flutterView);
    }
}
