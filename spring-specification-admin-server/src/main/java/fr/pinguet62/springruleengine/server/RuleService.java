package fr.pinguet62.springruleengine.server;

import fr.pinguet62.springruleengine.core.RuleDescription;
import fr.pinguet62.springruleengine.core.RuleName;
import fr.pinguet62.springruleengine.core.api.Rule;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;

@Service
public class RuleService {

    public @NotNull
    List<Class<Rule<?>>> getAllRules() {
        String packag = "fr/pinguet62/springruleengine" /* TODO */;

        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AssignableTypeFilter(Rule.class));
        Set<BeanDefinition> components = provider.findCandidateComponents(packag);
        return components.stream().map(component -> (Class<Rule<?>>) ClassUtils.resolveClassName(component.getBeanClassName(),
                provider.getResourceLoader().getClassLoader())).collect(toList());
    }

    /**
     * @return {@link RuleName} or {@link Class#getName()}
     */
    public String getKey(@NotNull Class<Rule<?>> ruleType) {
        return ruleType.getName();
    }

    /**
     * @return {@link RuleName} or {@link Class#getSimpleName()}
     */
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
    public @NotNull
    Optional<Class<Rule<?>>> getFromKey(@NotBlank String key) {
        try {
            return of((Class<Rule<?>>) Class.forName(key));
        } catch (ClassCastException e) { // TODO fix
            throw new IllegalArgumentException(e);
        } catch (ClassNotFoundException e) {
            return empty();
        }
    }

}