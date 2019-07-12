package com.ipaylinks.test.mock.server.business.czcb;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.ipaylinks.test.mock.server.api.ResponseTemplateEnum;
import com.ipaylinks.test.mock.server.api.czcb.bo.req.*;
import com.ipaylinks.test.mock.server.api.czcb.bo.resp.*;
import com.ipaylinks.test.mock.server.dao.model.ResponseRecordWithBLOBs;
import com.ipaylinks.test.mock.server.dao.model.ResponseTemplate;
import com.ipaylinks.test.mock.server.service.ResponseRecordService;
import com.ipaylinks.test.mock.server.service.ResponseTemplateService;
import com.ipaylinks.test.mock.server.util.function.ExpressionParserUtils;
import com.ipaylinks.test.mock.server.util.function.FunctionRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;

@Service
public class CzcbBusinessServiceImpl implements CzcbBusinessService {
    private Logger logger = LoggerFactory.getLogger(CzcbBusinessServiceImpl.class);
    @Autowired
    private ResponseTemplateService responseTemplateService;
    @Autowired
    private ResponseRecordService responseRecordService;
    @Override
    public CzcbResp<CzcbOd0007Resp> od0007(CzcbReq<CzcbOd0007Req> czcbOd0007Req,String accessPoint) {
        logger.info("Czcb od0007 req:{}", JSON.toJSONString(czcbOd0007Req));
        Date requestDate = new Date();
        //获取响应模板
        ResponseTemplate responseTemplate = responseTemplateService.selectByTemplateName(ResponseTemplateEnum.CZCB_OD0007.getName());
        try {
            //线程睡眠 构造超时场景
            Thread.sleep(responseTemplate.getSleepTime());
        } catch (InterruptedException e) {
            logger.error("Thread sleep error",e);
        }
        //获取响应模板内容
        String responseTemplateContent = responseTemplate.getResponseTemplateContent();
        logger.info("czcb od0007 respTemplate:{}", responseTemplateContent);

        //将请求的对象注入到standardEvaluationContext中
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        //初始化注册一些方法到上下文
        FunctionRegister.registerFunctionToContext(standardEvaluationContext);
        //初始化注册一些变量到上下文
        standardEvaluationContext.setVariable("null",null);
        standardEvaluationContext.setVariable("req",czcbOd0007Req);
        //解析数据库中的模板，得到响应字符串
        Expression expression = ExpressionParserUtils.parser.parseExpression(responseTemplateContent, ExpressionParserUtils.templateParserContext);
        String respJsonStr = expression.getValue(standardEvaluationContext, String.class);

        //fastjson 反序列化泛型
        CzcbResp<CzcbOd0007Resp> resp  =JSON.parseObject(respJsonStr, new com.alibaba.fastjson.TypeReference<CzcbResp<CzcbOd0007Resp>>(){});
        logger.info("Czcb od0007 resp:{}", JSON.toJSONString(resp));
/*
        //jackson 反序列化泛型
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            CzcbResp<CzcbOd0007Resp> resp2 = objectMapper.readValue(respJsonStr, new com.fasterxml.jackson.core.type.TypeReference<CzcbResp<CzcbOd0007Resp>>(){});
            logger.info("Czcb od0007 resp:{}", JSON.toJSONString(resp2));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Gson 反序列化泛型
        Type type = new TypeToken<CzcbResp<CzcbOd0007Resp>>(){}.getType();
        CzcbResp<CzcbOd0007Resp> resp3 = new Gson().fromJson(respJsonStr, type);
        logger.info("Czcb od0007 resp:{}", JSON.toJSONString(resp3));
*/

        Date responseDate = new Date();
        //保存响应记录
        ResponseRecordWithBLOBs responseRecord = new ResponseRecordWithBLOBs();
        responseRecord.setAccessPoint(accessPoint);
        responseRecord.setResponseTemplateName(ResponseTemplateEnum.CZCB_OD0007.getName());
        responseRecord.setUniqueRequestId(czcbOd0007Req.getRequestId());
        responseRecord.setRequestContent(JSON.toJSONString(czcbOd0007Req));
        responseRecord.setResponseContent(respJsonStr);
        responseRecord.setRequestDate(requestDate);
        responseRecord.setResponseDate(responseDate);
        responseRecordService.insertSelective(responseRecord);
        return resp;
    }

    @Override
    public CzcbResp<CzcbOa0003Resp> oa0003(CzcbReq<CzcbOa0003Req> czcbOa0003Req, String accessPoint) {
        logger.info("Czcb oa0003 req:{}", JSON.toJSONString(czcbOa0003Req));
        Date requestDate = new Date();
        //获取响应模板
        ResponseTemplate responseTemplate = responseTemplateService.selectByTemplateName(ResponseTemplateEnum.CZCB_OA0003.getName());
        try {
            //线程睡眠 构造超时场景
            Thread.sleep(responseTemplate.getSleepTime());
        } catch (InterruptedException e) {
            logger.error("Thread sleep error",e);
        }
        //获取响应模板内容
        String responseTemplateContent = responseTemplate.getResponseTemplateContent();
        logger.info("czcb oa0003 respTemplate:{}", responseTemplateContent);

        //将请求的对象注入到standardEvaluationContext中
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        //初始化注册一些方法到上下文
        FunctionRegister.registerFunctionToContext(standardEvaluationContext);
        //初始化注册一些变量到上下文
        standardEvaluationContext.setVariable("null",null);
        standardEvaluationContext.setVariable("req",czcbOa0003Req);

        //解析数据库中的模板，得到响应字符串
        Expression expression = ExpressionParserUtils.parser.parseExpression(responseTemplateContent, ExpressionParserUtils.templateParserContext);
        String respJsonStr = expression.getValue(standardEvaluationContext, String.class);

        //fastjson 反序列化泛型
        CzcbResp<CzcbOa0003Resp> resp  =JSON.parseObject(respJsonStr, new com.alibaba.fastjson.TypeReference<CzcbResp<CzcbOa0003Resp>>(){});
        logger.info("Czcb oa0003 resp:{}", JSON.toJSONString(resp));

        Date responseDate = new Date();
        //保存响应记录
        ResponseRecordWithBLOBs responseRecord = new ResponseRecordWithBLOBs();
        responseRecord.setAccessPoint(accessPoint);
        responseRecord.setResponseTemplateName(ResponseTemplateEnum.CZCB_OA0003.getName());
        responseRecord.setUniqueRequestId(czcbOa0003Req.getRequestId());
        responseRecord.setRequestContent(JSON.toJSONString(czcbOa0003Req));
        responseRecord.setResponseContent(respJsonStr);
        responseRecord.setRequestDate(requestDate);
        responseRecord.setResponseDate(responseDate);
        responseRecordService.insertSelective(responseRecord);
        return resp;
    }

    @Override
    public CzcbResp<CzcbOa0005Resp> oa0005(CzcbReq<CzcbOa0005Req> czcbOa0005Req, String accessPoint) {
        logger.info("czcb oa0005 req:{}", JSON.toJSONString(czcbOa0005Req));
        Date requestDate = new Date();
        //获取响应模板
        ResponseTemplate responseTemplate = responseTemplateService.selectByTemplateName(ResponseTemplateEnum.CZCB_OA0005.getName());
        try {
            //线程睡眠 构造超时场景
            Thread.sleep(responseTemplate.getSleepTime());
        } catch (InterruptedException e) {
            logger.error("Thread sleep error",e);
        }
        //获取响应模板内容
        String responseTemplateContent = responseTemplate.getResponseTemplateContent();
        logger.info("czcb oa0005 respTemplate:{}", responseTemplateContent);

        //将请求的对象注入到standardEvaluationContext中
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        //初始化注册一些方法到上下文
        FunctionRegister.registerFunctionToContext(standardEvaluationContext);
        //初始化注册一些变量到上下文
        standardEvaluationContext.setVariable("null",null);
        standardEvaluationContext.setVariable("req",czcbOa0005Req);

        //解析数据库中的模板，得到响应字符串
        Expression expression = ExpressionParserUtils.parser.parseExpression(responseTemplateContent, ExpressionParserUtils.templateParserContext);
        String respJsonStr = expression.getValue(standardEvaluationContext, String.class);

        //fastjson 反序列化泛型
        CzcbResp<CzcbOa0005Resp> resp  =JSON.parseObject(respJsonStr, new com.alibaba.fastjson.TypeReference<CzcbResp<CzcbOa0005Resp>>(){});
        logger.info("czcb oa0005 resp:{}", JSON.toJSONString(resp));

        Date responseDate = new Date();
        //保存响应记录
        ResponseRecordWithBLOBs responseRecord = new ResponseRecordWithBLOBs();
        responseRecord.setAccessPoint(accessPoint);
        responseRecord.setResponseTemplateName(ResponseTemplateEnum.CZCB_OA0005.getName());
        responseRecord.setUniqueRequestId(czcbOa0005Req.getRequestId());
        responseRecord.setRequestContent(JSON.toJSONString(czcbOa0005Req));
        responseRecord.setResponseContent(respJsonStr);
        responseRecord.setRequestDate(requestDate);
        responseRecord.setResponseDate(responseDate);
        responseRecordService.insertSelective(responseRecord);
        return resp;
    }

    @Override
    public CzcbResp<CzcbOa0015Resp> oa0015(CzcbReq<CzcbOa0015Req> czcbOa0015Req, String accessPoint) {
        logger.info("czcb oa0015 req:{}", JSON.toJSONString(czcbOa0015Req));
        Date requestDate = new Date();
        //获取响应模板
        ResponseTemplate responseTemplate = responseTemplateService.selectByTemplateName(ResponseTemplateEnum.CZCB_OA0015.getName());
        try {
            //线程睡眠 构造超时场景
            Thread.sleep(responseTemplate.getSleepTime());
        } catch (InterruptedException e) {
            logger.error("Thread sleep error",e);
        }
        //获取响应模板内容
        String responseTemplateContent = responseTemplate.getResponseTemplateContent();
        logger.info("czcb oa0015 respTemplate:{}", responseTemplateContent);

        //将请求的对象注入到standardEvaluationContext中
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        //初始化注册一些方法到上下文
        FunctionRegister.registerFunctionToContext(standardEvaluationContext);
        //初始化注册一些变量到上下文
        standardEvaluationContext.setVariable("null",null);
        standardEvaluationContext.setVariable("req",czcbOa0015Req);

        //解析数据库中的模板，得到响应字符串
        Expression expression = ExpressionParserUtils.parser.parseExpression(responseTemplateContent, ExpressionParserUtils.templateParserContext);
        String respJsonStr = expression.getValue(standardEvaluationContext, String.class);

        //fastjson 反序列化泛型
        CzcbResp<CzcbOa0015Resp> resp  =JSON.parseObject(respJsonStr, new com.alibaba.fastjson.TypeReference<CzcbResp<CzcbOa0015Resp>>(){});
        logger.info("czcb oa0015 resp:{}", JSON.toJSONString(resp));

        Date responseDate = new Date();
        //保存响应记录
        ResponseRecordWithBLOBs responseRecord = new ResponseRecordWithBLOBs();
        responseRecord.setAccessPoint(accessPoint);
        responseRecord.setResponseTemplateName(ResponseTemplateEnum.CZCB_OA0015.getName());
        responseRecord.setUniqueRequestId(czcbOa0015Req.getRequestId());
        responseRecord.setRequestContent(JSON.toJSONString(czcbOa0015Req));
        responseRecord.setResponseContent(respJsonStr);
        responseRecord.setRequestDate(requestDate);
        responseRecord.setResponseDate(responseDate);
        responseRecordService.insertSelective(responseRecord);
        return resp;
    }

    @Override
    public CzcbResp<CzcbOd0001Resp> od0001(CzcbReq<CzcbOd0001Req> czcbOd0001Req, String accessPoint) {
        logger.info("czcb od0001 req:{}", JSON.toJSONString(czcbOd0001Req));
        Date requestDate = new Date();
        //获取响应模板
        ResponseTemplate responseTemplate = responseTemplateService.selectByTemplateName(ResponseTemplateEnum.CZCB_OD0001.getName());
        try {
            //线程睡眠 构造超时场景
            Thread.sleep(responseTemplate.getSleepTime());
        } catch (InterruptedException e) {
            logger.error("Thread sleep error",e);
        }
        //获取响应模板内容
        String responseTemplateContent = responseTemplate.getResponseTemplateContent();
        logger.info("czcb od0001 respTemplate:{}", responseTemplateContent);

        //将请求的对象注入到standardEvaluationContext中
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        //初始化注册一些方法到上下文
        FunctionRegister.registerFunctionToContext(standardEvaluationContext);
        //初始化注册一些变量到上下文
        standardEvaluationContext.setVariable("null",null);
        standardEvaluationContext.setVariable("req",czcbOd0001Req);

        //解析数据库中的模板，得到响应字符串
        Expression expression = ExpressionParserUtils.parser.parseExpression(responseTemplateContent, ExpressionParserUtils.templateParserContext);
        String respJsonStr = expression.getValue(standardEvaluationContext, String.class);

        //fastjson 反序列化泛型
        CzcbResp<CzcbOd0001Resp> resp  =JSON.parseObject(respJsonStr, new com.alibaba.fastjson.TypeReference<CzcbResp<CzcbOd0001Resp>>(){});
        logger.info("czcb od0001 resp:{}", JSON.toJSONString(resp));

        Date responseDate = new Date();
        //保存响应记录
        ResponseRecordWithBLOBs responseRecord = new ResponseRecordWithBLOBs();
        responseRecord.setAccessPoint(accessPoint);
        responseRecord.setResponseTemplateName(ResponseTemplateEnum.CZCB_OD0001.getName());
        responseRecord.setUniqueRequestId(czcbOd0001Req.getRequestId());
        responseRecord.setRequestContent(JSON.toJSONString(czcbOd0001Req));
        responseRecord.setResponseContent(respJsonStr);
        responseRecord.setRequestDate(requestDate);
        responseRecord.setResponseDate(responseDate);
        responseRecordService.insertSelective(responseRecord);
        return resp;
    }

    @Override
    public CzcbResp<CzcbOd0002Resp> od0002(CzcbReq<CzcbOd0002Req> czcbOd0002Req, String accessPoint) {
        logger.info("czcb od0002 req:{}", JSON.toJSONString(czcbOd0002Req));
        Date requestDate = new Date();
        //获取响应模板
        ResponseTemplate responseTemplate = responseTemplateService.selectByTemplateName(ResponseTemplateEnum.CZCB_OD0002.getName());
        try {
            //线程睡眠 构造超时场景
            Thread.sleep(responseTemplate.getSleepTime());
        } catch (InterruptedException e) {
            logger.error("Thread sleep error",e);
        }
        //获取响应模板内容
        String responseTemplateContent = responseTemplate.getResponseTemplateContent();
        logger.info("czcb od0002 respTemplate:{}", responseTemplateContent);

        //将请求的对象注入到standardEvaluationContext中
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        //初始化注册一些方法到上下文
        FunctionRegister.registerFunctionToContext(standardEvaluationContext);
        //初始化注册一些变量到上下文
        standardEvaluationContext.setVariable("null",null);
        standardEvaluationContext.setVariable("req",czcbOd0002Req);

        //解析数据库中的模板，得到响应字符串
        Expression expression = ExpressionParserUtils.parser.parseExpression(responseTemplateContent, ExpressionParserUtils.templateParserContext);
        String respJsonStr = expression.getValue(standardEvaluationContext, String.class);

        //fastjson 反序列化泛型
        CzcbResp<CzcbOd0002Resp> resp  =JSON.parseObject(respJsonStr, new com.alibaba.fastjson.TypeReference<CzcbResp<CzcbOd0002Resp>>(){});
        logger.info("czcb od0002 resp:{}", JSON.toJSONString(resp));

        Date responseDate = new Date();
        //保存响应记录
        ResponseRecordWithBLOBs responseRecord = new ResponseRecordWithBLOBs();
        responseRecord.setAccessPoint(accessPoint);
        responseRecord.setResponseTemplateName(ResponseTemplateEnum.CZCB_OD0002.getName());
        responseRecord.setUniqueRequestId(czcbOd0002Req.getRequestId());
        responseRecord.setRequestContent(JSON.toJSONString(czcbOd0002Req));
        responseRecord.setResponseContent(respJsonStr);
        responseRecord.setRequestDate(requestDate);
        responseRecord.setResponseDate(responseDate);
        responseRecordService.insertSelective(responseRecord);
        return resp;
    }

    @Override
    public CzcbResp<CzcbOd0004Resp> od0004(CzcbReq<CzcbOd0004Req> czcbOd0004Req, String accessPoint) {
        logger.info("czcb od0004 req:{}", JSON.toJSONString(czcbOd0004Req));
        Date requestDate = new Date();
        //获取响应模板
        ResponseTemplate responseTemplate = responseTemplateService.selectByTemplateName(ResponseTemplateEnum.CZCB_OD0004.getName());
        try {
            //线程睡眠 构造超时场景
            Thread.sleep(responseTemplate.getSleepTime());
        } catch (InterruptedException e) {
            logger.error("Thread sleep error",e);
        }
        //获取响应模板内容
        String responseTemplateContent = responseTemplate.getResponseTemplateContent();
        logger.info("czcb od0004 respTemplate:{}", responseTemplateContent);

        //将请求的对象注入到standardEvaluationContext中
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        //初始化注册一些方法到上下文
        FunctionRegister.registerFunctionToContext(standardEvaluationContext);
        //初始化注册一些变量到上下文
        standardEvaluationContext.setVariable("null",null);
        standardEvaluationContext.setVariable("req",czcbOd0004Req);

        //解析数据库中的模板，得到响应字符串
        Expression expression = ExpressionParserUtils.parser.parseExpression(responseTemplateContent, ExpressionParserUtils.templateParserContext);
        String respJsonStr = expression.getValue(standardEvaluationContext, String.class);

        //fastjson 反序列化泛型
        CzcbResp<CzcbOd0004Resp> resp  =JSON.parseObject(respJsonStr, new com.alibaba.fastjson.TypeReference<CzcbResp<CzcbOd0004Resp>>(){});
        logger.info("czcb od0004 resp:{}", JSON.toJSONString(resp));

        Date responseDate = new Date();
        //保存响应记录
        ResponseRecordWithBLOBs responseRecord = new ResponseRecordWithBLOBs();
        responseRecord.setAccessPoint(accessPoint);
        responseRecord.setResponseTemplateName(ResponseTemplateEnum.CZCB_OD0004.getName());
        responseRecord.setUniqueRequestId(czcbOd0004Req.getRequestId());
        responseRecord.setRequestContent(JSON.toJSONString(czcbOd0004Req));
        responseRecord.setResponseContent(respJsonStr);
        responseRecord.setRequestDate(requestDate);
        responseRecord.setResponseDate(responseDate);
        responseRecordService.insertSelective(responseRecord);
        return resp;
    }

    @Override
    public CzcbResp<CzcbOd0005Resp> od0005(CzcbReq<CzcbOd0005Req> czcbOd0005Req, String accessPoint) {
        logger.info("czcb od0005 req:{}", JSON.toJSONString(czcbOd0005Req));
        Date requestDate = new Date();
        //获取响应模板
        ResponseTemplate responseTemplate = responseTemplateService.selectByTemplateName(ResponseTemplateEnum.CZCB_OD0005.getName());
        try {
            //线程睡眠 构造超时场景
            Thread.sleep(responseTemplate.getSleepTime());
        } catch (InterruptedException e) {
            logger.error("Thread sleep error",e);
        }
        //获取响应模板内容
        String responseTemplateContent = responseTemplate.getResponseTemplateContent();
        logger.info("czcb od0005 respTemplate:{}", responseTemplateContent);

        //将请求的对象注入到standardEvaluationContext中
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        //初始化注册一些方法到上下文
        FunctionRegister.registerFunctionToContext(standardEvaluationContext);
        //初始化注册一些变量到上下文
        standardEvaluationContext.setVariable("null",null);
        standardEvaluationContext.setVariable("req",czcbOd0005Req);

        //解析数据库中的模板，得到响应字符串
        Expression expression = ExpressionParserUtils.parser.parseExpression(responseTemplateContent, ExpressionParserUtils.templateParserContext);
        String respJsonStr = expression.getValue(standardEvaluationContext, String.class);

        //fastjson 反序列化泛型
        CzcbResp<CzcbOd0005Resp> resp  =JSON.parseObject(respJsonStr, new com.alibaba.fastjson.TypeReference<CzcbResp<CzcbOd0005Resp>>(){});
        logger.info("czcb od0005 resp:{}", JSON.toJSONString(resp));

        Date responseDate = new Date();
        //保存响应记录
        ResponseRecordWithBLOBs responseRecord = new ResponseRecordWithBLOBs();
        responseRecord.setAccessPoint(accessPoint);
        responseRecord.setResponseTemplateName(ResponseTemplateEnum.CZCB_OD0005.getName());
        responseRecord.setUniqueRequestId(czcbOd0005Req.getRequestId());
        responseRecord.setRequestContent(JSON.toJSONString(czcbOd0005Req));
        responseRecord.setResponseContent(respJsonStr);
        responseRecord.setRequestDate(requestDate);
        responseRecord.setResponseDate(responseDate);
        responseRecordService.insertSelective(responseRecord);
        return resp;
    }

    @Override
    public CzcbResp<CzcbOd0003Resp> od0003(CzcbReq<CzcbOd0003Req> czcbOd0003Req, String accessPoint) {
        logger.info("czcb od0003 req:{}", JSON.toJSONString(czcbOd0003Req));
        Date requestDate = new Date();
        //获取响应模板
        ResponseTemplate responseTemplate = responseTemplateService.selectByTemplateName(ResponseTemplateEnum.CZCB_OD0003.getName());
        try {
            //线程睡眠 构造超时场景
            Thread.sleep(responseTemplate.getSleepTime());
        } catch (InterruptedException e) {
            logger.error("Thread sleep error",e);
        }
        //获取响应模板内容
        String responseTemplateContent = responseTemplate.getResponseTemplateContent();
        logger.info("czcb od0003 respTemplate:{}", responseTemplateContent);

        //将请求的对象注入到standardEvaluationContext中
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        //初始化注册一些方法到上下文
        FunctionRegister.registerFunctionToContext(standardEvaluationContext);
        //初始化注册一些变量到上下文
        standardEvaluationContext.setVariable("null",null);
        standardEvaluationContext.setVariable("req",czcbOd0003Req);

        //解析数据库中的模板，得到响应字符串
        Expression expression = ExpressionParserUtils.parser.parseExpression(responseTemplateContent, ExpressionParserUtils.templateParserContext);
        String respJsonStr = expression.getValue(standardEvaluationContext, String.class);

        //fastjson 反序列化泛型
        CzcbResp<CzcbOd0003Resp> resp  =JSON.parseObject(respJsonStr, new com.alibaba.fastjson.TypeReference<CzcbResp<CzcbOd0003Resp>>(){});
        logger.info("czcb od0003 resp:{}", JSON.toJSONString(resp));

        Date responseDate = new Date();
        //保存响应记录
        ResponseRecordWithBLOBs responseRecord = new ResponseRecordWithBLOBs();
        responseRecord.setAccessPoint(accessPoint);
        responseRecord.setResponseTemplateName(ResponseTemplateEnum.CZCB_OD0003.getName());
        responseRecord.setUniqueRequestId(czcbOd0003Req.getRequestId());
        responseRecord.setRequestContent(JSON.toJSONString(czcbOd0003Req));
        responseRecord.setResponseContent(respJsonStr);
        responseRecord.setRequestDate(requestDate);
        responseRecord.setResponseDate(responseDate);
        responseRecordService.insertSelective(responseRecord);
        return resp;
    }

    @Override
    public CzcbResp<CzcbOd0006Resp> od0006(CzcbReq<CzcbOd0006Req> czcbOd0006Req, String accessPoint) {
        logger.info("czcb od0006 req:{}", JSON.toJSONString(czcbOd0006Req));
        Date requestDate = new Date();
        //获取响应模板
        ResponseTemplate responseTemplate = responseTemplateService.selectByTemplateName(ResponseTemplateEnum.CZCB_OD0006.getName());
        try {
            //线程睡眠 构造超时场景
            Thread.sleep(responseTemplate.getSleepTime());
        } catch (InterruptedException e) {
            logger.error("Thread sleep error",e);
        }
        //获取响应模板内容
        String responseTemplateContent = responseTemplate.getResponseTemplateContent();
        logger.info("czcb od0006 respTemplate:{}", responseTemplateContent);

        //将请求的对象注入到standardEvaluationContext中
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        //初始化注册一些方法到上下文
        FunctionRegister.registerFunctionToContext(standardEvaluationContext);
        //初始化注册一些变量到上下文
        standardEvaluationContext.setVariable("null",null);
        standardEvaluationContext.setVariable("req",czcbOd0006Req);

        //解析数据库中的模板，得到响应字符串
        Expression expression = ExpressionParserUtils.parser.parseExpression(responseTemplateContent, ExpressionParserUtils.templateParserContext);
        String respJsonStr = expression.getValue(standardEvaluationContext, String.class);

        //fastjson 反序列化泛型
        CzcbResp<CzcbOd0006Resp> resp  =JSON.parseObject(respJsonStr, new com.alibaba.fastjson.TypeReference<CzcbResp<CzcbOd0006Resp>>(){});
        logger.info("czcb od0006 resp:{}", JSON.toJSONString(resp));

        Date responseDate = new Date();
        //保存响应记录
        ResponseRecordWithBLOBs responseRecord = new ResponseRecordWithBLOBs();
        responseRecord.setAccessPoint(accessPoint);
        responseRecord.setResponseTemplateName(ResponseTemplateEnum.CZCB_OD0006.getName());
        responseRecord.setUniqueRequestId(czcbOd0006Req.getRequestId());
        responseRecord.setRequestContent(JSON.toJSONString(czcbOd0006Req));
        responseRecord.setResponseContent(respJsonStr);
        responseRecord.setRequestDate(requestDate);
        responseRecord.setResponseDate(responseDate);
        responseRecordService.insertSelective(responseRecord);
        return resp;
    }

    @Override
    public CzcbResp<CzcbOd0008Resp> od0008(CzcbReq<CzcbOd0008Req> czcbOd0008Req, String accessPoint) {
        logger.info("czcb od0008 req:{}", JSON.toJSONString(czcbOd0008Req));
        Date requestDate = new Date();
        //获取响应模板
        ResponseTemplate responseTemplate = responseTemplateService.selectByTemplateName(ResponseTemplateEnum.CZCB_OD0008.getName());
        try {
            //线程睡眠 构造超时场景
            Thread.sleep(responseTemplate.getSleepTime());
        } catch (InterruptedException e) {
            logger.error("Thread sleep error",e);
        }
        //获取响应模板内容
        String responseTemplateContent = responseTemplate.getResponseTemplateContent();
        logger.info("czcb od0008 respTemplate:{}", responseTemplateContent);

        //将请求的对象注入到standardEvaluationContext中
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        //初始化注册一些方法到上下文
        FunctionRegister.registerFunctionToContext(standardEvaluationContext);
        //初始化注册一些变量到上下文
        standardEvaluationContext.setVariable("null",null);
        standardEvaluationContext.setVariable("req",czcbOd0008Req);

        //解析数据库中的模板，得到响应字符串
        Expression expression = ExpressionParserUtils.parser.parseExpression(responseTemplateContent, ExpressionParserUtils.templateParserContext);
        String respJsonStr = expression.getValue(standardEvaluationContext, String.class);

        //fastjson 反序列化泛型
        CzcbResp<CzcbOd0008Resp> resp  =JSON.parseObject(respJsonStr, new com.alibaba.fastjson.TypeReference<CzcbResp<CzcbOd0008Resp>>(){});
        logger.info("czcb od0008 resp:{}", JSON.toJSONString(resp));

        Date responseDate = new Date();
        //保存响应记录
        ResponseRecordWithBLOBs responseRecord = new ResponseRecordWithBLOBs();
        responseRecord.setAccessPoint(accessPoint);
        responseRecord.setResponseTemplateName(ResponseTemplateEnum.CZCB_OD0008.getName());
        responseRecord.setUniqueRequestId(czcbOd0008Req.getRequestId());
        responseRecord.setRequestContent(JSON.toJSONString(czcbOd0008Req));
        responseRecord.setResponseContent(respJsonStr);
        responseRecord.setRequestDate(requestDate);
        responseRecord.setResponseDate(responseDate);
        responseRecordService.insertSelective(responseRecord);
        return resp;
    }

    @Override
    public CzcbResp<CzcbOd0009Resp> od0009(CzcbReq<CzcbOd0009Req> czcbOd0009Req, String accessPoint) {
        logger.info("czcb od0009 req:{}", JSON.toJSONString(czcbOd0009Req));
        Date requestDate = new Date();
        //获取响应模板
        ResponseTemplate responseTemplate = responseTemplateService.selectByTemplateName(ResponseTemplateEnum.CZCB_OD0009.getName());
        try {
            //线程睡眠 构造超时场景
            Thread.sleep(responseTemplate.getSleepTime());
        } catch (InterruptedException e) {
            logger.error("Thread sleep error",e);
        }
        //获取响应模板内容
        String responseTemplateContent = responseTemplate.getResponseTemplateContent();
        logger.info("czcb od0009 respTemplate:{}", responseTemplateContent);

        //将请求的对象注入到standardEvaluationContext中
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        //初始化注册一些方法到上下文
        FunctionRegister.registerFunctionToContext(standardEvaluationContext);
        //初始化注册一些变量到上下文
        standardEvaluationContext.setVariable("null",null);
        standardEvaluationContext.setVariable("req",czcbOd0009Req);

        //解析数据库中的模板，得到响应字符串
        Expression expression = ExpressionParserUtils.parser.parseExpression(responseTemplateContent, ExpressionParserUtils.templateParserContext);
        String respJsonStr = expression.getValue(standardEvaluationContext, String.class);

        //fastjson 反序列化泛型
        CzcbResp<CzcbOd0009Resp> resp  =JSON.parseObject(respJsonStr, new com.alibaba.fastjson.TypeReference<CzcbResp<CzcbOd0009Resp>>(){});
        logger.info("czcb od0009 resp:{}", JSON.toJSONString(resp));

        Date responseDate = new Date();
        //保存响应记录
        ResponseRecordWithBLOBs responseRecord = new ResponseRecordWithBLOBs();
        responseRecord.setAccessPoint(accessPoint);
        responseRecord.setResponseTemplateName(ResponseTemplateEnum.CZCB_OD0009.getName());
        responseRecord.setUniqueRequestId(czcbOd0009Req.getRequestId());
        responseRecord.setRequestContent(JSON.toJSONString(czcbOd0009Req));
        responseRecord.setResponseContent(respJsonStr);
        responseRecord.setRequestDate(requestDate);
        responseRecord.setResponseDate(responseDate);
        responseRecordService.insertSelective(responseRecord);
        return resp;
    }


}
