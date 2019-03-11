package lib.xm.mvp.util.image;

import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by XMclassmate on 2018/5/16.
 */
@GlideExtension
public class CommonGlideExtension {

    private CommonGlideExtension() {
    }

    @GlideOption
    public static void noCache(RequestOptions options) {
        options.diskCacheStrategy(DiskCacheStrategy.NONE);
    }
}
