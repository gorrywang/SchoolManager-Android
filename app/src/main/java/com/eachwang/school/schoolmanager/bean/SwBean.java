package com.eachwang.school.schoolmanager.bean;

/**
 * 失物招领实体类
 * Created by iswgr on 2017/11/8.
 */

public class SwBean {

    /**
     * createTime : Nov 6, 2017 10:07:15 PM
     * tel : 1599999999
     * id : 6
     * title : 失物招领6
     * addr : 11号宿舍楼409宿舍
     * studentNum : 201501002229
     * content : 我见到一个钱包,里面有6块钱
     * status : 0
     */

    private String createTime;
    private String tel;
    private int id;
    private String title;
    private String addr;
    private String studentNum;
    private String content;
    private int status;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(String studentNum) {
        this.studentNum = studentNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
