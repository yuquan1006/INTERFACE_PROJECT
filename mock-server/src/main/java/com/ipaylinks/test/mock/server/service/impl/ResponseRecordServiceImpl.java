package com.ipaylinks.test.mock.server.service.impl;

import com.ipaylinks.test.mock.server.dao.mapper.ResponseRecordMapper;
import com.ipaylinks.test.mock.server.dao.model.ResponseRecord;
import com.ipaylinks.test.mock.server.dao.model.ResponseRecordWithBLOBs;
import com.ipaylinks.test.mock.server.service.ResponseRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ResponseRecordServiceImpl implements ResponseRecordService {
    @Resource
    private ResponseRecordMapper responseRecordMapper;

    @Override
    public int insertSelective(ResponseRecordWithBLOBs record) {
        return responseRecordMapper.insertSelective(record);
    }

    @Override
    public ResponseRecordWithBLOBs selectByUniqueRequestId(String uniqueRequestId) {
        ResponseRecord responseRecord = new ResponseRecord();
        responseRecord.setUniqueRequestId(uniqueRequestId);
        return responseRecordMapper.findOne(responseRecord);
    }

    @Override
    public ResponseRecordWithBLOBs selectByAccessPointAndTemplateNameAndUniqueRequestId(String accessPoint, String templateName, String uniqueRequestId) {
        ResponseRecord responseRecord = new ResponseRecord();
        responseRecord.setAccessPoint(accessPoint);
        responseRecord.setResponseTemplateName(templateName);
        responseRecord.setUniqueRequestId(uniqueRequestId);
        return responseRecordMapper.findOne(responseRecord);
    }
}
