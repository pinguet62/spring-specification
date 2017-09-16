package fr.pinguet62.springruleengine.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BusinessRuleInputDto {

    @NotBlank
    private String id;

    @NotBlank
    private String argumentType;

    private String title;

}