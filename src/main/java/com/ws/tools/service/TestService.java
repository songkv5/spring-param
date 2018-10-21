package com.ws.tools.service;

import com.ws.tools.annotations.ParamCheck;
import com.ws.tools.web.request.TestRequest;
import org.springframework.stereotype.Service;

/**
 * @author willis
 * @chapter
 * @section
 * @since 2018年10月21日 23:23
 */
@Service
public class TestService {
    @ParamCheck
    public Object test(TestRequest request) {
        return request;
    }
}
