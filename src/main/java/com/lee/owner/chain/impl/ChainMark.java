package com.lee.owner.chain.impl;

import java.lang.annotation.*;

/**
 * @author joseph.li
 * @date 2020/4/24 12:25 下午
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ChainMark {

    int value() default 0;
}
