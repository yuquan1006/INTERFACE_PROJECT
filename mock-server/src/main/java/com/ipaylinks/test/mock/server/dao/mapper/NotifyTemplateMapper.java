package com.ipaylinks.test.mock.server.dao.mapper;

import com.ipaylinks.test.mock.server.dao.model.NotifyTemplate;

public interface NotifyTemplateMapper {
    int insert(NotifyTemplate record);

    int insertSelective(NotifyTemplate record);

    NotifyTemplate findOne(NotifyTemplate record);
}