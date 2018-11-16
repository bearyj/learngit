package com.jit.robert.domain;

import java.util.Date;

public class WaterLog {
    private Integer id;
    private Float oxygen;
    private Float ph;
    private Float temperature;
    private Date time;
    private Integer pound_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getOxygen() {
        return oxygen;
    }

    public void setOxygen(Float oxygen) {
        this.oxygen = oxygen;
    }

    public Float getPh() {
        return ph;
    }

    public void setPh(Float ph) {
        this.ph = ph;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getPound_id() {
        return pound_id;
    }

    public void setPound_id(Integer pound_id) {
        this.pound_id = pound_id;
    }
}
