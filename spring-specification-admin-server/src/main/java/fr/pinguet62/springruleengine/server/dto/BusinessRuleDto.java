package fr.pinguet62.springruleengine.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessRuleDto {

    private String id;

    private String argumentType;

    private RuleComponentDto rootRuleComponent;

    private String title;

}