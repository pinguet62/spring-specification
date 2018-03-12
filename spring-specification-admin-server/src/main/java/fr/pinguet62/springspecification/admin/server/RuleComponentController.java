package fr.pinguet62.springspecification.admin.server;

import fr.pinguet62.springspecification.admin.server.dto.ParameterDto;
import fr.pinguet62.springspecification.admin.server.dto.RuleComponentDto;
import fr.pinguet62.springspecification.admin.server.dto.RuleComponentInputDto;
import fr.pinguet62.springspecification.core.builder.database.model.RuleComponentEntity;
import fr.pinguet62.springspecification.core.builder.database.repository.RuleComponentRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static fr.pinguet62.springspecification.admin.server.RuleComponentController.PATH;
import static fr.pinguet62.springspecification.core.builder.database.autoconfigure.SpringSpecificationBeans.TRANSACTION_MANAGER_NAME;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Transactional(TRANSACTION_MANAGER_NAME)
@RestController
@RequestMapping(PATH)
@Api(tags = "RuleComponent", description = "Services related to `RuleComponent`s, structured as a *tree*")
public class RuleComponentController {

    public static final String PATH = "/ruleComponent";

    @Autowired
    private RuleComponentRepository ruleComponentRepository;

    @GetMapping("/{id}")
    @ApiOperation(value = "Find a `RuleComponent`s by `id`")
    public ResponseEntity<RuleComponentDto> getById(@NotNull @PathVariable @ApiParam(value = "Its `id`", required = true) Integer id) {
        Optional<RuleComponentEntity> entity = ruleComponentRepository.findById(id);
        if (!entity.isPresent())
            return ResponseEntity.status(NOT_FOUND).build();

        return ResponseEntity.ok(convert(entity.get()));
    }

    @PutMapping
    @ApiOperation(value = "Create a new `RuleComponent`")
    public ResponseEntity<RuleComponentDto> create(@Valid @RequestBody RuleComponentInputDto dto) {
        RuleComponentEntity entity = new RuleComponentEntity();
        // entity.setId();
        entity.setParent(ruleComponentRepository.findById(dto.getParent()).get());
        entity.setIndex(entity.getParent().getComponents().size()); // default: at the end
        entity.setKey(dto.getKey());
        entity.setDescription(dto.getDescription());
        entity = ruleComponentRepository.save(entity);

        ruleComponentRepository.findById(dto.getParent()).get().getComponents().add(entity);

        return ResponseEntity.created(URI.create(PATH + "/" + entity.getId())).body(convert(entity));
    }

    @PatchMapping("/{id}")
    @ApiOperation(value = "Update an existing `RuleComponent`")
    public ResponseEntity<RuleComponentDto> update(@NotNull @PathVariable @ApiParam(value = "Its `id`", required = true) Integer id, @Valid @RequestBody RuleComponentInputDto dto) {
        Optional<RuleComponentEntity> entityOp = ruleComponentRepository.findById(id);
        if (!entityOp.isPresent())
            return ResponseEntity.status(NOT_FOUND).build();
        RuleComponentEntity entity = entityOp.get();

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
            RuleComponentEntity tgtParent = dto.getParent() == null ? entity.getParent() : ruleComponentRepository.findById(dto.getParent()).get();
            int tgtIndex = dto.getIndex();
            for (int i = tgtIndex; i < tgtParent.getComponents().size(); i++) {
                RuleComponentEntity moved = tgtParent.getComponents().get(i);
                moved.setIndex(moved.getIndex() + 1);
            }
            entity.setIndex(tgtIndex);
        }

        if (dto.getParent() != null)
            entity.setParent(ruleComponentRepository.findById(dto.getParent()).get());

        entity = ruleComponentRepository.save(entity);

        return ResponseEntity.ok(convert(entity));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete an existing `RuleComponent`")
    public ResponseEntity<RuleComponentDto> delete(@NotNull @PathVariable @ApiParam(value = "Its `id`", required = true) Integer id) {
        Optional<RuleComponentEntity> entityOp = ruleComponentRepository.findById(id);
        if (!entityOp.isPresent())
            return ResponseEntity.status(NOT_FOUND).build();
        RuleComponentEntity entity = entityOp.get();

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
                                                .build())
                                .collect(toList()))
                .build();
        // @formatter:on
    }

}