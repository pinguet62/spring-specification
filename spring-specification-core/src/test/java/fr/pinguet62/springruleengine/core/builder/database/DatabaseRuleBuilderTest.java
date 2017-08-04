package fr.pinguet62.springruleengine.core.builder.database;

import fr.pinguet62.springruleengine.core.Context;
import fr.pinguet62.springruleengine.core.TestApplication;
import fr.pinguet62.springruleengine.core.api.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static fr.pinguet62.springruleengine.core.builder.database.DatabaseRuleBuilderTest.TestRules;
import static org.junit.Assert.assertNotNull;

/**
 * Test case:
 * <pre>
 *     and: {
 *         not: {
 *             first
 *         },
 *         or: {
 *             second,
 *             thrid
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
        public static class FirstCustomRule implements Rule {
            @Override
            public boolean test(Context context) {
                return true;
            }
        }

        @Component("secondCustomRule")
        public static class SecondCustomRule implements Rule {
            @Override
            public boolean test(Context context) {
                return true;
            }
        }

        @Component("thirdCustomRule")
        public static class ThirdCustomRule implements Rule {
            @Override
            public boolean test(Context context) {
                return true;
            }
        }
    }

    @Autowired
    private DatabaseRuleBuilder ruleBuilder;

    @Test
    public void test() {
        Rule rule = ruleBuilder.apply(1);
        assertNotNull(rule);
    }

}