package com.jit.robert.dto;

import lombok.Data;

import java.util.Date;

@Data
public class RobertDTO {
    private Integer id;
    private Integer customer_id;
    private String number;
    private String type;
    private String username;
    private String realname;
    private Integer sex;
    private Integer age;
    private String tel;
    private String category;
    private String feed_name;
    private String medicine_name;
    private String address;
    private String province;
    private String city;
    private String county;
    private Date registertime;



}
