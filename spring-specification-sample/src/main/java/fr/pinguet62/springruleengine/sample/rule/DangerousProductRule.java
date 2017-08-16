package fr.pinguet62.springruleengine.sample.rule;

import fr.pinguet62.springruleengine.core.RuleDescription;
import fr.pinguet62.springruleengine.core.RuleName;
import fr.pinguet62.springruleengine.core.api.Rule;
import fr.pinguet62.springruleengine.sample.model.Product;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@Component
@Scope(SCOPE_PROTOTYPE)
@RuleName("Dangerous product")
@RuleDescription("Test \"$product.dangerous==true\"")
public class DangerousProductRule implements Rule<Product> {

    @Override
    public boolean test(Product product) {
        return product.getDangerous();
    }

}