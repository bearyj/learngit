package com.jit.robert.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Disease {
    private Integer id;
    private String subKind;
    private String diseaseName;
    private String cause;
    private String symptom;
    private String treatment;
    private String image;
    private Integer visitCount;
    private Date publishTime;

}
