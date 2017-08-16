package fr.pinguet62.springruleengine.core.api;

import fr.pinguet62.springruleengine.core.RuleDescription;
import fr.pinguet62.springruleengine.core.RuleName;
import lombok.Getter;

@RuleName(value = "\"NOT\"")
@RuleDescription("Combination of rule using \"NOT\" operator.")
public class NotRule<T> implements Rule<T> {

    @Getter
    private final Rule<T> rule;

    public NotRule(Rule<T> rule) {
        this.rule = rule;
    }

    @Override
    public boolean test(T value) {
        return !rule.test(value);
    }

}