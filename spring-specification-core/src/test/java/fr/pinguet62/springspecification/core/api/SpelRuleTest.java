package fr.pinguet62.springspecification.core.api;

import lombok.Getter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.stereotype.Component;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @see SpelRule
 */
@RunWith(MockitoJUnitRunner.class)
public class SpelRuleTest {

    @Component("foo")
    public static class BeanSample {
        @Getter
        public String attr = "ok";
    }

    /**
     * Sample: value between 0 and 100.
     */
    @Test
    public void test() {
        SpelRule rule = new SpelRule(mock(BeanFactory.class));

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
        BeanFactory beanFactory = mock(BeanFactory.class);
        when(beanFactory.getBean("foo")).thenReturn(new BeanSample());

        SpelRule rule = new SpelRule(beanFactory);

        rule.setSpel("#value == @foo.attr");

        assertThat(rule.test("ok"), is(true));
        assertThat(rule.test("error"), is(false));
    }

}
