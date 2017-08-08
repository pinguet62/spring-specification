package fr.pinguet62.springruleengine.core.api;

import lombok.Getter;

public abstract class AbstractCompositeRule implements Rule {

    @Getter
    protected final Rule[] rules;

    public AbstractCompositeRule(Rule... rules) {
        if (rules.length == 0)
            throw new IllegalArgumentException("Require at least 1 " + Rule.class.getSimpleName());
        this.rules = rules;
    }

}