package lib.xm.mvp.util.image;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

/**
 * Created by XMclassmate on 2018/5/17.
 */

public class RoundBitmapTransform extends BitmapTransformation {

    private int radius;

    public RoundBitmapTransform(int radius) {
        this.radius = radius;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        if (toTransform == null) {
            return null;
        }
        int width = Math.min(toTransform.getWidth(), toTransform.getHeight());
        Bitmap souce = Bitmap.createBitmap(toTransform, (toTransform.getWidth() - width) / 2, (toTransform.getHeight() - width) / 2, width, width);
        Bitmap bitmap = pool.get(width, width, Bitmap.Config.ARGB_8888);
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(souce, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        RectF rectF = new RectF(0f, 0f, souce.getWidth(), souce.getHeight());
        paint.setAntiAlias(true);
        canvas.drawRoundRect(rectF, radius, radius, paint);

        return souce;
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

    }
}
