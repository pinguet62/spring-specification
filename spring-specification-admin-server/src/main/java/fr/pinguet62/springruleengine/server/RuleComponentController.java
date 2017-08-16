package fr.pinguet62.springruleengine.server;

import fr.pinguet62.springruleengine.core.builder.database.model.RuleComponentEntity;
import fr.pinguet62.springruleengine.core.builder.database.repository.RuleComponentRepository;
import fr.pinguet62.springruleengine.server.dto.ParameterDto;
import fr.pinguet62.springruleengine.server.dto.RuleComponentDto;
import fr.pinguet62.springruleengine.server.dto.RuleComponentInputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static fr.pinguet62.springruleengine.server.RuleComponentController.PATH;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Transactional
@RestController
@RequestMapping(PATH)
public class RuleComponentController {

    public static final String PATH = "/ruleComponent";

    @Autowired
    private RuleComponentRepository ruleComponentRepository;

    @GetMapping("/{id}")
    public ResponseEntity<RuleComponentDto> getById(@PathVariable("id") Integer id) {
        RuleComponentEntity entity = ruleComponentRepository.findOne(id);
        if (entity == null)
            return ResponseEntity.status(NOT_FOUND).build();

        return ResponseEntity.ok(convert(entity));
    }

    @PutMapping
    public ResponseEntity<RuleComponentDto> create(@RequestBody RuleComponentInputDto dto) {
        RuleComponentEntity entity = new RuleComponentEntity();
        // entity.setId();
        entity.setParent(ruleComponentRepository.findOne(dto.getParent()));
        entity.setIndex(entity.getParent().getComponents().size()); // default: at the end
        entity.setKey(dto.getKey());
        entity.setDescription(dto.getDescription());
        entity = ruleComponentRepository.save(entity);

        ruleComponentRepository.findOne(dto.getParent()).getComponents().add(entity);

        return ResponseEntity.created(URI.create(PATH + "/" + entity.getId())).body(convert(entity));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RuleComponentDto> update(@PathVariable("id") Integer id, @RequestBody RuleComponentInputDto dto) {
        RuleComponentEntity entity = ruleComponentRepository.findOne(id);
        if (entity == null)
            return ResponseEntity.status(NOT_FOUND).build();

        if (dto.getKey() != null)
            entity.setKey(dto.getKey());
        if (dto.getDescription() != null)
            entity.setDescription(dto.getDescription());

        if (dto.getIndex() != null) {
            // reset next rule index of initial parent
            List<RuleComponentEntity> srcChildren = entity.getParent().getComponents();
            for (int i = entity.getIndex(); i != srcChildren.size(); i++) {
                RuleComponentEntity moved = srcChildren.get(i);
                moved.setIndex(moved.getIndex() - 1);
            }
            // offset next rule of target parent
            RuleComponentEntity tgtParent = dto.getParent() == null ? entity.getParent() : ruleComponentRepository.findOne(dto.getParent());
            int tgtIndex = dto.getIndex();
            for (int i = tgtIndex; i < tgtParent.getComponents().size(); i++) {
                RuleComponentEntity moved = tgtParent.getComponents().get(i);
                moved.setIndex(moved.getIndex() + 1);
            }
            entity.setIndex(tgtIndex);
        }

        if (dto.getParent() != null)
            entity.setParent(ruleComponentRepository.findOne(dto.getParent()));

        entity = ruleComponentRepository.save(entity);

        return ResponseEntity.ok(convert(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RuleComponentDto> delete(@PathVariable("id") Integer id) {
        RuleComponentEntity entity = ruleComponentRepository.findOne(id);
        if (entity == null)
            return ResponseEntity.status(NOT_FOUND).build();

        RuleComponentDto dto = convert(entity);
        ruleComponentRepository.delete(entity);
        return ResponseEntity.ok(dto);
    }

    public static RuleComponentDto convert(RuleComponentEntity entity) {
        // @formatter:off
        return RuleComponentDto
                .builder()
                .id(entity.getId())
                .key(entity.getKey())
                .description(entity.getDescription())
                .components(
                        entity
                                .getComponents()
                                .stream()
                                .map(RuleComponentController::convert)
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