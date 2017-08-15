package fr.pinguet62.springruleengine.core.builder.database.factory;

import fr.pinguet62.springruleengine.core.api.Rule;
import fr.pinguet62.springruleengine.core.builder.database.model.RuleComponentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CompositeRuleFactory implements RuleFactory {

    @Autowired
    private ApiRuleFactory apiRuleFactory;

    @Autowired
    private CustomRuleFactory customRuleFactory;

    @Override
    public Optional<Rule<?>> apply(RuleComponentEntity ruleComponentEntity) {
        Optional<Rule<?>> apiRule = apiRuleFactory.apply(ruleComponentEntity);
        if (apiRule.isPresent())
            return apiRule;
        else
            return customRuleFactory.apply(ruleComponentEntity);
    }

}