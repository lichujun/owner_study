package com.lee.owner.demo;

import com.lee.owner.pojo.dto.DemoDTO;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author joseph.li
 * @date 2020/4/22 11:54 上午
 */
@Configuration
public class ConditionDemo {

    @Bean
    @ConditionalOnMissingClass("com.Test")
    public DemoDTO demoDTO() {
        return new DemoDTO();
    }
}
