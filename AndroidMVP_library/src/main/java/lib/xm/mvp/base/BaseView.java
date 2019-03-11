package lib.xm.mvp.base;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by XMclassmate on 2018/5/11.
 */

public interface BaseView {
    void showProgressPage(@Nullable Object obj);
    void hideProgressPage();
}
