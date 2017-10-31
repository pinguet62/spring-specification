package fr.pinguet62.springspecification.core.api;

import fr.pinguet62.springspecification.core.RuleDescription;
import fr.pinguet62.springspecification.core.RuleName;
import fr.pinguet62.springspecification.core.SpringRule;
import fr.pinguet62.springspecification.core.builder.database.factory.RuleChildren;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.Size;
import java.util.Collection;

@SpringRule
@RuleName(value = "\"OR\"")
@RuleDescription("Combination of rules using \"OR\" operator. False if empty.")
public class OrRule<T> extends AbstractCompositeRule<T> {

    /**
     * @throws IllegalArgumentException Empty value.
     */
    @Autowired
    public OrRule(@Size(min = 1) @RuleChildren Rule<T>... rules) {
        super(rules);
    }

    /**
     * @throws IllegalArgumentException Empty value.
     */
    public OrRule(@Size(min = 1) Collection<Rule<T>> rules) {
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