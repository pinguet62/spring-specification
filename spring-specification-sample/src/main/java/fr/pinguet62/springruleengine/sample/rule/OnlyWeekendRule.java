package fr.pinguet62.springruleengine.sample.rule;

import fr.pinguet62.springruleengine.core.RuleDescription;
import fr.pinguet62.springruleengine.core.RuleName;
import fr.pinguet62.springruleengine.core.api.Rule;
import fr.pinguet62.springruleengine.sample.model.Product;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@RuleName("Only weekend")
@RuleDescription("Test if input date is Saturday or Sunday")
public class OnlyWeekendRule implements Rule<Product> {

    @Override
    public boolean test(Product product) {
        // Date date = context.get("date", Date.class);
        // boolean result = date.getDay() == 6 || date.getDay() == 7;
        return false;// test
    }

}