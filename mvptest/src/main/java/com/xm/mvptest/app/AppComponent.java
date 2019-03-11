package com.xm.mvptest.app;

import com.xm.mvptest.modules.test.TestPresenter;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by XMclassmate on 2018/10/15
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    MyApplication getApplication();

    OkHttpClient getOkhttpClient();

    Retrofit getRetrofit();

    void inject(TestPresenter testPresenter);
}
