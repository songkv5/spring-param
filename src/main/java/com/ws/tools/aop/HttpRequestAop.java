package com.ws.tools.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author willis
 * @chapter
 * @section
 * @since 2018年10月21日 23:48
 */
@Component
public class HttpRequestAop extends HandlerInterceptorAdapter {

    private final Logger logger = LoggerFactory.getLogger(HttpRequestAop.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        StringBuilder logSb = new StringBuilder();
        Object allHeaders = getAllHeaders(request);
        Object allParams = getAllParams(request);
        String requestMethod = request.getMethod();
        logSb.append("收到远程地址->").append(request.getRemoteHost())
            .append("(").append(request.getRemoteAddr()).append(")")
            .append("的").append(requestMethod).append("请求。")
            .append("Request Path=").append(request.getRequestURI()).append(";")
            //可以用转换成json字符串再打印
            .append("Header=").append(allHeaders.toString()).append(";")
            .append("Request Param=").append(allParams.toString()).append(";");
        if ("post".equalsIgnoreCase(requestMethod)) {
            Object requestBody = getRequestBody(request);
            if (requestBody != null) {
                logSb.append("Request Body=");
            }
            if (requestBody instanceof String) {
                logSb.append(requestBody.toString());
            } else {
                logSb.append(requestBody.toString());
            }
        }
        logger.info(logSb.toString());
        return super.preHandle(request, response, handler);
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
    private Object getAllParams(HttpServletRequest request) {
        return request.getParameterMap();
    }
    private Object getRequestBody(HttpServletRequest request) {
        try {
            ServletInputStream inputStream = request.getInputStream();
            byte[] bytes = new byte[1024];
            int index = 0;
            StringBuffer sb = new StringBuffer();
            while(( index = inputStream.read(bytes, 0, bytes.length)) > 0) {
                sb.append(new String(bytes, 0, index, "utf-8"));
            }
            return sb.toString();
        } catch (Exception e) {
            logger.error("解析 requestbody 异常", e);
        }
        return "";
    }

}
