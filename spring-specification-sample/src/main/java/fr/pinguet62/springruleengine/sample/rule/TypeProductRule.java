package fr.pinguet62.springruleengine.sample.rule;

import fr.pinguet62.springruleengine.core.RuleDescription;
import fr.pinguet62.springruleengine.core.RuleName;
import fr.pinguet62.springruleengine.core.SpringRule;
import fr.pinguet62.springruleengine.core.api.Rule;
import fr.pinguet62.springruleengine.core.builder.database.parameter.RuleParameter;
import fr.pinguet62.springruleengine.sample.model.Product;
import lombok.Setter;

@SpringRule
@RuleName("Product type")
@RuleDescription("Test \"$product.type==$params.type\"")
public class TypeProductRule implements Rule<Product> {

    @Setter
    @RuleParameter("type")
    private String expectedType;

    @Override
    public boolean test(Product product) {
        return product.getType().equals(expectedType);
    }

}
