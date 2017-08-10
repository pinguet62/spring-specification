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
public class RuleDto {

    private Integer id;

    private String key;

    private String description;

    private List<RuleDto> components;

    private List<ParameterDto> parameters;

}