package com.xm.mvptest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ProgressBar;

/**
 * Created by XMclassmate on 2018/4/9.
 */

public class TextProgressbar extends ProgressBar {

    private int finishColor;
    private int unFinishColor;
    private int textColor;
    private int textSize;
    private Paint paint;

    public TextProgressbar(Context context) {
        this(context, null, 0);
    }

    public TextProgressbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextProgressbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

//        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, com.xm.mvp.R.styleable.TextProgressbar, defStyleAttr, 0);
//        for (int i = 0; i < ta.getIndexCount(); i++) {
//            int attr = ta.getIndex(i);
//            switch (attr) {
//                case com.xm.mvp.R.styleable.TextProgressbar_finish_color:
//                    finishColor = ta.getColor(attr, Color.WHITE);
//                    break;
//                case com.xm.mvp.R.styleable.TextProgressbar_unfinish_color:
//                    unFinishColor = ta.getColor(attr, Color.WHITE);
//                    break;
//                case com.xm.mvp.R.styleable.TextProgressbar_text_color:
//                    textColor = ta.getColor(attr, Color.RED);
//                    break;
//                case com.xm.mvp.R.styleable.TextProgressbar_text_size:
//                    textSize = ta.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, context.getResources().getDisplayMetrics()));
//                    break;
//                default:
//                    break;
//            }
//        }
//        ta.recycle();
//
//        paint = new Paint();
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        int pWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        int pHeight = getHeight() - getPaddingTop() - getPaddingBottom();
        float progress = getProgress() / getMax();
        float finishProgress = pWidth*progress;
        float unfinishProgress = pWidth - finishProgress;

        paint.setStrokeWidth(2);
        paint.setColor(finishColor);
        canvas.drawLine(getPaddingLeft(),height/2,getPaddingLeft()+finishProgress,height/2,paint);
    }
}
