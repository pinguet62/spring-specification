package fr.pinguet62.springspecification.core.api;

import org.junit.Test;

import static fr.pinguet62.springspecification.core.api.TestRules.FALSE_RULE;
import static fr.pinguet62.springspecification.core.api.TestRules.TRUE_RULE;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Check {@code default} {@link Rule} interface methods.
 *
 * @see Rule
 */
public class RuleTest {

    /**
     * @see Rule#and(Rule)
     */
    @Test
    public void test_and() {
        assertThat(TRUE_RULE.and(TRUE_RULE).test(null), is(true));
        assertThat(TRUE_RULE.and(FALSE_RULE).test(null), is(false));
        assertThat(FALSE_RULE.and(TRUE_RULE).test(null), is(false));
        assertThat(FALSE_RULE.and(FALSE_RULE).test(null), is(false));
    }

    /**
     * @see Rule#or(Rule)
     */
    @Test
    public void test_or() {
        assertThat(TRUE_RULE.or(TRUE_RULE).test(null), is(true));
        assertThat(TRUE_RULE.or(FALSE_RULE).test(null), is(true));
        assertThat(FALSE_RULE.or(TRUE_RULE).test(null), is(true));
        assertThat(FALSE_RULE.or(FALSE_RULE).test(null), is(false));
    }

    /**
     * @see Rule#not()
     */
    @Test
    public void test_not() {
        assertThat(TRUE_RULE.not().test(null), is(false));
        assertThat(FALSE_RULE.not().test(null), is(true));
    }

}