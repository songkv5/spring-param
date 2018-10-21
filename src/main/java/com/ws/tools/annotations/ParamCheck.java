package com.ws.tools.annotations;

import java.lang.annotation.*;

/**
 * @author willis<willissong.7@gmail.com>
 * @chapter 参数检查
 * @section
 * @since 2018年10月19日 14:18
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamCheck {
    //协议
    int protocol() default 0;
}
