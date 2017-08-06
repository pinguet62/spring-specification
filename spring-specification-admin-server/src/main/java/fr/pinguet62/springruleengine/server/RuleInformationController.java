package fr.pinguet62.springruleengine.server;

import fr.pinguet62.springruleengine.core.api.Rule;
import fr.pinguet62.springruleengine.server.dto.RuleInformationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/ruleCatalog")
public class RuleInformationController {

    @Autowired
    private RuleService ruleService;

    @GetMapping
    public List<RuleInformationDto> getAvailable() {
        return ruleService.getAllRules().stream().map(this::convert).collect(toList());
    }

    private RuleInformationDto convert(Class<Rule> ruleType) {
        // @formatter:off
        return RuleInformationDto
                .builder()
                .key(ruleService.getKey(ruleType))
                .name(ruleService.getName(ruleType))
                .description(ruleService.getDescription(ruleType))
                .build();
        // @formatter:on
    }

}