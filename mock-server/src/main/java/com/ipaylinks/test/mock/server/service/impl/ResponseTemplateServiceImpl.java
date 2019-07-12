package com.ipaylinks.test.mock.server.service.impl;

import com.ipaylinks.test.mock.server.dao.mapper.ResponseTemplateMapper;
import com.ipaylinks.test.mock.server.dao.model.ResponseTemplate;
import com.ipaylinks.test.mock.server.service.ResponseTemplateService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ResponseTemplateServiceImpl implements ResponseTemplateService {
    @Resource
    private ResponseTemplateMapper responseTemplateMapper;

    @Override
    public ResponseTemplate selectByAccessPoint(String accessPoint) {
        ResponseTemplate responseTemplate = new ResponseTemplate();
        responseTemplate.setAccessPoint(accessPoint);
        responseTemplate.setStatus(ResponseTemplate.StatusEnum.VALID.getCode());
        return responseTemplateMapper.findOne(responseTemplate);
    }
    @Override
    public ResponseTemplate selectByTemplateName(String templateName) {
        ResponseTemplate responseTemplate = new ResponseTemplate();
        responseTemplate.setResponseTemplateName(templateName);
        responseTemplate.setStatus(ResponseTemplate.StatusEnum.VALID.getCode());
        return responseTemplateMapper.findOne(responseTemplate);
    }

    @Override
    public ResponseTemplate selectByAccessPointAndTemplateName(String accessPoint, String templateName) {
        ResponseTemplate responseTemplate = new ResponseTemplate();
        responseTemplate.setAccessPoint(accessPoint);
        responseTemplate.setResponseTemplateName(templateName);
        responseTemplate.setStatus(ResponseTemplate.StatusEnum.VALID.getCode());
        return responseTemplateMapper.findOne(responseTemplate);
    }


}
