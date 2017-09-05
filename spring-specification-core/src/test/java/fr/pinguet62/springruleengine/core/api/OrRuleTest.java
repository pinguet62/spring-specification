package fr.pinguet62.springruleengine.core.api;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static fr.pinguet62.springruleengine.core.api.TestRules.FALSE_RULE;
import static fr.pinguet62.springruleengine.core.api.TestRules.TRUE_RULE;
import static java.util.Arrays.asList;
import static org.junit.Assert.*;

/**
 * @see OrRule
 */
public class OrRuleTest {

    /**
     * @see OrRule#test(Object)
     */
    @Test
    public void test_test() {
        assertTrue(new OrRule(TRUE_RULE).test(null));
        assertFalse(new OrRule(FALSE_RULE).test(null));

        assertTrue(new OrRule(TRUE_RULE, TRUE_RULE).test(null));
        assertTrue(new OrRule(TRUE_RULE, FALSE_RULE).test(null));
        assertTrue(new OrRule(FALSE_RULE, TRUE_RULE).test(null));
        assertFalse(new OrRule(FALSE_RULE, FALSE_RULE).test(null));
    }

    /**
     * @see OrRule#OrRule(Rule[])
     * @see OrRule#OrRule(Collection)
     */
    @Test
    public void test_empty() {
        for (Runnable fct : new ArrayList<Runnable>(asList(
                () -> new OrRule<String>(),
                () -> new OrRule<>(new ArrayList<>())
        )))
            try {
                fct.run();
                fail();
            } catch (IllegalArgumentException e) {
                // ok
            }
    }

}
