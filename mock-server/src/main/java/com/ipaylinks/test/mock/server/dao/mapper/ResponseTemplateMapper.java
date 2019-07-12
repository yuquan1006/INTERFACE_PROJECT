package com.ipaylinks.test.mock.server.dao.mapper;

import com.ipaylinks.test.mock.server.dao.model.ResponseTemplate;

public interface ResponseTemplateMapper {
    int insert(ResponseTemplate record);

    int insertSelective(ResponseTemplate record);

    ResponseTemplate findOne(ResponseTemplate record);
}