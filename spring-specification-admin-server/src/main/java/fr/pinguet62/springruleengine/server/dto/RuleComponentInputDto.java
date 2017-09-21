package fr.pinguet62.springruleengine.server.dto;

import fr.pinguet62.springruleengine.core.builder.database.model.RuleComponentEntity;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class RuleComponentInputDto {

    /**
     * @see RuleComponentEntity#getId()
     * @see RuleComponentEntity#getComponents()
     */
    private Integer parent;

    // TODO different for PATCH @NotBlank
    private String key;

    private String description;

    private Integer index;

}