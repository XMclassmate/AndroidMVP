package com.xm.mvptest.utils;

import com.xm.mvptest.app.MyApplication;
import com.xm.mvptest.bean.BaseResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import lib.xm.mvp.util.DeviceUtils;
import lib.xm.mvp.util.ThreadUtil;
import lib.xm.mvp.util.ToastUtil;

/**
 * Created by XMclassmate on 2018/5/28.
 */

public class HttpUtil {

    /**
     * 获取公共参数
     *
     * @param requestParams
     * @return
     */
    public static Map<String, String> getPostParams(Map<String, String> requestParams) {
        JSONObject obj = new JSONObject();
        Map<String, String> params = new HashMap<>();
        try {
            if (requestParams != null && requestParams.size() > 0) {
                Iterator iterator = requestParams.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    obj.put("" + entry.getKey(), "" + entry.getValue());
                }
            }
            obj.put("accptmd", "2");
            obj.put("branchcode", "247000");
            obj.put("distributorCode", "247");
            obj.put("distributorcode", "247");
            obj.put("branchCode", "247000");
            obj.put("fromApp", "2");
            obj.put("requestuk", UUID.randomUUID().toString().toLowerCase().replaceAll("-", ""));
            obj.put("phoneUnique", DeviceUtils.getLocalMacAddress(MyApplication.getAppContext()));
            obj.put("appVersion", "7.1.3");
            obj.put("phoneName", DeviceUtils.getModel());
            obj.put("phoneVersion", DeviceUtils.getAndroidVersion());
            params.put("params", obj.toString());
            params.put("digparams", MD5Util.md5NoPwd(obj.toString() + MD5Util.KEY));
            return params;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return params;
    }

    /**
     * 校验http请求结果
     *
     * @param observable
     * @param observer
     * @param <T>
     */
    public static <T extends BaseResult> void execute(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.from(ThreadUtil.getmThreadPoolExecutor()))
                .observeOn(Schedulers.from(ThreadUtil.getmThreadPoolExecutor()))
                .doOnNext(new Consumer<T>() {
                    @Override
                    public void accept(T t) throws Exception {
                        switch (t.getErrorCode()) {
                            case "0075"://交易密码错误
                            case "0203"://其他地方登录
                                ToastUtil.toast("其他地方登录");
                                break;
                            case "0228"://登陆超时
                                ToastUtil.toast("登陆超时");
                                break;
                            case "1125"://验证码错误
                            case "0251"://银行预留手机号错误
                            case "3303"://部分卖出成功
                            case "0000":
                                t.setErrorMsg("hhhhhhhhhhhhhhhhhhhhhhhhhhhh");
                                break;
                            default:
                                ToastUtil.toast("default");
                                break;
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
