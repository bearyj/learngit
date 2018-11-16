package com.jit.robert.domain;

import com.jit.robert.enums.RepairStatusEnum;

import java.util.Date;

public class Repair {
    private Integer id;
    private Integer technology_id;
    private Integer robert_id;
    private String description;
    private Integer status = RepairStatusEnum.REPAIR_WAITED.getCode();
    private Date time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTechnology_id() {
        return technology_id;
    }

    public void setTechnology_id(Integer technology_id) {
        this.technology_id = technology_id;
    }

    public Integer getRobert_id() {
        return robert_id;
    }

    public void setRobert_id(Integer robert_id) {
        this.robert_id = robert_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}




