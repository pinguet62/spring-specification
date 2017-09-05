package fr.pinguet62.springruleengine.core.api;

import org.junit.Test;

import static fr.pinguet62.springruleengine.core.api.TestRules.FALSE_RULE;
import static fr.pinguet62.springruleengine.core.api.TestRules.TRUE_RULE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @see RuleUtils
 */
public class RuleUtilsTest {

    /**
     * @see RuleUtils#and(Rule, Rule[])
     */
    @Test
    public void test_and() {
        assertTrue(RuleUtils.and(TRUE_RULE).test(null));
        assertFalse(RuleUtils.and(FALSE_RULE).test(null));

        assertTrue(RuleUtils.and(TRUE_RULE, TRUE_RULE).test(null));
        assertFalse(RuleUtils.and(TRUE_RULE, FALSE_RULE).test(null));
        assertFalse(RuleUtils.and(FALSE_RULE, TRUE_RULE).test(null));
        assertFalse(RuleUtils.and(FALSE_RULE, FALSE_RULE).test(null));
    }

    /**
     * @see RuleUtils#or(Rule, Rule[])
     */
    @Test
    public void test_or() {
        assertTrue(RuleUtils.or(TRUE_RULE).test(null));
        assertFalse(RuleUtils.or(FALSE_RULE).test(null));

        assertTrue(RuleUtils.or(TRUE_RULE, TRUE_RULE).test(null));
        assertTrue(RuleUtils.or(TRUE_RULE, FALSE_RULE).test(null));
        assertTrue(RuleUtils.or(FALSE_RULE, TRUE_RULE).test(null));
        assertFalse(RuleUtils.or(FALSE_RULE, FALSE_RULE).test(null));
    }

    /**
     * @see RuleUtils#not(Rule)
     */
    @Test
    public void test_not() {
        assertFalse(RuleUtils.not(TRUE_RULE).test(null));
        assertTrue(RuleUtils.not(FALSE_RULE).test(null));
    }

}