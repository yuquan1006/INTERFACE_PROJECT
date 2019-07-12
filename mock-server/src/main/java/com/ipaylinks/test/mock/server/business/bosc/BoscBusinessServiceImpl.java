package com.ipaylinks.test.mock.server.business.bosc;

import com.alibaba.fastjson.JSON;
import com.ipaylinks.test.mock.server.api.ResponseTemplateEnum;
import com.ipaylinks.test.mock.server.api.bosc.bo.req.*;
import com.ipaylinks.test.mock.server.api.bosc.bo.resp.*;
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

import java.util.Date;

@Service
public class BoscBusinessServiceImpl implements BoscBusinessService {
    private Logger logger = LoggerFactory.getLogger(BoscBusinessServiceImpl.class);
    @Autowired
    private ResponseTemplateService responseTemplateService;
    @Autowired
    private ResponseRecordService responseRecordService;

    /**
     * 子账户开户
     *
     * @param reqDocument BoscReqDocument<IBPS_OPENACCT_000001ReqBody>
     * @return BoscRespDocument<IBPS_OPENACCT_000001RespBody>
     */
    @Override
    public BoscRespDocument<IBPS_OPENACCT_000001RespBody> openacct000001(BoscReqDocument<IBPS_OPENACCT_000001ReqBody> reqDocument, String accessPoint) {
        logger.info("bosc openacct000001 req:{}", JSON.toJSONString(reqDocument));
        Date requestDate = new Date();
        //获取响应模板
        ResponseTemplate responseTemplate = responseTemplateService.selectByTemplateName(ResponseTemplateEnum.BOSC_OPENACCT000001.getName());
        try {
            //线程睡眠 构造超时场景
            Thread.sleep(responseTemplate.getSleepTime());
        } catch (InterruptedException e) {
            logger.error("Thread sleep error",e);
        }
        //获取响应模板内容
        String responseTemplateContent = responseTemplate.getResponseTemplateContent();
        logger.info("bosc openacct000001 respTemplate:{}", responseTemplateContent);

        //将请求的对象注入到standardEvaluationContext中
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        //初始化注册一些方法到上下文
        FunctionRegister.registerFunctionToContext(standardEvaluationContext);
        //初始化注册一些变量到上下文
        standardEvaluationContext.setVariable("null",null);
        standardEvaluationContext.setVariable("req",reqDocument);
        //解析数据库中的模板，得到响应字符串
        Expression expression = ExpressionParserUtils.parser.parseExpression(responseTemplateContent, ExpressionParserUtils.templateParserContext);
        String respJsonStr = expression.getValue(standardEvaluationContext, String.class);

        //fastjson 反序列化泛型
        BoscRespDocument<IBPS_OPENACCT_000001RespBody> resp  =JSON.parseObject(respJsonStr, new com.alibaba.fastjson.TypeReference<BoscRespDocument<IBPS_OPENACCT_000001RespBody>>(){});
        logger.info("bosc openacct000001 resp:{}", JSON.toJSONString(resp));

        Date responseDate = new Date();
        //保存响应记录
        ResponseRecordWithBLOBs responseRecord = new ResponseRecordWithBLOBs();
        responseRecord.setAccessPoint(accessPoint);
        responseRecord.setResponseTemplateName(ResponseTemplateEnum.BOSC_OPENACCT000001.getName());
        responseRecord.setUniqueRequestId(reqDocument.getHead().getHeadBusinessId());
        responseRecord.setRequestContent(JSON.toJSONString(reqDocument));
        responseRecord.setResponseContent(respJsonStr);
        responseRecord.setRequestDate(requestDate);
        responseRecord.setResponseDate(responseDate);
        responseRecordService.insertSelective(responseRecord);
        return resp;
    }

    /**
     * 账户信息修改
     *
     * @param reqDocument BoscReqDocument<IBPS_OPENACCT_000002ReqBody>
     * @return BoscRespDocument<IBPS_OPENACCT_000002RespBody>
     */
    @Override
    public BoscRespDocument<IBPS_OPENACCT_000002RespBody> openacct000002(BoscReqDocument<IBPS_OPENACCT_000002ReqBody> reqDocument, String accessPoint) {
        logger.info("bosc openacct000002 req:{}", JSON.toJSONString(reqDocument));
        Date requestDate = new Date();
        //获取响应模板
        ResponseTemplate responseTemplate = responseTemplateService.selectByTemplateName(ResponseTemplateEnum.BOSC_OPENACCT000002.getName());
        try {
            //线程睡眠 构造超时场景
            Thread.sleep(responseTemplate.getSleepTime());
        } catch (InterruptedException e) {
            logger.error("Thread sleep error",e);
        }
        //获取响应模板内容
        String responseTemplateContent = responseTemplate.getResponseTemplateContent();
        logger.info("bosc openacct000002 respTemplate:{}", responseTemplateContent);

        //将请求的对象注入到standardEvaluationContext中
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        //初始化注册一些方法到上下文
        FunctionRegister.registerFunctionToContext(standardEvaluationContext);
        //初始化注册一些变量到上下文
        standardEvaluationContext.setVariable("null",null);
        standardEvaluationContext.setVariable("req",reqDocument);
        //解析数据库中的模板，得到响应字符串
        Expression expression = ExpressionParserUtils.parser.parseExpression(responseTemplateContent, ExpressionParserUtils.templateParserContext);
        String respJsonStr = expression.getValue(standardEvaluationContext, String.class);

        //fastjson 反序列化泛型
        BoscRespDocument<IBPS_OPENACCT_000002RespBody> resp  =JSON.parseObject(respJsonStr, new com.alibaba.fastjson.TypeReference<BoscRespDocument<IBPS_OPENACCT_000002RespBody>>(){});
        logger.info("bosc openacct000002 resp:{}", JSON.toJSONString(resp));

        Date responseDate = new Date();
        //保存响应记录
        ResponseRecordWithBLOBs responseRecord = new ResponseRecordWithBLOBs();
        responseRecord.setAccessPoint(accessPoint);
        responseRecord.setResponseTemplateName(ResponseTemplateEnum.BOSC_OPENACCT000002.getName());
        responseRecord.setUniqueRequestId(reqDocument.getHead().getHeadBusinessId());
        responseRecord.setRequestContent(JSON.toJSONString(reqDocument));
        responseRecord.setResponseContent(respJsonStr);
        responseRecord.setRequestDate(requestDate);
        responseRecord.setResponseDate(responseDate);
        responseRecordService.insertSelective(responseRecord);
        return resp;
    }

    /**
     * 账户信息查询
     *
     * @param reqDocument BoscReqDocument<IBPS_OPENACCT_000004ReqBody>
     * @return BoscRespDocument<IBPS_OPENACCT_000004RespBody>
     */
    @Override
    public BoscRespDocument<IBPS_OPENACCT_000004RespBody> openacct000004(BoscReqDocument<IBPS_OPENACCT_000004ReqBody> reqDocument, String accessPoint) {
        logger.info("bosc openacct000004 req:{}", JSON.toJSONString(reqDocument));
        Date requestDate = new Date();
        //获取响应模板
        ResponseTemplate responseTemplate = responseTemplateService.selectByTemplateName(ResponseTemplateEnum.BOSC_OPENACCT000004.getName());
        try {
            //线程睡眠 构造超时场景
            Thread.sleep(responseTemplate.getSleepTime());
        } catch (InterruptedException e) {
            logger.error("Thread sleep error",e);
        }
        //获取响应模板内容
        String responseTemplateContent = responseTemplate.getResponseTemplateContent();
        logger.info("bosc openacct000004 respTemplate:{}", responseTemplateContent);

        //将请求的对象注入到standardEvaluationContext中
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        //初始化注册一些方法到上下文
        FunctionRegister.registerFunctionToContext(standardEvaluationContext);
        //初始化注册一些变量到上下文
        standardEvaluationContext.setVariable("null",null);
        standardEvaluationContext.setVariable("req",reqDocument);
        //解析数据库中的模板，得到响应字符串
        Expression expression = ExpressionParserUtils.parser.parseExpression(responseTemplateContent, ExpressionParserUtils.templateParserContext);
        String respJsonStr = expression.getValue(standardEvaluationContext, String.class);

        //fastjson 反序列化泛型
        BoscRespDocument<IBPS_OPENACCT_000004RespBody> resp  =JSON.parseObject(respJsonStr, new com.alibaba.fastjson.TypeReference<BoscRespDocument<IBPS_OPENACCT_000004RespBody>>(){});
        logger.info("bosc openacct000004 resp:{}", JSON.toJSONString(resp));

        Date responseDate = new Date();
        //保存响应记录
        ResponseRecordWithBLOBs responseRecord = new ResponseRecordWithBLOBs();
        responseRecord.setAccessPoint(accessPoint);
        responseRecord.setResponseTemplateName(ResponseTemplateEnum.BOSC_OPENACCT000004.getName());
        responseRecord.setUniqueRequestId(reqDocument.getHead().getHeadBusinessId());
        responseRecord.setRequestContent(JSON.toJSONString(reqDocument));
        responseRecord.setResponseContent(respJsonStr);
        responseRecord.setRequestDate(requestDate);
        responseRecord.setResponseDate(responseDate);
        responseRecordService.insertSelective(responseRecord);
        return resp;
    }

    /**
     * 账户信息签约
     *
     * @param reqDocument BoscReqDocument<IBPS_IBS_140002ReqBody>
     * @return BoscRespDocument<IBPS_IBS_140002RespBody>
     */
    @Override
    public BoscRespDocument<IBPS_IBS_140002RespBody> ibs140002(BoscReqDocument<IBPS_IBS_140002ReqBody> reqDocument, String accessPoint) {
        logger.info("bosc ibs140002 req:{}", JSON.toJSONString(reqDocument));
        Date requestDate = new Date();
        //获取响应模板
        ResponseTemplate responseTemplate = responseTemplateService.selectByTemplateName(ResponseTemplateEnum.BOSC_IBS140002.getName());
        try {
            //线程睡眠 构造超时场景
            Thread.sleep(responseTemplate.getSleepTime());
        } catch (InterruptedException e) {
            logger.error("Thread sleep error",e);
        }
        //获取响应模板内容
        String responseTemplateContent = responseTemplate.getResponseTemplateContent();
        logger.info("bosc ibs140002 respTemplate:{}", responseTemplateContent);

        //将请求的对象注入到standardEvaluationContext中
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        //初始化注册一些方法到上下文
        FunctionRegister.registerFunctionToContext(standardEvaluationContext);
        //初始化注册一些变量到上下文
        standardEvaluationContext.setVariable("null",null);
        standardEvaluationContext.setVariable("req",reqDocument);
        //解析数据库中的模板，得到响应字符串
        Expression expression = ExpressionParserUtils.parser.parseExpression(responseTemplateContent, ExpressionParserUtils.templateParserContext);
        String respJsonStr = expression.getValue(standardEvaluationContext, String.class);

        //fastjson 反序列化泛型
        BoscRespDocument<IBPS_IBS_140002RespBody> resp  =JSON.parseObject(respJsonStr, new com.alibaba.fastjson.TypeReference<BoscRespDocument<IBPS_IBS_140002RespBody>>(){});
        logger.info("bosc ibs140002 resp:{}", JSON.toJSONString(resp));

        Date responseDate = new Date();
        //保存响应记录
        ResponseRecordWithBLOBs responseRecord = new ResponseRecordWithBLOBs();
        responseRecord.setAccessPoint(accessPoint);
        responseRecord.setResponseTemplateName(ResponseTemplateEnum.BOSC_IBS140002.getName());
        responseRecord.setUniqueRequestId(reqDocument.getHead().getHeadBusinessId());
        responseRecord.setRequestContent(JSON.toJSONString(reqDocument));
        responseRecord.setResponseContent(respJsonStr);
        responseRecord.setRequestDate(requestDate);
        responseRecord.setResponseDate(responseDate);
        responseRecordService.insertSelective(responseRecord);
        return resp;
    }

    /**
     * 代付交易状态查询
     *
     * @param reqDocument BoscReqDocument<IBPS_IBS_110018ReqBody>
     * @return BoscRespDocument<IBPS_IBS_110018RespBody>
     */
    @Override
    public BoscRespDocument<IBPS_IBS_110018RespBody> ibs110018(BoscReqDocument<IBPS_IBS_110018ReqBody> reqDocument, String accessPoint) {
        logger.info("bosc ibs110018 req:{}", JSON.toJSONString(reqDocument));
        Date requestDate = new Date();
        //获取响应模板
        ResponseTemplate responseTemplate = responseTemplateService.selectByTemplateName(ResponseTemplateEnum.BOSC_IBS110018.getName());
        try {
            //线程睡眠 构造超时场景
            Thread.sleep(responseTemplate.getSleepTime());
        } catch (InterruptedException e) {
            logger.error("Thread sleep error",e);
        }
        //获取响应模板内容
        String responseTemplateContent = responseTemplate.getResponseTemplateContent();
        logger.info("bosc ibs110018 respTemplate:{}", responseTemplateContent);

        //将请求的对象注入到standardEvaluationContext中
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        //初始化注册一些方法到上下文
        FunctionRegister.registerFunctionToContext(standardEvaluationContext);
        //初始化注册一些变量到上下文
        standardEvaluationContext.setVariable("null",null);
        standardEvaluationContext.setVariable("req",reqDocument);
        //解析数据库中的模板，得到响应字符串
        Expression expression = ExpressionParserUtils.parser.parseExpression(responseTemplateContent, ExpressionParserUtils.templateParserContext);
        String respJsonStr = expression.getValue(standardEvaluationContext, String.class);

        //fastjson 反序列化泛型
        BoscRespDocument<IBPS_IBS_110018RespBody> resp  =JSON.parseObject(respJsonStr, new com.alibaba.fastjson.TypeReference<BoscRespDocument<IBPS_IBS_110018RespBody>>(){});
        logger.info("bosc ibs110018 resp:{}", JSON.toJSONString(resp));

        Date responseDate = new Date();
        //保存响应记录
        ResponseRecordWithBLOBs responseRecord = new ResponseRecordWithBLOBs();
        responseRecord.setAccessPoint(accessPoint);
        responseRecord.setResponseTemplateName(ResponseTemplateEnum.BOSC_IBS110018.getName());
        responseRecord.setUniqueRequestId(reqDocument.getHead().getHeadBusinessId());
        responseRecord.setRequestContent(JSON.toJSONString(reqDocument));
        responseRecord.setResponseContent(respJsonStr);
        responseRecord.setRequestDate(requestDate);
        responseRecord.setResponseDate(responseDate);
        responseRecordService.insertSelective(responseRecord);
        return resp;
    }
}
