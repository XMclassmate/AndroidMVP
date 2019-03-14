package com.xm.mvptest.bean;

/**
 * Created by XMclassmate on 2018/5/28.
 */

public class BaseResult extends BaseBean {

    private String errorCode;
    private String errorMsg;
    private String safetyAssurance;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getSafetyAssurance() {
        return safetyAssurance;
    }

    public void setSafetyAssurance(String safetyAssurance) {
        this.safetyAssurance = safetyAssurance;
    }
}
