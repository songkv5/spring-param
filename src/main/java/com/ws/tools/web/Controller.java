package com.ws.tools.web;

import com.ws.tools.service.TestService;
import com.ws.tools.web.request.TestRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author willis
 * @chapter
 * @section
 * @since 2018年10月21日 23:57
 */
@RestController
@RequestMapping("/")
public class Controller extends AbstractController{
    @Autowired
    private TestService service;

    @RequestMapping("/test")
    public Object test(TestRequest request) {
        return service.test(request);
    }
}
