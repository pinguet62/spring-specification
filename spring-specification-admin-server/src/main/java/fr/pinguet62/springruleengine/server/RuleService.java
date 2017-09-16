package fr.pinguet62.springruleengine.server;

import fr.pinguet62.springruleengine.core.RuleDescription;
import fr.pinguet62.springruleengine.core.RuleName;
import fr.pinguet62.springruleengine.core.api.Rule;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

@Service
public class RuleService {

    private final List<Class<Rule<?>>> ruleTypes = new ArrayList<>(); // cached

    /**
     * List all {@link BeanDefinition} of type {@link Rule}, and register.
     *
     * @throws ClassNotFoundException
     */
    public RuleService(@NotNull DefaultListableBeanFactory beanFactory) throws ClassNotFoundException {
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            String className = beanDefinition.getBeanClassName();
            if (className == null)
                continue;
            Class<?> beanType = Class.forName(beanDefinition.getBeanClassName());
            if (!Rule.class.isAssignableFrom(beanType))
                continue;
            ruleTypes.add((Class<Rule<?>>) beanType);
        }
    }

    public List<Class<Rule<?>>> getAllRules() {
        return ruleTypes;
    }

    /**
     * @return {@link RuleName} or {@link Class#getName()}
     */
    @NotBlank
    public String getKey(@NotNull Class<Rule<?>> ruleType) {
        return ruleType.getName();
    }

    /**
     * @return {@link RuleName} or {@link Class#getSimpleName()}
     */
    @NotBlank
    public String getName(@NotNull Class<Rule<?>> ruleType) {
        return ruleType.isAnnotationPresent(RuleName.class) ? ruleType.getDeclaredAnnotation(RuleName.class).value() : ruleType.getSimpleName();
    }

    /**
     * @return {@link RuleDescription} or {@code null}
     */
    public String getDescription(@NotNull Class<Rule<?>> ruleType) {
        return ruleType.isAnnotationPresent(RuleDescription.class) ? ruleType.getDeclaredAnnotation(RuleDescription.class).value() : null;
    }

    /**
     * @throws IllegalArgumentException Target {@link Class} is not of type {@link Rule}.
     */
    @NotNull
    public Optional<Class<Rule<?>>> getFromKey(@NotBlank String key) {
        try {
            return of((Class<Rule<?>>) Class.forName(key));
        } catch (ClassCastException e) { // TODO fix
            throw new IllegalArgumentException(e);
        } catch (ClassNotFoundException e) {
            return empty();
        }
    }

}