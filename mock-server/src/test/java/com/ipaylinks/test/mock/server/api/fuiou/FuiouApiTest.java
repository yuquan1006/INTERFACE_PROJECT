package com.ipaylinks.test.mock.server.api.fuiou;

import com.alibaba.fastjson.JSON;
import com.ipaylinks.test.mock.server.MockServerApplication;
import com.ipaylinks.test.mock.server.api.fuiou.bo.FuiouHead;
import com.ipaylinks.test.mock.server.api.fuiou.bo.notify.FuiouTransferNotifyRequest;
import com.ipaylinks.test.mock.server.api.fuiou.bo.notify.FuiouTransferNotifyRequestBody;
import com.ipaylinks.test.mock.server.api.fuiou.bo.notify.FuiouTransferNotifyRequestBody04;
import com.ipaylinks.test.mock.server.api.fuiou.bo.req.*;
import com.ipaylinks.test.mock.server.api.fuiou.bo.resp.FuiouTransferResponse;
import com.ipaylinks.test.mock.server.api.fuiou.bo.resp.TransDetailsQueryResponse;
import com.ipaylinks.test.mock.server.business.fuiou.FuiouConstants;
import com.ipaylinks.test.mock.server.config.FuiouProperties;
import com.ipaylinks.test.mock.server.config.RestTemplateConfig;
import com.ipaylinks.test.mock.server.util.BeanUtil;
import com.ipaylinks.test.mock.server.util.JAXBUtils;
import com.ipaylinks.test.mock.server.util.MD5Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MockServerApplication.class)
public class FuiouApiTest {
    private Logger logger = LoggerFactory.getLogger(FuiouApiTest.class);

    @Autowired
    private FuiouProperties fuiouProperties;
    @Autowired
    private MockMvc mockMvc;

    private String post(String reqStr){
        String url = "http://139.196.75.96:8088/mock-server/fuiou/xml";
//        String url = "http://127.0.0.1:8088/mock-server/fuiou/xml";
//        String url = "http://www-1.fuiou.com:9015/gpayapi/eics/common.fuiou";

        MultiValueMap<String, String> reqMap= new LinkedMultiValueMap<>();
        reqMap.add("reqStr", reqStr);
        String sign =MD5Utils.sign(reqStr + "|" + fuiouProperties.getXmlMd5Key(),"UTF-8");
        reqMap.add("sign", sign);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        logger.info("fuiou req:{}", reqMap);
        ResponseEntity<String> resp = RestTemplateConfig.getRestTemplate().exchange(url,
                HttpMethod.POST, new HttpEntity<>(reqMap, httpHeaders), String.class);
        String respStr = resp.getBody();
        logger.info("fuiou resp:{}", respStr);
        return respStr;
    }

    private String mockPost(String reqStr) throws Exception {
        String url = "/fuiou/xml";

        MultiValueMap<String, String> reqMap= new LinkedMultiValueMap<>();
        reqMap.add("reqStr", reqStr);
        String sign =MD5Utils.sign(reqStr + "|" + fuiouProperties.getXmlMd5Key(),"UTF-8");
        reqMap.add("sign", sign);

//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        logger.info("fuiou req:{}", reqMap);
        MockHttpServletResponse mockResp = mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .headers(httpHeaders)
                        .params(reqMap)
        ).andReturn().getResponse();
        String respStr = mockResp.getContentAsString();
        logger.info("fuiou resp:{}", respStr);
        return respStr;
    }

    @Test
    public void testTransfer() throws Exception {
        FuiouTransferRequest fuiouTransferRequest = new FuiouTransferRequest();
        FuiouHead head = new FuiouHead();
        head.setPartnerId(fuiouProperties.getXmlPartnerId());
        head.setTransNo(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
        head.setTimeStamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        head.setMessageVersion("001");
        head.setDataDirection("R");
        head.setMessageCode("011");
        head.setEncryptionCode("100");
        fuiouTransferRequest.setHead(head);

        FuiouTransferRequestBody body = new FuiouTransferRequestBody();
//        body.setBackNotifyUrl("");
        body.setBankCardNo("4563516154833049478");
        body.setOppositeName("IPAY张三");
        body.setOppositeIdNo("572922198802086528");
        body.setOppositeMobile("13355442211");
        body.setBankCardTp("01");
        body.setBankNo("0104");
        body.setCityNo("3050");
        body.setAmt("1");
        body.setRemark("账户:IPAY张三,金额:1");
        // 01: 不用人工复核，02: 需要复核，如果是02，必须人工复核以后才能查到转账记录
        body.setIsNeedReview("01");
        fuiouTransferRequest.setBody(body);

        String reqStr = JAXBUtils.toXML(fuiouTransferRequest);

        String respStr = post(reqStr);
//        String respStr = mockPost(reqStr);

        FuiouTransferResponse fuiouTransferResponse = JAXBUtils.formXMLIgnoreNamespace(respStr, FuiouTransferResponse.class);
        String signContent = null;
        if (respStr != null) {
            signContent = respStr.substring(respStr.indexOf(FuiouConstants.PREFIX_BODY),
                    respStr.indexOf(FuiouConstants.SUFFIX_BODY)+FuiouConstants.SUFFIX_BODY.length());
        }
        boolean verifyResult = MD5Utils.verify(signContent + "|" + fuiouProperties.getXmlMd5Key(),"UTF-8", fuiouTransferResponse.getSign());
        if(!verifyResult){
            logger.info("验签不通过");
        }
    }

    @Test
    public void transferNotify() {
        FuiouTransferNotifyRequest fuiouTransferNotifyRequest = new FuiouTransferNotifyRequest();

        FuiouTransferNotifyRequestBody body = new FuiouTransferNotifyRequestBody();
        body.setRspCode("5005");
        body.setRspDesc("成功");
        body.setNotifyType("04");

        FuiouTransferNotifyRequestBody04 body04 = new FuiouTransferNotifyRequestBody04();
        //原交易流水号
        body04.setEicSsn("812190422000000005030000000000");
        //富友交易流水号
        body04.setFuiouTransNo("190422142157002576");
        //转账金额 单位：分
        body04.setAmt("78191");
        body.setNotify04(body04);
        fuiouTransferNotifyRequest.setBody(body);

        String notifyReqStr = JAXBUtils.toXML(fuiouTransferNotifyRequest);
        String signContent = notifyReqStr.substring(notifyReqStr.indexOf(FuiouConstants.PREFIX_BODY),
                notifyReqStr.indexOf(FuiouConstants.SUFFIX_BODY)+FuiouConstants.SUFFIX_BODY.length());
        //加签
        String sign =MD5Utils.sign(signContent + "|" + fuiouProperties.getXmlMd5Key(),"UTF-8");

        fuiouTransferNotifyRequest.setSign(sign);
        notifyReqStr = JAXBUtils.toXML(fuiouTransferNotifyRequest);

        //本地
//        String notifyUrl = "http://127.0.0.1:8037/cfp-gateway/cfp/fuiou/accountInNotify";
        //测试1套
        String notifyUrl = "http://139.196.75.96:8037/cfp-gateway/cfp/fuiou/accountInNotify";
        //测试2套
//        String notifyUrl = "http://47.101.70.242:8037/cfp-gateway/cfp/fuiou/accountInNotify";

        MultiValueMap<String, String> reqMap= new LinkedMultiValueMap<>();
        reqMap.add("reqStr", notifyReqStr);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        logger.info("fuiou notify req:{}", JSON.toJSONString(fuiouTransferNotifyRequest));
        ResponseEntity<String> notifyResp = RestTemplateConfig.getRestTemplate().exchange(notifyUrl,
                HttpMethod.POST, new HttpEntity<>(reqMap, httpHeaders), String.class);
        logger.info("fuiou notify resp:{}", notifyResp.getBody());
    }

    @Test
    public void testTransDetailsQuery() throws Exception {
        TransDetailsQueryRequest transDetailsQueryRequest = new TransDetailsQueryRequest();
        FuiouHead head = new FuiouHead();
        head.setPartnerId(fuiouProperties.getXmlPartnerId());
        head.setTransNo(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
        head.setTimeStamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
        head.setMessageVersion("001");
        head.setDataDirection("R");
        head.setMessageCode("003");
        head.setEncryptionCode("100");
        transDetailsQueryRequest.setHead(head);

        TransDetailsQueryRequestBody body = new TransDetailsQueryRequestBody();
        body.setTransNo("20190505142028781");
        transDetailsQueryRequest.setBody(body);

        String reqStr = JAXBUtils.toXML(transDetailsQueryRequest);

        String respStr = post(reqStr);
//        String respStr = mockPost(reqStr);

        TransDetailsQueryResponse transDetailsQueryResponse = JAXBUtils.formXMLIgnoreNamespace(respStr, TransDetailsQueryResponse.class);
        String signContent = null;
        if (respStr != null) {
            signContent = respStr.substring(respStr.indexOf(FuiouConstants.PREFIX_BODY),
                    respStr.indexOf(FuiouConstants.SUFFIX_BODY)+FuiouConstants.SUFFIX_BODY.length());
        }

        boolean verifyResult = MD5Utils.verify(signContent + "|" + fuiouProperties.getXmlMd5Key(),"UTF-8", transDetailsQueryResponse.getSign());
        if(!verifyResult){
            logger.info("验签不通过");
        }
    }

    @Test
    public void testNotify() throws Exception {
        FuiouNotifyRequest request = new FuiouNotifyRequest();
        request.setMchntCd(fuiouProperties.getJsonPartnerId());
        request.setBackNotifyUrl("http://47.101.70.242:8037/cfp-gateway/cfp/fuiou/asynSetterOrderNotify");
        request.setFileTp("1");
        request.setFileNm("P1200979.txt");
        request.setOrderNo("P1200979");

        String signData = request.getSignData(fuiouProperties.getJsonMd5Key(),"|");
        //加签
        String sign = MD5Utils.sign(signData,"UTF-8");

        request.setMd5(sign);

        Map<String, String> reqMap = BeanUtil.beanToMap(request);
        //富友测试环境
//        String url = "http://www-1.fuiou.com:9015/gpayapi/api/notify.fuiou";
        String url = "/fuiou/json/notify.fuiou";
        MultiValueMap<String, String> reqMultiValueMap= new LinkedMultiValueMap<>();
        for(Map.Entry<String, String> entry : reqMap.entrySet()){
            reqMultiValueMap.add(entry.getKey(), entry.getValue());
        }

//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        logger.info("fuiou notify req:{}", JSON.toJSONString(request));
//        ResponseEntity<String> notifyResp = RestTemplateConfig.getRestTemplate().exchange(url,
//                HttpMethod.POST, new HttpEntity<>(reqMultiValueMap, httpHeaders), String.class);
//        logger.info("fuiou notify resp:{}", notifyResp.getBody());

        logger.info("fuiou req:{}", reqMultiValueMap);
        MockHttpServletResponse mockResp = mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .headers(httpHeaders)
                        .params(reqMultiValueMap)
        ).andReturn().getResponse();
        String respStr = mockResp.getContentAsString();
        logger.info("fuiou resp:{}", respStr);
    }

    @Test
    public void testSubmitOrder() throws Exception {
        FuiouSubmitOrderRequest request = new FuiouSubmitOrderRequest();
        request.setMchntCd(fuiouProperties.getJsonPartnerId());
        request.setSubCustNo(fuiouProperties.getJsonPartnerId());
        request.setOrderId(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")) + "0123456789123");
        request.setBackNotifyUrl("http://47.101.70.242:8037/cfp-gateway/cfp/fuiou/asynSetterOrderNotify");
        request.setTxnTp("1");
        //01：结算至富友账户；
        //02：结算至银行账户
        request.setSettleAccountsTp("01");
        request.setOutAcntNo("77162461451131");
        request.setOutAcntNm("Shanghai Fuiou Payment");
        request.setOutAcntBankNm("the Standard Chartered Bank");
        request.setOutCurCd("USD");
        request.setInAcntNo("421222199204200127");
        request.setInAcntNm("上海烛龙信息科技有限公司");
        request.setInAcntBankNm("中国工商银行");
        request.setBankNo("0102");
        request.setCityNo("1000");
        request.setCountryCd("HKG");
        //1：货物贸易；
        //2：服务贸易
        request.setOrderTp("1");
        request.setOrderAmt("1234");
        request.setVer("1.0.0");

        String signData = request.getSignData(fuiouProperties.getJsonMd5Key(),"|");
        logger.info("signData:{}", signData);
        //加签
        String sign = MD5Utils.sign(signData,"UTF-8");

        request.setMd5(sign);

        Map<String, String> reqMap = BeanUtil.beanToMap(request);
        //富友测试环境
//        String url = "http://www-1.fuiou.com:9015/gpayapi/api/submitOrder.fuiou";
//        String url = "/fuiou/json/submitOrder.fuiou";
        String url = "http://139.196.75.96:8088/mock-server/fuiou/json/submitOrder.fuiou";
        MultiValueMap<String, String> reqMultiValueMap= new LinkedMultiValueMap<>();
        for(Map.Entry<String, String> entry : reqMap.entrySet()){
            reqMultiValueMap.add(entry.getKey(), entry.getValue());
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        logger.info("fuiou submitOrder req:{}", JSON.toJSONString(request));
        ResponseEntity<String> notifyResp = RestTemplateConfig.getRestTemplate().exchange(url,
                HttpMethod.POST, new HttpEntity<>(reqMultiValueMap, httpHeaders), String.class);
        logger.info("fuiou submitOrder resp:{}", notifyResp.getBody());

//        logger.info("fuiou req:{}", reqMultiValueMap);
//        MockHttpServletResponse mockResp = mockMvc.perform(MockMvcRequestBuilders.post(url)
//                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
////                .headers(httpHeaders)
//                        .params(reqMultiValueMap)
//        ).andReturn().getResponse();
//        String respStr = mockResp.getContentAsString();
//        logger.info("fuiou resp:{}", respStr);
    }

    @Test
    public void testOrderQueryPage() throws Exception {
        FuiouOrderQueryPageRequest request = new FuiouOrderQueryPageRequest();
        request.setMchntCd(fuiouProperties.getJsonPartnerId());
        request.setPageNo("1");
        request.setPageSize("30");
        request.setOrderId("80419050700000000061");
//        request.setOrderNo("P2252084");
        String signData = request.getSignData(fuiouProperties.getJsonMd5Key(),"|");
        //加签
        String sign = MD5Utils.sign(signData,"UTF-8");

        request.setMd5(sign);

        Map<String, String> reqMap = BeanUtil.beanToMap(request);
        //富友测试环境
//        String url = "http://www-1.fuiou.com:9015/gpayapi/api/orderQueryPage.fuiou";
        String url = "/fuiou/json/orderQueryPage.fuiou";
        MultiValueMap<String, String> reqMultiValueMap= new LinkedMultiValueMap<>();
        for(Map.Entry<String, String> entry : reqMap.entrySet()){
            reqMultiValueMap.add(entry.getKey(), entry.getValue());
        }

//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        logger.info("fuiou orderQueryPage req:{}", JSON.toJSONString(request));
//        ResponseEntity<String> notifyResp = RestTemplateConfig.getRestTemplate().exchange(url,
//                HttpMethod.POST, new HttpEntity<>(reqMultiValueMap, httpHeaders), String.class);
//        logger.info("fuiou orderQueryPage resp:{}", notifyResp.getBody());

        logger.info("fuiou req:{}", reqMultiValueMap);
        MockHttpServletResponse mockResp = mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//                .headers(httpHeaders)
                        .params(reqMultiValueMap)
        ).andReturn().getResponse();
        String respStr = mockResp.getContentAsString();
        logger.info("fuiou resp:{}", respStr);
    }
}