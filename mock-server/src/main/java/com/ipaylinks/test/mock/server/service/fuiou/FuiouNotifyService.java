package com.ipaylinks.test.mock.server.service.fuiou;

import com.alibaba.fastjson.JSON;
import com.ipaylinks.test.mock.server.api.NotifyTemplateEnum;
import com.ipaylinks.test.mock.server.api.fuiou.bo.notify.FuiouNotifyNotifyRequest;
import com.ipaylinks.test.mock.server.api.fuiou.bo.notify.FuiouSubmitOrderNotifyRequest;
import com.ipaylinks.test.mock.server.api.fuiou.bo.notify.FuiouTransferNotifyRequest;
import com.ipaylinks.test.mock.server.api.fuiou.bo.req.FuiouNotifyRequest;
import com.ipaylinks.test.mock.server.api.fuiou.bo.req.FuiouSubmitOrderRequest;
import com.ipaylinks.test.mock.server.api.fuiou.bo.req.FuiouTransferRequest;
import com.ipaylinks.test.mock.server.business.fuiou.FuiouConstants;
import com.ipaylinks.test.mock.server.config.FuiouProperties;
import com.ipaylinks.test.mock.server.config.RestTemplateConfig;
import com.ipaylinks.test.mock.server.dao.model.NotifyRecordWithBLOBs;
import com.ipaylinks.test.mock.server.dao.model.NotifyTemplate;
import com.ipaylinks.test.mock.server.service.NotifyRecordService;
import com.ipaylinks.test.mock.server.service.NotifyTemplateService;
import com.ipaylinks.test.mock.server.util.BeanUtil;
import com.ipaylinks.test.mock.server.util.MD5Utils;
import com.ipaylinks.test.mock.server.util.function.ExpressionParserUtils;
import com.ipaylinks.test.mock.server.util.JAXBUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Date;
import java.util.Map;

@Service
public class FuiouNotifyService {
    private Logger logger = LoggerFactory.getLogger(FuiouNotifyService.class);
    @Autowired
    private NotifyTemplateService notifyTemplateService;
    @Autowired
    private NotifyRecordService notifyRecordService;
    @Autowired
    private FuiouProperties fuiouProperties;

    @Async
    public void asyncTransferNotify(FuiouTransferRequest fuiouTransferRequest, String accessPoint, StandardEvaluationContext standardEvaluationContext){
        Date requestDate = new Date();
        //通知
        String notifyUrl = fuiouTransferRequest.getBody().getBackNotifyUrl();
        //获取通知模板
        NotifyTemplate notifyTemplate = notifyTemplateService.selectByTemplateName(NotifyTemplateEnum.FUIOU_TRANSFER_NOTIFY.getName());
        try {
            //线程睡眠 构造超时场景
            Thread.sleep(notifyTemplate.getSleepTime());
        } catch (InterruptedException e) {
            logger.error("Thread sleep error",e);
        }
        if (notifyUrl == null) {
            notifyUrl = notifyTemplate.getNotifyUrl();
        }
        //获取通知模板内容
        String notifyTemplateContent = notifyTemplate.getNotifyTemplateContent();

        //解析数据库中的模板，得到响应字符串
        Expression expression = ExpressionParserUtils.parser.parseExpression(notifyTemplateContent, ExpressionParserUtils.templateParserContext);
        String fuiouTransferNotifyReqJsonStr = expression.getValue(standardEvaluationContext, String.class);

        //通知模板内容解析
        FuiouTransferNotifyRequest fuiouTransferNotifyRequest = null;
        if (fuiouTransferNotifyReqJsonStr != null) {
            fuiouTransferNotifyRequest = JSON.parseObject(fuiouTransferNotifyReqJsonStr, FuiouTransferNotifyRequest.class);
            String fuiouTransferNotifyRequestXML = JAXBUtils.toXML(fuiouTransferNotifyRequest);
            String signContent = fuiouTransferNotifyRequestXML.substring(fuiouTransferNotifyRequestXML.indexOf(FuiouConstants.PREFIX_BODY),
                    fuiouTransferNotifyRequestXML.indexOf(FuiouConstants.SUFFIX_BODY)+FuiouConstants.SUFFIX_BODY.length());
            //加签
            String sign = MD5Utils.sign(signContent + "|" + fuiouProperties.getXmlMd5Key(),"UTF-8");

            fuiouTransferNotifyRequest.setSign(sign);
        }
        String notifyReqStr = JAXBUtils.toXML(fuiouTransferNotifyRequest);
        MultiValueMap<String, String> reqMap= new LinkedMultiValueMap<>();
        reqMap.add("reqStr", notifyReqStr);
        logger.info("fuiou transferNotify req:{}", reqMap);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        ResponseEntity<String> notifyResp = RestTemplateConfig.getRestTemplate().exchange(notifyUrl,
                HttpMethod.POST, new HttpEntity<>(reqMap, httpHeaders), String.class);
        logger.info("fuiou transferNotify resp:{}", notifyResp.getBody());

        Date responseDate = new Date();
        //保存通知记录
        NotifyRecordWithBLOBs notifyRecord = new NotifyRecordWithBLOBs();
        notifyRecord.setAccessPoint(accessPoint);
        notifyRecord.setNotifyTemplateName(NotifyTemplateEnum.FUIOU_TRANSFER_NOTIFY.getName());
        notifyRecord.setNotifyUrl(notifyUrl);
        notifyRecord.setUniqueRequestId(fuiouTransferRequest.getHead().getTransNo());
        notifyRecord.setNotifyRequestContent(JSON.toJSONString(fuiouTransferNotifyRequest));
        notifyRecord.setNotifyResponseContent(notifyResp.getBody());
        notifyRecord.setNotifyRequestDate(requestDate);
        notifyRecord.setNotifyResponseDate(responseDate);
        notifyRecordService.insertSelective(notifyRecord);
    }

    @Async
    public void asyncNotifyNotify(FuiouNotifyRequest fuiouNotifyRequest, String accessPoint, StandardEvaluationContext standardEvaluationContext) throws IllegalAccessException {
        Date requestDate = new Date();
        //通知
        String notifyUrl = fuiouNotifyRequest.getBackNotifyUrl();
        //获取通知模板
        NotifyTemplate notifyTemplate = notifyTemplateService.selectByTemplateName(NotifyTemplateEnum.FUIOU_NOTIFY_NOTIFY.getName());
        try {
            //线程睡眠 构造超时场景
            Thread.sleep(notifyTemplate.getSleepTime());
        } catch (InterruptedException e) {
            logger.error("Thread sleep error",e);
        }
        if (notifyUrl == null) {
            notifyUrl = notifyTemplate.getNotifyUrl();
        }
        //获取通知模板内容
        String notifyTemplateContent = notifyTemplate.getNotifyTemplateContent();

        //解析数据库中的模板，得到响应字符串
        Expression expression = ExpressionParserUtils.parser.parseExpression(notifyTemplateContent, ExpressionParserUtils.templateParserContext);
        String fuiouNotifyNotifyReqJsonStr = expression.getValue(standardEvaluationContext, String.class);

        //通知模板内容解析
        FuiouNotifyNotifyRequest fuiouNotifyNotifyRequest = JSON.parseObject(fuiouNotifyNotifyReqJsonStr, FuiouNotifyNotifyRequest.class);

        Map<String, String> reqMap = BeanUtil.beanToMap(fuiouNotifyNotifyRequest);
        MultiValueMap<String, String> reqMultiValueMap= new LinkedMultiValueMap<>();
        for(Map.Entry<String, String> entry : reqMap.entrySet()){
            reqMultiValueMap.add(entry.getKey(), entry.getValue());
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        logger.info("fuiou NotifyNotify req:{}", JSON.toJSONString(fuiouNotifyNotifyRequest));
        ResponseEntity<String> notifyResp = RestTemplateConfig.getRestTemplate().exchange(notifyUrl,
                HttpMethod.POST, new HttpEntity<>(reqMultiValueMap, httpHeaders), String.class);
        logger.info("fuiou NotifyNotify resp:{}", notifyResp.getBody());

        Date responseDate = new Date();
        //保存通知记录
        NotifyRecordWithBLOBs notifyRecord = new NotifyRecordWithBLOBs();
        notifyRecord.setAccessPoint(accessPoint);
        notifyRecord.setNotifyTemplateName(NotifyTemplateEnum.FUIOU_NOTIFY_NOTIFY.getName());
        notifyRecord.setNotifyUrl(notifyUrl);
        notifyRecord.setUniqueRequestId(fuiouNotifyNotifyRequest.getOrderNo());
        notifyRecord.setNotifyRequestContent(JSON.toJSONString(fuiouNotifyNotifyRequest));
        notifyRecord.setNotifyResponseContent(notifyResp.getBody());
        notifyRecord.setNotifyRequestDate(requestDate);
        notifyRecord.setNotifyResponseDate(responseDate);
        notifyRecordService.insertSelective(notifyRecord);
    }

    public void asyncSubmitOrderNotify(FuiouSubmitOrderRequest fuiouSubmitOrderRequest, String accessPoint, StandardEvaluationContext standardEvaluationContext) throws IllegalAccessException {
        Date requestDate = new Date();
        //通知
        String notifyUrl = fuiouSubmitOrderRequest.getBackNotifyUrl();
        //获取通知模板
        NotifyTemplate notifyTemplate = notifyTemplateService.selectByTemplateName(NotifyTemplateEnum.FUIOU_SUBMIT_ORDER_NOTIFY.getName());
        try {
            //线程睡眠 构造超时场景
            Thread.sleep(notifyTemplate.getSleepTime());
        } catch (InterruptedException e) {
            logger.error("Thread sleep error",e);
        }
        if (notifyUrl == null) {
            notifyUrl = notifyTemplate.getNotifyUrl();
        }
        //获取通知模板内容
        String notifyTemplateContent = notifyTemplate.getNotifyTemplateContent();

        //解析数据库中的模板，得到响应字符串
        Expression expression = ExpressionParserUtils.parser.parseExpression(notifyTemplateContent, ExpressionParserUtils.templateParserContext);
        String fuiouSubmitOrderNotifyReqJsonStr = expression.getValue(standardEvaluationContext, String.class);

        //通知模板内容解析
        FuiouSubmitOrderNotifyRequest fuiouSubmitOrderNotifyRequest = JSON.parseObject(fuiouSubmitOrderNotifyReqJsonStr, FuiouSubmitOrderNotifyRequest.class);

        Map<String, String> reqMap = BeanUtil.beanToMap(fuiouSubmitOrderNotifyRequest);
        MultiValueMap<String, String> reqMultiValueMap= new LinkedMultiValueMap<>();
        for(Map.Entry<String, String> entry : reqMap.entrySet()){
            reqMultiValueMap.add(entry.getKey(), entry.getValue());
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        logger.info("fuiou SubmitOrder req:{}", JSON.toJSONString(fuiouSubmitOrderNotifyRequest));
        ResponseEntity<String> notifyResp = RestTemplateConfig.getRestTemplate().exchange(notifyUrl,
                HttpMethod.POST, new HttpEntity<>(reqMultiValueMap, httpHeaders), String.class);
        logger.info("fuiou SubmitOrder resp:{}", notifyResp.getBody());

        Date responseDate = new Date();
        //保存通知记录
        NotifyRecordWithBLOBs notifyRecord = new NotifyRecordWithBLOBs();
        notifyRecord.setAccessPoint(accessPoint);
        notifyRecord.setNotifyTemplateName(NotifyTemplateEnum.FUIOU_NOTIFY_NOTIFY.getName());
        notifyRecord.setNotifyUrl(notifyUrl);
        notifyRecord.setUniqueRequestId(fuiouSubmitOrderRequest.getOrderId());
        notifyRecord.setNotifyRequestContent(JSON.toJSONString(fuiouSubmitOrderNotifyRequest));
        notifyRecord.setNotifyResponseContent(notifyResp.getBody());
        notifyRecord.setNotifyRequestDate(requestDate);
        notifyRecord.setNotifyResponseDate(responseDate);
        notifyRecordService.insertSelective(notifyRecord);
    }
}
