package fr.pinguet62.springruleengine.core.api;

import fr.pinguet62.springruleengine.core.RuleDescription;
import fr.pinguet62.springruleengine.core.RuleName;
import fr.pinguet62.springruleengine.core.SpringRule;
import fr.pinguet62.springruleengine.core.builder.database.factory.RuleChildren;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.Size;
import java.util.Collection;

@SpringRule
@RuleName(value = "\"AND\"")
@RuleDescription("Combination of rules using \"AND\" operator. True if empty.")
public class AndRule<T> extends AbstractCompositeRule<T> {

    /**
     * @throws IllegalArgumentException Empty value.
     */
    @Autowired
    public AndRule(@Size(min = 1) @RuleChildren Rule<T>... rules) {
        super(rules);
    }

    /**
     * @throws IllegalArgumentException Empty value.
     */
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