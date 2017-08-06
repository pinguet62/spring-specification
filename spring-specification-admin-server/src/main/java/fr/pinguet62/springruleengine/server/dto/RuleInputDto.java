package fr.pinguet62.springruleengine.server.dto;

import fr.pinguet62.springruleengine.core.builder.database.model.RuleEntity;
import lombok.Data;

@Data
public class RuleInputDto {

    private Integer id;

    /**
     * @see RuleEntity#getId()
     * @see RuleEntity#getComponents()
     */
    private Integer parent;

    private String key;

    private String description;

    private Integer index;

}