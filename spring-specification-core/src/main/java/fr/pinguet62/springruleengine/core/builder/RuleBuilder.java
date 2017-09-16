package fr.pinguet62.springruleengine.core.builder;

import fr.pinguet62.springruleengine.core.api.Rule;

import javax.validation.constraints.NotNull;
import java.util.function.Function;

public interface RuleBuilder extends Function<String, Rule<?>> {

    @Override
    @NotNull Rule<?> apply(String key);

}