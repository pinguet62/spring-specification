package fr.pinguet62.springruleengine.core.builder.database;

import fr.pinguet62.springruleengine.core.api.Rule;
import fr.pinguet62.springruleengine.core.builder.RuleBuilder;
import fr.pinguet62.springruleengine.core.builder.database.factory.RuleFactory;
import fr.pinguet62.springruleengine.core.builder.database.repository.BusinessRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseRuleBuilder implements RuleBuilder {

    @Autowired
    private BusinessRuleRepository businessRuleRepository;

    @Autowired
    private RuleFactory ruleFactory;

    @Override
    public Rule<?> apply(String key) {
        return ruleFactory.apply(businessRuleRepository.findOne(key).getRootRuleComponent()).orElseThrow(IllegalArgumentException::new);
    }

}