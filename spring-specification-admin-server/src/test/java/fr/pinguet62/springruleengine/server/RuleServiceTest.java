package fr.pinguet62.springruleengine.server;

import fr.pinguet62.springruleengine.core.SpringRule;
import fr.pinguet62.springruleengine.core.api.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static fr.pinguet62.springruleengine.server.RuleServiceTest.SampleRule;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RuleService.class, SampleRule.class})
public class RuleServiceTest {

    @SpringRule
    public static class SampleRule implements Rule<String> {
        @Override
        public boolean test(String value) {
            return false;
        }
    }

    @Autowired
    private RuleService ruleService;

    @Test
    public void test() {
        assertThat(ruleService.getAllRules().contains(SampleRule.class), is(true));
        assertThat(ruleService.getAllRules().contains(BeanFactory.class), is(false));
    }

}

