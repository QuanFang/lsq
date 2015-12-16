package com.liushuqing.meta.entity;

import java.util.Date;

public class User extends BaseEntity {
    private Date createTime;
    private Date changeTime;
    private String name;
    private String passWord;
    private int leval;
    private String description;

    public Date getChangeTime() {
        return changeTime;
    }

    public User setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public User setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public String getPassWord() {
        return passWord;
    }

    public User setPassWord(String passWord) {
        this.passWord = passWord;
        return this;
    }

    public int getLeval() {
        return leval;
    }

    public User setLeval(int leval) {
        this.leval = leval;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public User setDescription(String description) {
        this.description = description;
        return this;
    }

}
