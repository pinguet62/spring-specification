package fr.pinguet62.springruleengine.core.api;

import fr.pinguet62.springruleengine.core.RuleDescription;
import fr.pinguet62.springruleengine.core.RuleName;

import java.util.Collection;

@RuleName(value = "\"OR\"")
@RuleDescription("Combination of rules using \"OR\" operator. False if empty.")
public class OrRule<T> extends AbstractCompositeRule<T> {

    public OrRule(Rule... rules) {
        super(rules);
    }

    public OrRule(Collection<Rule> rules) {
        super(rules.toArray(new Rule[rules.size()]));
    }

    @Override
    public boolean test(T value) {
        boolean result = false;
        for (Rule rule : rules)
            result |= rule.test(value);
        return result;
    }

}