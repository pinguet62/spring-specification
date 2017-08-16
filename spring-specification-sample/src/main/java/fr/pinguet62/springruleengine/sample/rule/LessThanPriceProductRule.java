package fr.pinguet62.springruleengine.sample.rule;

import fr.pinguet62.springruleengine.core.RuleDescription;
import fr.pinguet62.springruleengine.core.RuleName;
import fr.pinguet62.springruleengine.core.api.Rule;
import fr.pinguet62.springruleengine.core.builder.database.parameter.RuleParameter;
import fr.pinguet62.springruleengine.sample.model.Product;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
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