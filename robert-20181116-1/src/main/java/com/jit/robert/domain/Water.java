package com.jit.robert.domain;

import java.util.Date;

public class Water {
    private Integer id;
    private String NH;
    private String Nano2;
    private String Alkali;
    private String O2;
    private String temperature;
    private Integer pound_id;
    private String image;
    private String username;
    private String remark;
    private Date date;
    private String medicine;
    private String weather;
    private String ph;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNH() {
        return NH;
    }

    public void setNH(String NH) {
        this.NH = NH;
    }

    public String getNano2() {
        return Nano2;
    }

    public void setNano2(String nano2) {
        Nano2 = nano2;
    }

    public String getAlkali() {
        return Alkali;
    }

    public void setAlkali(String alkali) {
        Alkali = alkali;
    }

    public String getO2() {
        return O2;
    }

    public void setO2(String o2) {
        O2 = o2;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public Integer getPound_id() {
        return pound_id;
    }

    public void setPound_id(Integer pound_id) {
        this.pound_id = pound_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getPh() {
        return ph;
    }

    public void setPh(String ph) {
        this.ph = ph;
    }

    public Water() {
    }

    public Water(Integer id, String NH, String nano2, String alkali, String o2, String temperature, Integer pound_id, String image, String username, String remark, Date date, String medicine, String weather, String ph) {
        this.id = id;
        this.NH = NH;
        Nano2 = nano2;
        Alkali = alkali;
        O2 = o2;
        this.temperature = temperature;
        this.pound_id = pound_id;
        this.image = image;
        this.username = username;
        this.remark = remark;
        this.date = date;
        this.medicine = medicine;
        this.weather = weather;
        this.ph = ph;
    }

    @Override
    public String toString() {
        return "Water{" +
                "id=" + id +
                ", NH='" + NH + '\'' +
                ", Nano2='" + Nano2 + '\'' +
                ", Alkali='" + Alkali + '\'' +
                ", O2='" + O2 + '\'' +
                ", temperature='" + temperature + '\'' +
                ", pound_id=" + pound_id +
                ", image='" + image + '\'' +
                ", username='" + username + '\'' +
                ", remark='" + remark + '\'' +
                ", date=" + date +
                ", medicine='" + medicine + '\'' +
                ", weather='" + weather + '\'' +
                ", ph='" + ph + '\'' +
                '}';
    }
}
