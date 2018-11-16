package com.jit.robert.domain;

import com.jit.robert.enums.SexEnum;

import javax.xml.crypto.Data;
import java.util.Date;

public class Expert {
    private Integer id;
    private String username;
    private Integer sex = SexEnum.MALE.getCode();
    private Integer age;
    private String tel;
    private String email;
    private String province;
    private String city;
    private String county;
    private String address;
    private String image;
    private String realname;
    private String product; //产品类别
    private String major; //负责类别
    private String number; //工号
    private String degree; //学位
    private String company; //所在单位
    private String remark; //备注
    private Date sign_time; //签约时间


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getSign_time() {
        return sign_time;
    }

    public void setSign_time(Date sign_time) {
        this.sign_time = sign_time;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public Expert() {
    }

    public Expert(String username, Integer sex, Integer age, String tel, String email, String address, String image, String realname, String product, String major, String number, String degree, String company, String remark, Date sign_time) {
        this.username = username;
        this.sex = sex;
        this.age = age;
        this.tel = tel;
        this.email = email;
        this.address = address;
        this.image = image;
        this.realname = realname;
        this.product = product;
        this.major = major;
        this.number = number;
        this.degree = degree;
        this.company = company;
        this.remark = remark;
        this.sign_time = sign_time;
    }
}
