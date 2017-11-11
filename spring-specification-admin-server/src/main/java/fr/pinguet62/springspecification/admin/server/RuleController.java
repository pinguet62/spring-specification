package fr.pinguet62.springspecification.admin.server;

import fr.pinguet62.springspecification.admin.server.dto.RuleDto;
import fr.pinguet62.springspecification.core.RuleTypeFilter;
import fr.pinguet62.springspecification.core.api.Rule;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.function.Predicate;

import static fr.pinguet62.springspecification.admin.server.RuleController.PATH;
import static fr.pinguet62.springspecification.core.builder.database.autoconfigure.SpringSpecificationBeans.TRANSACTION_MANAGER_NAME;
import static java.util.stream.Collectors.toList;

@Transactional(TRANSACTION_MANAGER_NAME)
@RestController
@RequestMapping(PATH)
@Api(tags = "Rule", description = "Services related to `Rule`, elements used by pattern composition")
public class RuleController {

    public static final String PATH = "/rule";

    @Autowired
    private RuleService ruleService;

    @GetMapping
    @ApiOperation(value = "List all `Rule`s available into classpath.")
    public List<RuleDto> getAll() {
        return ruleService.getAllRules().stream().map(this::convert).collect(toList());
    }

    // TODO merge with getAll()
    @GetMapping(params = "argumentType")
    @ApiOperation(value = "Find `Rule`s by `argumentType` field")
    public List<RuleDto> getAvailable(@NotNull @RequestParam("argumentType") @ApiParam(value = "The type of `Rule.test()` argument. Used to *filter supported* `Rule`s supported by `BusinessRule` during adding.") String argumentTypeName) throws ClassNotFoundException {
        Class<Rule<?>> argumentType = (Class<Rule<?>>) Class.forName(argumentTypeName);
        Predicate<Class<Rule<?>>> ruleTypeFilter = new RuleTypeFilter(argumentType);
        return ruleService.getAllRules().stream().filter(ruleTypeFilter).map(this::convert).collect(toList());
    }

    private RuleDto convert(Class<Rule<?>> ruleType) {
        // @formatter:off
        return RuleDto
                .builder()
                .key(ruleService.getKey(ruleType))
                .name(ruleService.getName(ruleType))
                .description(ruleService.getDescription(ruleType))
                .build();
        // @formatter:on
    }

}