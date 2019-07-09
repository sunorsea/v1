package com.qf.v1itemweb;

import java.util.Date;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/18
 */
public class Student {
    private Integer id;
    private String name;
    private Date entryDate;

    public Student() {
    }

    public Student(Integer id, String name, Date entryDate) {
        this.id = id;
        this.name = name;
        this.entryDate = entryDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }
}
