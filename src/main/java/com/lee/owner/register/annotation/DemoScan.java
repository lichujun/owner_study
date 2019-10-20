package com.lee.owner.register.annotation;

import com.lee.owner.register.DemoRegister;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author lichujun
 * @date 2019/10/20 12:17 PM
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(DemoRegister.class)
public @interface DemoScan {
    String[] basePackages() default "";
}
