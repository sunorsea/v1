package com.qf.v1.api;

import com.qf.v1.common.pojo.ResultBean;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/17
 */
public interface ISearchService {
    public ResultBean synAllData();

    /**
     * 按照关键词搜索
     * @param keywords
     * @return
     */
    public ResultBean queryByKeywords(String keywords);

    public ResultBean synDataById(Long id);
}
