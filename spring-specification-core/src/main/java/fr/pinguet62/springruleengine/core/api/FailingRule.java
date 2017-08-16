package fr.pinguet62.springruleengine.core.api;

import fr.pinguet62.springruleengine.core.RuleDescription;
import fr.pinguet62.springruleengine.core.RuleName;

@RuleName(value = "\"FAIL\"")
@RuleDescription("Not implemented rule.")
public class FailingRule<T> implements Rule<T> {

    @Override
    public boolean test(T value) {
        throw new UnsupportedOperationException();
    }

}
