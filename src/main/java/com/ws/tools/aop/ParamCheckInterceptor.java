package com.ws.tools.aop;

import com.ws.tools.Exception.WSException;
import com.ws.tools.annotations.ParamCheck;
import com.ws.tools.annotations.ValidParam;
import com.ws.tools.consts.ErrorConst;
import com.ws.tools.utils.CommonBizUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author willis
 * @chapter 参数校验
 * @section
 * @since 2018年10月21日 23:22
 */
@Aspect
@Component
public class ParamCheckInterceptor {

    private final Logger logger = LoggerFactory.getLogger(ParamCheckInterceptor.class);

    //参数校验拦截器,拦击所有的service注解类下的所有带有paramcheck注解的方法
    @Pointcut("@within(org.springframework.stereotype.Service)) && @target(com.ws.tools.annotations.ParamCheck)")
    public void paramCheckInterceptor() {
    }

    @Around("paramCheckInterceptor()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable{
        Object result = null;
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method method = methodSignature.getMethod();
        ParamCheck annotation = method.getAnnotation(ParamCheck.class);
        //参数检查协议
        int protocol = annotation.protocol();
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return joinPoint.proceed();
        }
        //第一个参数
        Object param = args[0];
        Class<?> clazz = param.getClass();
        List<Field> allFields = getAllField(clazz);
        if (CollectionUtils.isEmpty(allFields)) {
            return joinPoint.proceed();
        }
        allFields.forEach(field -> {
            //校验参数合法性
            ValidParam validParam = field.getAnnotation(ValidParam.class);
            if (validParam == null) {
                return;
            }
            int[] paramProtocols = validParam.protocol();
            if (!ifOwnProtocol(paramProtocols, protocol)) {
                return;
            }

            field.setAccessible(true);
            try {
                Object value = field.get(param);
                //字段名
                String name = field.getName();
                boolean notNull = validParam.notNull();
                if (notNull) {//空校验
                    CommonBizUtils.paramMissingCheck(value, name);
                }
                //和0比较的值，对数字有效
                String compare0 = validParam.compare0();
                if (compare0 != null) {
                    if ("".equals(compare0.trim())) {
                        compare0 = null;
                    }
                }
                if (compare0 != null) {
                    if (value == null) {
                        CommonBizUtils.throwError(ErrorConst.PARAMS_MISSING, name);
                    }
                    if (value instanceof Number) {
                        long longValue = Long.parseLong(value.toString());
                        switch (compare0) {
                            case "=" : {
                                //= 暂时没有什么意义上，先不做处理
                                break;
                            }
                            case ">" : {
                                if (!(longValue > 0)) {
                                    CommonBizUtils.throwError(ErrorConst.PARAM_ILLEGAL, name);
                                }
                                break;
                            }
                            case "<" : {
                                if (!(longValue < 0)) {
                                    CommonBizUtils.throwError(ErrorConst.PARAM_ILLEGAL, name);
                                }
                                break;
                            }
                            case ">=" : {
                                if (!(longValue >= 0)) {
                                    CommonBizUtils.throwError(ErrorConst.PARAM_ILLEGAL, name);
                                }
                                break;
                            }
                            case "<=" : {
                                if (!(longValue <= 0)) {
                                    CommonBizUtils.throwError(ErrorConst.PARAM_ILLEGAL, name);
                                }
                                break;
                            }
                            default:{
                                break;
                            }
                        }
                    } else {
                        CommonBizUtils.throwError(ErrorConst.PARAM_ILLEGAL, "param " + name + " is not a number");
                    }
                }
            } catch (Exception e) {
                logger.error("校验字段", e);
                if (e instanceof WSException) throw (WSException) e;
                else {
                    logger.error("参数校验发生未知异常,", e);
                    CommonBizUtils.throwError(ErrorConst.SYSTEM_ERROR);
                }
            }
        });
        return result;
    }

    private static <T> List<Field> getAllField(final Class<T> clazz) {
        if (clazz == null) {
            return Collections.emptyList();
        }
        List<Field> fields = new ArrayList<>();
        Class<?> crtClazz = clazz;
        while(crtClazz != null) {
            Field[] fieldsTmp = crtClazz.getDeclaredFields();
            if (fieldsTmp != null && fieldsTmp.length > 0) {
                for (Field field : fieldsTmp) {
                    fields.add(field);
                }
            }
            crtClazz = crtClazz.getSuperclass();
        }
        return fields;
    }

    private boolean ifOwnProtocol(int[] protocols, int protocol) {
        if (protocols == null || protocols.length == 0) {
            return false;
        }
        for (int protocolTmp : protocols) {
            if (protocolTmp == protocol) {
                return true;
            }
        }
        return false;
    }

}
