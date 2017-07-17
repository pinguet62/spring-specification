package fr.pinguet62.springruleengine.core.sample.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {

    private String name;

    private Double price;

    private String color;

}