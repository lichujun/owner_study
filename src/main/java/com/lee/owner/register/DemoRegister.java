package com.lee.owner.register;

import com.lee.owner.register.annotation.DemoScan;
import com.lee.owner.register.scanner.DemoScanner;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

/**
 * 注册bean
 * @author lichujun
 * @date 2019/10/20 12:16 PM
 */
public class DemoRegister implements ImportBeanDefinitionRegistrar {

    @SuppressWarnings("NullableProblems")
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(DemoScan.class.getName()));
        if (MapUtils.isEmpty(attributes)) {
            return;
        }
        String[] basePackages = attributes.getStringArray("basePackages");
        DemoScanner demoScanner = new DemoScanner(beanDefinitionRegistry, DemoFactoryBean.class);
        demoScanner.registerFilters();
        demoScanner.scan(basePackages);
    }
}
