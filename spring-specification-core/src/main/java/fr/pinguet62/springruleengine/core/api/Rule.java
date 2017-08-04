package fr.pinguet62.springruleengine.core.api;

import java.util.function.Predicate;

import fr.pinguet62.springruleengine.core.Context;

@FunctionalInterface
public interface Rule extends Predicate<Context> {

    @Override
    boolean test(Context context);

    /** @see OrRule */
    default Rule and(Rule other) {
        return new AndRule(this, other);
    }

    /** @see OrRule */
    default Rule or(Rule other) {
        return new OrRule(this, other);
    }

    /** @see NotRule */
    default Rule not() {
        return new NotRule(this);
    }

}