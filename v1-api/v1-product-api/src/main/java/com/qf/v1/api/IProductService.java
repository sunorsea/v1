package com.qf.v1.api;

import com.github.pagehelper.PageInfo;
import com.qf.v1.common.base.IBaseService;
import com.qf.v1.entity.TProduct;
import com.qf.v1.pojo.TProductVO;

import java.util.List;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/11
 */
public interface IProductService extends IBaseService<TProduct> {

    public PageInfo<TProduct> page(Integer pageIndex, Integer pageSize);

    public Long save(TProductVO vo);

    public Long batchDel(List<Long> ids);

    TProductVO selectProductById(Long id);
}
