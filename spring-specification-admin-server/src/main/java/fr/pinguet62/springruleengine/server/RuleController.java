package fr.pinguet62.springruleengine.server;

import static java.util.stream.Collectors.toList;

import java.net.URI;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.pinguet62.springruleengine.core.RuleDescription;
import fr.pinguet62.springruleengine.core.RuleName;
import fr.pinguet62.springruleengine.core.builder.database.model.RuleEntity;
import fr.pinguet62.springruleengine.core.builder.database.repository.RuleRepository;
import fr.pinguet62.springruleengine.core.rule.Rule;
import fr.pinguet62.springruleengine.server.dto.ParameterDto;
import fr.pinguet62.springruleengine.server.dto.RuleDto;
import fr.pinguet62.springruleengine.server.dto.RuleInformationDto;

@Transactional
@RestController
@RequestMapping("/rule")
public class RuleController {

    @Autowired
    private RuleRepository ruleRepository;

    @GetMapping("/key")
    public List<RuleInformationDto> getAvailable() {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AssignableTypeFilter(Rule.class));
        String packag = "fr/pinguet62/springruleengine"; // TODO
        Set<BeanDefinition> components = provider.findCandidateComponents(packag);
        return components.stream().map(component -> (Class<Rule>) ClassUtils.resolveClassName(component.getBeanClassName(),
                provider.getResourceLoader().getClassLoader())).map(this::convert).collect(toList());
    }

    @GetMapping
    public RuleDto getAny() {
        RuleEntity entity = ruleRepository.findAll().get(0);
        RuleDto dto = convert(entity);
        return dto;
    }

    @PutMapping
    public ResponseEntity<RuleDto> create(@RequestBody RuleDto dto) {
        RuleEntity entity = new RuleEntity();
        // entity.setId();
        entity.setKey(dto.getKey());
        entity = ruleRepository.save(entity);

        ruleRepository.findOne(dto.getParent()).getComponents().add(entity);

        return ResponseEntity.created(URI.create("/rule/" + entity.getId())).body(convert(entity));
    }

    private RuleDto convert(RuleEntity entity) {
        // @formatter:off
        return RuleDto
                .builder()
                .id(entity.getId())
                .key(entity.getKey())
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
                .key(ruleType.getSimpleName())
                .name(ruleType.isAnnotationPresent(RuleName.class) ? ruleType.getDeclaredAnnotation(RuleName.class).value() : null)
                .description(ruleType.isAnnotationPresent(RuleDescription.class) ? ruleType.getDeclaredAnnotation(RuleDescription.class).value() : null)
                .build();
        // @formatter:on
    }

}