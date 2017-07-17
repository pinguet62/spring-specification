package fr.pinguet62.springruleengine.sample.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {

    private String name;

    private Double price;

    private String color;

}