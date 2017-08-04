package fr.pinguet62.springruleengine.core.api;

import fr.pinguet62.springruleengine.core.Context;

public class NotRule implements Rule {

    private final Rule rule;

    public NotRule(Rule rule) {
        this.rule = rule;
    }

    @Override
    public boolean test(Context context) {
        return !rule.test(context);
    }

}