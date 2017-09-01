package fr.pinguet62.springruleengine.core;

import fr.pinguet62.springruleengine.core.api.Rule;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ResolvableType;

import java.util.function.Predicate;

/**
 * {@link Predicate} to check if a {@link Rule} can be applied on {@link Class}.
 */
@RequiredArgsConstructor
public class RuleTypeFilter implements Predicate<Class<Rule<?>>> {

    private final Class<?> modelType;

    @Override
    public boolean test(Class<Rule<?>> ruleType) {
        ResolvableType resolvableType = ResolvableType.forClass(ruleType).as(Rule.class);
        if (resolvableType.resolve() == null)
            return false; // not a Rule<?>
        resolvableType = resolvableType.getGeneric(0);
        Class<?> resolvedType = resolvableType.resolve();
        if (resolvedType == null)
            return true; // generic without type restriction
        return resolvedType.isAssignableFrom(modelType);
    }

}
