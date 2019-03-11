package com.xm.mvptest.app;

import com.xm.mvptest.constant.UrlFormat;
import com.xm.mvptest.serverApi.UserApi;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import lib.xm.mvp.util.MyHttpLogIntercepter;
import lib.xm.mvp.util.SSLUtil;
import lib.xm.mvp.util.log.LogUtils;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by XMclassmate on 2018/10/15
 */
@Module
public class AppModule {

    private MyApplication application;

    public AppModule(MyApplication myApplication) {
        this.application = myApplication;
    }

    @Singleton
    @Provides
    public MyApplication provideApplication(){
        return this.application;
    }

    @Singleton
    @Provides
    public OkHttpClient provideOkhttpClient(){
        OkHttpClient okHttpClient = null;
        MyHttpLogIntercepter interceptor = new MyHttpLogIntercepter(false);

        if (SSLUtil.getSocketFactory() != null) {
            if (SSLUtil.getTrustManger() != null) {
                okHttpClient = new OkHttpClient().newBuilder().sslSocketFactory(SSLUtil.getSocketFactory(), SSLUtil.getTrustManger())
                        .addInterceptor(interceptor)
                        .readTimeout(1, TimeUnit.MINUTES)
                        .connectTimeout(1, TimeUnit.MINUTES)
                        .writeTimeout(1, TimeUnit.MINUTES)
                        .build();
            } else {
                okHttpClient = new OkHttpClient().newBuilder().sslSocketFactory(SSLUtil.getSocketFactory())
                        .addInterceptor(interceptor)
                        .readTimeout(1, TimeUnit.MINUTES)
                        .connectTimeout(1, TimeUnit.MINUTES)
                        .writeTimeout(1, TimeUnit.MINUTES)
                        .build();
            }
        } else {
            LogUtils.e("证书校验报错了!");
            okHttpClient = new OkHttpClient().newBuilder()
                    .addInterceptor(interceptor)
                    .readTimeout(1, TimeUnit.MINUTES)
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .writeTimeout(1, TimeUnit.MINUTES)
                    .build();
        }
        return okHttpClient;
    }

    @Singleton
    @Provides
    public Retrofit provideRetrofit(OkHttpClient client){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlFormat.mserverAPIUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }

    @Singleton
    @Provides
    public UserApi provideUserApi(Retrofit retrofit) {
        return retrofit.create(UserApi.class);
    }
}
