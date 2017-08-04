package fr.pinguet62.springruleengine.core.builder.database.factory;

import fr.pinguet62.springruleengine.core.api.Rule;
import fr.pinguet62.springruleengine.core.builder.database.model.RuleEntity;

import java.util.Optional;
import java.util.function.Function;

public interface RuleFactory extends Function<RuleEntity, Optional<Rule>> {

    @Override
    Optional<Rule> apply(RuleEntity ruleEntity);

}