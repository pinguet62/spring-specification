package fr.pinguet62.springruleengine.core.builder.database.parameter;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

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
        try {
            Class<?> type = rule.getClass();

            // process fields
            for (Field field : type.getDeclaredFields()) {
                RuleParameter annotation = field.getDeclaredAnnotation(RuleParameter.class);
                if (annotation == null)
                    continue;
                String key = annotation.value();
                Object value = parameters.get(key);
                field.setAccessible(true);
                field.set(rule, value);
            }

            // process setters
            for (Method method : type.getDeclaredMethods()) {
                RuleParameter annotation = method.getDeclaredAnnotation(RuleParameter.class);
                if (annotation == null)
                    continue;
                String key = annotation.value();
                Object value = parameters.get(key);
                method.setAccessible(true);
                method.invoke(rule, value);
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

}