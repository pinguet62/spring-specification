package fr.pinguet62.springruleengine.core.builder.database.parameter;

import fr.pinguet62.springruleengine.core.config.AutowireCandidateResolverAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.Map;

@Slf4j
public class RuleParameterAutowireCandidateResolver extends AutowireCandidateResolverAdapter {

    /**
     * If {@link RuleParameter}: process {@link RuleParameter#value()}.
     */
    @Override
    public Object getSuggestedValue(DependencyDescriptor descriptor) {
        // See QualifierAnnotationAutowireCandidateResolver#findValue(Annotation[])
        AnnotationAttributes attr = AnnotatedElementUtils.getMergedAnnotationAttributes(
                AnnotatedElementUtils.forAnnotations(descriptor.getAnnotations()), RuleParameter.class);
        if (attr == null) {
            MethodParameter methodParam = descriptor.getMethodParameter();
            if (methodParam != null)
                attr = AnnotatedElementUtils.getMergedAnnotationAttributes(
                        AnnotatedElementUtils.forAnnotations(methodParam.getMethodAnnotations()), RuleParameter.class);
        }
        if (attr != null) {
            String key = (String) attr.get(AnnotationUtils.VALUE); // CustomAnnotation spec
            Map<String, String> parameters = ParameterInjector.CONTEXT.get();
            if (parameters == null)
                log.warn("{}#CONTEXT not initialized", ParameterInjector.class.getSimpleName());
            else {
                String value = parameters.get(key);
                return value;
            }
        }

        return super.getSuggestedValue(descriptor); // default
    }

}
