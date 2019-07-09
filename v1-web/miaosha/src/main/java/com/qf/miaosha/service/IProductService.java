package com.qf.miaosha.service;

import com.qf.miaosha.entity.TProduct;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/7/8
 */
public interface IProductService {

    public TProduct getById(Long id);

    boolean sale(Long id);
}
