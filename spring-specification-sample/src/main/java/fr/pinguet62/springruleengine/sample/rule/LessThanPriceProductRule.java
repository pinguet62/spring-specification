package fr.pinguet62.springruleengine.sample.rule;

import fr.pinguet62.springruleengine.core.RuleDescription;
import fr.pinguet62.springruleengine.core.RuleName;
import fr.pinguet62.springruleengine.core.SpringRule;
import fr.pinguet62.springruleengine.core.api.Rule;
import fr.pinguet62.springruleengine.core.builder.database.parameter.RuleParameter;
import fr.pinguet62.springruleengine.sample.model.Product;
import lombok.Setter;

@SpringRule
@RuleName("Price less than")
@RuleDescription("Test \"$product.amount<$params.amount\"")
public class LessThanPriceProductRule implements Rule<Product> {

    @Setter
    @RuleParameter("amount")
    private Double maximalAmount;

    @Override
    public boolean test(Product product) {
        return product.getPrice() < maximalAmount;
    }

}