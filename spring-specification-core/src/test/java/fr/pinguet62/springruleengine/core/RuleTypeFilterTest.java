package fr.pinguet62.springruleengine.core;

import fr.pinguet62.springruleengine.core.api.Rule;
import org.junit.Test;

import java.util.function.Predicate;

import static fr.pinguet62.springruleengine.core.RuleTypeFilterTest.InputTypes.AbstractModel;
import static fr.pinguet62.springruleengine.core.RuleTypeFilterTest.InputTypes.Model;
import static fr.pinguet62.springruleengine.core.RuleTypeFilterTest.RuleTypes.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RuleTypeFilterTest {

    public static class InputTypes {
        public static class AbstractModel {
        }

        public static class Model extends AbstractModel {
        }
    }

    public static class RuleTypes {
        public static abstract class AbstractModelRule implements Rule<Model> {
        }

        public static class ModelRule implements Rule<Model> {
            @Override
            public boolean test(Model value) {
                return false;
            }
        }

        public static class OtherRule implements Rule<String> {
            @Override
            public boolean test(String value) {
                return false;
            }
        }

        public static class GenericRule<T> implements Rule<T> {
            @Override
            public boolean test(T value) {
                return false;
            }
        }

        public static class AbstractModelGenericRule<T extends AbstractModel> implements Rule<T> {
            @Override
            public boolean test(T value) {
                return false;
            }
        }

        public static class OtherGenericRule<T extends Number> implements Rule<T> {
            @Override
            public boolean test(T value) {
                return false;
            }
        }
    }

    @Test
    public void test() {
        Predicate<Class<Rule<?>>> filter = new RuleTypeFilter(Model.class);

        // bad type (compiler bypassed)
        assertFalse(filter.test((Class<Rule<?>>) (Class<?>) String.class));

        // simple
        assertTrue(filter.test((Class<Rule<?>>) (Class<?>) ModelRule.class));
        assertFalse(filter.test((Class<Rule<?>>) (Class<?>) OtherRule.class));
        // abstract
        assertTrue(filter.test((Class<Rule<?>>) (Class<?>) AbstractModelRule.class));
        // generic
        assertTrue(filter.test((Class<Rule<?>>) (Class<?>) GenericRule.class));
        assertTrue(filter.test((Class<Rule<?>>) (Class<?>) AbstractModelGenericRule.class));
        assertFalse(filter.test((Class<Rule<?>>) (Class<?>) OtherGenericRule.class));
    }

}
