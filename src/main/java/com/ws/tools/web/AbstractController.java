package com.ws.tools.web;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * @author willis
 * @chapter
 * @section
 * @since 2018年10月21日 23:18
 */
public class AbstractController {
    @Autowired
    private HttpServletRequest request;

}
