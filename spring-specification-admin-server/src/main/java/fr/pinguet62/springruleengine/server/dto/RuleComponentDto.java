package fr.pinguet62.springruleengine.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleComponentDto {

    @NotNull
    private Integer id;

    @NotBlank
    private String key;

    private String description;

    @NotNull
    private List<@NotNull @Valid RuleComponentDto> components;

    @NotNull
    private List<@NotNull @Valid ParameterDto> parameters;

}