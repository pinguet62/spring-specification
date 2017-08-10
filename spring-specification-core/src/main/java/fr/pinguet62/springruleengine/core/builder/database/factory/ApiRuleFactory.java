package fr.pinguet62.springruleengine.core.builder.database.factory;

import fr.pinguet62.springruleengine.core.api.AndRule;
import fr.pinguet62.springruleengine.core.api.NotRule;
import fr.pinguet62.springruleengine.core.api.OrRule;
import fr.pinguet62.springruleengine.core.api.Rule;
import fr.pinguet62.springruleengine.core.builder.database.model.RuleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.stream.Collectors.toList;

/**
 * {@link RuleFactory} for {@link Rule}s of base API.
 */
@Component
public class ApiRuleFactory implements RuleFactory {

    @Autowired
    private CompositeRuleFactory compositeRuleFactory;

    @Override
    public Optional<Rule<?>> apply(RuleEntity ruleEntity) {
        switch (ruleEntity.getKey()) {
            case "andRule":
                return of(new AndRule<>(ruleEntity.getComponents().stream().map(c -> compositeRuleFactory.apply(c).get()).collect(toList())));
            case "orRule":
                return of(new OrRule<>(ruleEntity.getComponents().stream().map(c -> compositeRuleFactory.apply(c).get()).collect(toList())));
            case "notRule":
                return of(new NotRule<>(compositeRuleFactory.apply(ruleEntity.getComponents().get(0)).get()));
            default:
                return empty();
        }
    }

}