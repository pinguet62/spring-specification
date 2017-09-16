package fr.pinguet62.springruleengine.server.dto;

import fr.pinguet62.springruleengine.core.builder.database.model.RuleComponentEntity;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Data
public class ParameterInputDto {

    @NotBlank
    private String key;

    private String value;

    // TODO URL parameter
    /**
     * @see RuleComponentEntity#getId()
     */
    // @NotNull
    private Integer ruleComponent;

}