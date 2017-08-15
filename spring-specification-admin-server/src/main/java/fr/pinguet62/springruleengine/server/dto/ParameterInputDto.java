package fr.pinguet62.springruleengine.server.dto;

import fr.pinguet62.springruleengine.core.builder.database.model.RuleComponentEntity;
import lombok.Data;

@Data
public class ParameterInputDto {

    private Integer id;

    private String key;

    private String value;

    private String type;

    /**
     * @see RuleComponentEntity#getId()
     */
    private Integer rule;

}