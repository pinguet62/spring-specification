package fr.pinguet62.springspecification.admin.server;

import fr.pinguet62.springspecification.admin.server.dto.ParameterDto;
import fr.pinguet62.springspecification.admin.server.dto.ParameterInputDto;
import fr.pinguet62.springspecification.core.api.Rule;
import fr.pinguet62.springspecification.core.builder.database.model.ParameterEntity;
import fr.pinguet62.springspecification.core.builder.database.model.RuleComponentEntity;
import fr.pinguet62.springspecification.core.builder.database.parameter.ParameterService;
import fr.pinguet62.springspecification.core.builder.database.repository.ParameterRepository;
import fr.pinguet62.springspecification.core.builder.database.repository.RuleComponentRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static fr.pinguet62.springspecification.admin.server.ParameterController.PATH;
import static fr.pinguet62.springspecification.core.builder.database.autoconfigure.SpringSpecificationBeans.TRANSACTION_MANAGER_NAME;
import static java.util.stream.Collectors.toList;

@Transactional(TRANSACTION_MANAGER_NAME)
@RestController
@RequestMapping(PATH)
@Api(tags = "Parameter", description = "Services related to `Parameter`s, linked to a `RuleComponent`")
public class ParameterController {

    public static final String PATH = "/parameter";

    @Autowired
    private ParameterService parameterService;

    @Autowired
    private RuleService ruleService;

    @Autowired
    private ParameterRepository parameterRepository;

    @Autowired
    private RuleComponentRepository ruleRepository;

    @GetMapping(params = "ruleComponent")
    @ApiOperation(value = "Find all `Parameter`s by parent `RuleComponent`")
    public ResponseEntity<List<ParameterDto>> getByRuleComponent(@NotNull @RequestParam("ruleComponent") @ApiParam(value = "The `RuleComponent.id`", required = true) Integer ruleComponentId) {
        Optional<RuleComponentEntity> rule = ruleRepository.findById(ruleComponentId);
        if (!rule.isPresent())
            return ResponseEntity.notFound().build();

        List<ParameterDto> dtos = rule.get().getParameters().stream().map(this::convert).collect(toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/key/{rule:.+}")
    @ApiOperation(value = "List all `Parameter.key` required by a `Rule`. Used for *autocompletion*.")
    public ResponseEntity<Set<String>> getKeyByRule(@NotBlank @PathVariable("rule") @ApiParam(value = "The `Rule` key", required = true) String ruleKey) {
        Optional<Class<Rule<?>>> ruleOp = ruleService.getFromKey(ruleKey);
        if (!ruleOp.isPresent())
            return ResponseEntity.notFound().build();

        Class<Rule<?>> rule = ruleOp.get();
        Set<String> keys = parameterService.getDeclaratedKeys(rule);
        return ResponseEntity.ok(keys);
    }

    @PutMapping
    @ApiOperation(value = "Create a new `Parameter`")
    public ResponseEntity<ParameterDto> create(@Valid @RequestBody ParameterInputDto dto) {
        ParameterEntity entity = new ParameterEntity();
        // entity.setId();
        entity.setKey(dto.getKey());
        entity.setValue(dto.getValue());
        entity.setRule(ruleRepository.findById(dto.getRuleComponent()).get());
        entity = parameterRepository.save(entity);

        return ResponseEntity.created(URI.create(PATH + "/" + entity.getId())).body(convert(entity));
    }

    @PostMapping("/{id}")
    @ApiOperation(value = "Update an existing `Parameter`")
    public ResponseEntity<ParameterDto> update(@NotNull @PathVariable @ApiParam(value = "Its `id`", required = true) Integer id, @Valid @RequestBody ParameterInputDto dto) {
        Optional<ParameterEntity> entityOp = parameterRepository.findById(id);
        if (!entityOp.isPresent())
            return ResponseEntity.notFound().build();
        ParameterEntity entity = entityOp.get();

        if (dto.getKey() != null)
            entity.setKey(dto.getKey());
        if (dto.getValue() != null)
            entity.setValue(dto.getValue());

        entity = parameterRepository.save(entity);

        return ResponseEntity.ok().body(convert(entity));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete an existing `Parameter`")
    public ResponseEntity<ParameterDto> delete(@NotNull @PathVariable @ApiParam(value = "Its `id`", required = true) Integer id) {
        Optional<ParameterEntity> entity = parameterRepository.findById(id);
        if (!entity.isPresent())
            return ResponseEntity.notFound().build();

        ParameterDto dto = convert(entity.get());

        parameterRepository.deleteById(id);

        return ResponseEntity.ok(dto);
    }

    private ParameterDto convert(ParameterEntity entity) {
        // @formatter:off
        return ParameterDto
                .builder()
                .id(entity.getId())
                .key(entity.getKey())
                .value(entity.getValue())
                .build();
        // @formatter:on
    }

}