package fr.pinguet62.springruleengine.core.rule;

public abstract class AbstractCompositeRule implements Rule {

    protected final Rule[] rules;

    public AbstractCompositeRule(Rule... rules) {
        if (rules.length == 0)
            throw new IllegalArgumentException("Require at least 1 " + Rule.class.getSimpleName());
        this.rules = rules;
    }

}