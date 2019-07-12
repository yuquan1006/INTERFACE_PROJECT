package com.ipaylinks.test.mock.server.business.czcb;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ipaylinks.test.mock.server.api.czcb.CzcbApi;
import com.ipaylinks.test.mock.server.api.czcb.bo.req.*;
import com.ipaylinks.test.mock.server.api.czcb.bo.resp.*;
import com.ipaylinks.test.mock.server.api.czcb.dto.req.CzcbReqDTO;
import com.ipaylinks.test.mock.server.api.czcb.dto.resp.CzcbRespDTO;
import com.ipaylinks.test.mock.server.service.czcb.CzcbSecurityService;
import com.ipaylinks.test.mock.server.service.czcb.CzcbSecurityServiceImpl;
import com.ipaylinks.test.mock.server.service.czcb.exception.CzcbException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CzcbApiImpl implements CzcbApi {
    private Logger logger = LoggerFactory.getLogger(CzcbApiImpl.class);
    @Resource(name = "serverCzcbSecurityService")
    private CzcbSecurityService czcbSecurityService;
    @Autowired
    private CzcbBusinessService czcbBusinessService;
    @Override
    public CzcbRespDTO processJSONRequest(CzcbReqDTO czcbReqDTO, String accessPoint) throws CzcbException {
        CzcbRespDTO czcbRespDTO = null;
        String czcbRespJson = null;

        String czcbReqJson = JSON.toJSONString(czcbReqDTO);
        //对请求报文进行验签
        boolean verifyResult = czcbSecurityService.verify(czcbReqJson);
        //对请求报文进行解密
        String decryptedCzcbReqJson = czcbSecurityService.decrypt(czcbReqJson);

        JSONObject jsonObject = JSONObject.parseObject(decryptedCzcbReqJson);
        String bizData = jsonObject.getString(CzcbSecurityServiceImpl.ENCRYPT_FIELD);

        String interfaceId = czcbReqDTO.getInterfaceId();
        logger.info("czcb 接口:{} 请求报文:{}", interfaceId, czcbReqJson);
        logger.info("czcb 接口:{} 请求报文验签结果 verifyResult:{}", interfaceId, verifyResult);
        logger.info("czcb 接口:{} 请求报文解密:{}", interfaceId, decryptedCzcbReqJson);
        if (CzcbInterfaceEnum.OD0007.getCode().equals(interfaceId)){
            CzcbOd0007Req od0007Req = JSON.parseObject(bizData, CzcbOd0007Req.class);
            CzcbReq<CzcbOd0007Req> czcbReq = new CzcbReq<>(od0007Req);
            czcbReq.setRequestId(czcbReqDTO.getRequestId());
            czcbReq.setChannelId(czcbReqDTO.getChannelId());
            czcbReq.setInterfaceId(czcbReqDTO.getInterfaceId());
            czcbReq.setTimestamp(czcbReqDTO.getTimestamp());
            czcbReq.setSignature(czcbReqDTO.getSignature());
            CzcbResp<CzcbOd0007Resp> czcbResp = czcbBusinessService.od0007(czcbReq, accessPoint);
            czcbRespJson = JSON.toJSONString(czcbResp);
        } else if (CzcbInterfaceEnum.OA0003.getCode().equals(interfaceId)){
            CzcbOa0003Req oa0003Req = JSON.parseObject(bizData, CzcbOa0003Req.class);
            CzcbReq<CzcbOa0003Req> czcbReq = new CzcbReq<>(oa0003Req);
            czcbReq.setRequestId(czcbReqDTO.getRequestId());
            czcbReq.setChannelId(czcbReqDTO.getChannelId());
            czcbReq.setInterfaceId(czcbReqDTO.getInterfaceId());
            czcbReq.setTimestamp(czcbReqDTO.getTimestamp());
            czcbReq.setSignature(czcbReqDTO.getSignature());
            CzcbResp<CzcbOa0003Resp> czcbResp = czcbBusinessService.oa0003(czcbReq, accessPoint);
            czcbRespJson = JSON.toJSONString(czcbResp);
        } else if (CzcbInterfaceEnum.OA0005.getCode().equals(interfaceId)){
            CzcbOa0005Req oa0005Req = JSON.parseObject(bizData, CzcbOa0005Req.class);
            CzcbReq<CzcbOa0005Req> czcbReq = new CzcbReq<>(oa0005Req);
            czcbReq.setRequestId(czcbReqDTO.getRequestId());
            czcbReq.setChannelId(czcbReqDTO.getChannelId());
            czcbReq.setInterfaceId(czcbReqDTO.getInterfaceId());
            czcbReq.setTimestamp(czcbReqDTO.getTimestamp());
            czcbReq.setSignature(czcbReqDTO.getSignature());
            CzcbResp<CzcbOa0005Resp> czcbResp = czcbBusinessService.oa0005(czcbReq, accessPoint);
            czcbRespJson = JSON.toJSONString(czcbResp);
        } else if (CzcbInterfaceEnum.OA0015.getCode().equals(interfaceId)){
            CzcbOa0015Req oa0015Req = JSON.parseObject(bizData, CzcbOa0015Req.class);
            CzcbReq<CzcbOa0015Req> czcbReq = new CzcbReq<>(oa0015Req);
            czcbReq.setRequestId(czcbReqDTO.getRequestId());
            czcbReq.setChannelId(czcbReqDTO.getChannelId());
            czcbReq.setInterfaceId(czcbReqDTO.getInterfaceId());
            czcbReq.setTimestamp(czcbReqDTO.getTimestamp());
            czcbReq.setSignature(czcbReqDTO.getSignature());
            CzcbResp<CzcbOa0015Resp> czcbResp = czcbBusinessService.oa0015(czcbReq, accessPoint);
            czcbRespJson = JSON.toJSONString(czcbResp);
        } else if (CzcbInterfaceEnum.OD0001.getCode().equals(interfaceId)){
            CzcbOd0001Req od0001Req = JSON.parseObject(bizData, CzcbOd0001Req.class);
            CzcbReq<CzcbOd0001Req> czcbReq = new CzcbReq<>(od0001Req);
            czcbReq.setRequestId(czcbReqDTO.getRequestId());
            czcbReq.setChannelId(czcbReqDTO.getChannelId());
            czcbReq.setInterfaceId(czcbReqDTO.getInterfaceId());
            czcbReq.setTimestamp(czcbReqDTO.getTimestamp());
            czcbReq.setSignature(czcbReqDTO.getSignature());
            CzcbResp<CzcbOd0001Resp> czcbResp = czcbBusinessService.od0001(czcbReq, accessPoint);
            czcbRespJson = JSON.toJSONString(czcbResp);
        } else if (CzcbInterfaceEnum.OD0002.getCode().equals(interfaceId)){
            CzcbOd0002Req od0002Req = JSON.parseObject(bizData, CzcbOd0002Req.class);
            CzcbReq<CzcbOd0002Req> czcbReq = new CzcbReq<>(od0002Req);
            czcbReq.setRequestId(czcbReqDTO.getRequestId());
            czcbReq.setChannelId(czcbReqDTO.getChannelId());
            czcbReq.setInterfaceId(czcbReqDTO.getInterfaceId());
            czcbReq.setTimestamp(czcbReqDTO.getTimestamp());
            czcbReq.setSignature(czcbReqDTO.getSignature());
            CzcbResp<CzcbOd0002Resp> czcbResp = czcbBusinessService.od0002(czcbReq, accessPoint);
            czcbRespJson = JSON.toJSONString(czcbResp);
        } else if (CzcbInterfaceEnum.OD0004.getCode().equals(interfaceId)){
            CzcbOd0004Req od0004Req = JSON.parseObject(bizData, CzcbOd0004Req.class);
            CzcbReq<CzcbOd0004Req> czcbReq = new CzcbReq<>(od0004Req);
            czcbReq.setRequestId(czcbReqDTO.getRequestId());
            czcbReq.setChannelId(czcbReqDTO.getChannelId());
            czcbReq.setInterfaceId(czcbReqDTO.getInterfaceId());
            czcbReq.setTimestamp(czcbReqDTO.getTimestamp());
            czcbReq.setSignature(czcbReqDTO.getSignature());
            CzcbResp<CzcbOd0004Resp> czcbResp = czcbBusinessService.od0004(czcbReq, accessPoint);
            czcbRespJson = JSON.toJSONString(czcbResp);
        } else if (CzcbInterfaceEnum.OD0005.getCode().equals(interfaceId)){
            CzcbOd0005Req od0005Req = JSON.parseObject(bizData, CzcbOd0005Req.class);
            CzcbReq<CzcbOd0005Req> czcbReq = new CzcbReq<>(od0005Req);
            czcbReq.setRequestId(czcbReqDTO.getRequestId());
            czcbReq.setChannelId(czcbReqDTO.getChannelId());
            czcbReq.setInterfaceId(czcbReqDTO.getInterfaceId());
            czcbReq.setTimestamp(czcbReqDTO.getTimestamp());
            czcbReq.setSignature(czcbReqDTO.getSignature());
            CzcbResp<CzcbOd0005Resp> czcbResp = czcbBusinessService.od0005(czcbReq, accessPoint);
            czcbRespJson = JSON.toJSONString(czcbResp);
        } else if (CzcbInterfaceEnum.OD0003.getCode().equals(interfaceId)){
            CzcbOd0003Req od0003Req = JSON.parseObject(bizData, CzcbOd0003Req.class);
            CzcbReq<CzcbOd0003Req> czcbReq = new CzcbReq<>(od0003Req);
            czcbReq.setRequestId(czcbReqDTO.getRequestId());
            czcbReq.setChannelId(czcbReqDTO.getChannelId());
            czcbReq.setInterfaceId(czcbReqDTO.getInterfaceId());
            czcbReq.setTimestamp(czcbReqDTO.getTimestamp());
            czcbReq.setSignature(czcbReqDTO.getSignature());
            CzcbResp<CzcbOd0003Resp> czcbResp = czcbBusinessService.od0003(czcbReq, accessPoint);
            czcbRespJson = JSON.toJSONString(czcbResp);
        } else if (CzcbInterfaceEnum.OD0006.getCode().equals(interfaceId)){
            CzcbOd0006Req od0006Req = JSON.parseObject(bizData, CzcbOd0006Req.class);
            CzcbReq<CzcbOd0006Req> czcbReq = new CzcbReq<>(od0006Req);
            czcbReq.setRequestId(czcbReqDTO.getRequestId());
            czcbReq.setChannelId(czcbReqDTO.getChannelId());
            czcbReq.setInterfaceId(czcbReqDTO.getInterfaceId());
            czcbReq.setTimestamp(czcbReqDTO.getTimestamp());
            czcbReq.setSignature(czcbReqDTO.getSignature());
            CzcbResp<CzcbOd0006Resp> czcbResp = czcbBusinessService.od0006(czcbReq, accessPoint);
            czcbRespJson = JSON.toJSONString(czcbResp);
        } else if (CzcbInterfaceEnum.OD0008.getCode().equals(interfaceId)){
            CzcbOd0008Req od0008Req = JSON.parseObject(bizData, CzcbOd0008Req.class);
            CzcbReq<CzcbOd0008Req> czcbReq = new CzcbReq<>(od0008Req);
            czcbReq.setRequestId(czcbReqDTO.getRequestId());
            czcbReq.setChannelId(czcbReqDTO.getChannelId());
            czcbReq.setInterfaceId(czcbReqDTO.getInterfaceId());
            czcbReq.setTimestamp(czcbReqDTO.getTimestamp());
            czcbReq.setSignature(czcbReqDTO.getSignature());
            CzcbResp<CzcbOd0008Resp> czcbResp = czcbBusinessService.od0008(czcbReq, accessPoint);
            czcbRespJson = JSON.toJSONString(czcbResp);
        } else if (CzcbInterfaceEnum.OD0009.getCode().equals(interfaceId)){
            CzcbOd0009Req od0009Req = JSON.parseObject(bizData, CzcbOd0009Req.class);
            CzcbReq<CzcbOd0009Req> czcbReq = new CzcbReq<>(od0009Req);
            czcbReq.setRequestId(czcbReqDTO.getRequestId());
            czcbReq.setChannelId(czcbReqDTO.getChannelId());
            czcbReq.setInterfaceId(czcbReqDTO.getInterfaceId());
            czcbReq.setTimestamp(czcbReqDTO.getTimestamp());
            czcbReq.setSignature(czcbReqDTO.getSignature());
            CzcbResp<CzcbOd0009Resp> czcbResp = czcbBusinessService.od0009(czcbReq, accessPoint);
            czcbRespJson = JSON.toJSONString(czcbResp);
        } else {
            logger.info("不支持的接口 interfaceId:{}", interfaceId);
        }
        //对返回报文进行加密后加签
        String signedCzcbRespJson = czcbSecurityService.sign(czcbRespJson);
        czcbRespDTO = JSON.parseObject(signedCzcbRespJson,CzcbRespDTO.class);
        logger.info("czcb 接口:{} 响应报文:{}", interfaceId, czcbRespJson);
        logger.info("czcb 接口:{} 响应报文加密后加签:{}", interfaceId, signedCzcbRespJson);
        return czcbRespDTO;
    }

}
