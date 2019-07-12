package com.ipaylinks.test.mock.server.api.ae;

import com.ipaylinks.test.mock.server.util.Country3166_1;
import okhttp3.*;
import okhttp3.MediaType;
import okhttp3.logging.HttpLoggingInterceptor;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.packager.GenericPackager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class AeApiMainTest {
    public static void main(String[] args) throws Exception{
        testAeAuthorization();
        testAeReversalAdvice();
    }

    public static void testAeAuthorization() throws Exception {
        ISOMsg reqISOMsg = new ISOMsg();
        reqISOMsg.setMTI("1100");//信息类型
        reqISOMsg.set(2, "373953192351004");//卡号
        reqISOMsg.set(3, "004800");//processing code :004000 = Card Authorization Request;004800 = Combination Automated Address Verification (AAV) and Authorization
        reqISOMsg.set(4, "100");//交易金额
        SimpleDateFormat gmt = new SimpleDateFormat("MMddHHmmss");
        gmt.setTimeZone(TimeZone.getTimeZone("GMT"));
        reqISOMsg.set(7, gmt.format(new Date()));//GMT时间 MMddHHmmss
        reqISOMsg.set(11, RandomStringUtils.randomNumeric(6));//系统跟踪号
        reqISOMsg.set(12, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss")));//local时间 yyMMddHHmmss
        reqISOMsg.set(14, "2310");//卡有效期年月
        reqISOMsg.set(19, "156");//账单国家
        reqISOMsg.set(22, "1600S0S00000");//pos 数据码
        reqISOMsg.set(25, "1900");//消息原因码，由美国运通在认证期间提供。
        reqISOMsg.set(26, "5311");//MCC 商户类别码
        reqISOMsg.set(27, "6"); //美国运通返回2位或者6位的响应码
        reqISOMsg.set(37, RandomStringUtils.randomNumeric(12));//唯一的12个字符的参考号，如果包含在原始请求消息中，它将被保留并在响应消息中返回，而不做任何更改。
        reqISOMsg.set(41, "Shanghai");//商户终端所在地，如果包含在原始请求消息中，它将被保留并在响应消息中返回，而不做任何更改。
        reqISOMsg.set(42, "3004180667");//MerchNbr  商户号
        reqISOMsg.set(43, getField43());//账单名称\\账单街道\\账单城市\\邮政编码 地区码 国家码;邮政编码右边补空格到10位;地区码右边补空格到3位;
        reqISOMsg.set(49, "840");//交易币种
        reqISOMsg.set(53, "1234");//4位校验码，如果53域有值，则22域的第七位必须是9/S/W
        reqISOMsg.set(60, getField60());
        reqISOMsg.set(63, getField63());
        ByteArrayOutputStream reqStream = new ByteArrayOutputStream();
        PrintStream reqPrintStream = new PrintStream(reqStream);
        reqISOMsg.dump(reqPrintStream, "");
        System.out.println("reqStream:" + reqStream.toString());

//        String url = "https://qwww318.americanexpress.com/IPPayments/inter/CardAuthorization.do";
        String url = "http://127.0.0.1:8088/mock-server/ae";
        System.out.println("url:" + url);

        ISOPackager isoPackager128 = new GenericPackager("jar:AE128.xml");
        reqISOMsg.setPackager(isoPackager128);
        byte[] bytes = reqISOMsg.pack();
        String authorizationRequestParam = Hex.encodeHexString(bytes, false);
        String reqStr = "AuthorizationRequestParam=" + authorizationRequestParam;
        System.out.println("reqStr:" + reqStr);

        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), reqStr);
        Map<String, String> headers = new HashMap<>();
        headers.put("MerchNbr", reqISOMsg.getString(42));
        headers.put("origin", "iPaylinks");
        headers.put("region", "JAPA");
        headers.put("country", "156");
        headers.put("message", "ISO GCAG");
        headers.put("Connection", "keep-alive");
        headers.put("RtInd", "050");
        System.out.println("headers:" + headers);
        Request request = new Request.Builder().url(url).headers(Headers.of(headers)).post(requestBody).build();
        OkHttpClient okHttpClient = getOkHttpClient();
        Response response = okHttpClient.newCall(request).execute();
        if (response.body() != null) {
            String respStr = response.body().string();
            System.out.println("respStr:" + respStr);
            ISOMsg respISOMsg = new ISOMsg();
            ISOPackager isoPackager64 = new GenericPackager("jar:AE64.xml");
            respISOMsg.setPackager(isoPackager64);
            respISOMsg.unpack(Hex.decodeHex(respStr));
            ByteArrayOutputStream respStream = new ByteArrayOutputStream();
            PrintStream respPrintStream = new PrintStream(respStream);
            respISOMsg.dump(respPrintStream, "");
            System.out.println("respStream:" + respStream.toString());
        }

    }

    public static void testAeReversalAdvice() throws Exception {
        ISOMsg reqISOMsg = new ISOMsg();
        reqISOMsg.setMTI("1420");//信息类型
        reqISOMsg.set(2, "373953192351004");//卡号
        reqISOMsg.set(3, "024000");//processing code :024000t;004000
        reqISOMsg.set(4, "100");//交易金额
        reqISOMsg.set(11, RandomStringUtils.randomNumeric(6));//系统跟踪号
        reqISOMsg.set(12, LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss")));//local时间 yyMMddHHmmss
        reqISOMsg.set(14, "2310");//卡有效期年月
        reqISOMsg.set(19, "156");//账单国家
        reqISOMsg.set(22, "1600S0S00000");//pos 数据码
        reqISOMsg.set(25, "1400");//消息原因码，由美国运通在认证期间提供。
        reqISOMsg.set(26, "5311");//MCC 商户类别码
        reqISOMsg.set(31, RandomStringUtils.randomNumeric(15));//唯一的15个字符的参考号,是正向交易返回的，如果包含在原始请求消息中，它将被保留并在响应消息中返回，而不做任何更改。
        reqISOMsg.set(37, RandomStringUtils.randomNumeric(12));//唯一的12个字符的参考号，如果包含在原始请求消息中，它将被保留并在响应消息中返回，而不做任何更改。
        reqISOMsg.set(41, "Shanghai");//商户终端所在地，如果包含在原始请求消息中，它将被保留并在响应消息中返回，而不做任何更改。
        reqISOMsg.set(42, "3004180667");//MerchNbr  商户号
        reqISOMsg.set(49, "840");//交易币种
        reqISOMsg.set(56, getField56(reqISOMsg));//交易币种
        ByteArrayOutputStream reqStream = new ByteArrayOutputStream();
        PrintStream reqPrintStream = new PrintStream(reqStream);
        reqISOMsg.dump(reqPrintStream, "");
        System.out.println("reqStream:" + reqStream.toString());

//        String url = "https://qwww318.americanexpress.com/IPPayments/inter/CardAuthorization.do";
        String url = "http://127.0.0.1:8088/mock-server/ae";
        System.out.println("url:" + url);

        ISOPackager isoPackager128 = new GenericPackager("jar:AE128.xml");
        reqISOMsg.setPackager(isoPackager128);
        byte[] bytes = reqISOMsg.pack();
        String authorizationRequestParam = Hex.encodeHexString(bytes, false);
        String reqStr = "AuthorizationRequestParam=" + authorizationRequestParam;
        System.out.println("reqStr:" + reqStr);

        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain"), reqStr);
        Map<String, String> headers = new HashMap<>();
        headers.put("MerchNbr", reqISOMsg.getString(42));
        headers.put("origin", "iPaylinks");
        headers.put("region", "JAPA");
        headers.put("country", "156");
        headers.put("message", "ISO GCAG");
        headers.put("Connection", "keep-alive");
        headers.put("RtInd", "050");
        System.out.println("headers:" + headers);
        Request request = new Request.Builder().url(url).headers(Headers.of(headers)).post(requestBody).build();
        OkHttpClient okHttpClient = getOkHttpClient();
        Response response = okHttpClient.newCall(request).execute();
        if (response.body() != null) {
            String respStr = response.body().string();
            System.out.println("respStr:" + respStr);
            ISOMsg respISOMsg = new ISOMsg();
            ISOPackager isoPackager64 = new GenericPackager("jar:AE64.xml");
            respISOMsg.setPackager(isoPackager64);
            respISOMsg.unpack(Hex.decodeHex(respStr));
            ByteArrayOutputStream respStream = new ByteArrayOutputStream();
            PrintStream respPrintStream = new PrintStream(respStream);
            respISOMsg.dump(respPrintStream, "");
            System.out.println("respStream:" + respStream.toString());
        }

    }

    private static String getField56(ISOMsg reqISOMsg) {
        //撤销的交易类型 + 系统跟踪号 + local时间 yyMMddHHmmss + \
        return "1100" + reqISOMsg.getString(11) + reqISOMsg.getString(12) + "\\";
    }

    private static final long READ_TIME_OUT = 30L;
    private static final long CONNECT_TIME_OUT = 5L;

    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClientBuilder.retryOnConnectionFailure(true)
                .readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS);
        return okHttpClientBuilder.build();
    }

    private static String getField43() {
        String billName = "ipaylinks=qyjr";
        if (StringUtils.length(billName) > 38) {
            billName = billName.substring(0, 38);
        }
        String billStreet = "shangcheng Rd518 18A";
        if (StringUtils.length(billStreet) > 30) {
            billStreet = billStreet.substring(0, 30);
        }

        String billCity = "Shanghai";
        if (StringUtils.length(billCity) > 15) {
            billCity = billCity.substring(0, 15);
        }

        String postcode = StringUtils.rightPad(StringUtils.trimToEmpty("200200"), 10);

        String region = StringUtils.rightPad(StringUtils.trimToEmpty("62"), 3);

        String country = "156";

        return StringUtils.trimToEmpty(billName) + "\\" + billStreet + "\\" + StringUtils.trimToEmpty(billCity) + "\\" + postcode + region + country;
    }

    private static String getField60(){
        String sellerId = StringUtils.rightPad("10000003781", 20);
        String email = "qiuhua.yang@ipaylinks.com";
        String emailLength = String.format("%02d", StringUtils.length(email));
        String phone = StringUtils.rightPad(StringUtils.trimToEmpty("15261471747"), 20);
        byte[] bytes = new byte[4];

        if (StringUtils.isNotEmpty(sellerId)) {
            bytes[0] = (byte) (bytes[0] | (0x1 << 6));
        }
        if (StringUtils.isNotEmpty(email)) {
            bytes[0] = (byte) (bytes[0] | (0x1 << 5));
        }
        if (StringUtils.isNotEmpty(phone)) {
            bytes[0] = (byte) (bytes[0] | (0x1 << 4));
        }
        String bitMap = new String(bytes);
        return "AX" + "ADD" + bitMap + sellerId + emailLength + StringUtils.trimToEmpty(email) + phone;
    }

    private static String getField63() {
        String billPostcode = getRightPostcode("200200");
        String billAddress = getRightAddress("shangcheng Rd518 18A", 20);
        String billFirstName = getRightName("jiani", 15);
        String billLastName = getRightName("chen", 30);
        String billPhone = getRightPhone("18270839203");
        String shipFirstName = "qiuhua";
        String shipLastName = "yang";
        String shipPostcode = getRightPostcode("210000");
        String shipAddress = getRightAddress("shangcheng Rd518 18B", 50);
        shipFirstName = getRightName(shipFirstName, 15);
        shipLastName = getRightName(shipLastName, 30);
        String shipPhone = getRightPhone("15261471747");
        String shipCountry = getRightShipCountry("156");
        String format = "AE";
//        String format = "AD";
        String result = "AX" + format + billPostcode + billAddress;
        if (StringUtils.isNotBlank(billFirstName) || StringUtils.isNotBlank(billLastName)) {
            result = result + billFirstName + billLastName;
        }
        if (StringUtils.isNotBlank(billPhone) || StringUtils.isNotBlank(shipPostcode)
                || StringUtils.isNotBlank(shipAddress) || StringUtils.isNotBlank(shipFirstName)
                || StringUtils.isNotBlank(shipLastName) || StringUtils.isNotBlank(shipPhone)
                || StringUtils.isNotBlank(shipCountry)) {
            result = result + billPhone + shipPostcode + shipAddress + shipFirstName
                    + shipLastName + shipPhone + shipCountry;
        }
        return result;
    }

    private static String getRightPostcode(String postcode) {
        String result = postcode;
        if (StringUtils.isEmpty(postcode) || !StringUtils.isAlphanumeric(postcode)) {
            result = "200000";
        }
        if (StringUtils.length(result) > 9) {
            result = result.substring(0, 9);
        }
        result = StringUtils.rightPad(StringUtils.trimToEmpty(result), 9);
        return result;
    }

    private static String getRightAddress(String address, int length) {
        String result = address;
        if (StringUtils.isEmpty(address) || !StringUtils.isAsciiPrintable(address)) {
            result = "shangcheng Rd518 18A";
        }
        if (StringUtils.isNotEmpty(result) && result.length() > length) {
            result = result.substring(0, length);
        }
        result = StringUtils.rightPad(StringUtils.trimToEmpty(result), length);
        return result;
    }

    private static String getRightName(String name, int length) {
        String result = name;
        if (StringUtils.isEmpty(name) || !StringUtils.isAlphanumeric(name)) {
            result = "ANONYMOUS";
        }
        if (StringUtils.isNotEmpty(result) && result.length() > length) {
            result = result.substring(0, length);
        }
        result = StringUtils.rightPad(StringUtils.trimToEmpty(result), length);
        return result;
    }

    private static String getRightPhone(String phone) {
        String result = phone;
        if (StringUtils.isEmpty(phone) || !StringUtils.isAlphanumeric(phone)) {
            result = "15261471747";
        }
        if (StringUtils.length(result) > 10) {
            result = result.substring(0, 10);
        }
        result = StringUtils.rightPad(StringUtils.trimToEmpty(result), 10);
        return result;
    }

    private static String getRightShipCountry(String country) {
        String result = country;
        if (StringUtils.isNotEmpty(country)) {
            Country3166_1 country3166_1 = null;
            try {
                country3166_1 = Country3166_1.getByLetter2(result);
            } catch (Exception e) {
                try {
                    country3166_1 = Country3166_1.getByLetter3(result);
                } catch (Exception e1) {
                    try {
                        country3166_1 = Country3166_1.getByNumeric(result);
                    }catch (Exception e2){
                        country3166_1 = Country3166_1.CN;
                    }
                }
            }
            if (country3166_1 != null) {
                result = country3166_1.getNumeric();
            } else {
                result = "156";
            }
        } else {
            result = StringUtils.rightPad("156", 3);
        }
        return result;
    }

}