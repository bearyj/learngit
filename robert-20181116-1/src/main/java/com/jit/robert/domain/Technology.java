package com.jit.robert.domain;

import com.jit.robert.enums.SexEnum;

import java.util.Date;

public class Technology {
    private Integer id;
    private String number; //工号
    private String position; //职位
    private Date enter_time; //入职时间
    private String username;
    private String tel;
    private String email;
    private String realname;
    private Integer sex = SexEnum.MALE.getCode();
    private Integer age;
    private String province;
    private String city;
    private String county;
    private String address;
    private String image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Date getEnter_time() {
        return enter_time;
    }

    public void setEnter_time(Date enter_time) {
        this.enter_time = enter_time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Technology() {
    }

    public Technology(String number, String position, Date enter_time, String username, String tel, String email, String realname, Integer sex, Integer age, String address, String image) {
        this.number = number;
        this.position = position;
        this.enter_time = enter_time;
        this.username = username;
        this.tel = tel;
        this.email = email;
        this.realname = realname;
        this.sex = sex;
        this.age = age;
        this.address = address;
        this.image = image;
    }
}
