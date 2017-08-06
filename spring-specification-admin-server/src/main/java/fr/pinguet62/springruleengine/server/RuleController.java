package fr.pinguet62.springruleengine.server;

import fr.pinguet62.springruleengine.core.RuleName;
import fr.pinguet62.springruleengine.core.builder.database.model.RuleEntity;
import fr.pinguet62.springruleengine.core.builder.database.repository.RuleRepository;
import fr.pinguet62.springruleengine.server.dto.ParameterDto;
import fr.pinguet62.springruleengine.server.dto.RuleDto;
import fr.pinguet62.springruleengine.server.dto.RuleInputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Transactional
@RestController
@RequestMapping("/rule")
public class RuleController {

    @Autowired
    private RuleRepository ruleRepository;

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
        entity.setParent(ruleRepository.findOne(dto.getParent()));
        entity.setKey(dto.getKey());
        entity.setDescription(dto.getDescription());
        entity = ruleRepository.save(entity);

        ruleRepository.findOne(dto.getParent()).getComponents().add(entity);

        return ResponseEntity.created(URI.create("/rule/" + entity.getId())).body(convert(entity));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RuleDto> update(@PathVariable("id") Integer id, @RequestBody RuleInputDto dto) {
        RuleEntity entity = ruleRepository.findOne(id);
        if (entity == null)
            return ResponseEntity.status(NOT_FOUND).build();

        if (dto.getKey() != null)
            entity.setKey(dto.getKey());
        if (dto.getDescription() != null)
            entity.setDescription(dto.getDescription());

        if (dto.getIndex() != null) {
            // reset next rule index of initial parent
            List<RuleEntity> srcChildren = entity.getParent().getComponents();
            for (int i = entity.getIndex(); i != srcChildren.size(); i++) {
                RuleEntity moved = srcChildren.get(i);
                moved.setIndex(moved.getIndex() - 1);
            }
            // offset next rule of target parent
            RuleEntity tgtParent = dto.getParent() == null ? entity.getParent() : ruleRepository.findOne(dto.getParent());
            int tgtIndex = dto.getIndex();
            for (int i = tgtIndex; i < tgtParent.getComponents().size(); i++) {
                RuleEntity moved = tgtParent.getComponents().get(i);
                moved.setIndex(moved.getIndex() + 1);
            }
            entity.setIndex(tgtIndex);
        }

        if (dto.getParent() != null)
            entity.setParent(ruleRepository.findOne(dto.getParent()));

        entity = ruleRepository.save(entity);

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

}