package com.qf.v1orderweb.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qf.v1orderweb.pojo.PayContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author rhb
 * @version 1.0
 * @Date 2019/7/1
 */
@Controller
@RequestMapping("aliPay")
public class PayController {

    @Autowired
    private AlipayClient alipayClient;

    @Value("${alipay.alipayPublicKey}")
    private String alipayPublicKey;

    @RequestMapping("toPay")
    public void toPay(HttpServletResponse response,String orderNo) throws IOException {
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
        alipayRequest.setReturnUrl("http://domain.com/CallBack/return_url.jsp");
        alipayRequest.setNotifyUrl("http://9r9rcu.natappfree.cc/aliPay/notifyPayResult");//在公共参数中设置回跳和通知地址

        PayContent payContent = new PayContent(orderNo, "FAST_INSTANT_TRADE_PAY", "8888",
                "电影院", "有钱任性");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(payContent);
        alipayRequest.setBizContent(json);

        String form="";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(form);//直接将完整的表单html输出到页面
        response.getWriter().flush();
        response.getWriter().close();
    }

    @RequestMapping("notifyPayResult")
    public void notifyPayResult(HttpServletRequest request,HttpServletResponse response) throws AlipayApiException, IOException {

        Map<String, String[]> sourceMap = request.getParameterMap();
        Map<String, String> paramsMap = new HashMap<>(); //将异步通知中收到的所有参数都存放到map中
        Set<Map.Entry<String, String[]>> entries = sourceMap.entrySet();
        for (Map.Entry<String, String[]> entry : entries) {
            StringBuilder value = new StringBuilder();
            String[] sourceArray = entry.getValue();
            for (int i = 0; i < sourceArray.length-1; i++) {
                value.append(sourceArray[i]).append(",");
            }
            value.append(sourceArray[sourceArray.length - 1]);
            paramsMap.put(entry.getKey(), value.toString());
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(paramsMap, alipayPublicKey, "utf-8", "RSA2"); //调用SDK验证签名
        if(signVerified){
            // TODO 验签成功后，按照支付结果异步通知中的描述，对支付结果中的业务内容进行二次校验，校验成功后在response中返回success并继续商户自身业务处理，校验失败返回failure
            System.out.println("是支付宝发送过来的信息");

            String trade_status = request.getParameter("trade_status");
            if ("TRADE_SUCCESS".equals(trade_status)) {
                System.out.println("支付成功");
            }

            String orderNo = request.getParameter("out_trade_no");
            String money = request.getParameter("total_amount");
            //TODO根据订单号去查询我们数据库的订单金额
            //支付宝发送过来的订单金额和我们的订单金额匹配,说明没有问题
            //修改订单的状态为已支付

            System.out.println(orderNo);
            System.out.println(money);
            //金额对不上需要订单号
            String trade_no = request.getParameter("trade_no");
            System.out.println(trade_no);
            PrintWriter writer = response.getWriter();
            writer.write("success");
            writer.flush();
            writer.close();

        }else{
            // TODO 验签失败则记录异常日志，并在response中返回failure.
            System.out.println("不是支付宝发送过来的");
            PrintWriter writer = response.getWriter();
            writer.write("failure");
            writer.flush();
            writer.close();
        }
    }

    @RequestMapping("getBill")
    public void getBill(HttpServletResponse servletResponse) throws AlipayApiException, IOException {
        AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
        request.setBizContent("{" +
                "\"bill_type\":\"trade\"," +
                "\"bill_date\":\"2019-07-01\"" +
                "  }");
        AlipayDataDataserviceBillDownloadurlQueryResponse response = alipayClient.execute(request);
        if(response.isSuccess()){
            System.out.println(response.getBillDownloadUrl());
            System.out.println("调用成功");
            servletResponse.getWriter().write("ok");
            servletResponse.getWriter().flush();
            servletResponse.getWriter().close();
        } else {
            System.out.println("调用失败");
            servletResponse.getWriter().write("notOk");
            servletResponse.getWriter().flush();
            servletResponse.getWriter().close();
        }
    }

}
