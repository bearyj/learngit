package com.jit.robert.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TaskDTO {
    private Integer id;
    private Integer robert_id;
    private String number; //机器人编号
    private String type;
    private String description;
    private Integer status;
    private Date time;
    private Integer customer_id;
    private String realname;
    private String username;
    private Integer sex;
    private Integer age;
    private String tel;
    private String technology_name;
    private String technology_username;
    private Integer technology_id;


}
