package com.xm.mvptest.serverApi;



import com.xm.mvptest.bean.GetUserResult;
import com.xm.mvptest.constant.UrlFormat;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by XMclassmate on 2018/5/17.
 */

public interface UserApi {

    @FormUrlEncoded
    @POST(UrlFormat.GET_APP_MAIN_URL_FORMAT)
    Observable<GetUserResult> getUser(@FieldMap Map<String, String> params);
}
