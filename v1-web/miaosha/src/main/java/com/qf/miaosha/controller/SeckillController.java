package com.qf.miaosha.controller;

import com.qf.miaosha.exception.SeckillException;
import com.qf.miaosha.pojo.ResultBean;
import com.qf.miaosha.service.ISeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/7/9
 */

@Controller
@RequestMapping("seckill")
public class SeckillController {

    @Autowired
    private ISeckillService seckillService;

    @RequestMapping("sale")
    @ResponseBody
    public ResultBean sale(Long seckillId,Long userId) {
        try {
            seckillService.sale(seckillId, userId);
            String orderNo = seckillService.senOrderMsg(seckillId, userId);
            return new ResultBean("200", orderNo);
        } catch (SeckillException e) {
            return new ResultBean("404", e.getMessage());
        }
    }

}
