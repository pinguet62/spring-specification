package fr.pinguet62.springruleengine.core.builder.database.factory;

import fr.pinguet62.springruleengine.core.api.AndRule;
import fr.pinguet62.springruleengine.core.api.NotRule;
import fr.pinguet62.springruleengine.core.api.OrRule;
import fr.pinguet62.springruleengine.core.api.Rule;
import fr.pinguet62.springruleengine.core.builder.database.model.RuleComponentEntity;
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
    public Optional<Rule<?>> apply(RuleComponentEntity ruleComponentEntity) {
        final String key = ruleComponentEntity.getKey();
        if (key.equals(AndRule.class.getName()))
            return of(new AndRule<>(ruleComponentEntity.getComponents().stream().map(c -> compositeRuleFactory.apply(c).get()).collect(toList())));
        else if (key.equals(OrRule.class.getName()))
            return of(new OrRule<>(ruleComponentEntity.getComponents().stream().map(c -> compositeRuleFactory.apply(c).get()).collect(toList())));
        else if (key.equals(NotRule.class.getName()))
            return of(new NotRule<>(compositeRuleFactory.apply(ruleComponentEntity.getComponents().get(0)).get()));
        else
            return empty();
    }
}
