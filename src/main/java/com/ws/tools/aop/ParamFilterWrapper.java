package com.ws.tools.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author willis
 * @chapter 自定义请求的servletrequest
 * @section
 * @since 2018年10月21日 23:45
 */
public class ParamFilterWrapper extends HttpServletRequestWrapper {

    private final Logger logger = LoggerFactory.getLogger(ParamFilterWrapper.class);
    //暂存requestBody
    private byte[] body;
    //暂存stream
    private ServletInputStream inputStream;

    public ParamFilterWrapper(HttpServletRequest request) {
        super(request);
        try {
            this.inputStream = request.getInputStream();
            byte[] bytes = new byte[1024];
            int index = 0;
            StringBuffer sb = new StringBuffer();
            while(( index = inputStream.read(bytes, 0, bytes.length)) > 0) {
                sb.append(new String(bytes, 0, index, "utf-8"));
            }
            body = sb.toString().getBytes();
        } catch (Exception e) {
            logger.error("解析 requestbody 异常", e);
            body =  new byte[0];
            this.inputStream = null;
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (inputStream == null)
            return super.getInputStream();

        final ByteArrayInputStream bais = new ByteArrayInputStream(body);

        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener listener) {

            }

            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

}
