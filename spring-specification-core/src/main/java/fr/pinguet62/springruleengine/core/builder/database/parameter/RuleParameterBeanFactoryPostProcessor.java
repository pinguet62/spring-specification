package fr.pinguet62.springruleengine.core.builder.database.parameter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AutowireCandidateResolver;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * Register {@link RuleParameterAutowireCandidateResolver}.
 */
@Component
public class RuleParameterBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) beanFactory;
        AutowireCandidateResolver autowireCandidateResolver = new RuleParameterAutowireCandidateResolver();
        defaultListableBeanFactory.setAutowireCandidateResolver(autowireCandidateResolver);
    }

}
