package fr.pinguet62.springruleengine.server;

import fr.pinguet62.springruleengine.core.api.Rule;
import fr.pinguet62.springruleengine.server.dto.RuleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/rule")
public class RuleController {

    @Autowired
    private RuleService ruleService;

    @GetMapping
    public List<RuleDto> getAvailable() {
        return ruleService.getAllRules().stream().map(this::convert).collect(toList());
    }

    private RuleDto convert(Class<Rule<?>> ruleType) {
        // @formatter:off
        return RuleDto
                .builder()
                .key(ruleService.getKey(ruleType))
                .name(ruleService.getName(ruleType))
                .description(ruleService.getDescription(ruleType))
                .build();
        // @formatter:on
    }

}