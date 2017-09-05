package fr.pinguet62.springruleengine.core.api;

import org.junit.Test;

import static fr.pinguet62.springruleengine.core.api.TestRules.FALSE_RULE;
import static fr.pinguet62.springruleengine.core.api.TestRules.TRUE_RULE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @see NotRule
 */
public class NotRuleTest {

    /**
     * @see NotRule#test(Object)
     */
    @Test
    public void test_test() {
        assertFalse(new NotRule(TRUE_RULE).test(null));
        assertTrue(new NotRule(FALSE_RULE).test(null));
    }

}
