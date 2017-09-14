package fr.pinguet62.springruleengine.core.api;

import org.junit.Test;

import static fr.pinguet62.springruleengine.core.api.TestRules.FALSE_RULE;
import static fr.pinguet62.springruleengine.core.api.TestRules.TRUE_RULE;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @see RuleUtils
 */
public class RuleUtilsTest {

    /**
     * @see RuleUtils#and(Rule, Rule[])
     */
    @Test
    public void test_and() {
        assertThat(RuleUtils.and(TRUE_RULE).test(null), is(true));
        assertThat(RuleUtils.and(FALSE_RULE).test(null), is(false));

        assertThat(RuleUtils.and(TRUE_RULE, TRUE_RULE).test(null), is(true));
        assertThat(RuleUtils.and(TRUE_RULE, FALSE_RULE).test(null), is(false));
        assertThat(RuleUtils.and(FALSE_RULE, TRUE_RULE).test(null), is(false));
        assertThat(RuleUtils.and(FALSE_RULE, FALSE_RULE).test(null), is(false));
    }

    /**
     * @see RuleUtils#or(Rule, Rule[])
     */
    @Test
    public void test_or() {
        assertThat(RuleUtils.or(TRUE_RULE).test(null), is(true));
        assertThat(RuleUtils.or(FALSE_RULE).test(null), is(false));

        assertThat(RuleUtils.or(TRUE_RULE, TRUE_RULE).test(null), is(true));
        assertThat(RuleUtils.or(TRUE_RULE, FALSE_RULE).test(null), is(true));
        assertThat(RuleUtils.or(FALSE_RULE, TRUE_RULE).test(null), is(true));
        assertThat(RuleUtils.or(FALSE_RULE, FALSE_RULE).test(null), is(false));
    }

    /**
     * @see RuleUtils#not(Rule)
     */
    @Test
    public void test_not() {
        assertThat(RuleUtils.not(TRUE_RULE).test(null), is(false));
        assertThat(RuleUtils.not(FALSE_RULE).test(null), is(true));
    }

}