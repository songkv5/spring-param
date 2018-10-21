package com.ws.tools.annotations;

import java.lang.annotation.*;

/**
 * @author willis<willissong.7@gmail.com>
 * @chapter 字段校验
 * @section
 * @since 2018年10月19日 14:22
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidParam {
    boolean notNull() default false;
    //"=" 或 ">" 或 ">=" 或 "<" 或 "<=";对数字有效
    String compare0() default "";

    /**
     * 校验协议,同
     * {@link ParamCheck}
     * 作为校验约定
     */

    int[] protocol() default {0};
}
