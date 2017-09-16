package fr.pinguet62.springruleengine.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParameterDto {

    @NotNull
    private Integer id;

    @NotBlank
    private String key;

    private String value;

}