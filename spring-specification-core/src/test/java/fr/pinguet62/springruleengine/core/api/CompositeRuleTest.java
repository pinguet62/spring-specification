package fr.pinguet62.springruleengine.core.api;

import org.junit.Test;

import static fr.pinguet62.springruleengine.core.api.TestRules.FALSE_RULE;
import static fr.pinguet62.springruleengine.core.api.TestRules.TRUE_RULE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CompositeRuleTest {

    /**
     * @see AndRule
     */
    @Test
    public void test_and() {
        assertTrue(new AndRule(TRUE_RULE, TRUE_RULE).test(null));
        assertFalse(new AndRule(TRUE_RULE, FALSE_RULE).test(null));
        assertFalse(new AndRule(FALSE_RULE, TRUE_RULE).test(null));
        assertFalse(new AndRule(FALSE_RULE, FALSE_RULE).test(null));
    }

    /**
     * @see OrRule
     */
    @Test
    public void test_or() {
        assertTrue(new OrRule(TRUE_RULE, TRUE_RULE).test(null));
        assertTrue(new OrRule(TRUE_RULE, FALSE_RULE).test(null));
        assertTrue(new OrRule(FALSE_RULE, TRUE_RULE).test(null));
        assertFalse(new OrRule(FALSE_RULE, FALSE_RULE).test(null));
    }

    /**
     * @see NotRule
     */
    @Test
    public void test_not() {
        assertFalse(new NotRule(TRUE_RULE).test(null));
        assertTrue(new NotRule(FALSE_RULE).test(null));
    }

}