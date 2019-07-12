package com.ipaylinks.test.mock.server.api.czcb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ipaylinks.test.mock.server.config.RestTemplateConfig;
import com.ipaylinks.test.mock.server.service.czcb.CzcbSecurityService;
import com.ipaylinks.test.mock.server.service.czcb.CzcbSecurityServiceImpl;
import com.ipaylinks.test.mock.server.service.czcb.exception.CzcbException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CzcbApiMainTest {

    public static void main(String[] args) throws Exception{
        CzcbApiMainTest czcbApiMainTest = new CzcbApiMainTest();
        czcbApiMainTest.setUp();
        czcbApiMainTest.od0007();
//        czcbApiMainTest.oa0003();
//        czcbApiMainTest.oa0005();
//        czcbApiMainTest.oa0015();
//        czcbApiMainTest.od0001();
//        czcbApiMainTest.od0002();
//        czcbApiMainTest.od0004();
//        czcbApiMainTest.od0005();
//        czcbApiMainTest.od0003();
//        czcbApiMainTest.od0006();
//        czcbApiMainTest.od0008();
//        czcbApiMainTest.od0009();

//        multiThreadTest(args);
    }

    private static void multiThreadTest(String[] args){
        long timeout = 60 * 1000L ;
        int threadCount = 5;
        int times = 10;
        String ip = "124.160.111.105";
        String port = "4430";
        if (args != null) {
            if (args.length>0){
                String threadCountStr = args[0];
                if (!StringUtils.isEmpty(threadCountStr)){
                    threadCount = Integer.parseInt(threadCountStr);
                }
            }
            if (args.length>1){
                String timesStr = args[1];
                if (!StringUtils.isEmpty(timesStr)){
                    times = Integer.parseInt(timesStr);
                }
            }
            if (args.length>2){
                ip = args[2];
            }
            if (args.length>3){
                port = args[3];
            }
        }
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        for (int i = 0; i < threadCount; i++) {
            int finalTimes = times;
            String finalIp = ip;
            String finalPort = port;
            int finalI = i;
            executorService.execute(()->{
                try {
                    for (int j = 0; j < finalTimes; j++) {
                        CzcbApiMainTest czcbApiMainTest = new CzcbApiMainTest();
                        czcbApiMainTest.setUp();
                        czcbApiMainTest.od0005(finalIp, finalPort);
                        System.out.println(Thread.currentThread().getName() + "-->>>>" + finalI+";times-->>>>" +j );
                    }
                } catch (CzcbException e) {
                    e.printStackTrace();
                }
            });
        }


        try {
            executorService.shutdown();
            // (所有的任务都结束的时候，返回TRUE)
            if(!executorService.awaitTermination(timeout, TimeUnit.MILLISECONDS)){
                // 超时的时候向线程池中所有的线程发出中断(interrupted)。
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            // awaitTermination方法被中断的时候也中止线程池中全部的线程的执行。
            System.out.println("awaitTermination interrupted: " + e);
            executorService.shutdownNow();
        }

    }

    private Logger logger = LoggerFactory.getLogger(CzcbApiMainTest.class);

    //稠州银行测试环境
//    private String clientPrivateKey = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQClCOrjiNW3dLIhL+r+0yLawH7TgjBpXRggy+o8na184Q+MRo3mmItAan50ytX118dfhPc4zeKZ02LAHZQixQ+OfNdNepDOM5pcB0HfXXPaXSMW3xS0c500IeP2Pq6YkCOdEf0DiCdp3sByX5HoqR7yUHLHLy3cq3zr3AOxihp7EMmqeFLLWTswcJaWZ0G9Dj4scR4+mU7ISP+w76XAYra4tjWDO1rAIgLYtRlqYrinwL0K0YTeSCwT84cFvgTJWOg6/P3XU37doxms8DADPkBhXmYJI1XfuL/zJgcQVYXBeG+c9+MJvqkephYT1prddmweth9X34ePAoi3QT+Av3H1AgMBAAECggEAOlSGDMjS3MYqpW81zD5c3dtWmDQ2Hy9vtTvNfQIwDOjRIX/YbK3y3eAl9OqyJ/PNvHWXGaUvV7Yue3+Y2Zcz8eUvBevxnWgAwVExV5zEs2Pi4JUVUNhiMkheNa0e8b5UUBJJDoQDhjhcZbBgtk58KSIFB2CmAY49F3IKfig1RtRtN3GxO0fD+jvjJ+uPFi6uSYPdlfiIiHO/BJrNvuIk6LnKwmeCBjwhDfoOZj+LwIpS8xG8hwoksaMGycl3eT7MraGsXSq0NaUZzz0ycC8hJfE0saxrLeoQRja2FanvpRCQAbHvFrdick8zOl1AnxNta2uFEN3BqbZIaJQiC3bfCwKBgQDXKE8L04p+BfJz/alwtrfcyxp2/BgBRU06ItspUVIs+YsFa2jjfvLIud+Ov2mCzFWPoyXcrr1pF7h0xOsrB+C+fsm3j/xJbciXX2abX/4coiclb1H74T8OPU9vSNTMzcx8Hf2LqvpaVkci+GnB7KBkvY+o0+m87BJ0gNdpU02N8wKBgQDEXN+MWhjwNu7e94Owad4b1vY8WIZNv9qmSnjgnIiAvayqjRcv6YC1kv5c9aEkv3N4zron7PryVXu0FnsphTCA6FUBQB4VPpeJAIiP164L/BBChQn9DLaIW06YqMiu7YN00Q4mDK9UWcdbMs4+AD9R7ntztaVRKQI2t9BUj2MydwKBgGqa9k+NyRkYKUF/dez7G66yk7JQxjQa+mDL1HtsZ6MRIejVh2rxA6qhLju0a7r7M4mv9Jqbmg3wStzQlRUFGj5I4/mnxgE7/B/EjTp/HZFiZrMNrTl4KZ4Mksc49vdjIOZ+lZoWZWVNSoHxQ7VqYS6JHh0woqeQ8LP5AsKVG+AxAoGAI1WXAHmB/U/VQhgGAWu7hQT0gD1pxXndjYjfIYPpaEk5VeNXpc59066kyPOGUMeGrtl8VAznx02smeZluT33UWr9SOFRyH7f485NxJyJ/CwafO3Vs0xjWDwd84iDvD6PJiksQ5qKvkH9hqKozC1dN5ATdOnd2rHnbRM8kMrQjdcCgYBuxCktP8re2JDfXHp67TTZcqWjECLc96AHb2qv4Fa36Gs1f9JT7SzuAmaIzISzgy+oOsfeQ2ZkVu6be0wEgJAMJoh/I/jGoY6VcUkatIuZ7qXUTIZJ5D409KjI6Fc1RzC1eHjGZP3FsGzBp4loYUYC0UjWFg4EvDGo65vSXgertA==";
//    private String serverPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApiv6uljjchFaMfP027BryqI1ZZvp+NsVu4zSkkdM4oMYATpKYMwbO/4bQXUC+1Z3g9XZ5Dod7QXl5z7bTNiEWvymBHigOXC0FGQTuc/EZpe4TnAxSUIcZa29fBQmlE3M8Os3CSSSdiLSbFoCl1IDBeknUL9cZoP36Xdx56B0B2Ofmb4Ey1iYQPYzuM3sEK72x3HbdEQEhTe4snfMngJ9pzlIy778gagGRMpadFaXGLluwxVAERDTd7dAPFD3VJ6NQWxGC5Es95tBU8WuyIqZUgTPD2Dy90DQQZLe+l344EHoWyEJAkUuI0I3qgeuxbDHQTa3r0ak15fC/sg21l/GFQIDAQAB";

    //本地
    private String clientPrivateKey= "MIIEwAIBADANBgkqhkiG9w0BAQEFAASCBKowggSmAgEAAoIBAQClEJN8MU4wqNort2mis+jU047UPZcTbv4ZzmrpenLqeCU2fuBhBsGWDHwMpz4DONIOdcH09ffYBjtf7QfBXwQe6sjKHl5vq2bdOqF/xOXCmJ2ViAMjo4kaPNdC2MEEaG2T1jZTGU6jp3J4BZCtD5qVe86wxBs34bNJra60G/WOo5GNWcXDstf3Es9o7OZ0rLWBVD7d3UPFpdbrRXTzY4GzfZ/lC/6gd/j/rxUgmSXuGCkX8orPn12W/MzIfUWbWmyeWbRpHVoKzntf+Coq5p3Z/6XJFiKoYBDKZDzDnHqHsSxj7iKKiJydLBOrrd0x2fZ0En908w6ZVwjGcDSvNZy1AgMBAAECggEBAJkoFUDR2vQJaE3R5CFEf5AiME+8ShaNERbO0aKLrF7kVdsHxJgilbLtKJjxAPgqW7VxDCOHqoz101fBbkW9LOym+uGXZhaFWm+BPGJ6RpnV07nwNsF1tvvyYeWzivzVDH907nkSbUYqU2sL9t6tMMjs1K5td7fVIu8FSanym1jZ7O9AcTZF51vgQcipOpYtmKgHuMzQVzA/TRubXWwG8LrSVIroQXiKkQe+9yPHoxE8vyZu/oDTNsQNJG6rkSnasj18TdbtQNwxMamfR94ZuJF/VrzpUnCyhMuHIniD4sni6Xf/QDxW6aWtiEmb3b4h8DDHLLWWJUU1pKrF6JIy9M0CgYEAz+TJ8H8l73098ioCK+nf6+FXTRt+UXxl7duauh6nJ3I2cx4Dna+qvbADjaxzne09ixbHl/0ceSpM5Qd9eKgGZWP2pdcUjpVQiAl2PyFufYzlP27nGJcBgC7M0qYknsbAoH7v0MKUZ/PrONzDalLjyxmiT/3K25l+xYao83sLM8sCgYEAy0KvUBJkpWm/QIUUP/pBoUQkql+3Jen17I9dKYdcREnpi43UVH4oTmssYL2ReUx13TpmmmX0t5eZW/XtFpjGGFySwgmjJtmQvFlUD9E7GuLmeVAIjhyDX8szmQPQ1Vwa9J3BL7zAMUHOjUHNrT/qwUPRHt77dg/IqUuhiWc1YX8CgYEAhA6S8D/0OwLVfKSOqJJxeQa1S/ew79YTcVpUGPIrv/Te/ZUNOvdBgCj0uOPYtXLj6xxXQQa5NYZXPgzZ+PDSz0kGfiX3mvLiKufHwyZbZ0k+vmKWTZpui5wu44hCLtGVvt2jPHq9hqL8wiwJvdpVdMgdwK44U/JXAuYdID0Cyq0CgYEAmUr3kLnucdnoxS4YcY+5g3gy9tMs+0kRkZElv7Y4XcAohHk4k2h52Xp2g5c0aXjCw0/SJcuS3gtVO8ejQRGDBMh8Xh6KjzgyMfpkrXEs1EAfdYpoiVTzt4E2rsXz75WBNs7q7ae6guuQbXQdO/YihBMzWcbtesK4oQVLFKdj2N0CgYEAoB3fYqQsZiVkMsxGjo3uyXlb3vvbSC8nu9VC/qYdg4DB5Uew5rsPQpsWRs4mJ4o76O00omg76fXt35EI87r2eIffVutA73yQ8DvtEy4TKhRR+fPV3gG03H0aO7PRY3Sda7RrlsyHNfnuiswhrm1Gtr+l0hbJXmVMomcN9NgYJCI=";
    private String serverPublicKey= "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2Rr5J6loDpwOQJhI/4hVTOk+JZdvFwG913keXOW0kmxeh/wUlUG++RqAHjMQqdV/CV32XPsh8N6XnPTK/wBvB4sCgcThNnpLcXN24Tbmx/svnCk+KRwCD5MJBx5PEMro7bTLlEKQZWwKb/cnu1L8KUeL4dNCpj6/lwfxcNaJytqfuItCYJukXC92bJ8eRWoX2efFCE0ICNCWAvgsfhgsbl6dlKQE8EXtBA7v+RHcWtr9kB8avZORSKTEpF/qqDd9vt0K6cKgVLXnX/COqHv0SwM72BK+LoJ3rEP8BXVj8FLhOIVf0C4UVVqkwejiRpZ8F+HCWq/ckcs+h67iKcwopwIDAQAB";
    private CzcbSecurityService czcbSecurityService = new CzcbSecurityServiceImpl(clientPrivateKey,serverPublicKey);

    private Map<String, Object> rootMap = new HashMap<>();
    private Map<String, Object> publicData = new HashMap<>();

    /*币种代码 说明
    1 人民币 12 英镑 13 港币 14 美元 27 日元 29 澳大利亚元 38 欧元
    */

    public void setUp() {
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

    private void post(Map rootMap, String ip, String port) throws CzcbException{
        String unencryptedReqStr = JSON.toJSONString(rootMap);
//        logger.info("czcb unencryptedReqStr:{}", unencryptedReqStr);
//        Reporter.log("czcb unencryptedReqStr："+unencryptedReqStr);
        //加密后加签
        String reqStr = czcbSecurityService.sign(unencryptedReqStr);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

//        String url = "http://127.0.0.1:8088/mock-server/czcb";
//        String url = "https://124.160.111.105:4430/obankApi/bankPlus";
        String url = "https://"+ip+":"+port+"/obankApi/bankPlus";
//        logger.info("czcb reqStr:{}", reqStr);
//        Reporter.log("czcb reqStr："+reqStr);
        ResponseEntity<String> resp = RestTemplateConfig.getRestTemplate().exchange(url,
                HttpMethod.POST, new HttpEntity<>(reqStr, httpHeaders), String.class);
        String respStr = resp.getBody();
//        logger.info("czcb respStr:{}", respStr);

//        Reporter.log("czcb respStr："+respStr);

        //验签
        if(!czcbSecurityService.verify(respStr)){
//            logger.info("验签失败");
//            throw new CzcbException("验签失败");
        }
        //解密
        String decryptedRespStr = czcbSecurityService.decrypt(respStr);
//        logger.info("czcb decryptedRespStr:{}", decryptedRespStr);
//        Reporter.log("czcb decryptedRespStr："+decryptedRespStr);

        JSONObject decryptedRespJSONObject = JSON.parseObject(decryptedRespStr);
        String requestId = decryptedRespJSONObject.getString("requestId");
//        logger.info("czcb resp requestId:{}", requestId);
        System.out.println( "czcb resp requestId-->>>>" +requestId );
        if(!requestId.equals(String.valueOf(rootMap.get("requestId")))){
            System.out.println( "czcb resp requestId:{} not equals czcb req requestId:{}-->>>>" +requestId+ ";" + String.valueOf(rootMap.get("requestId")));
        }
    }

    private void post(Map rootMap) throws CzcbException{
        String unencryptedReqStr = JSON.toJSONString(rootMap);
        logger.info("czcb unencryptedReqStr:{}", unencryptedReqStr);
//        Reporter.log("czcb unencryptedReqStr："+unencryptedReqStr);
        //加密后加签
        String reqStr = czcbSecurityService.sign(unencryptedReqStr);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);

//        String url = "http://127.0.0.1:8088/mock-server/czcb";
        String url = "http://139.196.75.96:8088/mock-server/czcb";
//        String url = "https://124.160.111.105:4430/obankApi/bankPlus";
        logger.info("czcb reqStr:{}", reqStr);
        System.out.println( "czcb reqStr-->>>>" +reqStr );
//        Reporter.log("czcb reqStr："+reqStr);
        ResponseEntity<String> resp = RestTemplateConfig.getRestTemplate().exchange(url,
                HttpMethod.POST, new HttpEntity<>(reqStr, httpHeaders), String.class);
        String respStr = resp.getBody();
        logger.info("czcb respStr:{}", respStr);
        System.out.println( "czcb respStr-->>>>" +respStr );
//        Reporter.log("czcb respStr："+respStr);

        //验签
        if(!czcbSecurityService.verify(respStr)){
//            logger.info("验签失败");
//            throw new CzcbException("验签失败");
        }
        //解密
        String decryptedRespStr = czcbSecurityService.decrypt(respStr);
        logger.info("czcb decryptedRespStr:{}", decryptedRespStr);
//        Reporter.log("czcb decryptedRespStr："+decryptedRespStr);
        System.out.println( "czcb decryptedRespStr-->>>>" +decryptedRespStr );
        JSONObject decryptedRespJSONObject = JSON.parseObject(decryptedRespStr);
        String requestId = decryptedRespJSONObject.getString("requestId");
//        logger.info("czcb resp requestId:{}", requestId);
        System.out.println( "czcb resp requestId-->>>>" +requestId );
        if(!requestId.equals(String.valueOf(rootMap.get("requestId")))){
            System.out.println( "czcb resp requestId:{} not equals czcb req requestId:{}-->>>>" +requestId+ ";" + String.valueOf(rootMap.get("requestId")));
        }
    }

    // 内部帐子户开户接口
    public void od0007() throws CzcbException {
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
    public void oa0003() throws CzcbException {
        Map<String, Object> bizData = new HashMap<>();
        bizData.put("publicData", publicData);
        //合作商用户唯一ID
        bizData.put("openId", "10000007381");
        //姓名
        bizData.put("customerName", "杨秋华");
        //身份证
        bizData.put("idNo", "32012519930807171X");

//        //合作商用户唯一ID
//        bizData.put("openId", "17621776588");
//        //姓名
//        bizData.put("customerName", "许兴国");
//        //身份证
//        bizData.put("idNo", "42082219880108685X");

        rootMap.put("requestId", UUID.randomUUID().toString());
        rootMap.put("interfaceId", "oa0003");
        rootMap.put("bizData", bizData);

        post(rootMap);
    }

    // oa0005 身份证正反面影像传输接口（暂不使用）
    public void oa0005() throws CzcbException {
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
    public void oa0015() throws CzcbException {
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
        //订单号
        bizData.put("tradeNoJD", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
        //验证码（不必填）
       //bizData.put("activeCode", "");

//        //合作商用户唯一ID
//        bizData.put("openId", "17621776588");
//        //内部帐子户
//        bizData.put("bankUserId", "1560201403990018004062");
//        //姓名
//        bizData.put("customerName", "许兴国");
//        //身份证
//        bizData.put("idNo", "42082219880108685X");
//        //新卡银行卡号
//        bizData.put("bindCard", "6217852990749972142");
//        //新卡银行预留手机号
//        bizData.put("bindTel", "17621776588");
//        //订单号
//        bizData.put("tradeNoJD", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
//        //验证码（不必填）
//        bizData.put("activeCode", "");

        rootMap.put("requestId", UUID.randomUUID().toString());
        rootMap.put("interfaceId", "oa0015");
        rootMap.put("bizData", bizData);

        post(rootMap);
    }

    // 账户余额查询
    public void od0001() throws CzcbException {
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
    public void od0002() throws CzcbException {
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
        bizData.put("pageCount", "2");
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
    public void od0004() throws CzcbException {
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
    public void od0005(String ip, String port) throws CzcbException {
        Map<String, Object> bizData = new HashMap<>();
        bizData.put("publicData", publicData);
        //合作商用户唯一ID
        bizData.put("openId", "10000007381");
        //结汇币种
        bizData.put("currencyID", "14");

        String requestId = UUID.randomUUID().toString();
//        logger.info("czcb req requestId:{}", requestId);
        System.out.println( "czcb req requestId-->>>>" +requestId );
        rootMap.put("requestId", requestId);
        rootMap.put("interfaceId", "od0005");
        rootMap.put("bizData", bizData);

        post(rootMap,ip, port);
    }

    // 汇率查询
    public void od0005() throws CzcbException {
        Map<String, Object> bizData = new HashMap<>();
        bizData.put("publicData", publicData);
        //合作商用户唯一ID
        bizData.put("openId", "10000007381");
        //结汇币种
        bizData.put("currencyID", "14");

        String requestId = UUID.randomUUID().toString();
//        logger.info("czcb req requestId:{}", requestId);
        System.out.println( "czcb req requestId-->>>>" +requestId );
        rootMap.put("requestId", requestId);
        rootMap.put("interfaceId", "od0005");
        rootMap.put("bizData", bizData);

        post(rootMap);
    }

    // 合作机构账户清分（对私业务）
    public void od0003() throws CzcbException {
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
    public void od0006() throws CzcbException {
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
        bizData.put("bankCard", "6222620110033651440");
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
    public void od0008() throws CzcbException {
        Map<String, Object> bizData = new HashMap<>();
        bizData.put("publicData", publicData);
        //合作商用户唯一ID
        bizData.put("openId", "10000007381");
        //订单号
        bizData.put("tradeNoJD", "20190426211750495");

        rootMap.put("requestId", UUID.randomUUID().toString());
        rootMap.put("interfaceId", "od0008");
        rootMap.put("bizData", bizData);

        post(rootMap);
    }

    //合作机构账户清分（对公业务）
    public void od0009() throws CzcbException {
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
