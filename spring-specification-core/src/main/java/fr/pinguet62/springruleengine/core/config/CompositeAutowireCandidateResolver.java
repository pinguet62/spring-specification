package fr.pinguet62.springruleengine.core.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.AutowireCandidateResolver;

public class CompositeAutowireCandidateResolver implements AutowireCandidateResolver, BeanFactoryAware {

    private final AutowireCandidateResolver[] autowireCandidateResolvers;

    public CompositeAutowireCandidateResolver(AutowireCandidateResolver... autowireCandidateResolvers) {
        this.autowireCandidateResolvers = autowireCandidateResolvers;
    }

    @Override
    public boolean isAutowireCandidate(BeanDefinitionHolder bdHolder, DependencyDescriptor descriptor) {
        for (AutowireCandidateResolver autowireCandidateResolver : autowireCandidateResolvers)
            if (autowireCandidateResolver.isAutowireCandidate(bdHolder, descriptor))
                return true;
        return false;
    }

    @Override
    public Object getSuggestedValue(DependencyDescriptor descriptor) {
        for (AutowireCandidateResolver autowireCandidateResolver : autowireCandidateResolvers) {
            Object result = autowireCandidateResolver.getSuggestedValue(descriptor);
            if (result != null)
                return result;
        }
        return null;
    }

    @Override
    public Object getLazyResolutionProxyIfNecessary(DependencyDescriptor descriptor, String beanName) {
        for (AutowireCandidateResolver autowireCandidateResolver : autowireCandidateResolvers) {
            Object result = autowireCandidateResolver.getLazyResolutionProxyIfNecessary(descriptor, beanName);
            if (result != null)
                return result;
        }
        return null;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        for (AutowireCandidateResolver autowireCandidateResolver : autowireCandidateResolvers)
            if (autowireCandidateResolver instanceof BeanFactoryAware)
                ((BeanFactoryAware) autowireCandidateResolver).setBeanFactory(beanFactory);
    }

}
