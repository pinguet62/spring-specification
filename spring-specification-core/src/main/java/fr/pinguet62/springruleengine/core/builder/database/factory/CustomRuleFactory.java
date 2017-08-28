package fr.pinguet62.springruleengine.core.builder.database.factory;

import fr.pinguet62.springruleengine.core.api.Rule;
import fr.pinguet62.springruleengine.core.builder.database.model.ParameterEntity;
import fr.pinguet62.springruleengine.core.builder.database.model.RuleComponentEntity;
import fr.pinguet62.springruleengine.core.builder.database.parameter.ParameterInjector;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

import static java.util.Optional.of;
import static java.util.stream.Collectors.toMap;

/**
 * {@link RuleFactory} for user's custom {@link Rule}s.
 */
@Component
public class CustomRuleFactory implements RuleFactory {

    @Autowired
    private BeanFactory factory;

    @Override
    public Optional<Rule<?>> apply(RuleComponentEntity ruleComponentEntity) {
        // Parameters
        Map<String, String> parameters = ruleComponentEntity.getParameters().stream().collect(toMap(ParameterEntity::getKey, ParameterEntity::getValue));
        ParameterInjector.CONTEXT.set(parameters);
        try {

            // Build
            Rule<?> rule;
            try {
                String className = ruleComponentEntity.getKey();
                Class<?> claz = Class.forName(className);
                rule = (Rule<?>) factory.getBean(claz);
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException(e);
            } catch (NoSuchBeanDefinitionException e) {
                throw new IllegalArgumentException(e);
            }

            return of(rule);

        } finally {
            ParameterInjector.CONTEXT.remove();
        }
    }

}