package fr.pinguet62.springruleengine.core;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import fr.pinguet62.springruleengine.core.Context;
import fr.pinguet62.springruleengine.core.builder.database.DatabaseRuleBuilder;
import fr.pinguet62.springruleengine.core.rule.Rule;
import fr.pinguet62.springruleengine.core.sample.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql("/sample.sql")
public class DatabaseRuleBuilderTest {

    @Autowired
    private DatabaseRuleBuilder ruleBuilder;

    @Test
    public void test() {
        Rule rule = ruleBuilder.build();

        Context context = TestUtils.sampleContext();
        boolean result = rule.test(context);
        assertTrue(result);
    }

}