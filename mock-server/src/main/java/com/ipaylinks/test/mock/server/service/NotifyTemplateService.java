package com.ipaylinks.test.mock.server.service;

import com.ipaylinks.test.mock.server.dao.model.NotifyTemplate;

public interface NotifyTemplateService {
    NotifyTemplate selectByAccessPoint(String accessPoint);
    NotifyTemplate selectByTemplateName(String templateName);
    NotifyTemplate selectByAccessPointAndTemplateName(String accessPoint, String templateName);
}
