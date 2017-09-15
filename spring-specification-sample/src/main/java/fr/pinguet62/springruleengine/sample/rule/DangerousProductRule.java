package fr.pinguet62.springruleengine.sample.rule;

import fr.pinguet62.springruleengine.core.RuleDescription;
import fr.pinguet62.springruleengine.core.RuleName;
import fr.pinguet62.springruleengine.core.SpringRule;
import fr.pinguet62.springruleengine.core.api.Rule;
import fr.pinguet62.springruleengine.sample.model.Product;

@SpringRule
@RuleName("Dangerous product")
@RuleDescription("Test \"$product.dangerous==true\"")
public class DangerousProductRule implements Rule<Product> {

    @Override
    public boolean test(Product product) {
        return product.getDangerous();
    }

}