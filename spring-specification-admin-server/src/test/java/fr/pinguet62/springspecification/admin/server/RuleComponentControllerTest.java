package fr.pinguet62.springspecification.admin.server;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import fr.pinguet62.springspecification.admin.server.dto.RuleComponentInputDto;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.springtestdbunit.assertion.DatabaseAssertionMode.NON_STRICT_UNORDERED;
import static fr.pinguet62.springspecification.admin.server.RuleComponentController.PATH;
import static java.nio.charset.Charset.defaultCharset;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;
import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @see RuleComponentController
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RuleComponentControllerTest.class)
@EnableAutoConfiguration
@AutoConfigureMockMvc
// DbUnit
@TestExecutionListeners(mergeMode = MERGE_WITH_DEFAULTS, listeners = DbUnitTestExecutionListener.class)
@DbUnitConfiguration(databaseConnection = "springSpecification.dataSource")
@DatabaseSetup("/RuleComponentControllerTest_dataset.xml")
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD) // simulate @Transactional (TODO check is works with MockMvc)
public class RuleComponentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * @see RuleComponentController#create(RuleComponentInputDto)
     */
    @Test
    @ExpectedDatabase(value = "/RuleComponentControllerTest_create_ExpectedDatabase.xml", assertionMode = NON_STRICT_UNORDERED)
    public void test_create() throws Exception {
        String body = IOUtils.toString(getClass().getResourceAsStream("/RuleComponentControllerTest_create_request.json"), defaultCharset());
        mockMvc
                .perform(
                        put(PATH)
                                .contentType(APPLICATION_JSON)
                                .content(body))
                .andExpect(status().isCreated());
    }

    /**
     * @see RuleComponentController#getById(Integer)
     */
    @Test
    public void test_getById() throws Exception {
        final int ruleComponentId = -1;
        mockMvc
                .perform(get(RuleComponentController.PATH + "/{id}", ruleComponentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(ruleComponentId)))
                .andExpect(jsonPath("$.key", equalTo("any")))
                .andExpect(jsonPath("$.components.length()", equalTo(1)))
                .andExpect(jsonPath("$.components[0].id", equalTo(-11)))
                .andExpect(jsonPath("$.components[0].key", equalTo("any")))
                .andExpect(jsonPath("$.components[0].parameters.length()", equalTo(1)))
                .andExpect(jsonPath("$.components[0].parameters[0].id", equalTo(-111)))
                .andExpect(jsonPath("$.components[0].parameters[0].key", equalTo("key111")))
                .andExpect(jsonPath("$.components[0].parameters[0].value", equalTo("value111")));
    }

    /**
     * @see RuleComponentController#update(Integer, RuleComponentInputDto)
     */
    @Test
    @ExpectedDatabase(value = "/RuleComponentControllerTest_update_ExpectedDatabase.xml", assertionMode = NON_STRICT_UNORDERED)
    public void test_update() throws Exception {
        int ruleComponentId = -11;
        String body = IOUtils.toString(getClass().getResourceAsStream("/RuleComponentControllerTest_update_request.json"), defaultCharset());
        mockMvc
                .perform(
                        patch(RuleComponentController.PATH + "/{id}", Integer.toString(ruleComponentId))
                                .contentType(APPLICATION_JSON)
                                .content(body))
                .andExpect(status().isOk());
    }

    /**
     * <ul>
     * <li>Delete all sub-{@link fr.pinguet62.springspecification.core.builder.database.model.RuleComponentEntity}</li>
     * <li>Delete all {@link fr.pinguet62.springspecification.core.builder.database.model.ParameterEntity}</li>
     * </ul>
     *
     * @see RuleComponentController#delete(Integer)
     */
    @Test
    @ExpectedDatabase(value = "/RuleComponentControllerTest_delete_ExpectedDatabase.xml", assertionMode = NON_STRICT_UNORDERED)
    public void test_delete() throws Exception {
        int ruleComponentId = -11;
        mockMvc
                .perform(delete(RuleComponentController.PATH + "/{id}", ruleComponentId))
                .andExpect(status().isOk());
    }

}
