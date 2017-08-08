package fr.pinguet62.springruleengine.core.builder.database.factory;

import fr.pinguet62.springruleengine.core.api.Rule;
import fr.pinguet62.springruleengine.core.builder.database.ParameterConverter;
import fr.pinguet62.springruleengine.core.builder.database.model.ParameterEntity;
import fr.pinguet62.springruleengine.core.builder.database.model.RuleEntity;
import fr.pinguet62.springruleengine.core.builder.database.parameter.RuleParameterProcessor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toMap;

/**
 * {@link RuleFactory} for user's custom {@link Rule}s.
 */
@Component
public class CustomRuleFactory implements RuleFactory {

    @Autowired
    private BeanFactory factory;

    @Autowired
    private ParameterConverter parameterConverter;

    @Override
    public Optional<Rule> apply(RuleEntity ruleEntity) {
        // Build
        Rule rule;
        try {
            String className = ruleEntity.getKey();
            rule = (Rule) factory.getBean(className);
        } catch (NoSuchBeanDefinitionException e) {
            return empty();
        }

        // Parameters
        Function<String, Class<?>> checkedClassForName = x -> {
            try {
                return Class.forName(x);
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException(e);
            }
        };
        Function<ParameterEntity, Object> converter = p -> parameterConverter.convert(p.getValue(),
                checkedClassForName.apply(p.getType()));
        Map<String, Object> parameters = ruleEntity.getParameters().stream().collect(toMap(p -> p.getKey(), converter));
        RuleParameterProcessor.process(rule, parameters);

        return of(rule);
    }

}