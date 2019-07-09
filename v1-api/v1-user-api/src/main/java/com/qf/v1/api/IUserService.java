package com.qf.v1.api;

import com.qf.v1.common.base.IBaseService;
import com.qf.v1.common.pojo.ResultBean;
import com.qf.v1.entity.TUser;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/25
 */
public interface IUserService extends IBaseService<TUser> {

    public ResultBean checkLogin(TUser user);

    public ResultBean checkIsLogin(String uuid);

    public ResultBean logout(String uuid);

}
