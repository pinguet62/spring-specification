package fr.pinguet62.springruleengine.core.builder.database.parameter;

import lombok.Getter;
import lombok.Setter;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @see RuleParameterBeanPostProcessor
 */
public class RuleParameterBeanPostProcessorTest {

    public static class TestModel {
        @RuleParameterMap
        private Map<String, Object> params;

        @Getter
        @RuleParameter("k1")
        private String attr;

        @Getter
        private String setter;

        @RuleParameter("k2")
        public void setSetter(String param) {
            setter = param;
        }
    }

    @Test
    public void test() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("k1", "v1");
        parameters.put("k2", "v2");
        TestModel rule = new TestModel();

        new RuleParameterBeanPostProcessor(parameters).postProcessBeforeInitialization(rule, null);

        assertEquals("v1", rule.getAttr());
        assertEquals("v2", rule.getSetter());
    }

    @Test
    public void test_map() {
        Map<String, Object> params = new HashMap<>();
        params.put("k1", "v1");
        params.put("k2", "v2");
        TestModel rule = new TestModel();

        new RuleParameterBeanPostProcessor(params).postProcessBeforeInitialization(rule, null);

        assertEquals(params, rule.params);
    }

}