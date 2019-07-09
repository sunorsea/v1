package com.qf.tempspringbootredis.entity;

import java.io.Serializable;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/24
 */
public class ProductType implements Serializable {
    private Integer id;
    private String name;

    public ProductType() {
    }

    public ProductType(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public ProductType(Student country) {

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
}
