package fr.pinguet62.springruleengine.core.rule;

import java.util.Collection;

import fr.pinguet62.springruleengine.core.Context;

public class OrRule extends AbstractCompositeRule {

    public OrRule(Rule... rules) {
        super(rules);
    }

    public OrRule(Collection<Rule> rules) {
        super(rules.toArray(new Rule[rules.size()]));
    }

    @Override
    public boolean test(Context context) {
        boolean result = false;
        for (Rule rule : rules)
            result |= rule.test(context);
        return result;
    }

}