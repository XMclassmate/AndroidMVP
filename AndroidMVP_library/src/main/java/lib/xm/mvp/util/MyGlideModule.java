package lib.xm.mvp.util;

import android.content.Context;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.module.GlideModule;

/**
 * Created by XMclassmate on 2018/5/16.
 */

public class MyGlideModule implements GlideModule {


    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
    }

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {

    }
}
