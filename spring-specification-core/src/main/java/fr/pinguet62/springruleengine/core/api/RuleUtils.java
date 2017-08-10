package fr.pinguet62.springruleengine.core.api;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RuleUtils {

    public <T> Rule<T> and(Rule<T>... rules) {
        return new AndRule<>(rules);
    }

    public <T> Rule<T> or(Rule<T>... rules) {
        return new OrRule<>(rules);
    }

    public <T> Rule<T> not(Rule<T> rule) {
        return new NotRule<>(rule);
    }

}