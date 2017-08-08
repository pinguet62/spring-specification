package fr.pinguet62.springruleengine.core.builder.database.parameter;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @see RuleParameter
 */
@UtilityClass
public class RuleParameterProcessor {

    public void process(Object rule, Map<String, Object> parameters) {
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