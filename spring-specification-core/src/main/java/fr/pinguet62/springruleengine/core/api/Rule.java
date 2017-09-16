package fr.pinguet62.springruleengine.core.api;

import javax.validation.constraints.NotNull;
import java.util.function.Predicate;

@FunctionalInterface
public interface Rule<T> extends Predicate<T> {

    @Override
    boolean test(T value);

    /**
     * @see OrRule
     */
    default Rule<T> and(@NotNull Rule<T> other) {
        return new AndRule<>(this, other);
    }

    /**
     * @see OrRule
     */
    default Rule<T> or(@NotNull Rule<T> other) {
        return new OrRule<>(this, other);
    }

    /**
     * @see NotRule
     */
    default Rule<T> not() {
        return new NotRule<>(this);
    }

}