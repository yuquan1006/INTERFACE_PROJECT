package com.ipaylinks.test.mock.server.business.ae;

import com.alibaba.fastjson.JSON;
import com.ipaylinks.test.mock.server.api.ResponseTemplateEnum;
import com.ipaylinks.test.mock.server.dao.model.ResponseRecordWithBLOBs;
import com.ipaylinks.test.mock.server.dao.model.ResponseTemplate;
import com.ipaylinks.test.mock.server.service.ResponseRecordService;
import com.ipaylinks.test.mock.server.service.ResponseTemplateService;
import com.ipaylinks.test.mock.server.util.function.ExpressionParserUtils;
import com.ipaylinks.test.mock.server.util.function.FunctionRegister;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jpos.iso.ISOMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.Map;

@Service
public class AeBusinessServiceImpl implements AeBusinessService{
    private Logger logger = LoggerFactory.getLogger(AeBusinessServiceImpl.class);
    @Autowired
    private ResponseTemplateService responseTemplateService;
    @Autowired
    private ResponseRecordService responseRecordService;

    @Override
    public ISOMsg authorization(ISOMsg reqISOMsg, String accessPoint) {
        Date requestDate = new Date();
        //获取响应模板
        ResponseTemplate responseTemplate = responseTemplateService.selectByTemplateName(ResponseTemplateEnum.AE_AUTHORIZATION.getName());
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
        standardEvaluationContext.setVariable("req",reqISOMsg);
        //解析数据库中的模板，得到响应字符串
        Expression expression = ExpressionParserUtils.parser.parseExpression(responseTemplateContent, ExpressionParserUtils.templateParserContext);
        String respJsonStr = expression.getValue(standardEvaluationContext, String.class);

        //fastjson 反序列化
        Map<String, String> respMap  =JSON.parseObject(respJsonStr, Map.class);
        ISOMsg respISOMsg = new ISOMsg();
        if (respMap != null) {
            for (Map.Entry<String, String> entry : respMap.entrySet()){
                respISOMsg.set(entry.getKey(), entry.getValue());
            }
        }
        String field4 = StringUtils.leftPad(respISOMsg.getString(4),12, "0");
        respISOMsg.set(4, field4);
        String field42 = StringUtils.leftPad(respISOMsg.getString(42),15, " ");
        respISOMsg.set(42, field42);

        Date responseDate = new Date();
        //保存响应记录
        ResponseRecordWithBLOBs responseRecord = new ResponseRecordWithBLOBs();
        responseRecord.setAccessPoint(accessPoint);
        responseRecord.setResponseTemplateName(ResponseTemplateEnum.AE_AUTHORIZATION.getName());
        responseRecord.setUniqueRequestId(reqISOMsg.getString(11));//11域：6位系统跟踪码;也可以用37域：12位参考号

        ByteArrayOutputStream reqStream = null;
        PrintStream reqPrintStream = null;
        ByteArrayOutputStream respStream = null;
        PrintStream respPrintStream = null;
        try {
            reqStream = new ByteArrayOutputStream();
            reqPrintStream = new PrintStream(reqStream);
            reqISOMsg.dump(reqPrintStream, "");
            logger.info("reqISOMsg:{}",reqStream);

            respStream = new ByteArrayOutputStream();
            respPrintStream = new PrintStream(reqStream);
            respISOMsg.dump(respPrintStream, "");
            logger.info("respISOMsg:{}",respStream);

            responseRecord.setRequestContent(reqStream.toString());
            responseRecord.setResponseContent(respStream.toString());
        } finally {
            IOUtils.closeQuietly(reqStream);
            IOUtils.closeQuietly(reqPrintStream);
            IOUtils.closeQuietly(respStream);
            IOUtils.closeQuietly(respPrintStream);
        }

        responseRecord.setRequestDate(requestDate);
        responseRecord.setResponseDate(responseDate);
        responseRecordService.insertSelective(responseRecord);
        return respISOMsg;
    }

    @Override
    public ISOMsg reversalAdvice(ISOMsg reqISOMsg, String accessPoint) {
        Date requestDate = new Date();
        //获取响应模板
        ResponseTemplate responseTemplate = responseTemplateService.selectByTemplateName(ResponseTemplateEnum.AE_REVERSAL_ADVICE.getName());
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
        standardEvaluationContext.setVariable("req",reqISOMsg);
        //解析数据库中的模板，得到响应字符串
        Expression expression = ExpressionParserUtils.parser.parseExpression(responseTemplateContent, ExpressionParserUtils.templateParserContext);
        String respJsonStr = expression.getValue(standardEvaluationContext, String.class);

        //fastjson 反序列化
        Map<String, String> respMap  =JSON.parseObject(respJsonStr, Map.class);
        ISOMsg respISOMsg = new ISOMsg();
        if (respMap != null) {
            for (Map.Entry<String, String> entry : respMap.entrySet()){
                respISOMsg.set(entry.getKey(), entry.getValue());
            }
        }
        String field4 = StringUtils.leftPad(respISOMsg.getString(4),12, "0");
        respISOMsg.set(4, field4);
        String field42 = StringUtils.leftPad(respISOMsg.getString(42),15, " ");
        respISOMsg.set(42, field42);

        Date responseDate = new Date();
        //保存响应记录
        ResponseRecordWithBLOBs responseRecord = new ResponseRecordWithBLOBs();
        responseRecord.setAccessPoint(accessPoint);
        responseRecord.setResponseTemplateName(ResponseTemplateEnum.AE_REVERSAL_ADVICE.getName());
        responseRecord.setUniqueRequestId(reqISOMsg.getString(11));//11域：6位系统跟踪码;也可以用37域：12位参考号

        ByteArrayOutputStream reqStream = null;
        PrintStream reqPrintStream = null;
        ByteArrayOutputStream respStream = null;
        PrintStream respPrintStream = null;
        try {
            reqStream = new ByteArrayOutputStream();
            reqPrintStream = new PrintStream(reqStream);
            reqISOMsg.dump(reqPrintStream, "");
            logger.info("reqISOMsg:{}",reqStream);

            respStream = new ByteArrayOutputStream();
            respPrintStream = new PrintStream(reqStream);
            respISOMsg.dump(respPrintStream, "");
            logger.info("respISOMsg:{}",respStream);

            responseRecord.setRequestContent(reqStream.toString());
            responseRecord.setResponseContent(respStream.toString());
        } finally {
            IOUtils.closeQuietly(reqStream);
            IOUtils.closeQuietly(reqPrintStream);
            IOUtils.closeQuietly(respStream);
            IOUtils.closeQuietly(respPrintStream);
        }

        responseRecord.setRequestDate(requestDate);
        responseRecord.setResponseDate(responseDate);
        responseRecordService.insertSelective(responseRecord);
        return respISOMsg;
    }
}
