package com.lee.owner.register.annotation;

import java.lang.annotation.*;

/**
 * @author lichujun
 * @date 2019/10/20 3:52 PM
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DemoAnnotation {
    String value() default "";
}

