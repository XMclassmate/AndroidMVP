package lib.xm.mvp.util.image;

import android.content.Context;
import android.widget.ImageView;


/**
 * 图片加载类，基于Glide.
 */

public class ImageLoadUtil {

    public static void loadImage(Context context, ImageView imageView, String url) {
        GlideApp.with(context)
                .load(url)
                .centerCrop()
                .into(imageView);
    }

    /**
     * 加载图片带默认图
     *
     * @param context
     * @param imageView
     * @param url
     * @param defaultResId
     */
    public static void loadImage(Context context, ImageView imageView, String url, int defaultResId) {
        GlideApp.with(context)
                .load(url)
                .placeholder(defaultResId)
                .error(defaultResId)
                .centerCrop()
                .into(imageView);
    }

    /**
     * 指定图片大小
     *
     * @param context
     * @param imageView
     * @param url
     * @param width
     * @param heigh
     * @param defaultResId
     */
    public static void loadImage(Context context, ImageView imageView, String url, int width, int heigh, int defaultResId) {
        GlideApp.with(context)
                .load(url)
                .placeholder(defaultResId)
                .error(defaultResId)
                .centerCrop()
                .override(width, heigh)
                .into(imageView);
    }

    /**
     * 不适用缓存
     *
     * @param context
     * @param imageView
     * @param url
     * @param defaultResId
     */
    public static void loadImageNoCache(Context context, ImageView imageView, String url, int defaultResId) {
        GlideApp.with(context)
                .load(url)
                .placeholder(defaultResId)
                .error(defaultResId)
                .centerCrop()
                .noCache()
                .into(imageView);
    }

    /**
     * 加载圆形图片
     *
     * @param context
     * @param imageView
     * @param url
     * @param defaultResId
     */
    public static void loadCircleImage(Context context, ImageView imageView, String url, int defaultResId) {
        GlideApp.with(context)
                .load(url)
                .placeholder(defaultResId)
                .error(defaultResId)
                .circleCrop()
                .into(imageView);
    }

    /**
     * 加载圆角图片
     *
     * @param context
     * @param imageView
     * @param url
     * @param radius
     * @param defaultResId
     */
    public static void loadRoundImage(Context context, ImageView imageView, String url, int radius, int defaultResId) {
        GlideApp.with(context)
                .load(url)
                .placeholder(defaultResId)
                .error(defaultResId)
                .transform(new RoundBitmapTransform(radius))
                .into(imageView);
    }
}
