package fr.pinguet62.springruleengine.sample.rule;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import fr.pinguet62.springruleengine.core.Context;
import fr.pinguet62.springruleengine.core.RuleDescription;
import fr.pinguet62.springruleengine.core.RuleName;
import fr.pinguet62.springruleengine.core.api.Rule;
import fr.pinguet62.springruleengine.sample.model.Product;

@Component
@Scope("prototype")
@RuleName("Has color")
@RuleDescription("Test \"$context.product.color=$params.color\"")
public class HasColorRule implements Rule {

    private final Map<String, Object> params;

    public HasColorRule(Map<String, Object> params) {
        this.params = params;
    }

    @Override
    public boolean test(Context context) {
        String expectedColor = (String) params.get("color");

        Product product = context.get("product", Product.class);
        String actualColor = product.getColor();

        return actualColor.equals(expectedColor);
    }

}