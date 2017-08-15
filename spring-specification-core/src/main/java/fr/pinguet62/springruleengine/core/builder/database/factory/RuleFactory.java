package fr.pinguet62.springruleengine.core.builder.database.factory;

import fr.pinguet62.springruleengine.core.api.Rule;
import fr.pinguet62.springruleengine.core.builder.database.model.RuleComponentEntity;

import java.util.Optional;
import java.util.function.Function;

public interface RuleFactory extends Function<RuleComponentEntity, Optional<Rule<?>>> {

    @Override
    Optional<Rule<?>> apply(RuleComponentEntity ruleComponentEntity);

}