package fr.pinguet62.springruleengine.core.builder.database.parameter;

import fr.pinguet62.springruleengine.core.api.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @see ParameterService
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ParameterService.class})
public class ParameterServiceTest {

    public static class ParameterizedModel implements Rule<String> {
        private String other;

        @RuleParameter("attribute")
        private String attr;

        public ParameterizedModel(String other) {
        }

        public ParameterizedModel(String arg1, @RuleParameter("constructor") String arg2, String arg3) {
        }

        public void setOther(String value) {
        }

        @RuleParameter("setter")
        public void setSetter(String value) {
        }

        @Override
        public boolean test(String value) {
            throw new UnsupportedOperationException();
        }
    }

    @Autowired
    private ParameterService parameterService;

    @Test
    public void test() {
        Set<String> keys = parameterService.getDeclaratedKeys((Class<Rule<?>>) (Class<?>) ParameterizedModel.class);

        assertEquals(3, keys.size());
        assertTrue(keys.contains("attribute"));
        assertTrue(keys.contains("constructor"));
        assertTrue(keys.contains("setter"));
    }

}
