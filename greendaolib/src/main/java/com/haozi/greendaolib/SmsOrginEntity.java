package com.haozi.greendaolib;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Android Studio.
 * ProjectName: EducationApp
 * Author: Haozi
 * Date: 2018/8/7
 * Time: 23:22
 */
@Entity
public class SmsOrginEntity {

    @Id(autoincrement = true)
    private Long id;
    private String content;
    private int type;
    private long time;
    private int person;
    private String address;
    @Generated(hash = 1439300970)
    public SmsOrginEntity(Long id, String content, int type, long time, int person,
            String address) {
        this.id = id;
        this.content = content;
        this.type = type;
        this.time = time;
        this.person = person;
        this.address = address;
    }
    @Generated(hash = 1836532971)
    public SmsOrginEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public long getTime() {
        return this.time;
    }
    public void setTime(long time) {
        this.time = time;
    }
    public int getPerson() {
        return this.person;
    }
    public void setPerson(int person) {
        this.person = person;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }


}
