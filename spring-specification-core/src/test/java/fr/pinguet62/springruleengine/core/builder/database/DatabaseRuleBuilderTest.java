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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
        assertNotNull(rule);

        assertEquals(AndRule.class, rule.getClass());
        AndRule andRule = (AndRule) rule;
        Rule[] subAndRules = andRule.getRules();
        {
            assertEquals(NotRule.class, subAndRules[0].getClass());
            NotRule notRule = (NotRule) subAndRules[0];
            Rule subNotRules = notRule.getRule();
            {
                assertEquals(FirstCustomRule.class, subNotRules.getClass());
                FirstCustomRule firstCustomRule = (FirstCustomRule) subNotRules;
                assertEquals("111_v1", firstCustomRule.param1);
                assertEquals("111_v2", firstCustomRule.param2);
            }
        }
        {
            assertEquals(OrRule.class, subAndRules[1].getClass());
            OrRule notRule = (OrRule) subAndRules[1];
            Rule[] subOrRules = notRule.getRules();
            {
                assertEquals(SecondCustomRule.class, subOrRules[0].getClass());
                SecondCustomRule secondCustomRule = (SecondCustomRule) subOrRules[0];
            }
            {
                assertEquals(ThirdCustomRule.class, subOrRules[1].getClass());
                ThirdCustomRule thirdCustomRule = (ThirdCustomRule) subOrRules[1];
                assertEquals("122_v1", thirdCustomRule.param);
            }
        }
    }

}