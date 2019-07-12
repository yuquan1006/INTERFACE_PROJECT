package com.ipaylinks.test.mock.server.business.bosc;

import com.ipaylinks.test.mock.server.api.bosc.BoscApi;
import com.ipaylinks.test.mock.server.api.bosc.bo.req.*;
import com.ipaylinks.test.mock.server.api.bosc.bo.resp.*;
import com.ipaylinks.test.mock.server.api.bosc.dto.req.BoscReqDTO;
import com.ipaylinks.test.mock.server.api.bosc.dto.resp.BoscRespDTO;
import com.ipaylinks.test.mock.server.service.bosc.BoscSecurityService;
import com.ipaylinks.test.mock.server.util.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Service
public class BoscApiImpl implements BoscApi {
    private Logger logger = LoggerFactory.getLogger(BoscApiImpl.class);

    @Resource(name = "serverBoscSecurityService")
    private BoscSecurityService boscSecurityService;

    @Autowired
    private BoscBusinessService boscBusinessService;

    @Override
    public BoscRespDTO processJSONRequest(BoscReqDTO boscReqDTO, String accessPoint) throws Exception {
        Map<String, Map<String, String>> reqDocument = boscReqDTO.getDocument();
        Map<String, String> reqHead = reqDocument.get(BoscConstants.HEAD);
        Map<String, String> reqBody = reqDocument.get(BoscConstants.BODY);

        //获取请求报文加签源串
        Map<String, String> reqSignMap = new HashMap<>();
        reqSignMap.putAll(reqHead);
        reqSignMap.putAll(reqBody);
        String reqSignData = getSignData(reqSignMap);
        String reqSignature = reqBody.get(BoscConstants.Signature);

        logger.info("bosc 请求报文加签源串:{}", reqSignData);
        logger.info("bosc 请求报文加签串:{}", reqSignature);

        boolean verifyResult = boscSecurityService.verify(reqSignData.getBytes(BoscConstants.GBK), reqSignature, BoscSecurityService.SIGNATURE_ALGORITHM_SHA1);
        logger.info("bosc 请求报文验签结果 verifyResult:{}", verifyResult);

        BoscReqHead reqHeadBO = BeanUtil.mapToBean(reqHead, BoscReqHead.class);

        Map<String, Map<String, String>> respDocument = new HashMap<>();
        Map<String, String> respHead = null;
        Map<String, String> respBody = null;

        String tranCode = reqHeadBO.getHeadTranCode();
        if (BoscInterfaceEnum.IBPS_OPENACCT_000001.getCode().equals(tranCode)){
            IBPS_OPENACCT_000001ReqBody reqBodyBO = BeanUtil.mapToBean(reqBody, IBPS_OPENACCT_000001ReqBody.class);

            BoscReqDocument<IBPS_OPENACCT_000001ReqBody> reqDocumentBO = new BoscReqDocument<>();
            reqDocumentBO.setHead(reqHeadBO);
            reqDocumentBO.setBody(reqBodyBO);

            BoscRespDocument<IBPS_OPENACCT_000001RespBody> respDocumentBO = boscBusinessService.openacct000001(reqDocumentBO, accessPoint);
            BoscRespHead respHeadBO = respDocumentBO.getHead();
            respHead = BeanUtil.beanToMap(respHeadBO);
            IBPS_OPENACCT_000001RespBody respBodyBO = respDocumentBO.getBody();
            respBody = BeanUtil.beanToMap(respBodyBO);

        } else if (BoscInterfaceEnum.IBPS_OPENACCT_000002.getCode().equals(tranCode)){
            IBPS_OPENACCT_000002ReqBody reqBodyBO = BeanUtil.mapToBean(reqBody, IBPS_OPENACCT_000002ReqBody.class);

            BoscReqDocument<IBPS_OPENACCT_000002ReqBody> reqDocumentBO = new BoscReqDocument<>();
            reqDocumentBO.setHead(reqHeadBO);
            reqDocumentBO.setBody(reqBodyBO);

            BoscRespDocument<IBPS_OPENACCT_000002RespBody> respDocumentBO = boscBusinessService.openacct000002(reqDocumentBO, accessPoint);
            BoscRespHead respHeadBO = respDocumentBO.getHead();
            respHead = BeanUtil.beanToMap(respHeadBO);
            IBPS_OPENACCT_000002RespBody respBodyBO = respDocumentBO.getBody();
            respBody = BeanUtil.beanToMap(respBodyBO);

        } else if (BoscInterfaceEnum.IBPS_OPENACCT_000004.getCode().equals(tranCode)){
            IBPS_OPENACCT_000004ReqBody reqBodyBO = BeanUtil.mapToBean(reqBody, IBPS_OPENACCT_000004ReqBody.class);

            BoscReqDocument<IBPS_OPENACCT_000004ReqBody> reqDocumentBO = new BoscReqDocument<>();
            reqDocumentBO.setHead(reqHeadBO);
            reqDocumentBO.setBody(reqBodyBO);

            BoscRespDocument<IBPS_OPENACCT_000004RespBody> respDocumentBO = boscBusinessService.openacct000004(reqDocumentBO, accessPoint);
            BoscRespHead respHeadBO = respDocumentBO.getHead();
            respHead = BeanUtil.beanToMap(respHeadBO);
            IBPS_OPENACCT_000004RespBody respBodyBO = respDocumentBO.getBody();
            respBody = BeanUtil.beanToMap(respBodyBO);

        } else if (BoscInterfaceEnum.IBPS_IBS_140002.getCode().equals(tranCode)){
            IBPS_IBS_140002ReqBody reqBodyBO = BeanUtil.mapToBean(reqBody, IBPS_IBS_140002ReqBody.class);

            BoscReqDocument<IBPS_IBS_140002ReqBody> reqDocumentBO = new BoscReqDocument<>();
            reqDocumentBO.setHead(reqHeadBO);
            reqDocumentBO.setBody(reqBodyBO);

            BoscRespDocument<IBPS_IBS_140002RespBody> respDocumentBO = boscBusinessService.ibs140002(reqDocumentBO, accessPoint);
            BoscRespHead respHeadBO = respDocumentBO.getHead();
            respHead = BeanUtil.beanToMap(respHeadBO);
            IBPS_IBS_140002RespBody respBodyBO = respDocumentBO.getBody();
            respBody = BeanUtil.beanToMap(respBodyBO);

        } else if (BoscInterfaceEnum.IBPS_IBS_110018.getCode().equals(tranCode)){
            IBPS_IBS_110018ReqBody reqBodyBO = BeanUtil.mapToBean(reqBody, IBPS_IBS_110018ReqBody.class);

            BoscReqDocument<IBPS_IBS_110018ReqBody> reqDocumentBO = new BoscReqDocument<>();
            reqDocumentBO.setHead(reqHeadBO);
            reqDocumentBO.setBody(reqBodyBO);

            BoscRespDocument<IBPS_IBS_110018RespBody> respDocumentBO = boscBusinessService.ibs110018(reqDocumentBO, accessPoint);
            BoscRespHead respHeadBO = respDocumentBO.getHead();
            respHead = BeanUtil.beanToMap(respHeadBO);
            IBPS_IBS_110018RespBody respBodyBO = respDocumentBO.getBody();
            respBody = BeanUtil.beanToMap(respBodyBO);

        } else {
            logger.info("不支持的接口 HeadTranCode:{}", tranCode);
        }

        //获取响应报文加签源串
        Map<String, String> respSignMap = new HashMap<>();
        if (respHead != null) {
            respSignMap.putAll(respHead);
        }
        if (respBody != null) {
            respSignMap.putAll(respBody);
        }
        String respSignData = getSignData(respSignMap);

        String respSignature = boscSecurityService.sign(respSignData.getBytes(BoscConstants.GBK), BoscSecurityService.SIGNATURE_ALGORITHM_SHA1).replace("\r\n", "");
        if (respBody != null) {
            respBody.put(BoscConstants.Signature, respSignature);
        }
        logger.info("bosc 响应报文加签源串:{}", respSignData);
        logger.info("bosc 响应报文加签串:{}", respSignature);

        respDocument.put(BoscConstants.HEAD, respHead);
        respDocument.put(BoscConstants.BODY, respBody);
        return new BoscRespDTO(respDocument);
    }

    /**
     *  获取加签源串
     * @param map Map<String, String>
     * @return 加签源串
     */
    private String getSignData(Map<String, String> map) {
        StringBuilder signData = new StringBuilder();
        TreeMap<String, String> treeMap = new TreeMap<>(map);
        for(Map.Entry<String, String> entry : treeMap.entrySet()){
            if(!StringUtils.isEmpty(entry.getValue()) && !BoscConstants.Signature.equals(entry.getKey())
                    && !BoscConstants.koalB64Cert.equals(entry.getKey())){
                signData.append(entry.getKey());
                signData.append(BoscConstants.EQUAL);
                signData.append(entry.getValue());
                signData.append(BoscConstants.AND);
            }
        }
        //去掉末尾的&
        signData.setLength(signData.length()-1);
        return signData.toString();
    }

}
