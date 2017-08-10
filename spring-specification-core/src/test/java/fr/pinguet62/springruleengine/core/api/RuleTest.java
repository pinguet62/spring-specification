package fr.pinguet62.springruleengine.core.api;

import org.junit.Test;

import static fr.pinguet62.springruleengine.core.api.TestRules.FALSE_RULE;
import static fr.pinguet62.springruleengine.core.api.TestRules.TRUE_RULE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @see Rule
 */
public class RuleTest {

    /**
     * @see Rule#and(Rule)
     */
    @Test
    public void test_and() {
        assertTrue(TRUE_RULE.and(TRUE_RULE).test(null));
        assertFalse(TRUE_RULE.and(FALSE_RULE).test(null));
        assertFalse(FALSE_RULE.and(TRUE_RULE).test(null));
        assertFalse(FALSE_RULE.and(FALSE_RULE).test(null));
    }

    /**
     * @see Rule#or(Rule)
     */
    @Test
    public void test_or() {
        assertTrue(TRUE_RULE.or(TRUE_RULE).test(null));
        assertTrue(TRUE_RULE.or(FALSE_RULE).test(null));
        assertTrue(FALSE_RULE.or(TRUE_RULE).test(null));
        assertFalse(FALSE_RULE.or(FALSE_RULE).test(null));
    }

    /**
     * @see Rule#not()
     */
    @Test
    public void test_not() {
        assertFalse(TRUE_RULE.not().test(null));
        assertTrue(FALSE_RULE.not().test(null));
    }

}