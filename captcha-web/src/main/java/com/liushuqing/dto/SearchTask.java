/**
 * 
 */
package com.liushuqing.dto;

import java.util.Date;

/**
 * @author nancheng
 *
 */
public class SearchTask {
    private int id;
    private String name;
    private String keyword;
    private int type;
    private Date createTime;
    private Date finishTime;
    public int getId() {
        return id;
    }
    public SearchTask setId(int id) {
        this.id = id;
        return this;
    }
    public String getName() {
        return name;
    }
    public SearchTask setName(String name) {
        this.name = name;
        return this;
    }
    public String getKeyword() {
        return keyword;
    }
    public SearchTask setKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }
    public int getType() {
        return type;
    }
    public SearchTask setType(int type) {
        this.type = type;
        return this;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public SearchTask setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    public Date getFinishTime() {
        return finishTime;
    }
    public SearchTask setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
        return this;
    }
}
