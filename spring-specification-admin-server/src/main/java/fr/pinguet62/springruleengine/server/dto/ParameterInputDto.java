package fr.pinguet62.springruleengine.server.dto;

import fr.pinguet62.springruleengine.core.builder.database.model.RuleEntity;
import lombok.Data;

@Data
public class ParameterInputDto {

    private Integer id;

    private String key;

    private String value;

    private String type;

    /**
     * @see RuleEntity#getId()
     */
    private Integer rule;

}