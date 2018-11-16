package com.jit.robert.domain;

public class Alarm {
    private Integer id;
    private Integer robert_id;
    private Float oxygen_max;
    private Float oxygen_min;
    private Float ph_max;
    private Float ph_min;
    private Float temperature_max;
    private Float temperature_min;
    private Integer power;
    private Integer feed;
    private Integer medicine;
    private Integer wash;
    private String robert_number;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRobert_id() {
        return robert_id;
    }

    public void setRobert_id(Integer robert_id) {
        this.robert_id = robert_id;
    }

    public Float getOxygen_max() {
        return oxygen_max;
    }

    public void setOxygen_max(Float oxygen_max) {
        this.oxygen_max = oxygen_max;
    }

    public Float getOxygen_min() {
        return oxygen_min;
    }

    public void setOxygen_min(Float oxygen_min) {
        this.oxygen_min = oxygen_min;
    }

    public Float getPh_max() {
        return ph_max;
    }

    public void setPh_max(Float ph_max) {
        this.ph_max = ph_max;
    }

    public Float getPh_min() {
        return ph_min;
    }

    public void setPh_min(Float ph_min) {
        this.ph_min = ph_min;
    }

    public Float getTemperature_max() {
        return temperature_max;
    }

    public void setTemperature_max(Float temperature_max) {
        this.temperature_max = temperature_max;
    }

    public Float getTemperature_min() {
        return temperature_min;
    }

    public void setTemperature_min(Float temperature_min) {
        this.temperature_min = temperature_min;
    }

    public Integer getPower() {
        return power;
    }

    public void setPower(Integer power) {
        this.power = power;
    }

    public Integer getFeed() {
        return feed;
    }

    public void setFeed(Integer feed) {
        this.feed = feed;
    }

    public Integer getMedicine() {
        return medicine;
    }

    public void setMedicine(Integer medicine) {
        this.medicine = medicine;
    }

    public Integer getWash() {
        return wash;
    }

    public void setWash(Integer wash) {
        this.wash = wash;
    }

    public String getRobert_number() {
        return robert_number;
    }

    public void setRobert_number(String robert_number) {
        this.robert_number = robert_number;
    }

}
