package fr.pinguet62.springruleengine.server;

import static java.util.stream.Collectors.toList;

import java.beans.Introspector;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import fr.pinguet62.springruleengine.core.api.Rule;

@Service
public class RuleService {

    public @NotNull List<Class<Rule>> getAllRules() {
        String packag = "fr/pinguet62/springruleengine" /* TODO */;

        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AssignableTypeFilter(Rule.class));
        Set<BeanDefinition> components = provider.findCandidateComponents(packag);
        return components.stream().map(component -> (Class<Rule>) ClassUtils.resolveClassName(component.getBeanClassName(),
                provider.getResourceLoader().getClassLoader())).collect(toList());
    }

    public String getKey(@NotNull Class<Rule> rule) {
        return Introspector.decapitalize(ClassUtils.getShortName(rule));
    }

    public @NotNull Optional<Class<Rule>> getFromKey(@NotNull String key) {
        return getAllRules().stream().filter(rule -> getKey(rule).equals(key)).reduce((a, b) -> {
            throw new IllegalArgumentException("Many " + Rule.class.getSimpleName() + " found for key: " + key);
        });
    }

}