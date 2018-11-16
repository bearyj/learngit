package com.jit.robert.enums;

public enum RepairStatusEnum implements CodeEnum{
    REPAIR_WAITED(0,"待维修"),

    REPAIRING(1,"维修中"),

    REPAIR_DONE(2,"已完成维修"),

    REPAIR_IS_FAIL(3,"损耗太大，无法维修")

    ;

    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    RepairStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
