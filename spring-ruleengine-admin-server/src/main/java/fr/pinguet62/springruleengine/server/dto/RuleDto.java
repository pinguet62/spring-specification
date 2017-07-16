package fr.pinguet62.springruleengine.server.dto;

import java.util.List;

import fr.pinguet62.springruleengine.core.builder.database.model.RuleEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleDto {

    private Integer id;

    private String key;

    private List<RuleDto> components;

    private List<ParameterDto> parameters;

    /**
     * @see RuleEntity#getId()
     * @see RuleEntity#getComponents()
     */
    private Integer parent;

}