package fr.pinguet62.springspecification.core.api;

import org.junit.Test;

/**
 * @see FailingRule
 */
public class FailingRuleTest {

    /**
     * @see FailingRule#test(Object)
     */
    @Test(expected = Exception.class)
    public void test_test() {
        new FailingRule<String>().test("any");
    }

}
