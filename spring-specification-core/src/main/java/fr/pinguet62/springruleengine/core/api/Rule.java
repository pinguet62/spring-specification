package fr.pinguet62.springruleengine.core.api;

import java.util.function.Predicate;

@FunctionalInterface
public interface Rule<T> extends Predicate<T> {

    @Override
    boolean test(T value);

    /**
     * @see OrRule
     */
    default Rule<T> and(Rule<T> other) {
        return new AndRule<>(this, other);
    }

    /**
     * @see OrRule
     */
    default Rule<T> or(Rule<T> other) {
        return new OrRule<>(this, other);
    }

    /**
     * @see NotRule
     */
    default Rule<T> not() {
        return new NotRule<>(this);
    }

}