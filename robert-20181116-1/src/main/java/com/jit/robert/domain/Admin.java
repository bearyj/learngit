package com.jit.robert.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Admin {
    private Integer id;
    private String username;
    private String realname;
    private String number;
    private String department;
    private String user_group; //所属用户组
    private Integer status;//权限管理,启用或禁用
    private String remark;
    private String image;
    private Date registertime;

}
