package fr.pinguet62.springruleengine.core.api;

import fr.pinguet62.springruleengine.core.Jsr303ValidationAspect;
import org.aspectj.lang.annotation.Aspect;
import org.junit.Test;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collection;

import static fr.pinguet62.springruleengine.core.api.TestRules.FALSE_RULE;
import static fr.pinguet62.springruleengine.core.api.TestRules.TRUE_RULE;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * @see OrRule
 */
public class OrRuleTest {

    /**
     * @see OrRule#test(Object)
     */
    @Test
    public void test_test() {
        assertThat(new OrRule(TRUE_RULE).test(null), is(true));
        assertThat(new OrRule(FALSE_RULE).test(null), is(false));

        assertThat(new OrRule(TRUE_RULE, TRUE_RULE).test(null), is(true));
        assertThat(new OrRule(TRUE_RULE, FALSE_RULE).test(null), is(true));
        assertThat(new OrRule(FALSE_RULE, TRUE_RULE).test(null), is(true));
        assertThat(new OrRule(FALSE_RULE, FALSE_RULE).test(null), is(false));
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
                fail(format("@%s %s not enabled", Aspect.class.getSimpleName(), Jsr303ValidationAspect.class.getSimpleName()));
            } catch (ConstraintViolationException e) {
                // ok
            }
    }

}
