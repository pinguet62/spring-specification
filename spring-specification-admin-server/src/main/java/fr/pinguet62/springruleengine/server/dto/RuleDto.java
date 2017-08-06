package fr.pinguet62.springruleengine.server.dto;

import java.util.List;

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

    private String name;

    private String description;

    private List<RuleDto> components;

    private List<ParameterDto> parameters;

}