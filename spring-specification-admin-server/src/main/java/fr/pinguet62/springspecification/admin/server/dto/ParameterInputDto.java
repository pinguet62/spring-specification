package fr.pinguet62.springspecification.admin.server.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class ParameterInputDto {

    @NotBlank
    private String key;

    private String value;

}