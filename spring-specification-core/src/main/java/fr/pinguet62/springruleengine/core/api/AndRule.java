package fr.pinguet62.springruleengine.core.api;

import fr.pinguet62.springruleengine.core.RuleDescription;
import fr.pinguet62.springruleengine.core.RuleName;

import java.util.Collection;

@RuleName(value = "\"AND\" combinator")
@RuleDescription("Combination of rules using \"AND\" operator. True if empty.")
public class AndRule<T> extends AbstractCompositeRule<T> {

    public AndRule(Rule<T>... rules) {
        super(rules);
    }

    public AndRule(Collection<Rule<T>> rules) {
        super(rules.toArray(new Rule[rules.size()]));
    }

    @Override
    public boolean test(T value) {
        boolean result = true;
        for (Rule<T> rule : rules)
            result &= rule.test(value);
        return result;
    }

}