package fr.pinguet62.springruleengine.core.builder.database.parameter;

import fr.pinguet62.springruleengine.core.TestApplication;
import lombok.Getter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static fr.pinguet62.springruleengine.core.builder.database.parameter.RuleParameterAutowireCandidateResolverTest.TestModel;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * @see RuleParameterAutowireCandidateResolver
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TestApplication.class, TestModel.class})
public class RuleParameterAutowireCandidateResolverTest {

    @Component
    @Scope(SCOPE_PROTOTYPE)
    public static class TestModel {
        @Getter
        @RuleParameter("k_attribute")
        private String attribute;

        @Getter
        private String constructor;

        @Getter
        private String setter;

        @Getter
        @RuleParameter("k_spel")
        private Integer spel;

        public TestModel(@RuleParameter("k_constructor") String constructor) {
            this.constructor = constructor;
        }

        @RuleParameter("k_setter")
        public void setSetter(String param) {
            setter = param;
        }
    }

    @Autowired
    private BeanFactory beanFactory;

    @Test
    public void test() {
        // context
        Map<String, String> parameters = new HashMap<>();
        parameters.put("k_attribute", "v_attribute");
        parameters.put("k_setter", "v_setter");
        parameters.put("k_constructor", "v_constructor");
        parameters.put("k_spel", "#{ T(java.lang.Math).sqrt(1764) }");
        ParameterInjector.CONTEXT.set(parameters);

        // execution
        TestModel rule = beanFactory.getBean(TestModel.class);

        // check
        assertEquals("v_attribute", rule.getAttribute());
        assertEquals("v_setter", rule.getSetter());
        assertEquals("v_constructor", rule.getConstructor());
        assertTrue(0 <= rule.getSpel() && rule.getSpel() <= 100);
    }

}