package fr.pinguet62.springspecification.admin.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RuleDto {

    @NotBlank
    private String key;

    @NotBlank
    private String name;

    private String description;

}
