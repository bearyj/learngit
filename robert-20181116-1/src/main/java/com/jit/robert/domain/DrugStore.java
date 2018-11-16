package com.jit.robert.domain;

import lombok.Data;

import java.util.Date;

@Data
public class DrugStore {
    private Integer id;
    private String name;
    private Integer kind;
    private String subKind;
    private String type;
    private String price;
    private String manualInstruct;
    private String company;
    private String contact;
    private String telPhone;
    private String image;
    private Integer visitCount;
    private Date publishTime;
}
