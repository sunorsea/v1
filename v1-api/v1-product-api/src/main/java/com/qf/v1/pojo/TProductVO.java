package com.qf.v1.pojo;

import com.qf.v1.entity.TProduct;
import com.qf.v1.entity.TProductDesc;
import com.qf.v1.entity.TProductType;

import java.io.Serializable;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/12
 */
public class TProductVO implements Serializable {
    private TProduct product;
    private String productDesc;

    private TProductType productType;

    private TProductDesc proDesc;

    public TProductDesc getProDesc() {
        return proDesc;
    }

    public void setProDesc(TProductDesc proDesc) {
        this.proDesc = proDesc;
    }

    public TProductType getProductType() {
        return productType;
    }

    public void setProductType(TProductType productType) {
        this.productType = productType;
    }

    public TProduct getProduct() {
        return product;
    }

    public void setProduct(TProduct product) {
        this.product = product;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }
}
