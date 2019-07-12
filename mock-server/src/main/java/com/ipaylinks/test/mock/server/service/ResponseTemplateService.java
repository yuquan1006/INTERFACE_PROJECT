package com.ipaylinks.test.mock.server.service;

import com.ipaylinks.test.mock.server.dao.model.ResponseTemplate;

public interface ResponseTemplateService {
    ResponseTemplate selectByAccessPoint(String accessPoint);
    ResponseTemplate selectByTemplateName(String templateName);
    ResponseTemplate selectByAccessPointAndTemplateName(String accessPoint, String templateName);
}
