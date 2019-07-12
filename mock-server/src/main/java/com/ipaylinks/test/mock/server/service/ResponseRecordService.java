package com.ipaylinks.test.mock.server.service;

import com.ipaylinks.test.mock.server.dao.model.ResponseRecordWithBLOBs;

public interface ResponseRecordService {
    int insertSelective(ResponseRecordWithBLOBs record);

    ResponseRecordWithBLOBs selectByUniqueRequestId(String uniqueRequestId);


    ResponseRecordWithBLOBs selectByAccessPointAndTemplateNameAndUniqueRequestId(String accessPoint, String templateName, String uniqueRequestId);
}
