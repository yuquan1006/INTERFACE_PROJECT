package com.ipaylinks.test.mock.server.business.fuiou;

import com.alibaba.fastjson.JSON;
import com.ipaylinks.test.mock.server.api.ResponseTemplateEnum;
import com.ipaylinks.test.mock.server.api.fuiou.bo.req.*;
import com.ipaylinks.test.mock.server.api.fuiou.bo.resp.*;
import com.ipaylinks.test.mock.server.config.FuiouProperties;
import com.ipaylinks.test.mock.server.dao.model.ResponseRecordWithBLOBs;
import com.ipaylinks.test.mock.server.dao.model.ResponseTemplate;
import com.ipaylinks.test.mock.server.service.ResponseRecordService;
import com.ipaylinks.test.mock.server.service.ResponseTemplateService;
import com.ipaylinks.test.mock.server.service.fuiou.FuiouNotifyService;
import com.ipaylinks.test.mock.server.util.JAXBUtils;
import com.ipaylinks.test.mock.server.util.MD5Utils;
import com.ipaylinks.test.mock.server.util.function.ExpressionParserUtils;
import com.ipaylinks.test.mock.server.util.function.FunctionRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
public class FuiouBusinessServiceImpl implements FuiouBusinessService {
    private Logger logger = LoggerFactory.getLogger(FuiouBusinessServiceImpl.class);
    @Autowired
    private ResponseTemplateService responseTemplateService;
    @Autowired
    private ResponseRecordService responseRecordService;
    @Autowired
    private FuiouProperties fuiouProperties;
    @Autowired
    private FuiouNotifyService fuiouNotifyService;

    @Override
    public String transfer(FuiouTransferRequest fuiouTransferRequest, String accessPoint) {
        logger.info("fuiou transfer req:{}", JSON.toJSONString(fuiouTransferRequest));
        Date requestDate = new Date();
        //获取响应模板
        ResponseTemplate responseTemplate = responseTemplateService.selectByTemplateName(ResponseTemplateEnum.FUIOU_TRANSFER.getName());
        try {
            //线程睡眠 构造超时场景
            Thread.sleep(responseTemplate.getSleepTime());
        } catch (InterruptedException e) {
            logger.error("Thread sleep error",e);
        }
        //获取响应模板内容
        String responseTemplateContent = responseTemplate.getResponseTemplateContent();

        //将请求的对象注入到standardEvaluationContext中
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        //初始化注册一些方法到上下文
        FunctionRegister.registerFunctionToContext(standardEvaluationContext);
        //初始化注册一些变量到上下文
        standardEvaluationContext.setVariable("null",null);
        standardEvaluationContext.setVariable("req",fuiouTransferRequest);
        //解析数据库中的模板，得到响应字符串
        Expression expression = ExpressionParserUtils.parser.parseExpression(responseTemplateContent, ExpressionParserUtils.templateParserContext);
        String fuiouTransferRespJsonStr = expression.getValue(standardEvaluationContext, String.class);

        FuiouTransferResponse fuiouTransferResponse = null;
        if (fuiouTransferRespJsonStr != null) {
            fuiouTransferResponse = JSON.parseObject(fuiouTransferRespJsonStr, FuiouTransferResponse.class);
            String fuiouTransferResponseXML = JAXBUtils.toXML(fuiouTransferResponse);
            String signContent = fuiouTransferResponseXML.substring(fuiouTransferResponseXML.indexOf(FuiouConstants.PREFIX_BODY),
                    fuiouTransferResponseXML.indexOf(FuiouConstants.SUFFIX_BODY)+FuiouConstants.SUFFIX_BODY.length());
            //加签
            String sign =MD5Utils.sign(signContent + "|" + fuiouProperties.getXmlMd5Key(),"UTF-8");
            fuiouTransferResponse.setSign(sign);
        }
        logger.info("fuiou transfer resp:{}", JSON.toJSONString(fuiouTransferResponse));
        Date responseDate = new Date();
        //保存响应记录
        ResponseRecordWithBLOBs responseRecord = new ResponseRecordWithBLOBs();
        responseRecord.setAccessPoint(accessPoint);
        responseRecord.setResponseTemplateName(ResponseTemplateEnum.FUIOU_TRANSFER.getName());
        responseRecord.setUniqueRequestId(fuiouTransferRequest.getHead().getTransNo());
        responseRecord.setRequestContent(JSON.toJSONString(fuiouTransferRequest));
        responseRecord.setResponseContent(JSON.toJSONString(fuiouTransferResponse));
        responseRecord.setRequestDate(requestDate);
        responseRecord.setResponseDate(responseDate);
        responseRecordService.insertSelective(responseRecord);

        standardEvaluationContext.setVariable("resp",fuiouTransferResponse);
        //异步转账通知
        fuiouNotifyService.asyncTransferNotify(fuiouTransferRequest, accessPoint, standardEvaluationContext);

        String respStr = JAXBUtils.toXML(fuiouTransferResponse);
        return respStr;
    }

    @Override
    public String transDetailsQuery(TransDetailsQueryRequest transDetailsQueryRequest, String accessPoint) {
        logger.info("fuiou transDetailsQuery req:{}", JSON.toJSONString(transDetailsQueryRequest));
        Date requestDate = new Date();
        //获取响应模板
        ResponseTemplate responseTemplate = responseTemplateService.selectByTemplateName(ResponseTemplateEnum.FUIOU_TRANS_DETAILS_QUERY.getName());
        try {
            //线程睡眠 构造超时场景
            Thread.sleep(responseTemplate.getSleepTime());
        } catch (InterruptedException e) {
            logger.error("Thread sleep error",e);
        }
        //获取响应模板内容
        String responseTemplateContent = responseTemplate.getResponseTemplateContent();

        //将请求的对象注入到standardEvaluationContext中
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        //初始化注册一些方法到上下文
        FunctionRegister.registerFunctionToContext(standardEvaluationContext);
        //初始化注册一些变量到上下文
        standardEvaluationContext.setVariable("null",null);
        standardEvaluationContext.setVariable("req",transDetailsQueryRequest);

        //将上次请求的对象注入到standardEvaluationContext中
        String transferTransNo = transDetailsQueryRequest.getBody().getTransNo();
        ResponseRecordWithBLOBs transferRecord = responseRecordService.selectByUniqueRequestId(transferTransNo);

        String transferReqJson = transferRecord.getRequestContent();
        FuiouTransferRequest transferReqBO = JSON.parseObject(transferReqJson, FuiouTransferRequest.class);
        standardEvaluationContext.setVariable("transferReqBO",transferReqBO);

        String transferRespJson = transferRecord.getResponseContent();
        FuiouTransferResponse transferRespBO = JSON.parseObject(transferRespJson, FuiouTransferResponse.class);
        standardEvaluationContext.setVariable("transferRespBO",transferRespBO);

        //解析数据库中的模板，得到响应字符串
        Expression expression = ExpressionParserUtils.parser.parseExpression(responseTemplateContent, ExpressionParserUtils.templateParserContext);
        String transDetailsQueryRespJsonStr = expression.getValue(standardEvaluationContext, String.class);

        TransDetailsQueryResponse transDetailsQueryResponse = null;
        if (transDetailsQueryRespJsonStr != null) {
            transDetailsQueryResponse = JSON.parseObject(transDetailsQueryRespJsonStr, TransDetailsQueryResponse.class);
            String transDetailsQueryResponseXML = JAXBUtils.toXML(transDetailsQueryResponse);
            String signContent = transDetailsQueryResponseXML.substring(transDetailsQueryResponseXML.indexOf(FuiouConstants.PREFIX_BODY),
                    transDetailsQueryResponseXML.indexOf(FuiouConstants.SUFFIX_BODY)+FuiouConstants.SUFFIX_BODY.length());
            //加签
            String sign =MD5Utils.sign(signContent + "|" + fuiouProperties.getXmlMd5Key(),"UTF-8");
            transDetailsQueryResponse.setSign(sign);
        }
        logger.info("fuiou transDetailsQuery resp:{}", JSON.toJSONString(transDetailsQueryResponse));
        Date responseDate = new Date();
        //保存响应记录
        ResponseRecordWithBLOBs responseRecord = new ResponseRecordWithBLOBs();
        responseRecord.setAccessPoint(accessPoint);
        responseRecord.setResponseTemplateName(ResponseTemplateEnum.FUIOU_TRANSFER.getName());
        responseRecord.setUniqueRequestId(transDetailsQueryRequest.getHead().getTransNo());
        responseRecord.setRequestContent(JSON.toJSONString(transDetailsQueryRequest));
        responseRecord.setResponseContent(JSON.toJSONString(transDetailsQueryResponse));
        responseRecord.setRequestDate(requestDate);
        responseRecord.setResponseDate(responseDate);
        responseRecordService.insertSelective(responseRecord);

        standardEvaluationContext.setVariable("resp",transDetailsQueryResponse);

        String respStr = JAXBUtils.toXML(transDetailsQueryResponse);
        return respStr;
    }

    @Override
    public FuiouNotifyResponse notify(FuiouNotifyRequest fuiouNotifyRequest, String accessPoint) throws IllegalAccessException {
        logger.info("fuiou notify req:{}", JSON.toJSONString(fuiouNotifyRequest));
        Date requestDate = new Date();
        //获取响应模板
        ResponseTemplate responseTemplate = responseTemplateService.selectByTemplateName(ResponseTemplateEnum.FUIOU_NOTIFY.getName());
        try {
            //线程睡眠 构造超时场景
            Thread.sleep(responseTemplate.getSleepTime());
        } catch (InterruptedException e) {
            logger.error("Thread sleep error",e);
        }
        //获取响应模板内容
        String responseTemplateContent = responseTemplate.getResponseTemplateContent();

        //将请求的对象注入到standardEvaluationContext中
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        //初始化注册一些方法到上下文
        FunctionRegister.registerFunctionToContext(standardEvaluationContext);
        //初始化注册一些变量到上下文
        standardEvaluationContext.setVariable("null",null);
        standardEvaluationContext.setVariable("req",fuiouNotifyRequest);

        //解析数据库中的模板，得到响应字符串
        Expression expression = ExpressionParserUtils.parser.parseExpression(responseTemplateContent, ExpressionParserUtils.templateParserContext);
        String fuiouNotifyResponseJsonStr = expression.getValue(standardEvaluationContext, String.class);

        FuiouNotifyResponse fuiouNotifyResponse = null;
        if (fuiouNotifyResponseJsonStr != null) {
            fuiouNotifyResponse = JSON.parseObject(fuiouNotifyResponseJsonStr, FuiouNotifyResponse.class);

            String signData = fuiouNotifyResponse.getSignData(fuiouProperties.getJsonMd5Key(),"|");
            //加签
            String sign = MD5Utils.sign(signData,"UTF-8");

            fuiouNotifyResponse.setMd5(sign);
        }
        logger.info("fuiou notify resp:{}", JSON.toJSONString(fuiouNotifyResponse));
        Date responseDate = new Date();
        //保存响应记录
        ResponseRecordWithBLOBs responseRecord = new ResponseRecordWithBLOBs();
        responseRecord.setAccessPoint(accessPoint);
        responseRecord.setResponseTemplateName(ResponseTemplateEnum.FUIOU_NOTIFY.getName());
        responseRecord.setUniqueRequestId(fuiouNotifyRequest.getOrderNo());
        responseRecord.setRequestContent(JSON.toJSONString(fuiouNotifyRequest));
        responseRecord.setResponseContent(JSON.toJSONString(fuiouNotifyResponse));
        responseRecord.setRequestDate(requestDate);
        responseRecord.setResponseDate(responseDate);
        responseRecordService.insertSelective(responseRecord);

        standardEvaluationContext.setVariable("resp",fuiouNotifyResponse);
        //异步文件上传通知接口通知
        fuiouNotifyService.asyncNotifyNotify(fuiouNotifyRequest, accessPoint, standardEvaluationContext);

        return fuiouNotifyResponse;
    }

    @Override
    public FuiouSubmitOrderResponse submitOrder(FuiouSubmitOrderRequest fuiouSubmitOrderRequest, String accessPoint) throws IllegalAccessException {
        logger.info("fuiou submitOrder req:{}", JSON.toJSONString(fuiouSubmitOrderRequest));
        Date requestDate = new Date();
        //获取响应模板
        ResponseTemplate responseTemplate = responseTemplateService.selectByTemplateName(ResponseTemplateEnum.FUIOU_SUBMIT_ORDER.getName());
        try {
            //线程睡眠 构造超时场景
            Thread.sleep(responseTemplate.getSleepTime());
        } catch (InterruptedException e) {
            logger.error("Thread sleep error",e);
        }
        //获取响应模板内容
        String responseTemplateContent = responseTemplate.getResponseTemplateContent();

        //将请求的对象注入到standardEvaluationContext中
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        //初始化注册一些方法到上下文
        FunctionRegister.registerFunctionToContext(standardEvaluationContext);
        //初始化注册一些变量到上下文
        standardEvaluationContext.setVariable("null",null);
        standardEvaluationContext.setVariable("req",fuiouSubmitOrderRequest);

        //解析数据库中的模板，得到响应字符串
        Expression expression = ExpressionParserUtils.parser.parseExpression(responseTemplateContent, ExpressionParserUtils.templateParserContext);
        String fuiouSubmitOrderResponseJsonStr = expression.getValue(standardEvaluationContext, String.class);

        FuiouSubmitOrderResponse fuiouSubmitOrderResponse = null;
        if (fuiouSubmitOrderResponseJsonStr != null) {
            fuiouSubmitOrderResponse = JSON.parseObject(fuiouSubmitOrderResponseJsonStr, FuiouSubmitOrderResponse.class);

            String signData = fuiouSubmitOrderResponse.getSignData(fuiouProperties.getJsonMd5Key(),"|");
            //加签
            String sign = MD5Utils.sign(signData,"UTF-8");

            fuiouSubmitOrderResponse.setMd5(sign);
        }
        logger.info("fuiou submitOrder resp:{}", JSON.toJSONString(fuiouSubmitOrderResponse));
        Date responseDate = new Date();
        //保存响应记录
        ResponseRecordWithBLOBs responseRecord = new ResponseRecordWithBLOBs();
        responseRecord.setAccessPoint(accessPoint);
        responseRecord.setResponseTemplateName(ResponseTemplateEnum.FUIOU_NOTIFY.getName());
        responseRecord.setUniqueRequestId(fuiouSubmitOrderRequest.getOrderId());
        responseRecord.setRequestContent(JSON.toJSONString(fuiouSubmitOrderRequest));
        responseRecord.setResponseContent(JSON.toJSONString(fuiouSubmitOrderResponse));
        responseRecord.setRequestDate(requestDate);
        responseRecord.setResponseDate(responseDate);
        responseRecordService.insertSelective(responseRecord);

        standardEvaluationContext.setVariable("resp",fuiouSubmitOrderResponse);
        //异步跨境收汇订单提交接口通知
        fuiouNotifyService.asyncSubmitOrderNotify(fuiouSubmitOrderRequest, accessPoint, standardEvaluationContext);

        return fuiouSubmitOrderResponse;
    }

    @Override
    public FuiouOrderQueryPageResponse orderQueryPage(FuiouOrderQueryPageRequest fuiouOrderQueryPageRequest, String accessPoint) throws IllegalAccessException {
        logger.info("fuiou orderQueryPage req:{}", JSON.toJSONString(fuiouOrderQueryPageRequest));
        Date requestDate = new Date();
        //获取响应模板
        ResponseTemplate responseTemplate = responseTemplateService.selectByTemplateName(ResponseTemplateEnum.FUIOU_ORDER_QUERY_PAGE.getName());
        try {
            //线程睡眠 构造超时场景
            Thread.sleep(responseTemplate.getSleepTime());
        } catch (InterruptedException e) {
            logger.error("Thread sleep error",e);
        }
        //获取响应模板内容
        String responseTemplateContent = responseTemplate.getResponseTemplateContent();

        //将请求的对象注入到standardEvaluationContext中
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        //初始化注册一些方法到上下文
        FunctionRegister.registerFunctionToContext(standardEvaluationContext);
        //初始化注册一些变量到上下文
        standardEvaluationContext.setVariable("null",null);
        standardEvaluationContext.setVariable("req", fuiouOrderQueryPageRequest);

        //解析数据库中的模板，得到响应字符串
        Expression expression = ExpressionParserUtils.parser.parseExpression(responseTemplateContent, ExpressionParserUtils.templateParserContext);
        String fuiouOrderQueryPageResponseJsonStr = expression.getValue(standardEvaluationContext, String.class);

        FuiouOrderQueryPageResponse fuiouOrderQueryPageResponse = null;
        if (fuiouOrderQueryPageResponseJsonStr != null) {
            fuiouOrderQueryPageResponse = JSON.parseObject(fuiouOrderQueryPageResponseJsonStr, FuiouOrderQueryPageResponse.class);

            String signData = fuiouOrderQueryPageResponse.getSignData(fuiouProperties.getJsonMd5Key(),"|");
            //加签
            String sign = MD5Utils.sign(signData,"UTF-8");

            fuiouOrderQueryPageResponse.setMd5(sign);
        }
        logger.info("fuiou orderQueryPage resp:{}", JSON.toJSONString(fuiouOrderQueryPageResponse));
        Date responseDate = new Date();
        //保存响应记录
        ResponseRecordWithBLOBs responseRecord = new ResponseRecordWithBLOBs();
        responseRecord.setAccessPoint(accessPoint);
        responseRecord.setResponseTemplateName(ResponseTemplateEnum.FUIOU_NOTIFY.getName());
        String uniqueRequestId = "";
        if (!StringUtils.isEmpty(fuiouOrderQueryPageRequest.getOrderId())){
            uniqueRequestId = fuiouOrderQueryPageRequest.getOrderId();
        } else if (!StringUtils.isEmpty(fuiouOrderQueryPageRequest.getOrderNo())) {
            uniqueRequestId = fuiouOrderQueryPageRequest.getOrderNo();
        }
        responseRecord.setUniqueRequestId(uniqueRequestId);
        responseRecord.setRequestContent(JSON.toJSONString(fuiouOrderQueryPageRequest));
        responseRecord.setResponseContent(JSON.toJSONString(fuiouOrderQueryPageResponse));
        responseRecord.setRequestDate(requestDate);
        responseRecord.setResponseDate(responseDate);
        responseRecordService.insertSelective(responseRecord);

        standardEvaluationContext.setVariable("resp",fuiouOrderQueryPageResponse);

        return fuiouOrderQueryPageResponse;
    }

}
