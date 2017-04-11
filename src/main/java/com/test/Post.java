package com.test;

/**
 * Created by cikai on 2017/4/11.
 */
public class Post {
    private Integer tid;
    private Integer uid;
    private Integer nid;
    private String title;
    private String content;
    private Integer is_top;
    private Integer is_essence;
    private Double weight;
    private Integer create_time;
    private Integer update_time;
    private Integer status;

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getNid() {
        return nid;
    }

    public void setNid(Integer nid) {
        this.nid = nid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getIs_top() {
        return is_top;
    }

    public void setIs_top(Integer is_top) {
        this.is_top = is_top;
    }

    public Integer getIs_essence() {
        return is_essence;
    }

    public void setIs_essence(Integer is_essence) {
        this.is_essence = is_essence;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Integer getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Integer create_time) {
        this.create_time = create_time;
    }

    public Integer getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Integer update_time) {
        this.update_time = update_time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
