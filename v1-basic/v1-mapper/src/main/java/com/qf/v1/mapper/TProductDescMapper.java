package com.qf.v1.mapper;

import com.qf.v1.common.base.IBaseDao;
import com.qf.v1.entity.TProductDesc;

public interface TProductDescMapper extends IBaseDao<TProductDesc> {
    TProductDesc selectByProductId(Long productId);
}