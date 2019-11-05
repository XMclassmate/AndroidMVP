package lib.xm.mvp.listener;

import android.util.Log;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

import lib.xm.mvp.bean.OKHttpEventBean;
import lib.xm.mvp.util.log.LogUtils;
import okhttp3.Call;
import okhttp3.EventListener;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by XMclassmate on 2019/11/5 16:34
 * Description:
 */
public class OkHttpEventListener extends EventListener {

    public static final Factory FACTORY = new Factory() {
        @Override
        public EventListener create(Call call) {
            return new OkHttpEventListener();
        }
    };

    private OKHttpEventBean okHttpEventBean;

    public OkHttpEventListener() {
        super();
        okHttpEventBean = new OKHttpEventBean();
    }

    @Override
    public void dnsStart(Call call, String domainName) {
        super.dnsStart(call, domainName);
        okHttpEventBean.dnsStart = System.currentTimeMillis();
    }

    @Override
    public void dnsEnd(Call call, String domainName, List<InetAddress> inetAddressList) {
        super.dnsEnd(call, domainName, inetAddressList);
        okHttpEventBean.dnsEnd = System.currentTimeMillis();
    }

    @Override
    public void requestBodyStart(Call call) {
        super.requestBodyStart(call);
    }

    @Override
    public void requestBodyEnd(Call call, long byteCount) {
        super.requestBodyEnd(call, byteCount);
    }

    @Override
    public void requestHeadersStart(Call call) {
        super.requestHeadersStart(call);
    }

    @Override
    public void requestHeadersEnd(Call call, Request request) {
        super.requestHeadersEnd(call, request);
    }

    @Override
    public void responseBodyStart(Call call) {
        super.responseBodyStart(call);
    }

    @Override
    public void responseBodyEnd(Call call, long byteCount) {
        super.responseBodyEnd(call, byteCount);
        okHttpEventBean.responseBodySize = byteCount;
    }

    @Override
    public void responseHeadersStart(Call call) {
        super.responseHeadersStart(call);
    }

    @Override
    public void responseHeadersEnd(Call call, Response response) {
        super.responseHeadersEnd(call, response);
    }

    @Override
    public void callEnd(Call call) {
        super.callEnd(call);
        okHttpEventBean.apiSuccess = true;
        LogUtils.e("本次请求成功："+ okHttpEventBean.toString());
    }

    @Override
    public void callFailed(Call call, IOException ioe) {
        super.callFailed(call, ioe);
        okHttpEventBean.apiSuccess = false;
        okHttpEventBean.errorMsg = Log.getStackTraceString(ioe);
        LogUtils.e("本次请求失败："+ okHttpEventBean.toString());
    }
}
