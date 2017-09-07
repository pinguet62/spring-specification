package fr.pinguet62.springruleengine.server;

import fr.pinguet62.springruleengine.core.api.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static fr.pinguet62.springruleengine.server.RuleController.PATH;
import static fr.pinguet62.springruleengine.server.TestRules.NumberRule;
import static fr.pinguet62.springruleengine.server.TestRules.StringRule;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @see RuleController
 */
@RunWith(SpringRunner.class)
@WebMvcTest
@ContextConfiguration(classes = RuleController.class)
public class RuleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private RuleService ruleService;

    /**
     * @see RuleController#getAll()
     */
    @Test
    public void test_getAll() throws Exception {
        when(ruleService.getAllRules()).thenReturn(asList(
                (Class<Rule<?>>) (Class<?>) StringRule.class,
                (Class<Rule<?>>) (Class<?>) NumberRule.class));
        mockMvc
                .perform(get(PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", equalTo(2)))
                .andExpect(jsonPath("$[0].key", not(is(nullValue()))));
    }

}
