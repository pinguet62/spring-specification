package fr.pinguet62.springruleengine.core.api;

import fr.pinguet62.springruleengine.core.RuleDescription;
import fr.pinguet62.springruleengine.core.RuleName;
import fr.pinguet62.springruleengine.core.builder.database.factory.RuleChild;
import lombok.Getter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@RuleName(value = "\"NOT\"")
@RuleDescription("Combination of rule using \"NOT\" operator.")
@Component
@Scope(SCOPE_PROTOTYPE)
public class NotRule<T> implements Rule<T> {

    @Getter
    private final Rule<T> rule;

    public NotRule(@RuleChild Rule<T> rule) {
        this.rule = rule;
    }

    @Override
    public boolean test(T value) {
        return !rule.test(value);
    }

}