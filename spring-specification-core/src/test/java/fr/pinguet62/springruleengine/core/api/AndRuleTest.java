package fr.pinguet62.springruleengine.core.api;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static fr.pinguet62.springruleengine.core.api.TestRules.FALSE_RULE;
import static fr.pinguet62.springruleengine.core.api.TestRules.TRUE_RULE;
import static java.util.Arrays.asList;
import static org.junit.Assert.*;

/**
 * @see AndRule
 */
public class AndRuleTest {

    /**
     * @see AndRule#test(Object)
     */
    @Test
    public void test_test() {
        assertTrue(new AndRule(TRUE_RULE).test(null));
        assertFalse(new AndRule(FALSE_RULE).test(null));

        assertTrue(new AndRule(TRUE_RULE, TRUE_RULE).test(null));
        assertFalse(new AndRule(TRUE_RULE, FALSE_RULE).test(null));
        assertFalse(new AndRule(FALSE_RULE, TRUE_RULE).test(null));
        assertFalse(new AndRule(FALSE_RULE, FALSE_RULE).test(null));
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
                fail();
            } catch (IllegalArgumentException e) {
                // ok
            }
    }

}
