package fr.pinguet62.springruleengine.core.api;

import fr.pinguet62.springruleengine.core.Jsr303ValidationAspect;
import org.aspectj.lang.annotation.Aspect;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static fr.pinguet62.springruleengine.core.api.TestRules.FALSE_RULE;
import static fr.pinguet62.springruleengine.core.api.TestRules.TRUE_RULE;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * @see AndRule
 */
public class AndRuleTest {

    /**
     * @see AndRule#test(Object)
     */
    @Test
    public void test_test() {
        assertThat(new AndRule(TRUE_RULE).test(null), is(true));
        assertThat(new AndRule(FALSE_RULE).test(null), is(false));

        assertThat(new AndRule(TRUE_RULE, TRUE_RULE).test(null), is(true));
        assertThat(new AndRule(TRUE_RULE, FALSE_RULE).test(null), is(false));
        assertThat(new AndRule(FALSE_RULE, TRUE_RULE).test(null), is(false));
        assertThat(new AndRule(FALSE_RULE, FALSE_RULE).test(null), is(false));
    }

    /**
     * @see AndRule#AndRule(Rule[])
     * @see AndRule#AndRule(Collection)
     */
    @Test
    public void test_empty() {
        for (Runnable fct : new ArrayList<Runnable>(asList(
                () -> new AndRule<String>(),
                () -> new AndRule<>(new ArrayList<>())
        )))
            try {
                fct.run();
                fail(format("Maybe @%s %s not enabled", Aspect.class.getSimpleName(), Jsr303ValidationAspect.class.getSimpleName()));
            } catch (RuntimeException e) {
                // ok
            }
    }

}
