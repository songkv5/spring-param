package com.ws.tools.consts;

/**
 * @author willis
 * @chapter
 * @section
 * @since 2018年10月21日 23:32
 */
public class ErrorConst {
    public static final ErrorCode PARAMS_MISSING = new ErrorCode(10001, "缺少必要参数");
    public static final ErrorCode PARAM_ILLEGAL = new ErrorCode(10002, "参数不合法");
    public static final ErrorCode SYSTEM_ERROR = new ErrorCode(50000, "系统内部错误");
}
