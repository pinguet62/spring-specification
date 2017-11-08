package fr.pinguet62.springspecification.core.builder.database;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import fr.pinguet62.springspecification.core.SpringRule;
import fr.pinguet62.springspecification.core.TestApplication;
import fr.pinguet62.springspecification.core.api.AndRule;
import fr.pinguet62.springspecification.core.api.NotRule;
import fr.pinguet62.springspecification.core.api.OrRule;
import fr.pinguet62.springspecification.core.api.Rule;
import fr.pinguet62.springspecification.core.builder.database.parameter.RuleParameter;
import lombok.Getter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static fr.pinguet62.springspecification.core.builder.database.DatabaseRuleBuilderTest.TestRules;
import static fr.pinguet62.springspecification.core.builder.database.DatabaseRuleBuilderTest.TestRules.*;
import static fr.pinguet62.springspecification.core.builder.database.autoconfigure.SpringSpecificationBeans.DATASOURCE_NAME;
import static fr.pinguet62.springspecification.core.builder.database.autoconfigure.SpringSpecificationBeans.TRANSACTION_MANAGER_NAME;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

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
@Transactional(TRANSACTION_MANAGER_NAME)
// DbUnit
@TestExecutionListeners(mergeMode = MERGE_WITH_DEFAULTS, listeners = DbUnitTestExecutionListener.class)
@DbUnitConfiguration(databaseConnection = DATASOURCE_NAME)
@DatabaseSetup("/sample.xml")
public class DatabaseRuleBuilderTest {

    @Component
    public static class TestRules {
        @SpringRule
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

        @SpringRule
        public static class SecondCustomRule implements Rule<Void> {
            @Override
            public boolean test(Void value) {
                return true;
            }
        }

        @SpringRule
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