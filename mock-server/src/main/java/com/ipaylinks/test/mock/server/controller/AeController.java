package com.ipaylinks.test.mock.server.controller;

import com.ipaylinks.test.mock.server.api.ae.AeApi;
import com.ipaylinks.test.mock.server.util.ServletUtils;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOPackager;
import org.jpos.iso.packager.GenericPackager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@RestController
public class AeController {
    private Logger logger = LoggerFactory.getLogger(AeController.class);
    @Autowired
    private AeApi aeApi;
    public AeController() throws ISOException {
    }
    private ISOPackager isoPackager128 = new GenericPackager("jar:AE128.xml");
    private ISOPackager isoPackager64 = new GenericPackager("jar:AE64.xml");

    @RequestMapping(value = {"/ae"}, produces = {"text/plain;charset=UTF-8"})
    public String processISO8583Request(HttpServletRequest httpServletRequest) throws Exception {
        String requestString = ServletUtils.convertStreamToString(httpServletRequest.getInputStream());
        logger.info("requestString:{}",requestString);
        String[] requestStrings = requestString.split("=");
        String authorizationRequestParam = requestStrings[1].trim();

        ISOMsg reqISOMsg = new ISOMsg();
        reqISOMsg.setPackager(isoPackager128);
        reqISOMsg.unpack(Hex.decodeHex(authorizationRequestParam));
        ISOMsg respISOMsg = aeApi.processISO8583Request(reqISOMsg, httpServletRequest.getRequestURI());

        respISOMsg.setPackager(isoPackager64);
        byte[] bytes = respISOMsg.pack();
        String respStr = Hex.encodeHexString(bytes, false);
        logger.info("respStr:{}",respStr);
        return respStr;
    }

}
