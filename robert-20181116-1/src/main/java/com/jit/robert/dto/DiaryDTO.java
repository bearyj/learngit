package com.jit.robert.dto;

import lombok.Data;

import java.util.Date;

@Data
public class DiaryDTO {
    private Integer id;
    private String NH;
    private String Nano2;
    private String Alkali;
    private String O2;
    private String temperature;
    private Integer pound_id;
    private String image;
    private String username;
    private String remark;
    private Date date;
    private String medicine;
    private String weather;
    private String ph;
    private Integer count1;
    private Integer count2;
    private Integer count3;
    private Integer count4;
    private Integer count5;
    private Integer count6;
    private Integer count_total;
}
