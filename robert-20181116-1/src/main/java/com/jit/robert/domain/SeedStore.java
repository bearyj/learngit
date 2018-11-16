package com.jit.robert.domain;

import lombok.Data;

import java.util.Date;

@Data
public class SeedStore {
    private Integer id;
    private String title;
    private Integer kind;
    private String subKind;
    private String price;
    private String description;
    private String company;
    private String productPlace;
    private String contact;
    private String telPhone;
    private String image;
    private Integer visitCount;
    private Date publishTime;
}
