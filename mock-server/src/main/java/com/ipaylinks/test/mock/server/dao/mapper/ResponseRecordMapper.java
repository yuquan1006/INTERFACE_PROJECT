package com.ipaylinks.test.mock.server.dao.mapper;

import com.ipaylinks.test.mock.server.dao.model.ResponseRecord;
import com.ipaylinks.test.mock.server.dao.model.ResponseRecordWithBLOBs;

public interface ResponseRecordMapper {
    int insert(ResponseRecordWithBLOBs record);

    int insertSelective(ResponseRecordWithBLOBs record);

    ResponseRecordWithBLOBs findOne(ResponseRecord record);
}