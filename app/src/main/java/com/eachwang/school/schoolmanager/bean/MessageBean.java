package com.eachwang.school.schoolmanager.bean;

/**
 * 消息实体类
 * Created by iswgr on 2017/11/8.
 */

public class MessageBean {
    /**
     * createTime : Nov 6, 2017 2:25:30 PM
     * name : admin
     * id : 6
     * title : 标题标题标题标题6
     * content : 内容内容内容内容内容内容6
     */

    private String createTime;
    private String name;
    private int id;
    private String title;
    private String content;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
}
