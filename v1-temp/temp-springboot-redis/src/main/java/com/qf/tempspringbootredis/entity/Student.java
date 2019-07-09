package com.qf.tempspringbootredis.entity;

import java.io.Serializable;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/24
 */
public class Student implements Serializable {

    private Long id;
    private String name;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public Student() {
        System.out.println("====");
    }

    public Student(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
