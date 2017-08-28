package fr.pinguet62.springruleengine.core.api;

import fr.pinguet62.springruleengine.core.TestApplication;
import lombok.Getter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import static fr.pinguet62.springruleengine.core.api.SpelRuleTest.BeanSample;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @see SpelRule
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestApplication.class, BeanSample.class})
public class SpelRuleTest {

    @Component("foo")
    @Scope(SCOPE_PROTOTYPE)
    public static class BeanSample {
        @Getter
        public String attr = "ok";
    }

    @Autowired
    private BeanFactory beanFactory;

    /**
     * Sample: value between 0 and 100.
     */
    @Test
    public void test() {
        SpelRule rule = new SpelRule();
        rule.setSpel("0 <= #value && #value <= 18");

        assertFalse(rule.test(-1));
        assertTrue(rule.test(1));
        assertTrue(rule.test(8));
        assertTrue(rule.test(18));
        assertFalse(rule.test(19));
        assertFalse(rule.test(42));
    }

    /**
     * Access to current Spring Context.
     */
    @Test
    public void test_springContext() {
        SpelRule rule = beanFactory.getBean(SpelRule.class);

        rule.setSpel("#value == @foo.attr");

        assertTrue(rule.test("ok"));
        assertFalse(rule.test("error"));
    }

}
