package fr.pinguet62.springruleengine.sample.rule;

import fr.pinguet62.springruleengine.core.Context;
import fr.pinguet62.springruleengine.core.RuleDescription;
import fr.pinguet62.springruleengine.core.RuleName;
import fr.pinguet62.springruleengine.core.api.Rule;
import fr.pinguet62.springruleengine.core.builder.database.parameter.RuleParameter;
import fr.pinguet62.springruleengine.sample.model.Product;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@RuleName("Price greater than")
@RuleDescription("Test \"$context.product.color>$params.amount\"")
public class PriceGreaterThanRule implements Rule {

    @Setter
    @RuleParameter("amount")
    private Double minimalAmount;

    @Override
    public boolean test(Context context) {
        Product product = context.get("product", Product.class);
        Double actualPrice = product.getPrice();

        return actualPrice > minimalAmount;
    }

}