package fr.pinguet62.springruleengine.core.api;

import lombok.Getter;

public abstract class AbstractCompositeRule<T> implements Rule<T> {

    @Getter
    protected final Rule<T>[] rules;

    /**
     * @throws IllegalArgumentException Empty value.
     */
    public AbstractCompositeRule(Rule<T>... rules) {
        if (rules.length == 0)
            throw new IllegalArgumentException("Require at least 1 " + Rule.class.getSimpleName());
        this.rules = rules;
    }

}