package com.ws.tools.utils;

import com.ws.tools.Exception.WSException;
import com.ws.tools.consts.ErrorCode;
import com.ws.tools.consts.ErrorConst;

import java.util.List;

/**
 * @author willis/songkai
 * @desc
 * @since 2018年06月14日 20:15
 */
public class CommonBizUtils {
    /**
     * 参数检查
     * @param param
     * @param ext
     */
    public static void paramMissingCheck(Object param, String ext) {
        if (param == null) {
            throwError(ErrorConst.PARAMS_MISSING, ext);
        }
        if (param instanceof Number) {
            if (((Number) param).longValue() < 0) {
                throwError(ErrorConst.PARAMS_MISSING, ext);
            }
        }
        if (param instanceof String) {
            if (param != null) {
                if ("".equals(((String) param).trim())) {
                    param = null;
                }
            }
            if (param == null) {
                throwError(ErrorConst.PARAMS_MISSING, ext);
            }
        }
        if (param instanceof List) {
            if (((List) param).isEmpty()) {
                throwError(ErrorConst.PARAMS_MISSING, ext);
            }
        }

    }

    public static void paramMissingCheck(Object param) {
        paramMissingCheck(param, null);
    }

    public static void throwError(ErrorCode errorCode, String ext) {
        if (ext != null) {
            if ("".equals(ext.trim())) {
                ext = null;
            }
        }
        String msgExt = ext == null ? errorCode.getMsg() : errorCode.getMsg() + ":" + ext;
        throw new WSException(errorCode.getCode(), msgExt);
    }

    public static void throwError(int code, String msg) {
        throw new WSException(code, msg);
    }
    public static void throwError(ErrorCode errorCode) {
        throwError(errorCode, null);
    }


    public static String num2String(Number number) {
        if (number == null) {
            return null;
        }
        return String.format("%.2f", number);
    }

}
