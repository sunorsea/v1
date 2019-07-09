package com.qf.v1userservice.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.v1.api.IUserService;
import com.qf.v1.common.base.BaseServiceImpl;
import com.qf.v1.common.base.IBaseDao;
import com.qf.v1.common.pojo.ResultBean;
import com.qf.v1.entity.TUser;
import com.qf.v1.mapper.TUserMapper;
import com.qf.v1userservice.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/25
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<TUser> implements IUserService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private TUserMapper userMapper;

    @Override
    public IBaseDao<TUser> getBaseDao() {
        return userMapper;
    }

    @Override
    public int insertSelective(TUser record) {
        super.insertSelective(record);
        return record.getId().intValue();
    }

    /**
     * 无状态方式
     * @param user
     * @return
     */
    @Override
    public ResultBean checkLogin(TUser user) {
        TUser currentUser=userMapper.selectByUsername(user.getUsername());
        if (currentUser != null) {
            if (currentUser.getPassword().equals(user.getPassword())) {
                JwtUtils jwtUtils = new JwtUtils();
                jwtUtils.setSecretKey("java1902");
                jwtUtils.setTtl(30*60*1000);
                String jwtToken = jwtUtils.createJwtToken(currentUser.getId().toString(), currentUser.getUsername());

                return new ResultBean("200", jwtToken);
            }
        }
        return new ResultBean("404","");
    }

    /**
     * redis方式
     * @param user
     * @return
     */
   /* @Override
    public ResultBean checkLogin(TUser user) {
        TUser currentUser=userMapper.selectByUsername(user.getUsername());
        if (currentUser != null) {
            if (currentUser.getPassword().equals(user.getPassword())) {
                String uuid = UUID.randomUUID().toString();
                String key = new StringBuilder("user:token:").append(uuid).toString();
                user.setPassword(null);
                redisTemplate.opsForValue().set(key, user);
                redisTemplate.expire(key, 30, TimeUnit.MINUTES);
                return new ResultBean("200", uuid);
            }
        }
        return new ResultBean("404","");
    }*/

    @Override
    public ResultBean checkIsLogin(String uuid) {
        try {
            JwtUtils jwtUtils = new JwtUtils();
            jwtUtils.setSecretKey("java1902");
            Claims claims = jwtUtils.parseJwtToken(uuid);

            //还要做刷新凭证的有效期


            TUser user = new TUser();
            user.setId(Long.parseLong(claims.getId()));
            user.setUsername(claims.getSubject());

            return new ResultBean("200", user);
        } catch (Exception e) {
            return new ResultBean("404",null);
        }

    }

    /**
     * redis方式
     * @param uuid
     * @return
     */
    /*@Override
    public ResultBean checkIsLogin(String uuid) {
        String key = new StringBuilder("user:token:").append(uuid).toString();
        TUser currentUser = (TUser) redisTemplate.opsForValue().get(key);
        if (currentUser != null) {
            redisTemplate.expire(key, 30, TimeUnit.MINUTES);
            return new ResultBean("200", currentUser);
        }

        return new ResultBean("404",null);
    }*/


    //无状态不需要登出,只要改controller
    @Override
    public ResultBean logout(String uuid) {
        String key = new StringBuilder("user:token:").append(uuid).toString();
        Boolean delete = redisTemplate.delete(key);
        if (delete) {
            return new ResultBean("200","删除成功");
        }
        return new ResultBean("404","删除失败");
    }
}
