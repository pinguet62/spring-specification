package fr.pinguet62.springruleengine.core.api;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RuleUtils {

    public Rule and(Rule... rules) {
        return new AndRule(rules);
    }

    public Rule or(Rule... rules) {
        return new OrRule(rules);
    }

    public Rule not(Rule rule) {
        return new NotRule(rule);
    }

}