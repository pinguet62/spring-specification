package fr.pinguet62.springruleengine.core.builder.database;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fr.pinguet62.springruleengine.core.builder.RuleBuilder;
import fr.pinguet62.springruleengine.core.builder.database.model.ParameterEntity;
import fr.pinguet62.springruleengine.core.builder.database.model.RuleEntity;
import fr.pinguet62.springruleengine.core.builder.database.repository.RuleRepository;
import fr.pinguet62.springruleengine.core.rule.AndRule;
import fr.pinguet62.springruleengine.core.rule.NotRule;
import fr.pinguet62.springruleengine.core.rule.OrRule;
import fr.pinguet62.springruleengine.core.rule.Rule;

@Component
public class DatabaseRuleBuilder implements RuleBuilder {

    @Autowired
    private BeanFactory factory;

    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private ParameterConverter parameterConverter;

    @Override
    public Rule build() {
        return convert(ruleRepository.findAll().get(0));
    }

    private Rule convert(RuleEntity ruleEntity) {
        String key = ruleEntity.getKey();
        if (key.equals("and")) {
            List<Rule> rules = ruleEntity.getComponents().stream().map(this::convert).collect(toList());
            return new AndRule(rules);
        } else if (key.equals("or")) {
            List<Rule> rules = ruleEntity.getComponents().stream().map(this::convert).collect(toList());
            return new OrRule(rules);
        } else if (key.equals("not")) {
            Rule rule = convert(ruleEntity.getComponents().get(0));
            return new NotRule(rule);
        } else {
            // Parameters
            Function<String, Class<?>> checkedClassForName = x -> {
                try {
                    return Class.forName(x);
                } catch (ClassNotFoundException e) {
                    throw new IllegalArgumentException(e);
                }
            };
            Function<ParameterEntity, Object> converter = p -> parameterConverter.convert(p.getValue(),
                    checkedClassForName.apply(p.getType()));
            Map<String, Object> parameters = ruleEntity.getParameters().stream().collect(toMap(p -> p.getKey(), converter));

            // Build
            Object[] args = parameters.isEmpty() ? new Object[] {} : new Object[] { parameters };
            return (Rule) factory.getBean(key, args);
        }
    }

}