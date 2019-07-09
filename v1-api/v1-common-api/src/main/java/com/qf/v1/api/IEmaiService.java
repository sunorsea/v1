package com.qf.v1.api;

import com.qf.v1.common.pojo.ResultBean;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/25
 */
public interface IEmaiService {

    public ResultBean send(String to, String subject, String text);
}
