package com.ipaylinks.test.mock.server.service.impl;

import com.ipaylinks.test.mock.server.dao.mapper.NotifyRecordMapper;
import com.ipaylinks.test.mock.server.dao.model.NotifyRecordWithBLOBs;
import com.ipaylinks.test.mock.server.service.NotifyRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class NotifyRecordServiceImpl implements NotifyRecordService {
    @Resource
    private NotifyRecordMapper notifyRecordMapper;
    @Override
    public int insertSelective(NotifyRecordWithBLOBs record) {
        return notifyRecordMapper.insertSelective(record);
    }
}
