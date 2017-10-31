package fr.pinguet62.springspecification.core.builder.database.factory;

import fr.pinguet62.springspecification.core.api.Rule;
import fr.pinguet62.springspecification.core.builder.database.model.ParameterEntity;
import fr.pinguet62.springspecification.core.builder.database.model.RuleComponentEntity;
import fr.pinguet62.springspecification.core.builder.database.parameter.ParameterInjector;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

@Component
public class RuleFactory implements Function<RuleComponentEntity, Optional<Rule<?>>> {

    @Autowired
    private BeanFactory factory;

    @Override
    public Optional<Rule<?>> apply(RuleComponentEntity ruleComponentEntity) {
        requireNonNull(ruleComponentEntity);

        // Parameters
        Map<String, String> parameters = ruleComponentEntity.getParameters().stream().collect(toMap(ParameterEntity::getKey, ParameterEntity::getValue));
        ParameterInjector.CONTEXT.set(parameters);
        try {

            // Rule
            Rule<?> rule;
            try {
                List<Rule<?>> subRules = ruleComponentEntity
                        .getComponents()
                        .stream()
                        .map(this::apply)
                        .map(r -> r.orElseThrow(IllegalArgumentException::new))
                        .collect(toList());
                RuleInjector.CONTEXT.set(subRules);

                String className = ruleComponentEntity.getKey();
                Class<?> claz = Class.forName(className);
                rule = (Rule<?>) factory.getBean(claz);
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException(e);
            } catch (NoSuchBeanDefinitionException e) {
                throw new IllegalArgumentException(e);
            } finally {
                RuleInjector.CONTEXT.remove();
            }

            return of(rule);

        } finally {
            ParameterInjector.CONTEXT.remove();
        }
    }

}