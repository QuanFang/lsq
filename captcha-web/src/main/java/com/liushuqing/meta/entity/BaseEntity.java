package com.liushuqing.meta.entity;

public class BaseEntity {
    private int id;
    private int version;

    public int getId() {
        return id;
    }

    public BaseEntity setId(int id) {
        this.id = id;
        return this;
    }

    public int getVersion() {
        return version;
    }

    public BaseEntity setVersion(int version) {
        this.version = version;
        return this;
    }
}
