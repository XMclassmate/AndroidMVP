package lib.xm.mvp.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.wang.avi.AVLoadingIndicatorView;
import com.xm.mvp.R;

/**
 *
 * @author XMclassmate
 * @date 2017/4/12
 */

public class ProgressDialog extends Dialog {

    private Context context;
    private TextView textView;
    private AVLoadingIndicatorView loadingView;

    public ProgressDialog(Context context, int theme, int color, String msg) {
        super(context, theme);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.progress_dialog_view, null);
        textView = (TextView) view.findViewById(R.id.tv_progress);
        loadingView = (AVLoadingIndicatorView) view.findViewById(R.id.loading);
        loadingView.setIndicatorColor(color);
        if (TextUtils.isEmpty(msg)) {
            textView.setVisibility(View.GONE);
        }else{
            textView.setVisibility(View.VISIBLE);
            textView.setText(msg);
        }
        setContentView(view);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = (int) (getWidth()*0.5);
        params.height = (int) (params.width*0.5);
        getWindow().setAttributes(params);

        //设置动画
        getWindow().setWindowAnimations(R.style.DialogAnim);
    }


    private float getWidth() {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Activity activity = (Activity) context;
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }
}
