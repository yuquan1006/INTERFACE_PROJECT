package com.ipaylinks.test.mock.server.api.fuiou;

import com.ipaylinks.test.mock.server.api.fuiou.bo.req.FuiouNotifyRequest;
import com.ipaylinks.test.mock.server.api.fuiou.bo.req.FuiouOrderQueryPageRequest;
import com.ipaylinks.test.mock.server.api.fuiou.bo.req.FuiouSubmitOrderRequest;
import com.ipaylinks.test.mock.server.api.fuiou.bo.resp.FuiouNotifyResponse;
import com.ipaylinks.test.mock.server.api.fuiou.bo.resp.FuiouOrderQueryPageResponse;
import com.ipaylinks.test.mock.server.api.fuiou.bo.resp.FuiouSubmitOrderResponse;
import com.ipaylinks.test.mock.server.api.fuiou.dto.req.FuiouRequestDTO;

public interface FuiouApi {
    String processXMLRequest(FuiouRequestDTO fuiouRequestDTO, String accessPoint);
    FuiouNotifyResponse notify(FuiouNotifyRequest fuiouNotifyRequest, String accessPoint) throws IllegalAccessException;
    FuiouSubmitOrderResponse submitOrder(FuiouSubmitOrderRequest fuiouSubmitOrderRequest, String requestURI) throws IllegalAccessException;
    FuiouOrderQueryPageResponse orderQueryPage(FuiouOrderQueryPageRequest fuiouOrderQueryPageRequest, String requestURI) throws IllegalAccessException;
}
