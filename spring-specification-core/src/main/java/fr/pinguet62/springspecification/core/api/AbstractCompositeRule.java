package fr.pinguet62.springspecification.core.api;

import lombok.Getter;

import javax.validation.constraints.Size;

public abstract class AbstractCompositeRule<T> implements Rule<T> {

    @Getter
    protected final Rule<T>[] rules;

    /**
     * @throws IllegalArgumentException Empty value.
     */
    public AbstractCompositeRule(@Size(min = 1) Rule<T>... rules) {
        if (rules.length == 0)
            throw new IllegalArgumentException("At least 1 " + Rule.class.getSimpleName() + " is required");
        this.rules = rules;
    }

}