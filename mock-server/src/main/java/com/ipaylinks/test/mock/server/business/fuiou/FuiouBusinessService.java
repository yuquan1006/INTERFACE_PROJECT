package com.ipaylinks.test.mock.server.business.fuiou;

import com.ipaylinks.test.mock.server.api.fuiou.bo.req.*;
import com.ipaylinks.test.mock.server.api.fuiou.bo.resp.*;

public interface FuiouBusinessService {
    String transfer(FuiouTransferRequest fuiouTransferRequest, String accessPoint);
    String transDetailsQuery(TransDetailsQueryRequest transDetailsQueryRequest, String accessPoint);

    FuiouNotifyResponse notify(FuiouNotifyRequest fuiouNotifyRequest, String accessPoint) throws IllegalAccessException;
    FuiouSubmitOrderResponse submitOrder(FuiouSubmitOrderRequest fuiouSubmitOrderRequest, String accessPoint) throws IllegalAccessException;
    FuiouOrderQueryPageResponse orderQueryPage(FuiouOrderQueryPageRequest fuiouOrderQueryPageRequest, String accessPoint) throws IllegalAccessException;
}
