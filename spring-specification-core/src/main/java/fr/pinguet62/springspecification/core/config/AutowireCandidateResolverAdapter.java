package fr.pinguet62.springspecification.core.config;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.AutowireCandidateResolver;

/**
 * Sample {@link AutowireCandidateResolver} with default behavior.
 */
public class AutowireCandidateResolverAdapter implements AutowireCandidateResolver {

    @Override
    public boolean isAutowireCandidate(BeanDefinitionHolder bdHolder, DependencyDescriptor descriptor) {
        return false;
    }

    @Override
    public Object getSuggestedValue(DependencyDescriptor descriptor) {
        return null;
    }

    @Override
    public Object getLazyResolutionProxyIfNecessary(DependencyDescriptor descriptor, String beanName) {
        return null;
    }

}
