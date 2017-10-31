package fr.pinguet62.springspecification.admin.server.dto;

import fr.pinguet62.springspecification.core.builder.database.model.RuleComponentEntity;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

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