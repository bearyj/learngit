package com.jit.robert.dto;

import com.jit.robert.enums.SexEnum;
import lombok.Data;

import java.util.Date;

@Data
public class TechnologyDTO {
    private Integer id;
    private String number; //工号
    private String position; //职位
    private Date enter_time; //入职时间
    private String username;
    private String tel;
    private String email;
    private String realname;
    private Integer sex = SexEnum.MALE.getCode();
    private Integer age;
    private String province;
    private String city;
    private String county;
    private String address;
    private Integer repairNum;
    private String image;
}
