package fr.pinguet62.springruleengine.server;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.pinguet62.springruleengine.core.RuleDescription;
import fr.pinguet62.springruleengine.core.RuleName;
import fr.pinguet62.springruleengine.core.builder.database.model.RuleEntity;
import fr.pinguet62.springruleengine.core.builder.database.repository.RuleRepository;
import fr.pinguet62.springruleengine.core.api.Rule;
import fr.pinguet62.springruleengine.server.dto.ParameterDto;
import fr.pinguet62.springruleengine.server.dto.RuleDto;
import fr.pinguet62.springruleengine.server.dto.RuleInformationDto;
import fr.pinguet62.springruleengine.server.dto.RuleInputDto;

@Transactional
@RestController
@RequestMapping("/api")
public class RuleController {

    @Autowired
    private RuleRepository ruleRepository;

    @Autowired
    private RuleService ruleService;

    @GetMapping("/key")
    public List<RuleInformationDto> getAvailable() {
        return ruleService.getAllRules().stream().map(this::convert).collect(toList());
    }

    @GetMapping
    public List<RuleDto> getAllRoots() {
        return ruleRepository.findByParentIsNull().stream().map(this::convert).collect(toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RuleDto> getById(@PathVariable("id") Integer id) {
        RuleEntity entity = ruleRepository.findOne(id);
        if (entity == null)
            return ResponseEntity.status(NOT_FOUND).build();

        return ResponseEntity.ok(convert(entity));
    }

    @PutMapping
    public ResponseEntity<RuleDto> create(@RequestBody RuleInputDto dto) {
        RuleEntity entity = new RuleEntity();
        // entity.setId();
        entity.setKey(dto.getKey());
        entity.setDescription(dto.getDescription());
        entity = ruleRepository.save(entity);

        ruleRepository.findOne(dto.getParent()).getComponents().add(entity);

        return ResponseEntity.created(URI.create("/api/" + entity.getId())).body(convert(entity));
    }

    @PostMapping("/{id}")
    public ResponseEntity<RuleDto> update(@PathVariable("id") Integer id, @RequestBody RuleInputDto dto) {
        RuleEntity entity = ruleRepository.findOne(id);
        if (entity == null)
            return ResponseEntity.status(NOT_FOUND).build();

        if (dto.getKey() != null)
            entity.setKey(dto.getKey());
        if (dto.getDescription() != null)
            entity.setDescription(dto.getDescription());
        entity = ruleRepository.save(entity);

        return ResponseEntity.ok(convert(entity));
    }

    @PostMapping("/{id}/index/{index}")
    public ResponseEntity<RuleDto> changeIndex(@PathVariable("id") Integer id, @PathVariable("index") Integer tgtIndex) {
        RuleEntity entity = ruleRepository.findOne(id);
        if (entity == null)
            return ResponseEntity.status(NOT_FOUND).build();

        int srcIndex = entity.getIndex();
        int sens = (tgtIndex > srcIndex ? -1 : +1); // delta on index
        List<RuleEntity> otherRules = entity.getParent().getComponents();
        for (int i = srcIndex - sens; i != tgtIndex - sens; i -= sens) {
            RuleEntity moved = otherRules.get(i);
            moved.setIndex(moved.getIndex() + sens);
        }
        entity.setIndex(tgtIndex);

        return ResponseEntity.ok(convert(entity));
    }

    private RuleDto convert(RuleEntity entity) {
        // @formatter:off
        return RuleDto
                .builder()
                .id(entity.getId())
                .key(entity.getKey())
                .description(entity.getDescription())
                .components(
                        entity
                            .getComponents()
                            .stream()
                            .map(this::convert)
                            .collect(toList()))
                .parameters(
                        entity
                            .getParameters()
                            .stream()
                            .map(p ->
                                    ParameterDto
                                        .builder()
                                        .id(p.getId())
                                        .key(p.getKey())
                                        .value(p.getValue())
                                        .type(p.getType())
                                        .build())
                            .collect(toList()))
                .build();
        // @formatter:on
    }

    private RuleInformationDto convert(Class<Rule> ruleType) {
        // @formatter:off
        return RuleInformationDto
                .builder()
                .key(ruleService.getKey(ruleType))
                .name(ruleType.isAnnotationPresent(RuleName.class) ? ruleType.getDeclaredAnnotation(RuleName.class).value() : null)
                .description(ruleType.isAnnotationPresent(RuleDescription.class) ? ruleType.getDeclaredAnnotation(RuleDescription.class).value() : null)
                .build();
        // @formatter:on
    }

}