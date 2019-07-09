package com.qf.miaosha.service;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/7/9
 */
public interface ISeckillService {

    public void sale(Long seckillId, Long userId);

    String senOrderMsg(Long seckillId, Long userId);
}
