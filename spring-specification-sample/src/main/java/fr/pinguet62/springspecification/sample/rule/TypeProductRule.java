package fr.pinguet62.springspecification.sample.rule;

import fr.pinguet62.springspecification.core.RuleDescription;
import fr.pinguet62.springspecification.core.RuleName;
import fr.pinguet62.springspecification.core.SpringRule;
import fr.pinguet62.springspecification.core.api.Rule;
import fr.pinguet62.springspecification.core.builder.database.parameter.RuleParameter;
import fr.pinguet62.springspecification.sample.model.Product;
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
