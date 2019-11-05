package lib.xm.mvp.bean;

/**
 * Created by XMclassmate on 2019/11/5 16:40
 * Description:
 */
public class OKHttpEventBean {

    public long dnsStart;
    public long dnsEnd;
    public long responseBodySize;
    public boolean apiSuccess;
    public String errorMsg;

    @Override
    public String toString() {
        return "OKHttpEventBean{" +
                "dnsStart=" + dnsStart +
                ", dnsEnd=" + dnsEnd +
                ", responseBodySize=" + responseBodySize +
                ", apiSuccess=" + apiSuccess +
                ", errorMsg='" + errorMsg + '\'' +
                '}';
    }
}
