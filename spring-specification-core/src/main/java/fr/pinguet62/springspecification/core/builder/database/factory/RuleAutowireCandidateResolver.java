package fr.pinguet62.springspecification.core.builder.database.factory;

import fr.pinguet62.springspecification.core.config.AutowireCandidateResolverAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.DependencyDescriptor;

import java.lang.annotation.Annotation;

@Slf4j
public class RuleAutowireCandidateResolver extends AutowireCandidateResolverAdapter {

    /**
     * If {@link RuleChild} or {@link RuleChildren}: inject.
     */
    @Override
    public Object getSuggestedValue(DependencyDescriptor descriptor) {
        for (Annotation annotation : descriptor.getAnnotations())
            if (annotation.annotationType() == RuleChildren.class)
                return RuleInjector.CONTEXT.get();
        for (Annotation annotation : descriptor.getAnnotations())
            if (annotation.annotationType() == RuleChild.class)
                return RuleInjector.CONTEXT.get().get(0); // TODO check unique

        return super.getSuggestedValue(descriptor); // default
    }

}
