package com.jit.robert.domain;

import java.util.Date;

public class Downlog {
    private Integer id;
    private String downlogname;
    private Date downlogtime;
    private String username;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDownlogname() {
        return downlogname;
    }

    public void setDownlogname(String downlogname) {
        this.downlogname = downlogname;
    }

    public Date getDownlogtime() {
        return downlogtime;
    }

    public void setDownlogtime(Date downlogtime) {
        this.downlogtime = downlogtime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
