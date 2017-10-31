package fr.pinguet62.springspecification.sample.rule;

import fr.pinguet62.springspecification.core.RuleDescription;
import fr.pinguet62.springspecification.core.RuleName;
import fr.pinguet62.springspecification.core.SpringRule;
import fr.pinguet62.springspecification.core.api.Rule;
import fr.pinguet62.springspecification.sample.model.Product;

@SpringRule
@RuleName("Dangerous product")
@RuleDescription("Test \"$product.dangerous==true\"")
public class DangerousProductRule implements Rule<Product> {

    @Override
    public boolean test(Product product) {
        return product.getDangerous();
    }

}