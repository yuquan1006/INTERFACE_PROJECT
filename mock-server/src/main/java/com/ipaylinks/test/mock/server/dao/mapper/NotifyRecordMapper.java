package com.ipaylinks.test.mock.server.dao.mapper;

import com.ipaylinks.test.mock.server.dao.model.NotifyRecordWithBLOBs;

public interface NotifyRecordMapper {
    int insert(NotifyRecordWithBLOBs record);

    int insertSelective(NotifyRecordWithBLOBs record);
}