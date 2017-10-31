package fr.pinguet62.springspecification.admin.server;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import fr.pinguet62.springspecification.admin.server.dto.BusinessRuleInputDto;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.github.springtestdbunit.assertion.DatabaseAssertionMode.NON_STRICT_UNORDERED;
import static java.nio.charset.Charset.defaultCharset;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;
import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.web.util.UriUtils.encode;

/**
 * @see BusinessRuleController
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BusinessRuleControllerTest.class)
@EnableAutoConfiguration
@AutoConfigureMockMvc
// DbUnit
@TestExecutionListeners(mergeMode = MERGE_WITH_DEFAULTS, listeners = DbUnitTestExecutionListener.class)
@DbUnitConfiguration(databaseConnection = "springSpecification.dataSource")
@DatabaseSetup("/BusinessRuleControllerTest_dataset.xml")
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD) // simulate @Transactional (TODO check is works with MockMvc)
public class BusinessRuleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * @see BusinessRuleController#getById(String)
     */
    @Test
    public void test_getById() throws Exception {
        final String businessRuleId = "test";
        mockMvc
                .perform(get(BusinessRuleController.PATH + "/{id}", businessRuleId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(businessRuleId)))
                .andExpect(jsonPath("$.argumentType", equalTo("fr.pinguet62.springspecification.sample.model.Product")))
                .andExpect(jsonPath("$.rootRuleComponent.id", equalTo(-1)));
    }

    /**
     * @see BusinessRuleController#getById(String)
     */
    @Test
    public void test_getById_notFound() throws Exception {
        mockMvc
                .perform(get(BusinessRuleController.PATH + "/{id}", "unknown"))
                .andExpect(status().isNotFound());
    }

    /**
     * @see BusinessRuleController#getAll()
     */
    @Test
    public void test_getAll() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.get(BusinessRuleController.PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()", equalTo(2)))
                .andExpect(jsonPath("$[?(@.rootRuleComponent.id==-1)].id", equalTo(asList("test"))))
                .andExpect(jsonPath("$[?(@.rootRuleComponent.id==-2)].id", equalTo(asList("other"))));
    }

    /**
     * @see BusinessRuleController#create(BusinessRuleInputDto)
     */
    @Test
    @ExpectedDatabase(value = "/BusinessRuleControllerTest_create_ExpectedDatabase.xml", assertionMode = NON_STRICT_UNORDERED)
    public void test_create() throws Exception {
        String body = IOUtils.toString(getClass().getResourceAsStream("/BusinessRuleControllerTest_create_request.json"), defaultCharset());
        mockMvc
                .perform(
                        MockMvcRequestBuilders.put(BusinessRuleController.PATH)
                                .contentType(APPLICATION_JSON)
                                .content(body))
                .andExpect(status().isCreated())
                .andExpect(redirectedUrl(encode(BusinessRuleController.PATH + "/" + "new id", defaultCharset().name())));
    }

    /**
     * <ul>
     * <li>Delete all {@link fr.pinguet62.springspecification.core.builder.database.model.BusinessRuleEntity}</li>
     * <li>Delete all {@link fr.pinguet62.springspecification.core.builder.database.model.RuleComponentEntity}</li>
     * <li>Delete all {@link fr.pinguet62.springspecification.core.builder.database.model.ParameterEntity}</li>
     * </ul>
     *
     * @see BusinessRuleController#delete(String)
     */
    @Test
    @ExpectedDatabase(value = "/BusinessRuleControllerTest_delete_ExpectedDatabase.xml", assertionMode = NON_STRICT_UNORDERED)
    public void test_delete() throws Exception {
        String businessRuleId = "test";
        mockMvc
                .perform(delete(BusinessRuleController.PATH + "/{id}", businessRuleId))
                .andExpect(status().isOk());
    }

}
