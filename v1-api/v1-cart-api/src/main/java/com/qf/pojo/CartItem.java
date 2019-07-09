package com.qf.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/29
 */
public class CartItem implements Serializable,Comparable<CartItem> {

    private long serialVersionUID = 1L;

    private Long productId;

    private Integer count;

    private Date updateTime;

    @Override
    public String toString() {
        return "CartItem{" +
                "productId=" + productId +
                ", count=" + count +
                ", updateTime=" + updateTime +
                '}';
    }

    public CartItem() {
    }

    public CartItem(Long productId, Integer count, Date updateTime) {
        this.productId = productId;
        this.count = count;
        this.updateTime = updateTime;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public int compareTo(CartItem o) {
        return (int) (o.getUpdateTime().getTime()-this.getUpdateTime().getTime());
    }
}
