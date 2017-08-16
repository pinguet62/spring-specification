package fr.pinguet62.springruleengine.sample.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {

    private String type;

    private Double price;

    private Boolean dangerous;

}