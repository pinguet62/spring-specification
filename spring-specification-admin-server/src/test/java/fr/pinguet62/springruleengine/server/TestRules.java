package fr.pinguet62.springruleengine.server;

import fr.pinguet62.springruleengine.core.api.Rule;
import fr.pinguet62.springruleengine.core.builder.database.parameter.RuleParameter;
import lombok.experimental.UtilityClass;

/**
 * Set of {@link Rule} implementations.
 */
@UtilityClass
public class TestRules {

    public static class StringRule implements Rule<String> {
        @Override
        public boolean test(String value) {
            throw new UnsupportedOperationException();
        }
    }

    public static class NumberRule implements Rule<Number> {
        @Override
        public boolean test(Number value) {
            throw new UnsupportedOperationException();
        }
    }

    public static class ParameterizedRule implements Rule<String> {
        private String other;

        @RuleParameter("attribute")
        private String attr;

        public ParameterizedRule(String other) {
        }

        public ParameterizedRule(String arg1, @RuleParameter("constructor") String arg2, String arg3) {
        }

        public void setOther(String value) {
        }

        @RuleParameter("setter")
        public void setSetter(String value) {
        }

        @Override
        public boolean test(String value) {
            throw new UnsupportedOperationException();
        }
    }

}
