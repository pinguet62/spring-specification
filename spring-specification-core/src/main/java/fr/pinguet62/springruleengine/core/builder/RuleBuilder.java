package fr.pinguet62.springruleengine.core.builder;

import fr.pinguet62.springruleengine.core.api.Rule;

import java.util.function.Function;

public interface RuleBuilder extends Function<Integer, Rule> {

    @Override
    Rule apply(Integer key);

}