package fr.pinguet62.springruleengine.sample.rule;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.pinguet62.springruleengine.core.Context;
import fr.pinguet62.springruleengine.core.RuleDescription;
import fr.pinguet62.springruleengine.core.RuleName;
import fr.pinguet62.springruleengine.core.rule.Rule;

@Component
@Scope("prototype")
@RuleName("Only weekend")
@RuleDescription("Test if input date is Saturday or Sunday")
public class OnlyWeekendRule implements Rule {

    @Override
    public boolean test(Context context) {
        // Date date = context.get("date", Date.class);
        // boolean result = date.getDay() == 6 || date.getDay() == 7;
        return false;// test
    }

}