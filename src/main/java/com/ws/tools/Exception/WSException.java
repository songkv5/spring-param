package com.ws.tools.Exception;

/**
 * @author willis
 * @chapter
 * @section
 * @since 2018年10月21日 23:27
 */
public class WSException extends RuntimeException {
    private int code = 500;
    private String msg;

    public WSException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public WSException() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
