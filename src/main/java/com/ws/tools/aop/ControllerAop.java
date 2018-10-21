package com.ws.tools.aop;

import com.ws.tools.Exception.WSException;
import com.ws.tools.consts.ErrorConst;
import com.ws.tools.web.Response;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author willis
 * @chapter
 * @section
 * @since 2018年10月21日 23:40
 */
@Component
@Aspect
public class ControllerAop {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerAop.class);
    //拦截com.ws.tools.web包下任意带有注解RestController的类
    @Pointcut("execution(public * com.ws.tools.web.*.*(..)) && @within(org.springframework.web.bind.annotation.RestController) @@annotation()")
    public void aroundPointCut() {
    }

    @Around("aroundPointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        //拿到request对象
        HttpServletRequest request = servletRequestAttributes.getRequest();

        StringBuilder logSb = new StringBuilder();
        String requestUuid = UUID.randomUUID().toString();
        // 拦截请求记录request日志
        //获取ActionDesc注解
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        //请求参数
        Object[] args = pjp.getArgs();
        //取得返回类型的泛型
        Type type = method.getGenericReturnType();
        String typeName = type.getTypeName();

        // 执行接口方法
        long t1 = System.currentTimeMillis();
        Object result = null;
        try {
            result = pjp.proceed();
            if (result instanceof Response) {
                Response response = (Response)result;
                Object data = response.getData();
                if (data == null) {
                    response.setData(new Object());
                    response.setMsg("接口请求成功");
                    result = response;
                }
            }
        } catch (Exception e) {
            Response response = new Response();
            response.setSuccess(false);
            if (type != null){
                //如果返回类型是boolean型,抛出异常时则data字段设为false
                if (typeName.contains("java.lang.Boolean")) {
                    response.setData(Boolean.FALSE);
                }
                if (typeName.contains("java.lang.Integer")) {
                    response.setData(0);
                }
            }
            if (e instanceof WSException) {
                LOGGER.error(">", e);
                WSException exception = (WSException)e;
                response.setCode(exception.getCode());
                response.setMsg(exception.getMessage());
            } else {
                response.setCode(ErrorConst.SYSTEM_ERROR.getCode());
                e.printStackTrace();
                LOGGER.error("接口调用失败,", e);
                response.setMsg(ErrorConst.SYSTEM_ERROR.getMsg());
            }
            result = response;
        }

        long t2 = System.currentTimeMillis();
        logSb.append("result=").append(result.toString()).append(";耗时:[").append(t2 - t1).append("ms]");
        LOGGER.info(logSb.toString());
        return result;
    }

    private Object getAllHeaders(HttpServletRequest request) {
        Map<String, Object> headerMap = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            headerMap.put(headerName, headerValue);
        }
        return headerMap;
    }
}
