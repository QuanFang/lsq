/**
 * 
 */
package com.liushuqing.meta.entity;

import java.util.Date;

/**
 * @author nancheng
 *
 */
public class SearchResult extends BaseEntity {

    private String name;
    private String url;
    private String downloadLink;
    private String content;
    private int id;
    private int type;
    private int searchTaskId;
    private Date createdTime;
    private Date finishTime;

    public String getName() {
        return name;
    }

    public SearchResult setName(String name) {
        this.name = name;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public SearchResult setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public SearchResult setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
        return this;
    }

    public String getContent() {
        return content;
    }

    public SearchResult setContent(String content) {
        this.content = content;
        return this;
    }

    public int getId() {
        return id;
    }

    public SearchResult setId(int id) {
        this.id = id;
        return this;
    }

    public int getType() {
        return type;
    }

    public SearchResult setType(int type) {
        this.type = type;
        return this;
    }

    public int getSearchTaskId() {
        return searchTaskId;
    }

    public SearchResult setSearchTaskId(int searchTaskId) {
        this.searchTaskId = searchTaskId;
        return this;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public SearchResult setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public SearchResult setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
        return this;
    }

    @Override
    public String toString() {
        return "name is: "+name+"url is: "+url;
    }
}
