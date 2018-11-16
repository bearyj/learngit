package com.jit.robert.enums;

public enum UserTypeEnum {

    CUSTOMER(0,"普通用户"),

    TECHNOLOGY(1,"技术人员"),

    EXPERT(2,"专家")
    ;

    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    UserTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
