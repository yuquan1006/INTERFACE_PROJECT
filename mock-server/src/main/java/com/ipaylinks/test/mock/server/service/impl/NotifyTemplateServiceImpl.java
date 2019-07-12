package com.ipaylinks.test.mock.server.service.impl;

import com.ipaylinks.test.mock.server.dao.mapper.NotifyTemplateMapper;
import com.ipaylinks.test.mock.server.dao.model.NotifyTemplate;
import com.ipaylinks.test.mock.server.service.NotifyTemplateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class NotifyTemplateServiceImpl implements NotifyTemplateService {
    @Resource
    private NotifyTemplateMapper notifyTemplateMapper;

    @Override
    public NotifyTemplate selectByAccessPoint(String accessPoint) {
        NotifyTemplate notifyTemplate = new NotifyTemplate();
        notifyTemplate.setAccessPoint(accessPoint);
        notifyTemplate.setStatus(NotifyTemplate.StatusEnum.VALID.getCode());
        return notifyTemplateMapper.findOne(notifyTemplate);
    }

    @Override
    public NotifyTemplate selectByTemplateName(String templateName) {
        NotifyTemplate notifyTemplate = new NotifyTemplate();
        notifyTemplate.setNotifyTemplateName(templateName);
        notifyTemplate.setStatus(NotifyTemplate.StatusEnum.VALID.getCode());
        return notifyTemplateMapper.findOne(notifyTemplate);
    }

    @Override
    public NotifyTemplate selectByAccessPointAndTemplateName(String accessPoint, String templateName) {
        NotifyTemplate notifyTemplate = new NotifyTemplate();
        notifyTemplate.setAccessPoint(accessPoint);
        notifyTemplate.setNotifyTemplateName(templateName);
        notifyTemplate.setStatus(NotifyTemplate.StatusEnum.VALID.getCode());
        return notifyTemplateMapper.findOne(notifyTemplate);
    }
}
