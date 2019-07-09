package com.qf.v1.mapper;

import com.qf.v1.common.base.IBaseDao;
import com.qf.v1.entity.TProduct;

import java.util.List;

public interface TProductMapper extends IBaseDao<TProduct> {
    Long batchUpdateFlag(List<Long> ids);
}