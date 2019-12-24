package com.lee.owner.interceptor;

import com.lee.owner.context.DataContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author joseph.li
 * @date 2019-10-09 14:18
 */
@Component
public class HttpInterceptor extends HandlerInterceptorAdapter implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        applicationContext.getBeansOfType(DataContext.class)
                .values()
                .forEach(DataContext::releaseResource);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
