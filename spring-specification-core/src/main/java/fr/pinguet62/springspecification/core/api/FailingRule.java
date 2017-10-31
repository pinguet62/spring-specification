package fr.pinguet62.springspecification.core.api;

import fr.pinguet62.springspecification.core.RuleDescription;
import fr.pinguet62.springspecification.core.RuleName;
import fr.pinguet62.springspecification.core.SpringRule;

@SpringRule
@RuleName(value = "\"FAIL\"")
@RuleDescription("Not implemented rule.")
public class FailingRule<T> implements Rule<T> {

    @Override
    public boolean test(T value) {
        throw new UnsupportedOperationException();
    }

}
