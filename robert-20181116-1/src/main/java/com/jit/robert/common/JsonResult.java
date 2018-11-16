package com.jit.robert.common;

public class JsonResult {
    private static final Integer OK = 0;
    private static final Integer ERROR = 100;
    private int loginStatus;//登录状态 0：未登录  1：登录
    private Integer code;//结果编码 0：相关错误信息  1：正确信息
    private String message;//结果信息
    private Object res;//返回的json数据

    public int getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(int loginStatus) {
        this.loginStatus = loginStatus;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getRes() {
        return res;
    }

    public void setRes(Object res) {
        this.res = res;
    }
}
