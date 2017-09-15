package fr.pinguet62.springruleengine.core.api;

import fr.pinguet62.springruleengine.core.RuleDescription;
import fr.pinguet62.springruleengine.core.RuleName;
import fr.pinguet62.springruleengine.core.SpringRule;
import fr.pinguet62.springruleengine.core.builder.database.factory.RuleChildren;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

@SpringRule
@RuleName(value = "\"OR\"")
@RuleDescription("Combination of rules using \"OR\" operator. False if empty.")
public class OrRule<T> extends AbstractCompositeRule<T> {

    /**
     * @throws IllegalArgumentException Empty value.
     */
    @Autowired
    public OrRule(@RuleChildren Rule<T>... rules) {
        super(rules);
    }

    /**
     * @throws IllegalArgumentException Empty value.
     */
    public OrRule(Collection<Rule<T>> rules) {
        super(rules.toArray(new Rule[rules.size()]));
    }

    @Override
    public boolean test(T value) {
        boolean result = false;
        for (Rule<T> rule : rules)
            result |= rule.test(value);
        return result;
    }

}