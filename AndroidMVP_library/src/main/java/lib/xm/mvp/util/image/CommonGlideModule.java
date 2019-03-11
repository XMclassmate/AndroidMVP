package lib.xm.mvp.util.image;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import lib.xm.mvp.util.SSLUtil;
import lib.xm.mvp.util.log.LogUtils;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by XMclassmate on 2018/5/16.
 */
@GlideModule
public class CommonGlideModule extends AppGlideModule {
    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpGlideUrlLoader.Factory(getOkHttpClient()));
    }

    private OkHttpClient getOkHttpClient() {
        OkHttpClient okHttpClient = null;
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtils.e(message);
            }
        });
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
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
}
