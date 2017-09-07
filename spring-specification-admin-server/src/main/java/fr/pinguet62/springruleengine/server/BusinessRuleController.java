package fr.pinguet62.springruleengine.server;

import fr.pinguet62.springruleengine.core.api.FailingRule;
import fr.pinguet62.springruleengine.core.api.Rule;
import fr.pinguet62.springruleengine.core.builder.database.model.BusinessRuleEntity;
import fr.pinguet62.springruleengine.core.builder.database.model.RuleComponentEntity;
import fr.pinguet62.springruleengine.core.builder.database.repository.BusinessRuleRepository;
import fr.pinguet62.springruleengine.core.builder.database.repository.RuleComponentRepository;
import fr.pinguet62.springruleengine.server.dto.BusinessRuleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;

import static fr.pinguet62.springruleengine.server.BusinessRuleController.PATH;
import static java.nio.charset.Charset.defaultCharset;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.web.util.UriUtils.encode;

@RestController
@RequestMapping(PATH)
public class BusinessRuleController {

    public static final String PATH = "/businessRule";

    @Autowired
    private BusinessRuleRepository businessRuleRepository;

    @Autowired
    private RuleComponentRepository ruleComponentRepository;

    @Autowired
    private RuleService ruleService;

    @GetMapping
    public List<BusinessRuleDto> getAll() {
        return businessRuleRepository.findAll().stream().map(this::convert).collect(toList());
    }

    @GetMapping("/{id:.+}")
    public ResponseEntity<BusinessRuleDto> getById(@PathVariable String id) {
        BusinessRuleEntity entity = businessRuleRepository.findOne(id);
        if (entity == null)
            return ResponseEntity.status(NOT_FOUND).build();

        return ResponseEntity.ok(convert(entity));
    }

    @PutMapping
    public ResponseEntity<BusinessRuleDto> create(@RequestBody BusinessRuleDto dto) throws UnsupportedEncodingException {
        RuleComponentEntity rootRuleComponent = new RuleComponentEntity();
        rootRuleComponent.setParent(null /*root*/);
        rootRuleComponent.setIndex(0);
        rootRuleComponent.setKey(ruleService.getKey((Class<Rule<?>>) (Class<?>) FailingRule.class)); // TODO fix generic
        rootRuleComponent.setDescription(null);
        rootRuleComponent = ruleComponentRepository.save(rootRuleComponent);

        BusinessRuleEntity entity = new BusinessRuleEntity();
        entity.setId(dto.getId());
        entity.setArgumentType(dto.getArgumentType());
        entity.setRootRuleComponent(rootRuleComponent);
        entity.setTitle(dto.getTitle());
        entity = businessRuleRepository.save(entity);

        return ResponseEntity
                .created(URI.create(encode(PATH + "/" + entity.getId(), defaultCharset().name())))
                .body(convert(entity));
    }

    @DeleteMapping("/{id:.+}")
    public ResponseEntity<BusinessRuleDto> delete(@PathVariable String id) {
        BusinessRuleEntity entity = businessRuleRepository.findOne(id);
        if (entity == null)
            return ResponseEntity.notFound().build();

        BusinessRuleDto dto = convert(entity);

        businessRuleRepository.delete(id);

        return ResponseEntity.ok(dto);
    }

    private BusinessRuleDto convert(BusinessRuleEntity entity) {
        // @formatter:off
        return BusinessRuleDto
                .builder()
                .id(entity.getId())
                .argumentType(entity.getArgumentType())
                .rootRuleComponent(RuleComponentController.convert(entity.getRootRuleComponent()))
                .title(entity.getTitle())
                .build();
        // @formatter:on
    }

}