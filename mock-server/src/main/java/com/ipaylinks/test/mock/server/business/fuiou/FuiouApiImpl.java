package com.ipaylinks.test.mock.server.business.fuiou;

import com.ipaylinks.test.mock.server.api.fuiou.FuiouApi;
import com.ipaylinks.test.mock.server.api.fuiou.bo.req.*;
import com.ipaylinks.test.mock.server.api.fuiou.bo.resp.*;
import com.ipaylinks.test.mock.server.api.fuiou.dto.req.FuiouRequestDTO;
import com.ipaylinks.test.mock.server.config.FuiouProperties;
import com.ipaylinks.test.mock.server.util.JAXBUtils;
import com.ipaylinks.test.mock.server.util.MD5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FuiouApiImpl implements FuiouApi {
    private Logger logger = LoggerFactory.getLogger(FuiouApiImpl.class);

    @Autowired
    private FuiouProperties fuiouProperties;

    @Autowired
    private FuiouBusinessService fuiouBusinessService;

    @Override
    public String processXMLRequest(FuiouRequestDTO fuiouRequestDTO, String accessPoint) {
        String respStr = null;
        logger.info("fuiou 请求报文:{}", fuiouRequestDTO);
        String reqStr = fuiouRequestDTO.getReqStr();
        boolean verifyResult = MD5Utils.verify(reqStr + "|" + fuiouProperties.getXmlMd5Key(),"UTF-8", fuiouRequestDTO.getSign());
        logger.info("fuiou 请求报文验签结果 verifyResult:{}", verifyResult);

        FuiouRequest fuiouRequest = JAXBUtils.formXML(reqStr, FuiouRequest.class);
        String messageCode = fuiouRequest.getHead().getMessageCode();
        if (FuiouInterfaceEnum.TRANSFER.getCode().equals(messageCode)){
            FuiouTransferRequest fuiouTransferRequest = JAXBUtils.formXML(reqStr, FuiouTransferRequest.class);
            respStr = fuiouBusinessService.transfer(fuiouTransferRequest, accessPoint);
        } else if (FuiouInterfaceEnum.TRANS_DETAILS_QUERY.getCode().equals(messageCode)){
            TransDetailsQueryRequest transDetailsQueryRequest = JAXBUtils.formXML(reqStr, TransDetailsQueryRequest.class);
            respStr = fuiouBusinessService.transDetailsQuery(transDetailsQueryRequest, accessPoint);
        } else {
            logger.info("不支持的接口 messageCode:{}", messageCode);
        }
        logger.info("fuiou 响应报文:{}", respStr);
        return respStr;
    }

    @Override
    public FuiouNotifyResponse notify(FuiouNotifyRequest fuiouNotifyRequest, String accessPoint) throws IllegalAccessException {
        return fuiouBusinessService.notify(fuiouNotifyRequest, accessPoint);
    }

    @Override
    public FuiouSubmitOrderResponse submitOrder(FuiouSubmitOrderRequest fuiouSubmitOrderRequest, String accessPoint) throws IllegalAccessException {
        return fuiouBusinessService.submitOrder(fuiouSubmitOrderRequest, accessPoint);
    }

    @Override
    public FuiouOrderQueryPageResponse orderQueryPage(FuiouOrderQueryPageRequest fuiouOrderQueryPageRequest, String accessPoint) throws IllegalAccessException {
        return fuiouBusinessService.orderQueryPage(fuiouOrderQueryPageRequest, accessPoint);
    }

}
