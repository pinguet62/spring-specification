package fr.pinguet62.springruleengine.core.builder.database.parameter;

import fr.pinguet62.springruleengine.core.api.Rule;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

// TODO factorize with RuleParameterBeanPostProcessor
@Service
public class ParameterService {

    // TODO factorize with RuleParameterBeanPostProcessor methods
    public Set<String> getDeclaratedKeys(Class<Rule<?>> ruleType) {
        Set<String> keys = new HashSet<>();

        for (Field field : ruleType.getDeclaredFields()) {
            RuleParameter annotation = field.getDeclaredAnnotation(RuleParameter.class);
            if (annotation == null)
                continue;
            keys.add(annotation.value());
        }

        // process setters
        for (Method method : ruleType.getDeclaredMethods()) {
            RuleParameter annotation = method.getDeclaredAnnotation(RuleParameter.class);
            if (annotation == null)
                continue;
            keys.add(annotation.value());
        }

        return keys;
    }

}
