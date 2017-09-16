package fr.pinguet62.springruleengine.sample;

import fr.pinguet62.springruleengine.core.api.Rule;
import fr.pinguet62.springruleengine.core.builder.RuleBuilder;
import fr.pinguet62.springruleengine.sample.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@Controller
public class TestController {

    @Autowired
    private RuleBuilder builder;

    /**
     * Server end-point who execute test.
     */
    @PostMapping("/sample/{rule}")
    public @Valid ResponseEntity<Boolean> server(@NotNull @PathVariable("rule") String businessRuleKey, @Valid @RequestBody Product product) {
        try {
            Rule<Product> rule = (Rule<Product>) builder.apply(businessRuleKey);
            boolean result = rule.test(product);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            log.warn("test error", e);
            return new ResponseEntity(e.toString(), INTERNAL_SERVER_ERROR);
        }
    }

}
