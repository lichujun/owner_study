package com.lee.owner.register.scanner;

import com.lee.owner.register.DemoFactoryBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

/**
 * @author lichujun
 * @date 2019/10/20 12:20 PM
 */
@Slf4j
public class DemoScanner extends ClassPathBeanDefinitionScanner {

    private final Class<? extends DemoFactoryBean> factoryClass;

    public DemoScanner(BeanDefinitionRegistry registry, Class<? extends DemoFactoryBean> factoryClass) {
        super(registry, false);
        this.factoryClass = factoryClass;
    }

    public void registerFilters() {
        // default include filter that accepts all classes
        addIncludeFilter((metadataReader, metadataReaderFactory) -> true);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> beanDefinitionHolders = super.doScan(basePackages);
        if (beanDefinitionHolders.isEmpty()) {
            log.warn("No interface was found in '" + Arrays.toString(basePackages)
                    + "' package. Please check your configuration.");
        } else {
            processBeanDefinitions(beanDefinitionHolders);
        }
        return beanDefinitionHolders;
    }

    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitionHolders) {
        GenericBeanDefinition beanDefinition;
        String beanClassName;
        for (BeanDefinitionHolder beanDefinitionHolder : beanDefinitionHolders) {
            beanDefinition = (GenericBeanDefinition) beanDefinitionHolder.getBeanDefinition();
            beanClassName = beanDefinition.getBeanClassName();
            Objects.requireNonNull(beanClassName);
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(beanClassName);
            beanDefinition.setBeanClass(factoryClass);
        }
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }
}
