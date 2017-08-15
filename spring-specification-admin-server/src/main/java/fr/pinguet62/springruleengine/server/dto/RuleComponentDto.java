package fr.pinguet62.springruleengine.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleComponentDto {

    private Integer id;

    private String key;

    private String description;

    private List<RuleComponentDto> components;

    private List<ParameterDto> parameters;

}