package fr.pinguet62.springruleengine.core.builder.database.parameter;

import fr.pinguet62.springruleengine.core.api.Rule;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.core.annotation.AnnotationUtils.findAnnotation;

@Service
public class ParameterService {

    public @NotNull Set<String> getDeclaratedKeys(@NotNull Class<Rule<?>> ruleType) {
        Set<String> keys = new HashSet<>();

        // TODO SmartInstantiationAwareBeanPostProcessor#determineCandidateConstructors(Class<?> beanClass, String beanName)
        for (Constructor constructor : ruleType.getDeclaredConstructors()) {
            for (Parameter parameter : constructor.getParameters()) {
                RuleParameter annotation = findAnnotation(parameter, RuleParameter.class);
                if (annotation == null)
                    continue;
                keys.add(annotation.value());
            }
        }

        // TODO DependencyDescriptor & InjectedElement in accordance with AutowireCandidateResolverAdapter#getSuggestedValue(DependencyDescriptor)
        for (Field field : ruleType.getDeclaredFields()) {
            RuleParameter annotation = findAnnotation(field, RuleParameter.class);
            if (annotation == null)
                continue;
            keys.add(annotation.value());
        }

        // process setters
        for (Method method : ruleType.getDeclaredMethods()) {
            RuleParameter annotation = findAnnotation(method, RuleParameter.class);
            if (annotation == null)
                continue;
            keys.add(annotation.value());
        }

        return keys;
    }

}
