package fr.pinguet62.springruleengine.server.dto;

import fr.pinguet62.springruleengine.core.builder.database.model.RuleComponentEntity;
import lombok.Data;

@Data
public class RuleComponentInputDto {

    private Integer id;

    /**
     * @see RuleComponentEntity#getId()
     * @see RuleComponentEntity#getComponents()
     */
    private Integer parent;

    private String key;

    private String description;

    private Integer index;

}