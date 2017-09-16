package fr.pinguet62.springruleengine.sample.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class Product {

    @NotNull
    private String type;

    @NotNull
    private Double price;

    @NotNull
    private Boolean dangerous;

}