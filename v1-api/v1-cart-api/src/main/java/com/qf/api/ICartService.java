package com.qf.api;

import com.qf.v1.common.pojo.ResultBean;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/29
 */
public interface ICartService {

    public ResultBean add(String uuid,Long productId,Integer count);

    public ResultBean del(String uuid,Long productId);

    public ResultBean update(String uuid, Long productId, Integer count);

    public ResultBean query(String uuid);
}
