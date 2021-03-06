package fr.pinguet62.springspecification.core.builder.database.autoconfigure;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

/**
 * Restore default priority of Spring Boot auto-configurated {@link Bean}s if necessary,
 * in order to avoid multiple bean definition caused by those generated by Spring Specification.
 *
 * @see SpringSpecificationBean
 */
public class FixPrimaryBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    /**
     * From {@link Class type}, if (in addition to Spring Specification {@link Bean}s):
     * <ul>
     * <li>there is only 1 {@link Bean} found: declare as {@link BeanDefinition#isPrimary() primary};</li>
     * <li>there are many {@link Bean}s found: keep current configuration, because user had to define his own configuration.</li>
     * </ul>
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        for (SpringSpecificationBean bean : SpringSpecificationBean.values()) {
            String[] beanNames = beanFactory.getBeanNamesForType(bean.getBeanType());
            if (beanNames.length != 2)
                continue;
            String userBeanName = Arrays
                    .stream(beanNames)
                    .filter(bd -> !bd.equals(bean.getBeanName()))
                    .findFirst() // must be unique
                    .orElseThrow(RuntimeException::new)
                    .replaceFirst("^&", "")/*fix factory Spring prefix name*/;
            beanFactory.getBeanDefinition(userBeanName).setPrimary(true);
        }
    }

    /**
     * No action.
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
    }

}
