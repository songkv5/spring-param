package com.ws.tools.config;

import com.ws.tools.aop.HttpParamFilter;
import com.ws.tools.aop.HttpRequestAop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.Filter;

/**
 * @author willis
 * @chapter
 * @section
 * @since 2018年10月21日 23:53
 */
@Configuration
public class SpringConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private HttpRequestAop httpRequestAop;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(httpRequestAop).addPathPatterns("/**/*");
        super.addInterceptors(registry);
    }
    @Bean
    public FilterRegistrationBean traceIdHttpFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(customHttpTraceFilter());
        registration.addUrlPatterns("/*");
        registration.setName("customHttpTraceFilter");
        registration.setOrder(2);
        return registration;
    }
    @Bean(name = "customHttpTraceFilter")
    public Filter customHttpTraceFilter() {
        return new HttpParamFilter();
    }
}
