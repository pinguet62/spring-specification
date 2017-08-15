package fr.pinguet62.springruleengine.server;

import fr.pinguet62.springruleengine.core.builder.database.ParameterConverter;
import fr.pinguet62.springruleengine.core.builder.database.ParameterConverter.Converter;
import fr.pinguet62.springruleengine.core.builder.database.model.ParameterEntity;
import fr.pinguet62.springruleengine.core.builder.database.model.RuleComponentEntity;
import fr.pinguet62.springruleengine.core.builder.database.repository.ParameterRepository;
import fr.pinguet62.springruleengine.core.builder.database.repository.RuleComponentRepository;
import fr.pinguet62.springruleengine.server.dto.ParameterDto;
import fr.pinguet62.springruleengine.server.dto.ParameterInputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Transactional
@RestController
@RequestMapping("/parameter")
public class ParameterController {

    @Autowired
    private ParameterConverter parameterConverter;

    @Autowired
    private ParameterRepository parameterRepository;

    @Autowired
    private RuleComponentRepository ruleRepository;

    @GetMapping("/type")
    public ResponseEntity<List<String>> getSupportedTypes() {
        List<String> supportedTypes = parameterConverter.getConverters().stream().map(Converter::getTargetType)
                .map(Class::getName).collect(toList());
        if (supportedTypes.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(supportedTypes);
        return ResponseEntity.ok(supportedTypes);
    }

    @GetMapping(params = "ruleComponent")
    public ResponseEntity<List<ParameterDto>> getByRuleComponent(@RequestParam("ruleComponent") Integer ruleComponentId) {
        RuleComponentEntity rule = ruleRepository.findOne(ruleComponentId);
        if (rule == null)
            return ResponseEntity.notFound().build();

        List<ParameterDto> dtos = rule.getParameters().stream().map(this::convert).collect(toList());
        return ResponseEntity.ok(dtos);
    }

    @PutMapping
    public ResponseEntity<ParameterDto> create(@RequestBody ParameterInputDto dto) {
        ParameterEntity entity = new ParameterEntity();
        // entity.setId();
        entity.setKey(dto.getKey());
        entity.setValue(dto.getValue());
        entity.setType(dto.getType());
        entity.setRule(ruleRepository.findOne(dto.getRule()));
        entity = parameterRepository.save(entity);

        return ResponseEntity.created(URI.create("/parameter/" + entity.getId())).body(convert(entity));
    }

    @PostMapping("/{id}")
    public ResponseEntity<ParameterDto> update(@PathVariable("id") Integer id, @RequestBody ParameterInputDto dto) {
        ParameterEntity entity = parameterRepository.findOne(id);
        if (entity == null)
            return ResponseEntity.status(NOT_FOUND).build();

        if (dto.getKey() != null)
            entity.setKey(dto.getKey());
        if (dto.getValue() != null)
            entity.setValue(dto.getValue());
        if (dto.getType() != null)
            entity.setType(dto.getType());

        entity = parameterRepository.save(entity);

        return ResponseEntity.ok().body(convert(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ParameterDto> delete(@PathVariable Integer id) {
        ParameterEntity entity = parameterRepository.findOne(id);
        if (entity == null)
            return ResponseEntity.notFound().build();

        ParameterDto dto = convert(entity);

        parameterRepository.delete(id);

        return ResponseEntity.ok(dto);
    }

    private ParameterDto convert(ParameterEntity entity) {
        // @formatter:off
        return ParameterDto
                .builder()
                .id(entity.getId())
                .key(entity.getKey())
                .value(entity.getValue())
                .type(entity.getType())
                .build();
        // @formatter:on
    }

}