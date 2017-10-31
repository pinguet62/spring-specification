package fr.pinguet62.springspecification.core.api;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.ArrayUtils;

@UtilityClass
public class RuleUtils {

    public <T> Rule<T> and(Rule<T> rule, Rule<T>... rules) {
        Rule<T>[] allRules = ArrayUtils.insert(0, rules, rule);
        return new AndRule<>(allRules);
    }

    public <T> Rule<T> or(Rule<T> rule, Rule<T>... rules) {
        Rule<T>[] allRules = ArrayUtils.insert(0, rules, rule);
        return new OrRule<>(allRules);
    }

    public <T> Rule<T> not(Rule<T> rule) {
        return new NotRule<>(rule);
    }

}