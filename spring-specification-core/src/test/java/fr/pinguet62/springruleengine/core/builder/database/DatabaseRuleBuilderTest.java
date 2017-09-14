package fr.pinguet62.springruleengine.core.builder.database;

import fr.pinguet62.springruleengine.core.TestApplication;
import fr.pinguet62.springruleengine.core.api.AndRule;
import fr.pinguet62.springruleengine.core.api.NotRule;
import fr.pinguet62.springruleengine.core.api.OrRule;
import fr.pinguet62.springruleengine.core.api.Rule;
import fr.pinguet62.springruleengine.core.builder.database.parameter.RuleParameter;
import lombok.Getter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static fr.pinguet62.springruleengine.core.builder.database.DatabaseRuleBuilderTest.TestRules;
import static fr.pinguet62.springruleengine.core.builder.database.DatabaseRuleBuilderTest.TestRules.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * Test case:
 * <pre>
 *     and: {
 *         not: {
 *             first
 *         },
 *         or: {
 *             second,
 *             third
 *         }
 *     }
 * </pre>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestApplication.class, TestRules.class})
@Sql("/sample.sql")
@Transactional
public class DatabaseRuleBuilderTest {

    @Component
    public static class TestRules {
        @Component("firstCustomRule")
        @Scope(SCOPE_PROTOTYPE)
        public static class FirstCustomRule implements Rule<Void> {
            public FirstCustomRule(@RuleParameter("") String param1) {
            }

            @Getter
            @RuleParameter("111_k1")
            private String param1;

            @Getter
            @RuleParameter("111_k2")
            private String param2;

            @Override
            public boolean test(Void value) {
                return true;
            }
        }

        @Component("secondCustomRule")
        @Scope(SCOPE_PROTOTYPE)
        public static class SecondCustomRule implements Rule<Void> {
            @Override
            public boolean test(Void value) {
                return true;
            }
        }

        @Component("thirdCustomRule")
        @Scope(SCOPE_PROTOTYPE)
        public static class ThirdCustomRule implements Rule<Void> {
            @Getter
            @RuleParameter("122_k1")
            private String param;

            @Override
            public boolean test(Void value) {
                return true;
            }
        }
    }

    @Autowired
    private DatabaseRuleBuilder ruleBuilder;

    @Test
    public void test() {
        Rule rule = ruleBuilder.apply("test");
        assertThat(rule, is(not(nullValue())));

        assertThat(rule, is(instanceOf(AndRule.class)));
        AndRule andRule = (AndRule) rule;
        Rule[] subAndRules = andRule.getRules();
        {
            assertThat(subAndRules[0], is(instanceOf(NotRule.class)));
            NotRule notRule = (NotRule) subAndRules[0];
            Rule subNotRules = notRule.getRule();
            {
                assertThat(subNotRules, is(instanceOf(FirstCustomRule.class)));
                FirstCustomRule firstCustomRule = (FirstCustomRule) subNotRules;
                assertThat(firstCustomRule.param1, is("111_v1"));
                assertThat(firstCustomRule.param2, is("111_v2"));
            }
        }
        {
            assertThat(subAndRules[1], is(instanceOf(OrRule.class)));
            OrRule notRule = (OrRule) subAndRules[1];
            Rule[] subOrRules = notRule.getRules();
            {
                assertThat(subOrRules[0], is(instanceOf(SecondCustomRule.class)));
                SecondCustomRule secondCustomRule = (SecondCustomRule) subOrRules[0];
            }
            {
                assertThat(subOrRules[1], is(instanceOf(ThirdCustomRule.class)));
                ThirdCustomRule thirdCustomRule = (ThirdCustomRule) subOrRules[1];
                assertThat(thirdCustomRule.param, is("122_v1"));
            }
        }
    }

}