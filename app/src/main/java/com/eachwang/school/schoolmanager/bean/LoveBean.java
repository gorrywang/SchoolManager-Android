package com.eachwang.school.schoolmanager.bean;

/**
 * 表白墙实体类
 * Created by iswgr on 2017/11/8.
 */

public class LoveBean {

    /**
     * id : 6
     * title : 小明我爱你6
     * content : 第一次遇见你就对你心动了6
     * createTime : 2017-11-06 20:57:05.0
     */

    private int id;
    private String title;
    private String content;
    private String createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
