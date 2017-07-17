package fr.pinguet62.springruleengine.core.sample.rule;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.pinguet62.springruleengine.core.Context;
import fr.pinguet62.springruleengine.core.rule.Rule;
import fr.pinguet62.springruleengine.core.sample.model.Product;

@Component
@Scope("prototype")
public class PriceGreaterThanRule implements Rule {

    private final Map<String, Object> params;

    public PriceGreaterThanRule(Map<String, Object> params) {
        this.params = params;
    }

    @Override
    public boolean test(Context context) {
        Double minimalAmount = (Double) params.get("amount");

        Product product = context.get("product", Product.class);
        Double actualPrice = product.getPrice();

        return actualPrice > minimalAmount;
    }

}