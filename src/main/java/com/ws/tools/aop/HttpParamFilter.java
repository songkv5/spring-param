package com.ws.tools.aop;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author willis
 * @chapter 定制filter，打印参数
 * @section
 * @since 2018年10月21日 23:44
 */
public class HttpParamFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
        throws IOException, ServletException {
        final HttpServletRequest req = (HttpServletRequest)servletRequest;
        if ("POST".equals(req.getMethod())) {
            ServletRequest reqq = new ParamFilterWrapper((HttpServletRequest) servletRequest);
            filterChain.doFilter(reqq, servletResponse);
        } else {
            filterChain.doFilter(req, servletResponse);
        }
    }
}
