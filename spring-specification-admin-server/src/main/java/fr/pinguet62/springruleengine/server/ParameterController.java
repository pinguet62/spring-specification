package fr.pinguet62.springruleengine.server;

import fr.pinguet62.springruleengine.core.api.Rule;
import fr.pinguet62.springruleengine.core.builder.database.model.ParameterEntity;
import fr.pinguet62.springruleengine.core.builder.database.model.RuleComponentEntity;
import fr.pinguet62.springruleengine.core.builder.database.parameter.ParameterService;
import fr.pinguet62.springruleengine.core.builder.database.repository.ParameterRepository;
import fr.pinguet62.springruleengine.core.builder.database.repository.RuleComponentRepository;
import fr.pinguet62.springruleengine.server.dto.ParameterDto;
import fr.pinguet62.springruleengine.server.dto.ParameterInputDto;
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

import static fr.pinguet62.springruleengine.core.builder.database.autoconfigure.SpringSpecificationBeans.TRANSACTION_MANAGER_NAME;
import static fr.pinguet62.springruleengine.server.ParameterController.PATH;
import static java.util.stream.Collectors.toList;

@Transactional(TRANSACTION_MANAGER_NAME)
@RestController
@RequestMapping(PATH)
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
    public ResponseEntity<List<ParameterDto>> getByRuleComponent(@NotNull @RequestParam("ruleComponent") Integer ruleComponentId) {
        Optional<RuleComponentEntity> rule = ruleRepository.findById(ruleComponentId);
        if (!rule.isPresent())
            return ResponseEntity.notFound().build();

        List<ParameterDto> dtos = rule.get().getParameters().stream().map(this::convert).collect(toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/key/{rule:.+}")
    public ResponseEntity<Set<String>> getKeyByRule(@NotBlank @PathVariable("rule") String ruleKey) {
        Optional<Class<Rule<?>>> ruleOp = ruleService.getFromKey(ruleKey);
        if (!ruleOp.isPresent())
            return ResponseEntity.notFound().build();

        Class<Rule<?>> rule = ruleOp.get();
        Set<String> keys = parameterService.getDeclaratedKeys(rule);
        return ResponseEntity.ok(keys);
    }

    @PutMapping
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
    public ResponseEntity<ParameterDto> update(@NotNull @PathVariable Integer id, @Valid @RequestBody ParameterInputDto dto) {
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
    public ResponseEntity<ParameterDto> delete(@NotNull @PathVariable Integer id) {
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