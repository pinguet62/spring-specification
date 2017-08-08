package fr.pinguet62.springruleengine.core.builder.database.parameter;

import lombok.Getter;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @see RuleParameterProcessor
 */
public class RuleParameterProcessorTest {

    public static class TestModel {
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

        RuleParameterProcessor.process(rule, parameters);

        assertEquals("v1", rule.getAttr());
        assertEquals("v2", rule.getSetter());
    }

}