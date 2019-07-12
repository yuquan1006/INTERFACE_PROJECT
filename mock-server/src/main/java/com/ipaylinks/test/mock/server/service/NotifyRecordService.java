package com.ipaylinks.test.mock.server.service;

import com.ipaylinks.test.mock.server.dao.model.NotifyRecordWithBLOBs;

public interface NotifyRecordService {
    int insertSelective(NotifyRecordWithBLOBs record);
}
