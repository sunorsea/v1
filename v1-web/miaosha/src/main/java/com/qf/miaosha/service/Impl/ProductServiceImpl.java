package com.qf.miaosha.service.Impl;

import com.qf.miaosha.entity.TProduct;
import com.qf.miaosha.mapper.TProductMapper;
import com.qf.miaosha.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/7/8
 */
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private TProductMapper productMapper;

    @Override
    @Cacheable(value = "product",key = "#id")
    public TProduct getById(Long id) {

        return productMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional
    public boolean sale(Long id) {
        int store = productMapper.selectStoreById(id);
        if (store > 0) {
            productMapper.updateStoreById(id);
            return true;
        }
        return false;
    }
}
