package com.lee.owner.register;

import com.lee.owner.register.annotation.DemoAnnotation;
import org.springframework.beans.factory.FactoryBean;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * @author lichujun
 * @date 2019/10/20 4:54 PM
 */
public class DemoFactoryBean implements FactoryBean<Object> {

    private static final InvocationHandler INVOCATION_HANDLER = (proxy, method, args) -> {
        DemoAnnotation demoAnnotation = method.getAnnotation(DemoAnnotation.class);
        if (demoAnnotation == null) {
            System.out.println("ops");
            return null;
        } else {
            System.out.println(demoAnnotation.value());
            return null;
        }
    };

    private static final ClassLoader CLASS_LOADER = DemoFactoryBean.class.getClassLoader();

    private Class<?> interfaceClass;

    public DemoFactoryBean(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    @Override
    public Object getObject() {
        Class<?>[] clazzArr = {interfaceClass};
        @SuppressWarnings("unchcked")
        Object factory = Proxy.newProxyInstance(CLASS_LOADER, clazzArr, INVOCATION_HANDLER);
        return factory;
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceClass;
    }

}
