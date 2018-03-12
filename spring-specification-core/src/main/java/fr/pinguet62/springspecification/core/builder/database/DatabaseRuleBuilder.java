package fr.pinguet62.springspecification.core.builder.database;

import fr.pinguet62.springspecification.core.api.Rule;
import fr.pinguet62.springspecification.core.builder.RuleBuilder;
import fr.pinguet62.springspecification.core.builder.database.factory.RuleFactory;
import fr.pinguet62.springspecification.core.builder.database.repository.BusinessRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;

import static fr.pinguet62.springspecification.core.builder.database.autoconfigure.SpringSpecificationBeans.TRANSACTION_MANAGER_NAME;
import static java.util.Objects.requireNonNull;

@Component
public class DatabaseRuleBuilder implements RuleBuilder {

    @Autowired
    private BusinessRuleRepository businessRuleRepository;

    @Autowired
    private RuleFactory ruleFactory;

    @Transactional(value = TRANSACTION_MANAGER_NAME, readOnly = true)
    @Override
    public @NotNull Rule<?> apply(String key) {
        requireNonNull(key);
        return ruleFactory.apply(businessRuleRepository.findById(key).get().getRootRuleComponent()).orElseThrow(IllegalArgumentException::new);
    }

}