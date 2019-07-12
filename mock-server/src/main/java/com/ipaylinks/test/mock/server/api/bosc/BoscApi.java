package com.ipaylinks.test.mock.server.api.bosc;

import com.ipaylinks.test.mock.server.api.bosc.dto.req.BoscReqDTO;
import com.ipaylinks.test.mock.server.api.bosc.dto.resp.BoscRespDTO;

public interface BoscApi {
    BoscRespDTO processJSONRequest(BoscReqDTO boscReqDTO, String requestURI) throws Exception;
}
