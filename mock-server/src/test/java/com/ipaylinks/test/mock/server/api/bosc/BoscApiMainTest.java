package com.ipaylinks.test.mock.server.api.bosc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ipaylinks.test.mock.server.business.bosc.BoscConstants;
import com.ipaylinks.test.mock.server.config.RestTemplateConfig;
import com.ipaylinks.test.mock.server.service.bosc.BoscCertificateUtils;
import com.ipaylinks.test.mock.server.service.bosc.BoscSecurityService;
import com.koalii.svs.SvsSign;
import com.koalii.svs.SvsVerify;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.StringUtils;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class BoscApiMainTest {

    public static void main(String[] args) throws Exception {
//        testIBPS_OPENACCT_000001();
//        testIBPS_OPENACCT_000002();
        testIBPS_OPENACCT_000004();
//        testIBPS_IBS_140002();
//        testIBPS_IBS_110018();
    }

    private static final Logger logger = LoggerFactory.getLogger(BoscApiMainTest.class);

    private static final String privateKeyPath = "/opt/cfp/bosc/private.pfx";

    private static final String privateKeyPassword = "123123";

//    private static final String publicKeyPath = "/opt/cfp/bosc/public.cer";

    //0525上海银行升级签名算法为SHA256withRSA，升级后的上海银行公钥文件为
    private static final String publicKeyPath = "/opt/cfp/bosc/bankofshanghai0525_test.cer";

    private static String post(String tranCode, Map<String, String> bodyMap) throws Exception {

        Map<String, String> headMap = new HashMap<>();
        headMap.put("HeadTranCode", tranCode);
        headMap.put("HeadBusinessId", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
        headMap.put("HeadReqDate", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        headMap.put("HeadReqTime", LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss")));
        headMap.put("HeadSndOrgan", BoscConstants.HEAD_SND_ORGAN);
        headMap.put("HeadMsgVersion", BoscConstants.HEAD_MSG_VERSION);
        headMap.put("HeadRevOrgan", BoscConstants.HEAD_REV_ORGAN);

        Map<String, String> signMap = new HashMap<>();
        signMap.putAll(headMap);
        signMap.putAll(bodyMap);
        String signData = getSignData(signMap);

/*        SvsSign signer = new SvsSign();
        signer.initSignCertAndKey(privateKeyPath, privateKeyPassword);
        String signature = signer.signData(signData.getBytes("GBK")).replace("\r\n", "");
        bodyMap.put("Signature",signature);

        //公钥 koalB64Cert
        String koalB64Cert = signer.getEncodedSignCert().replace("\r\n", "");
//        bodyMap.put("koalB64Cert", koalB64Cert);*/

        //我方私钥
        PrivateKey privateKey = BoscCertificateUtils.getPrivateKey(privateKeyPath, privateKeyPassword, BoscCertificateUtils.KEY_STORE_TYPE_PKCS12);
        //对方公钥
        PublicKey publicKey = BoscCertificateUtils.getPublicKey(publicKeyPath);
        BoscSecurityService boscSecurityService = new BoscSecurityService(privateKey,publicKey);

        String signature = boscSecurityService.sign(signData.getBytes(BoscConstants.GBK), BoscSecurityService.SIGNATURE_ALGORITHM_SHA1).replace("\r\n", "");
        bodyMap.put(BoscConstants.Signature,signature);

        JSONObject document = new JSONObject();

        document.put("head", headMap);
        document.put("body",bodyMap);

        JSONObject reqJson = new JSONObject();
        reqJson.put("document", document);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "text/plain; charset=utf-8");

//        String url = "http://127.0.0.1:8088/mock-server/bosc";
//        String url = "http://139.196.75.96:8088/mock-server/bosc";
        String url = "https://203.156.238.218:20325";
        logger.info("bosc reqStr:{}", reqJson.toJSONString());
        ResponseEntity<String> resp = RestTemplateConfig.getRestTemplate().exchange(url,
                HttpMethod.POST, new HttpEntity<>(reqJson.toJSONString(), httpHeaders), String.class);
        String respStr = resp.getBody();
        logger.info("bosc respStr:{}", respStr);

        JSONObject respJson = JSON.parseObject(respStr);
        JSONObject respDocument = respJson.getJSONObject("document");
        JSONObject respHead = respDocument.getJSONObject("head");
        JSONObject respBody = respDocument.getJSONObject("body");
        String respSignature = respBody.getString("Signature");

        Map<String, String> respSignMap = new HashMap<>();
        putJSONObjectToMap(respHead, respSignMap);
        putJSONObjectToMap(respBody, respSignMap);

        String respSignData = getSignData(respSignMap);

        logger.info("bosc respSignData:{}", respSignData);
        logger.info("bosc respSignature:{}", respSignature);
/*        SvsVerify verifier = new SvsVerify();
        verifier.initSignCertFile(publicKeyPath);
        int verifyResult = verifier.verifySign(respSignData.getBytes("GBK"),respSignature);
        //verifyResult 0：验签通过；1：验签不通过
        if (verifyResult != 0){
            logger.info("bosc 验签不通过 verifyResult:{}", verifyResult);
        }*/

        boolean verifyResult = boscSecurityService.verify(respSignData.getBytes(BoscConstants.GBK), respSignature, BoscSecurityService.SIGNATURE_ALGORITHM_SHA1);
        logger.info("bosc 响应报文验签结果 verifyResult:{}", verifyResult);
        if (!verifyResult){
            logger.info("bosc 响应报文验签不通过 verifyResult:{}", verifyResult);
        }

        return respStr;
    }

    private static void putJSONObjectToMap(JSONObject jsonObject, Map<String, String> map){
        for(Map.Entry<String, Object> entry : jsonObject.entrySet()){
            if (entry.getValue() instanceof String) {
                map.put(entry.getKey(), (String) entry.getValue());
            }
        }
    }

    private static String getSignData(Map<String, String> map) {
        StringBuilder signData = new StringBuilder();
        TreeMap<String, String> treeMap = new TreeMap<>(map);
        for(Map.Entry<String, String> entry : treeMap.entrySet()){
            if(!StringUtils.isEmpty(entry.getValue()) && !"Signature".equals(entry.getKey())
                    && !"koalB64Cert".equals(entry.getKey())){
                signData.append(entry.getKey());
                signData.append("=");
                signData.append(entry.getValue());
                signData.append("&");
            }
        }
        //去掉末尾的&
        signData.setLength(signData.length()-1);
        return signData.toString();
    }


    //子账户开户
    public static void testIBPS_OPENACCT_000001() throws Exception {
        //交易代码
        String tranCode = "IBPS_OPENACCT_000001";
        //请求报文体
        Map<String, String>  bodyMap = new HashMap<>();
        bodyMap.put("ProductCd", BoscConstants.BODY_PRODUCT);
        bodyMap.put("IdNo", "32012519930807171X");
        bodyMap.put("CustName", "杨秋华");
//        bodyMap.put("ExpDay", "20261006"); //非必填
        bodyMap.put("MobilePhone", "15261471747");
        bodyMap.put("BindCardNo", "6217880800001810391");
        bodyMap.put("ReservedPhone", "15261471747");

        String respStr = post(tranCode, bodyMap);

        //ShAppNo 主账号 623185009700473421
    }

    //账户信息修改
    public static void testIBPS_OPENACCT_000002() throws Exception {
        //交易代码
        String tranCode = "IBPS_OPENACCT_000002";
        //请求报文体
        Map<String, String>  bodyMap = new HashMap<>();
        bodyMap.put("EacctNo", "623185009900011674");
        bodyMap.put("ProductCd", BoscConstants.BODY_PRODUCT);
        bodyMap.put("IdNo", "32012519930807171X");
        bodyMap.put("CustName", "杨建华");
        bodyMap.put("NewCustName", "杨秋华"); //非必填
//        bodyMap.put("CustName", "杨秋华");
//        bodyMap.put("NewCustName", "杨建华"); //非必填
//        bodyMap.put("BindCardNo", "6222620110033651440"); //非必填
//        bodyMap.put("NewCardNo", "6217880800001810391"); //非必填
//        bodyMap.put("NewReservedPhone", "15261471747"); //非必填
//        bodyMap.put("ReservedPhone", "15261471747"); //非必填

        String respStr = post(tranCode, bodyMap);
    }

    //账户信息查询
    public static void testIBPS_OPENACCT_000004() throws Exception {
        //交易代码
        String tranCode = "IBPS_OPENACCT_000004";
        //请求报文体
        Map<String, String>  bodyMap = new HashMap<>();
//        bodyMap.put("EacctNo", "623185009900011674"); //非必填
        bodyMap.put("ProductParam", BoscConstants.BODY_PRODUCT);
        bodyMap.put("IdNo", "32012519930807171X");
//        bodyMap.put("SubAcctNo", ""); //非必填

        String respStr = post(tranCode, bodyMap);
    }

    //账户信息签约
    public static void testIBPS_IBS_140002() throws Exception {
        //交易代码
        String tranCode = "IBPS_IBS_140002";
        //请求报文体
        Map<String, String>  bodyMap = new HashMap<>();
        bodyMap.put("CustName", "杨秋华");
        bodyMap.put("IdType", "01"); //01：固定为身份证号
        bodyMap.put("IdNo", "32012519930807171X");
        bodyMap.put("ExpDay", "20261006");
        bodyMap.put("EacctNo", "623185009900011674");
        bodyMap.put("BindCardNo", "6217880800001810391");
        bodyMap.put("ReservedPhone", "15261471747");
        bodyMap.put("CardNo", "6217880800001810391");
        bodyMap.put("Riskinfomobile", "15261471747");
        bodyMap.put("ExchangeCode", ""); //非必填 跨境收汇用途
        bodyMap.put("SignTypeNo", "0"); //0：新增;1：删除;2：修改手机号（修改卡号算新增）

        String respStr = post(tranCode, bodyMap);
    }

    //代付交易状态查询
    public static void testIBPS_IBS_110018() throws Exception {
        //交易代码
        String tranCode = "IBPS_IBS_110018";
        //请求报文体
        Map<String, String>  bodyMap = new HashMap<>();
        bodyMap.put("SerialNo", "IPL0120180313000000001"); //组合提现来盘中的银行业务编号  IPL0120180313000000001

        String respStr = post(tranCode, bodyMap);
    }

}