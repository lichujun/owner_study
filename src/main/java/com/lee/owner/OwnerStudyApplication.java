package com.lee.owner;

import com.lee.owner.interceptor.HttpInterceptor;
import com.lee.owner.register.annotation.DemoScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@DemoScan(basePackages = "com.lee.owner.register.demo")
@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy=true)
@EnableTransactionManagement
public class OwnerStudyApplication implements WebMvcConfigurer {

    private final HttpInterceptor httpInterceptor;

    public OwnerStudyApplication(HttpInterceptor httpInterceptor) {
        this.httpInterceptor = httpInterceptor;
    }

    public static void main(String[] args) {
        SpringApplication.run(OwnerStudyApplication.class, args);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(httpInterceptor);
    }
}
