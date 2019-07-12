package com.ipaylinks.test.mock.server.api.czcb;

import com.alibaba.fastjson.JSON;
import com.ipaylinks.test.mock.server.MockServerApplication;
import com.ipaylinks.test.mock.server.service.czcb.CzcbSecurityService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testng.Reporter;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SpringBootTest(classes = MockServerApplication.class)
public class CzcbApiTest extends AbstractTestNGSpringContextTests {
    private Logger logger = LoggerFactory.getLogger(CzcbApiTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Resource(name = "clientCzcbSecurityService")
    private CzcbSecurityService czcbSecurityService;

    private Map<String, Object> rootMap = new HashMap<>();
    private Map<String, Object> publicData = new HashMap<>();

    /*币种代码 说明
    1 人民币 12 英镑 13 港币 14 美元 27 日元 29 澳大利亚元 38 欧元
    */

    @BeforeTest
    public void setUp() throws Exception {
        publicData.put("ip", "116.247.106.162");
        publicData.put("osType", System.getProperty("os.name"));
        publicData.put("uuid", UUID.randomUUID().toString());
        publicData.put("imei", UUID.randomUUID().toString());
        publicData.put("phoneType", "HUAWEI Mate 10");
        //我的手机mac地址
//            publicData.put("mac", "38:37:8b:c2:3d:2e");
        //我的电脑mac地址
        publicData.put("mac", "74-C6-3B-61-39-4D");
        publicData.put("address", "上海市浦东新区");

        rootMap.put("channelId", "ipl");
        rootMap.put("timestamp", System.currentTimeMillis() + ""); // 避免科学计数法
        rootMap.put("signature", "signature");
    }

    private void post(Map rootMap) throws Exception {
        String unencryptedReqStr = JSON.toJSONString(rootMap);
        logger.info("czcb unencryptedReqStr:{}", unencryptedReqStr);
        Reporter.log("czcb unencryptedReqStr："+unencryptedReqStr);
        //加密后加签
        String reqStr = czcbSecurityService.sign(unencryptedReqStr);

//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

//        String url = "http://127.0.0.1:8088/mock-server/czcb";
////        String url = "https://124.160.111.105:4430/obankApi/bankPlus";
//        logger.info("czcb reqStr:{}", reqStr);
//        Reporter.log("czcb reqStr："+reqStr);
//        ResponseEntity<String> resp = RestTemplateConfig.getRestTemplate().exchange(url,
//                HttpMethod.POST, new HttpEntity<>(reqStr, httpHeaders), String.class);
//        String respStr = resp.getBody();
//        logger.info("czcb respStr:{}", respStr);
//        Reporter.log("czcb respStr："+respStr);

        String url = "/czcb";
//        logger.info("fuiou transfer req:{}", reqMap);
        MockHttpServletResponse mockResp = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
//                .headers(httpHeaders)
                .content(reqStr)
        ).andReturn().getResponse();
        String respStr = mockResp.getContentAsString();
//        logger.info("fuiou transfer resp:{}", respStr);

        //验签
        if(!czcbSecurityService.verify(respStr)){
            logger.info("验签失败");
//            throw new Exception("验签失败");
        }
        //解密
        String decryptedRespStr = czcbSecurityService.decrypt(respStr);
        logger.info("czcb decryptedRespStr:{}", decryptedRespStr);
        Reporter.log("czcb decryptedRespStr："+decryptedRespStr);
    }

    // 内部帐子户开户接口
    @Test
    public void od0007() throws Exception {
        Map<String, Object> bizData = new HashMap<>();
        bizData.put("publicData", publicData);
        //合作商用户唯一ID
        bizData.put("openId", "17621776588");
        //姓名
        bizData.put("customerName", "许兴国");
        //身份证
        bizData.put("idNo", "42082219880108685X");
        //银行卡号
        bizData.put("bindCard", "6217850800013939226");
        //银行预留手机号
        bizData.put("bindTel", "17621776588");

        rootMap.put("requestId", UUID.randomUUID().toString());
        rootMap.put("interfaceId", "od0007");
        rootMap.put("bizData", bizData);

        post(rootMap);
    }

    // 内部帐子户信息查询
    @Test
    public void oa0003() throws Exception {
        Map<String, Object> bizData = new HashMap<>();
        bizData.put("publicData", publicData);
        //合作商用户唯一ID
        bizData.put("openId", "17621776588");
        //姓名
        bizData.put("customerName", "许兴国");
        //身份证
        bizData.put("idNo", "42082219880108685X");

        rootMap.put("requestId", UUID.randomUUID().toString());
        rootMap.put("interfaceId", "oa0003");
        rootMap.put("bizData", bizData);

        post(rootMap);
    }

    // oa0005 身份证正反面影像传输接口（暂不使用）
    @Test
    public void oa0005() throws Exception {
        Map<String, Object> bizData = new HashMap<>();
        bizData.put("publicData", publicData);

        //合作商用户唯一ID
        bizData.put("openId", "10000007381");
        //内部帐子户
        bizData.put("bankUserId", "1560201403990018003519");
        //姓名
        bizData.put("customerName", "杨秋华");
        //身份证
        bizData.put("idNo", "32012519930807171X");
        //身份证正面图像
        bizData.put("frontImg", getBase64ImageStr("F:\\版本文档\\平台收款\\测试资料\\身份证正面.jpg"));
        //身份证反面图像
        bizData.put("backImg", getBase64ImageStr("F:\\版本文档\\平台收款\\测试资料\\身份证反面.jpg"));

        rootMap.put("requestId", UUID.randomUUID().toString());
        rootMap.put("interfaceId", "oa0005");
        rootMap.put("bizData", bizData);

        post(rootMap);
    }

    private String getBase64ImageStr(String imagePath){
        String result = null;
        try {
            InputStream inputStream = new FileInputStream(imagePath);
            byte[] imageBytes = IOUtils.toByteArray(inputStream);
            result = Base64.encodeBase64String(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    // 子账号换绑卡
    @Test
    public void oa0015() throws Exception {
        Map<String, Object> bizData = new HashMap<>();
        bizData.put("publicData", publicData);
        //合作商用户唯一ID
        bizData.put("openId", "10000007381");
        //内部帐子户
        bizData.put("bankUserId", "1560201403990018003519");
        //姓名
        bizData.put("customerName", "杨秋华");
        //身份证
        bizData.put("idNo", "32012519930807171X");
        //新卡银行卡号
        bizData.put("bindCard", "6222620110033651440");
        //新卡银行预留手机号
        bizData.put("bindTel", "15261471747");
        //验证码（不必填）
//        bizData.put("activeCode", "");

        rootMap.put("requestId", UUID.randomUUID().toString());
        rootMap.put("interfaceId", "oa0015");
        rootMap.put("bizData", bizData);

        post(rootMap);
    }

    // 账户余额查询
    @Test
    public void od0001() throws Exception {
        Map<String, Object> bizData = new HashMap<>();
        bizData.put("publicData", publicData);
        //合作商用户唯一ID
        bizData.put("openId", "10000007381");
        //账号（不必填）
        //GBP NRA56504122010500000076
        //USD NRA56504142010500000205
        //JPY   NRA56504272010500000066
        //AUD NRA56504292010500000054
        //EUR  NRA56504382010500000170
        //CNY  5650401403990006
        bizData.put("acctNo", "5650401403990006");

        rootMap.put("requestId", UUID.randomUUID().toString());
        rootMap.put("interfaceId", "od0001");
        rootMap.put("bizData", bizData);

        post(rootMap);
    }

    // 账户明细
    @Test
    public void od0002() throws Exception {
        Map<String, Object> bizData = new HashMap<>();
        bizData.put("publicData", publicData);
        //合作商用户唯一ID
        bizData.put("openId", "10000007381");
        //账号（不必填）
        //GBP NRA56504122010500000076
        //USD NRA56504142010500000205
        //JPY   NRA56504272010500000066
        //AUD NRA56504292010500000054
        //EUR  NRA56504382010500000170
        //CNY  5650401403990006
        bizData.put("acctNo", "5650401403990006");

        //第几页
        bizData.put("pageIndex", "1");
        //每页显示条数
        bizData.put("pageCount", "100");
        //查询开始时间 yyyy-MM-dd
        bizData.put("beginDate", "2019-01-24");
        //查询结束时间 yyyy-MM-dd
        bizData.put("endDate", "2019-04-24");

        rootMap.put("requestId", UUID.randomUUID().toString());
        rootMap.put("interfaceId", "od0002");
        rootMap.put("bizData", bizData);

        post(rootMap);
    }

    // 合作机构账户结汇
    @Test
    public void od0004() throws Exception {
        Map<String, Object> bizData = new HashMap<>();
        bizData.put("publicData", publicData);
        //合作商用户唯一ID
        bizData.put("openId", "10000007381");
        //合作机构NRA外币账户
        //GBP NRA56504122010500000076
        //USD NRA56504142010500000205
        //JPY   NRA56504272010500000066
        //AUD NRA56504292010500000054
        //EUR  NRA56504382010500000170
        //CNY  5650401403990006
        bizData.put("nraAcctNo", "NRA56504142010500000205");
        //结汇币种
        bizData.put("currencyID", "14");
        //结汇类型 1：按照外币金额结汇；2：按照人名币金额结汇
        bizData.put("settleType", "2");
        //结汇金额
        bizData.put("amount", "100");
        //订单号
        bizData.put("tradeNoJD", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
        //资金用途
        // 1  工资、2  赡家款、 3  货款、 4  其他 
        bizData.put("remark", "4");
        //资金用途 1
        //资金用途是其他时，填写实际用途 
        bizData.put("remark1", "");

        rootMap.put("requestId", UUID.randomUUID().toString());
        rootMap.put("interfaceId", "od0004");
        rootMap.put("bizData", bizData);

        post(rootMap);
    }

    // 汇率查询
    @Test(invocationCount = 2)
    public void od0005() throws Exception {
        Map<String, Object> bizData = new HashMap<>();
        bizData.put("publicData", publicData);
        //合作商用户唯一ID
        bizData.put("openId", "10000007381");
        //结汇币种
        bizData.put("currencyID", "14");

        rootMap.put("requestId", UUID.randomUUID().toString());
        rootMap.put("interfaceId", "od0005");
        rootMap.put("bizData", bizData);

        post(rootMap);
    }

    // 合作机构账户清分（对私业务）
    @Test
    public void od0003() throws Exception {
        Map<String, Object> bizData = new HashMap<>();
        bizData.put("publicData", publicData);
        //合作商用户唯一ID
        bizData.put("openId", "10000007381");
        //合作机构NRA外币账户  结汇+清分传人民币内部帐号 ；如果只是人民币，清分传NRA人民币账号 
        //GBP NRA56504122010500000076
        //USD NRA56504142010500000205
        //JPY   NRA56504272010500000066
        //AUD NRA56504292010500000054
        //EUR  NRA56504382010500000170
        //CNY  5650401403990006
        bizData.put("acctNo", "5650401403990006");
        //充值金额
        bizData.put("amount", "100");
        //转入子户账号
        bizData.put("bankCard", "1560201403990018003519");
        //转入户名
        bizData.put("customerName", "杨秋华");
        //订单号
        bizData.put("tradeNoJD", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
        /*
         *   USD 00100011709                 IPAYLINKS US, INC.
         *   EUR DK5489000000013914  DEUTSCHE BANK AG
         *   GBP DK2789000000013915  DEUTSCHE BANK AG
         *   JPY 216-2213092                   QUEEN BEE CAPITAL CO., LTD
         * */
        //汇款客户境外账号
        bizData.put("forAcctNo", "00100011709");
        //汇款客户境外户名
        bizData.put("forAcctName", "IPAYLINKS US, INC.");
        //汇款客户常驻国家代码 
        bizData.put("forAcctCtryCode", "USA");
        //资金用途
        // 1  工资、2  赡家款、 3  货款、 4  其他 
        bizData.put("remark", "4");
        //资金用途 1
        //资金用途是其他时，填写实际用途 
        bizData.put("remark1", "");
        //结汇订单号
        bizData.put("exchgTradeNo", "20190426200931689");

        rootMap.put("requestId", UUID.randomUUID().toString());
        rootMap.put("interfaceId", "od0003");
        rootMap.put("bizData", bizData);

        post(rootMap);
    }

    // 内部帐子户提现
    @Test
    public void od0006() throws Exception {
        Map<String, Object> bizData = new HashMap<>();
        bizData.put("publicData", publicData);
        //合作商用户唯一ID
        bizData.put("openId", "10000007381");
        //内部帐子户
        bizData.put("bankUserId", "1560201403990018003519");
        //姓名
        bizData.put("customerName", "杨秋华");
        //身份证
        bizData.put("idNo", "32012519930807171X");
        //提现金额
        bizData.put("amount", "100");
        //银行卡号
        bizData.put("bankCard", "6228480395120598176");
        //订单号
        bizData.put("tradeNoJD", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
        //结汇订单号
        bizData.put("exchgTradeNo", "20190426200931689");

        rootMap.put("requestId", UUID.randomUUID().toString());
        rootMap.put("interfaceId", "od0006");
        rootMap.put("bizData", bizData);

        post(rootMap);
    }

    // 订单状态查询
    @Test
    public void od0008() throws Exception {
        Map<String, Object> bizData = new HashMap<>();
        bizData.put("publicData", publicData);
        //合作商用户唯一ID
        bizData.put("openId", "10000007381");
        //订单号
        bizData.put("tradeNoJD", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));

        rootMap.put("requestId", UUID.randomUUID().toString());
        rootMap.put("interfaceId", "od0008");
        rootMap.put("bizData", bizData);

        post(rootMap);
    }

    //合作机构账户清分（对公业务）
    @Test
    public void od0009() throws Exception {
        Map<String, Object> bizData = new HashMap<>();
        bizData.put("publicData", publicData);
        //合作商用户唯一ID
        bizData.put("openId", "10000007381");
        //合作机构NRA外币账户  结汇+清分传人民币内部帐号 ；如果只是人民币，清分传NRA人民币账号 
        //GBP NRA56504122010500000076
        //USD NRA56504142010500000205
        //JPY   NRA56504272010500000066
        //AUD NRA56504292010500000054
        //EUR  NRA56504382010500000170
        //CNY  5650401403990006
        bizData.put("acctNo", "5650401403990006");
        //充值金额
        bizData.put("amount", "");
        //联行行号
        bizData.put("bankNo", "");
        //转入他行账号
        bizData.put("bankCard", "");
        //转入他行户名 
        bizData.put("customerName", "");
        //订单号
        bizData.put("tradeNoJD", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
        //汇款客户境外账号
        bizData.put("forAcctNo", "");
        //汇款客户境外户名
        bizData.put("forAcctName", "");
        //汇款客户常驻国家代码 
        bizData.put("forAcctCtryCode", "CHN");
        //资金用途
        // 1  工资、2  赡家款、 3  货款、 4  其他 
        bizData.put("remark", "4");
        //资金用途 1
        //资金用途是其他时，填写实际用途 
        bizData.put("remark1", "");
        //结汇订单号
        bizData.put("exchgTradeNo", "");

        rootMap.put("requestId", UUID.randomUUID().toString());
        rootMap.put("interfaceId", "od0009");
        rootMap.put("bizData", bizData);

        post(rootMap);
    }
}