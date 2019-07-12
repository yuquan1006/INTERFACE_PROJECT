package com.ipaylinks.test.mock.server.api.czcb;

import com.ipaylinks.test.mock.server.api.czcb.dto.req.CzcbReqDTO;
import com.ipaylinks.test.mock.server.api.czcb.dto.resp.CzcbRespDTO;
import com.ipaylinks.test.mock.server.service.czcb.exception.CzcbException;

public interface CzcbApi {

    CzcbRespDTO processJSONRequest(CzcbReqDTO czcbReqDTO, String accessPoint) throws CzcbException;

}
