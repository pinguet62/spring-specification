package fr.pinguet62.springruleengine.core.builder.database.factory;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.SimpleAutowireCandidateResolver;

import java.lang.annotation.Annotation;

@Slf4j
public class RuleAutowireCandidateResolver extends SimpleAutowireCandidateResolver {

    /**
     * If {@link RuleChild} or {@link RuleChildren}: inject.<br>
     */
    @Override
    public Object getSuggestedValue(DependencyDescriptor descriptor) {
        for (Annotation annotation : descriptor.getAnnotations())
            if (annotation.annotationType() == RuleChildren.class)
                return RuleInjector.CONTEXT.get();
        for (Annotation annotation : descriptor.getAnnotations())
            if (annotation.annotationType() == RuleChild.class)
                return RuleInjector.CONTEXT.get().get(0); // TODO check unique

        // default
        return super.getSuggestedValue(descriptor);
    }

}
