package fr.pinguet62.springspecification.admin.server;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import fr.pinguet62.springspecification.admin.server.dto.ParameterInputDto;
import fr.pinguet62.springspecification.core.api.Rule;
import fr.pinguet62.springspecification.core.builder.database.parameter.ParameterService;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static com.github.springtestdbunit.assertion.DatabaseAssertionMode.NON_STRICT_UNORDERED;
import static fr.pinguet62.springspecification.admin.server.TestRules.ParameterizedRule;
import static fr.pinguet62.springspecification.core.builder.database.autoconfigure.SpringSpecificationBeans.DATASOURCE_NAME;
import static java.nio.charset.Charset.defaultCharset;
import static java.util.Arrays.asList;
import static java.util.Optional.of;
import static org.apache.commons.collections.SetUtils.unmodifiableSet;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;
import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @see ParameterController
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ParameterControllerTest.class)
@EnableAutoConfiguration
@AutoConfigureMockMvc
// DbUnit
@TestExecutionListeners(mergeMode = MERGE_WITH_DEFAULTS, listeners = DbUnitTestExecutionListener.class)
@DbUnitConfiguration(databaseConnection = DATASOURCE_NAME)
@DatabaseSetup("/ParameterControllerTest_dataset.xml")
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD) // simulate @Transactional (TODO check it works with MockMvc)
public class ParameterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private RuleService ruleService;

    @MockBean
    private ParameterService parameterService;

    /**
     * @see ParameterController#getByRuleComponent(Integer)
     */
    @Test
    public void test_getByRuleComponent() throws Exception {
        final int ruleComponentId = -1;
        mockMvc
                .perform(get(RuleComponentController.PATH + "/{ruleComponent}/parameter", ruleComponentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()", equalTo(2)))
                .andExpect(jsonPath("$[?(@.id==-1)].key", equalTo(asList("key1"))))
                .andExpect(jsonPath("$[?(@.id==-1)].value", equalTo(asList("value1"))));
    }

    /**
     * @see ParameterController#getKeyByRule(String)
     */
    @Test
    public void test_getKeyByRule() throws Exception {
        when(ruleService.getFromKey(anyString())).thenReturn(of((Class<Rule<?>>) (Class<?>) ParameterizedRule.class)); // not 404
        final Set<String> parameterKeys = unmodifiableSet(new HashSet(asList("constructor", "attribute", "setter")));
        when(parameterService.getDeclaratedKeys(any(Class.class))).thenReturn(parameterKeys);

        mockMvc
                .perform(get(RuleController.PATH + "/{rule}/parameter/key", "any"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()", equalTo(3)))
                .andExpect(jsonPath("$", containsInAnyOrder(parameterKeys.toArray())));
    }

    /**
     * @see ParameterController#getKeyByRule(String)
     */
    @Test
    public void test_getKeyByRule_notFound() throws Exception {
        String ruleKey = "unknown";
        mockMvc
                .perform(get(RuleController.PATH + "/{rule}/key", ruleKey))
                .andExpect(status().isNotFound());
    }

    /**
     * @see ParameterController#create(Integer, ParameterInputDto)
     */
    @Test
    @ExpectedDatabase(value = "/ParameterControllerTest_create_ExpectedDatabase.xml", assertionMode = NON_STRICT_UNORDERED)
    public void test_create() throws Exception {
        String body = IOUtils.toString(getClass().getResourceAsStream("/ParameterControllerTest_create_request.json"), defaultCharset());
        mockMvc
                .perform(
                        put(RuleComponentController.PATH + "/{ruleComponent}/parameter", -1)
                                .contentType(APPLICATION_JSON)
                                .content(body))
                .andExpect(status().isCreated());
    }

    /**
     * @see ParameterController#update(Integer, Integer, ParameterInputDto)
     */
    @Test
    @ExpectedDatabase(value = "/ParameterControllerTest_update_ExpectedDatabase.xml", assertionMode = NON_STRICT_UNORDERED)
    public void test_update() throws Exception {
        int parameterId = -2;
        String body = IOUtils.toString(getClass().getResourceAsStream("/ParameterControllerTest_update_request.json"), defaultCharset());
        mockMvc
                .perform(
                        post(RuleComponentController.PATH + "/{ruleComponent}/parameter/{id}", Integer.toString(-1), Integer.toString(parameterId))
                                .contentType(APPLICATION_JSON)
                                .content(body))
                .andExpect(status().isOk());
    }

    /**
     * @see ParameterController#update(Integer, Integer, ParameterInputDto)
     */
    @Test
    public void test_update_notFound() throws Exception {
        int parameterId = 999; // not in database
        String body = IOUtils.toString(getClass().getResourceAsStream("/ParameterControllerTest_update_request.json"), defaultCharset()); // any validating @RequestBody
        mockMvc
                .perform(
                        post(RuleComponentController.PATH + "/{ruleComponent}/parameter/{id}", -1, parameterId)
                                .contentType(APPLICATION_JSON)
                                .content(body))
                .andExpect(status().isNotFound());
    }

    /**
     * @see ParameterController#delete(Integer, Integer)
     */
    @Test
    @ExpectedDatabase(value = "/ParameterControllerTest_delete_ExpectedDatabase.xml", assertionMode = NON_STRICT_UNORDERED)
    public void test_delete() throws Exception {
        int parameterId = -2;
        mockMvc
                .perform(delete(RuleComponentController.PATH + "/{ruleComponent}/parameter/{id}", -1, parameterId))
                .andExpect(status().isOk());
    }

    /**
     * @see ParameterController#delete(Integer, Integer)
     */
    @Test
    public void test_delete_notFound() throws Exception {
        int parameterId = 999; // not in database
        mockMvc
                .perform(delete(RuleComponentController.PATH + "/{ruleComponent}/parameter/{id}", -1, parameterId))
                .andExpect(status().isNotFound());
    }

}
