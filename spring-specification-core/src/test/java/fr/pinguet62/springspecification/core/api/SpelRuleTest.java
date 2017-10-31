package fr.pinguet62.springspecification.core.api;

import fr.pinguet62.springspecification.core.TestApplication;
import lombok.Getter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import static fr.pinguet62.springspecification.core.api.SpelRuleTest.BeanSample;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @see SpelRule
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestApplication.class, BeanSample.class})
public class SpelRuleTest {

    @Component("foo")
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

        assertThat(rule.test(-1), is(false));
        assertThat(rule.test(1), is(true));
        assertThat(rule.test(8), is(true));
        assertThat(rule.test(18), is(true));
        assertThat(rule.test(19), is(false));
        assertThat(rule.test(42), is(false));
    }

    /**
     * Access to current Spring Context.
     */
    @Test
    public void test_springContext() {
        SpelRule rule = beanFactory.getBean(SpelRule.class);

        rule.setSpel("#value == @foo.attr");

        assertThat(rule.test("ok"), is(true));
        assertThat(rule.test("error"), is(false));
    }

}
