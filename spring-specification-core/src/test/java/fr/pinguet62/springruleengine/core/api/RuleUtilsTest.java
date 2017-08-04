package fr.pinguet62.springruleengine.core.api;

import org.junit.Test;

import static fr.pinguet62.springruleengine.core.api.RuleUtils.*;
import static fr.pinguet62.springruleengine.core.api.TestRules.FALSE_RULE;
import static fr.pinguet62.springruleengine.core.api.TestRules.TRUE_RULE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/** @see RuleUtils */
public class RuleUtilsTest {

    @Test
    public void test_and() {
        assertTrue(and(TRUE_RULE, TRUE_RULE).test(null));
        assertFalse(and(TRUE_RULE, FALSE_RULE).test(null));
        assertFalse(and(FALSE_RULE, TRUE_RULE).test(null));
        assertFalse(and(FALSE_RULE, FALSE_RULE).test(null));
    }

    @Test
    public void test_or() {
        assertTrue(or(TRUE_RULE, TRUE_RULE).test(null));
        assertTrue(or(TRUE_RULE, FALSE_RULE).test(null));
        assertTrue(or(FALSE_RULE, TRUE_RULE).test(null));
        assertFalse(or(FALSE_RULE, FALSE_RULE).test(null));
    }

    @Test
    public void test_not() {
        assertFalse(not(TRUE_RULE).test(null));
        assertTrue(not(FALSE_RULE).test(null));
    }

}