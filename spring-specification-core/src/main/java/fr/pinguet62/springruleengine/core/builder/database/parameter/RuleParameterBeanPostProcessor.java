package fr.pinguet62.springruleengine.core.builder.database.parameter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.function.BiFunction;

// TODO Spring workflow (fix parameter with prototype scope?)

/**
 * Process each {@link Field} and {@link Method setter} annotated with {@link RuleParameter}.
 */
public class RuleParameterBeanPostProcessor implements BeanPostProcessor {

    private final Map<String, Object> parameters;

    public RuleParameterBeanPostProcessor(Map<String, Object> parameters) {
        this.parameters = parameters;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        processRuleParameterAnnotations(bean);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public void processRuleParameterAnnotations(Object rule) {
        processRuleParameterAnnotations(rule, (a, p) -> p.get(a.value()), RuleParameter.class);
        processRuleParameterAnnotations(rule, (a, p) -> p, RuleParameterMap.class);
    }

    public <A extends Annotation> void processRuleParameterAnnotations(Object rule, BiFunction<A, Map<String, Object>, Object> paramConverter, Class<A> annotationType) {
        try {
            Class<?> type = rule.getClass();

            // process fields
            for (Field field : type.getDeclaredFields()) {
                A annotation = field.getDeclaredAnnotation(annotationType);
                if (annotation == null)
                    continue;
                Object value = paramConverter.apply(annotation, parameters);
                field.setAccessible(true);
                field.set(rule, value);
            }

            // process setters
            for (Method method : type.getDeclaredMethods()) {
                A annotation = method.getDeclaredAnnotation(annotationType);
                if (annotation == null)
                    continue;
                Object value = paramConverter.apply(annotation, parameters);
                method.setAccessible(true);
                method.invoke(rule, value);
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

}