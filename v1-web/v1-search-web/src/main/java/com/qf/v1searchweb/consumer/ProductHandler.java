package com.qf.v1searchweb.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.v1.api.ISearchService;
import com.qf.v1.common.constant.RabbitMQConstant;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/6/21
 */
@Component
@RabbitListener(queues = RabbitMQConstant.PRODUCT_SEARCH_QUEUE)
public class ProductHandler {

    @Reference
    private ISearchService searchService;

    @RabbitHandler
    public void processAdd(Long id) {
        System.out.println(id);
        searchService.synDataById(id);
    }

}
