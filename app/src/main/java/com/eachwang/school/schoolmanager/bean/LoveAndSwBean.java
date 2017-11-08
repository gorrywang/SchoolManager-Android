package com.eachwang.school.schoolmanager.bean;

/**
 * 最新的失物招领与表白墙
 * Created by iswgr on 2017/11/8.
 */

public class LoveAndSwBean {

    /**
     * createTime : Nov 6, 2017 8:57:05 PM
     * id : 6
     * title : 小明我爱你6
     * studentNum : 201501002229
     * content : 第一次遇见你就对你心动了6
     * tel : 1599999999
     * addr : 11号宿舍楼409宿舍
     * status : 0
     */

    private String createTime;
    private int id;
    private String title;
    private String studentNum;
    private String content;
    private String tel;
    private String addr;
    private int status;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
