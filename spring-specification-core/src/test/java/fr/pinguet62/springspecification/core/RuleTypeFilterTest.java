package fr.pinguet62.springspecification.core;

import fr.pinguet62.springspecification.core.api.Rule;
import org.junit.Test;

import java.util.function.Predicate;

import static fr.pinguet62.springspecification.core.RuleTypeFilterTest.InputTypes.AbstractModel;
import static fr.pinguet62.springspecification.core.RuleTypeFilterTest.InputTypes.Model;
import static fr.pinguet62.springspecification.core.RuleTypeFilterTest.RuleTypes.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

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
        assertThat(filter.test((Class<Rule<?>>) (Class<?>) String.class), is(false));

        // simple
        assertThat(filter.test((Class<Rule<?>>) (Class<?>) ModelRule.class), is(true));
        assertThat(filter.test((Class<Rule<?>>) (Class<?>) OtherRule.class), is(false));
        // abstract
        assertThat(filter.test((Class<Rule<?>>) (Class<?>) AbstractModelRule.class), is(true));
        // generic
        assertThat(filter.test((Class<Rule<?>>) (Class<?>) GenericRule.class), is(true));
        assertThat(filter.test((Class<Rule<?>>) (Class<?>) AbstractModelGenericRule.class), is(true));
        assertThat(filter.test((Class<Rule<?>>) (Class<?>) OtherGenericRule.class), is(false));
    }

}
