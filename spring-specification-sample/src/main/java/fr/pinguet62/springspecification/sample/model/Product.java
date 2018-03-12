package fr.pinguet62.springspecification.sample.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Product {

    @Id
    private Integer id;

    @NotNull
    private String type;

    @NotNull
    private Double price;

    @NotNull
    private Boolean dangerous;

}