package fr.pinguet62.springruleengine.core.api;

import fr.pinguet62.springruleengine.core.RuleDescription;
import fr.pinguet62.springruleengine.core.RuleName;
import fr.pinguet62.springruleengine.core.builder.database.factory.RuleChildren;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@RuleName(value = "\"OR\"")
@RuleDescription("Combination of rules using \"OR\" operator. False if empty.")
@Component
@Scope(SCOPE_PROTOTYPE)
public class OrRule<T> extends AbstractCompositeRule<T> {

    @Autowired
    public OrRule(@RuleChildren Rule<T>... rules) {
        super(rules);
    }

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