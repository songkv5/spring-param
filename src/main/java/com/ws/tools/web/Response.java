package com.ws.tools.web;

/**
 * @author willis
 * @chapter
 * @section
 * @since 2018年10月21日 23:41
 */
public class Response<T> {
    private T data;
    private int code;
    private String msg;
    private boolean success;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
