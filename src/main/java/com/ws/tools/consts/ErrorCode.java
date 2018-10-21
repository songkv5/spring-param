package com.ws.tools.consts;

import java.io.Serializable;

/**
 * @author willis
 * @chapter
 * @section
 * @since 2018年10月21日 23:32
 */
public class ErrorCode implements Serializable{
    private int code;
    private String msg;

    public ErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }
}
