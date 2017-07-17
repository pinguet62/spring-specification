package fr.pinguet62.springruleengine.core.rule;

import java.util.function.Predicate;

import fr.pinguet62.springruleengine.core.Context;

@FunctionalInterface
public interface Rule extends Predicate<Context> {

    @Override
    boolean test(Context context);

}