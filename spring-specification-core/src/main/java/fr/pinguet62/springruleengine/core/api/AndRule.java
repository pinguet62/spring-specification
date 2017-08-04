package fr.pinguet62.springruleengine.core.api;

import java.util.Collection;

import fr.pinguet62.springruleengine.core.Context;
import fr.pinguet62.springruleengine.core.RuleDescription;
import fr.pinguet62.springruleengine.core.RuleName;

@RuleName(value = "\"AND\" combinator")
@RuleDescription("Combination of rules using \"AND\" operator. True if empty.")
public class AndRule extends AbstractCompositeRule {

    public AndRule(Rule... rules) {
        super(rules);
    }

    public AndRule(Collection<Rule> rules) {
        super(rules.toArray(new Rule[rules.size()]));
    }

    @Override
    public boolean test(Context context) {
        boolean result = true;
        for (Rule rule : rules)
            result &= rule.test(context);
        return result;
    }

}