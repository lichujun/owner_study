package com.lee.owner;

import com.lee.owner.interceptor.HttpInterceptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
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
