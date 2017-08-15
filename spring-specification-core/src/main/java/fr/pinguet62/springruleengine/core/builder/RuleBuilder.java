package fr.pinguet62.springruleengine.core.builder;

import fr.pinguet62.springruleengine.core.api.Rule;

import java.util.function.Function;

public interface RuleBuilder extends Function<String, Rule<?>> {

    @Override
    Rule<?> apply(String key);

}