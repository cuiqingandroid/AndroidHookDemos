package com.okex.net;


import org.json.JSONObject;

public class ErrorStatus {
    /**
     * 网络请求错误
     */
    public final static int IO_EXCEPTION = -1;
    /**
     *
     */
    public final static int DATA_EMPTY = -10001;

    /**
     * 数据格式错误
     */
    public static final int DATA_ERROR = -10002;



    public static String getErrorMsg(int errorCode) {
        if (errorCode == DATA_EMPTY) {
            return "没有数据";
        } else if (errorCode == DATA_ERROR) {
            return "数据格式错误";
        }
        return "IO网络错误";
    }

    private int errorCode;

    private String errorMsg;

    private boolean isHttp = false;

    public void fromJson(JSONObject jsonObject) {
        if (null == jsonObject) {
            return;
        }

        this.errorCode = jsonObject.optInt("errno", IO_EXCEPTION);
        this.errorMsg = jsonObject.optString("errmsg");
    }

    public ErrorStatus() {
    }

    public ErrorStatus(int errorCode) {
        this.errorCode = errorCode;
        this.errorMsg = getErrorMsg(errorCode);
    }

    public ErrorStatus(int errorCode, String msg) {
        this.errorCode = errorCode;
        this.errorMsg = msg;
    }


    /**
     * 是否是http错误
     */
    @SuppressWarnings("unused")
    public boolean isHttpError() {
        return isHttp;
    }

    public boolean hasError() {
        return errorCode != 0 && errorCode != 200;
    }

    public String getErrorMsg(){
        return errorMsg;
    }

    /**
     * 数据内容错误
     */
    public static ErrorStatus dataError() {
        return new ErrorStatus(DATA_ERROR);
    }

    /**
     * 无数据内容
     */
    public static ErrorStatus dataEmptyError() {
        return new ErrorStatus(DATA_ERROR);
    }

    /**
     * http 网络错误
     *
     * @param httpCode 错误码：例如500
     */
    public static ErrorStatus networkError(int httpCode) {
        ErrorStatus status = new ErrorStatus(httpCode, "网络错误");
        status.isHttp = true;
        return status;
    }

}
