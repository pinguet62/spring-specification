package fr.pinguet62.springruleengine.server;

import fr.pinguet62.springruleengine.core.api.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import static fr.pinguet62.springruleengine.server.RuleServiceTest.SampleRule;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RuleService.class, SampleRule.class})
public class RuleServiceTest {

    @Component
    @Scope(SCOPE_PROTOTYPE)
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
        assertTrue(ruleService.getAllRules().contains(SampleRule.class));
        assertFalse(ruleService.getAllRules().contains(BeanFactory.class));
    }

}

