package fr.pinguet62.springruleengine.core.api;

import fr.pinguet62.springruleengine.core.RuleDescription;
import fr.pinguet62.springruleengine.core.RuleName;
import fr.pinguet62.springruleengine.core.SpringRule;
import fr.pinguet62.springruleengine.core.builder.database.factory.RuleChild;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@SpringRule@RuleName(value = "\"NOT\"")
@RuleDescription("Combination of rule using \"NOT\" operator.")
public class NotRule<T> implements Rule<T> {

    @Getter
    private final Rule<T> rule;

    public NotRule(@NotNull @RuleChild Rule<T> rule) {
        this.rule = rule;
    }

    @Override
    public boolean test(T value) {
        return !rule.test(value);
    }

}