package fr.pinguet62.springspecification.admin.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessRuleDto {

    @NotBlank
    private String id;

    @NotBlank
    private String argumentType;

    @NotNull
    @Valid
    private RuleComponentDto rootRuleComponent;

    private String title;

}