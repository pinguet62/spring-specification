package fr.pinguet62.springspecification.admin.server.dto;

import fr.pinguet62.springspecification.core.builder.database.model.RuleComponentEntity;
import lombok.Data;

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