package com.jit.robert.domain;

import lombok.Data;

@Data
public class Product {
    private Integer id;
    private String name;
    private Integer kind;
    private String subKind;
    private String description;
    private String image;
}
