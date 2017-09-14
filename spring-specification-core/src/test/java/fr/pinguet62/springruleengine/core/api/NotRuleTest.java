package fr.pinguet62.springruleengine.core.api;

import org.junit.Test;

import static fr.pinguet62.springruleengine.core.api.TestRules.FALSE_RULE;
import static fr.pinguet62.springruleengine.core.api.TestRules.TRUE_RULE;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @see NotRule
 */
public class NotRuleTest {

    /**
     * @see NotRule#test(Object)
     */
    @Test
    public void test_test() {
        assertThat(new NotRule(TRUE_RULE).test(null), is(false));
        assertThat(new NotRule(FALSE_RULE).test(null), is(true));
    }

}
