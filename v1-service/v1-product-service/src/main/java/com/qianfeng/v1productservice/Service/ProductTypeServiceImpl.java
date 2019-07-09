package com.qianfeng.v1productservice.Service;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.v1.api.IProductTypeService;
import com.qf.v1.common.base.BaseServiceImpl;
import com.qf.v1.common.base.IBaseDao;
import com.qf.v1.entity.TProductType;
import com.qf.v1.mapper.TProductTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/14
 */
@Service
public class ProductTypeServiceImpl extends BaseServiceImpl<TProductType> implements IProductTypeService {

    @Autowired
    private TProductTypeMapper productTypeMapper;

    @Override
    public IBaseDao<TProductType> getBaseDao() {
        return productTypeMapper;
    }
}
