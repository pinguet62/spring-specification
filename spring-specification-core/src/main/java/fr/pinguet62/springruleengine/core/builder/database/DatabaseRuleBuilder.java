package fr.pinguet62.springruleengine.core.builder.database;

import fr.pinguet62.springruleengine.core.api.Rule;
import fr.pinguet62.springruleengine.core.builder.RuleBuilder;
import fr.pinguet62.springruleengine.core.builder.database.factory.CompositeRuleFactory;
import fr.pinguet62.springruleengine.core.builder.database.model.RuleEntity;
import fr.pinguet62.springruleengine.core.builder.database.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseRuleBuilder implements RuleBuilder {

    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private CompositeRuleFactory compositeRuleFactory;

    @Override
    public Rule<?> apply(Integer key) {
        return convert(ruleRepository.findOne(key));
    }

    private Rule<?> convert(RuleEntity ruleEntity) {
        return compositeRuleFactory.apply(ruleEntity).orElseThrow(IllegalArgumentException::new);
    }

}