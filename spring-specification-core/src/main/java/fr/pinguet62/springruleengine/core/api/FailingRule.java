package fr.pinguet62.springruleengine.core.api;

import fr.pinguet62.springruleengine.core.RuleDescription;
import fr.pinguet62.springruleengine.core.RuleName;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@RuleName(value = "\"FAIL\"")
@RuleDescription("Not implemented rule.")
@Component
@Scope(SCOPE_PROTOTYPE)
public class FailingRule<T> implements Rule<T> {

    @Override
    public boolean test(T value) {
        throw new UnsupportedOperationException();
    }

}
