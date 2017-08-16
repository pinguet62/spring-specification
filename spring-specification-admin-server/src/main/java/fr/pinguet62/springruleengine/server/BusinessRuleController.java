package fr.pinguet62.springruleengine.server;

import fr.pinguet62.springruleengine.core.builder.database.model.BusinessRuleEntity;
import fr.pinguet62.springruleengine.core.builder.database.repository.BusinessRuleRepository;
import fr.pinguet62.springruleengine.server.dto.BusinessRuleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static fr.pinguet62.springruleengine.server.BusinessRuleController.PATH;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping(PATH)
public class BusinessRuleController {

    public static final String PATH = "/businessRule";

    @Autowired
    private BusinessRuleRepository businessRuleRepository;

    @GetMapping
    public List<BusinessRuleDto> getAll() {
        return businessRuleRepository.findAll().stream().map(this::convert).collect(toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BusinessRuleDto> getById(@PathVariable("id") String id) {
        BusinessRuleEntity entity = businessRuleRepository.findOne(id);
        if (entity == null)
            return ResponseEntity.status(NOT_FOUND).build();

        return ResponseEntity.ok(convert(entity));
    }

    private BusinessRuleDto convert(BusinessRuleEntity entity) {
        // @formatter:off
        return BusinessRuleDto
                .builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .rootRuleComponent(RuleComponentController.convert(entity.getRootRuleComponent()))
                .build();
        // @formatter:on
    }

}