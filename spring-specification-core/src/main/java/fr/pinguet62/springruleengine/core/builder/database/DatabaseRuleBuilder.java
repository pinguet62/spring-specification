package fr.pinguet62.springruleengine.core.builder.database;

import fr.pinguet62.springruleengine.core.api.Rule;
import fr.pinguet62.springruleengine.core.builder.RuleBuilder;
import fr.pinguet62.springruleengine.core.builder.database.factory.CompositeRuleFactory;
import fr.pinguet62.springruleengine.core.builder.database.model.RuleComponentEntity;
import fr.pinguet62.springruleengine.core.builder.database.repository.BusinessRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseRuleBuilder implements RuleBuilder {

    @Autowired
    private BusinessRuleRepository businessRuleRepository;

    @Autowired
    private CompositeRuleFactory compositeRuleFactory;

    @Override
    public Rule<?> apply(String key) {
        return convert(businessRuleRepository.findOne(key).getRootRuleComponent());
    }

    private Rule<?> convert(RuleComponentEntity ruleComponentEntity) {
        return compositeRuleFactory.apply(ruleComponentEntity).orElseThrow(IllegalArgumentException::new);
    }

}