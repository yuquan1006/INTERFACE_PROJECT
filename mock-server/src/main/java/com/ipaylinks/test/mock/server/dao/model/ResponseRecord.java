package com.ipaylinks.test.mock.server.dao.model;

import java.util.Date;

public class ResponseRecord {
    private Integer id;

    private String accessPoint;

    private String responseTemplateName;

    private String uniqueRequestId;

    private Date requestDate;

    private Date responseDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccessPoint() {
        return accessPoint;
    }

    public void setAccessPoint(String accessPoint) {
        this.accessPoint = accessPoint == null ? null : accessPoint.trim();
    }

    public String getResponseTemplateName() {
        return responseTemplateName;
    }

    public void setResponseTemplateName(String responseTemplateName) {
        this.responseTemplateName = responseTemplateName == null ? null : responseTemplateName.trim();
    }

    public String getUniqueRequestId() {
        return uniqueRequestId;
    }

    public void setUniqueRequestId(String uniqueRequestId) {
        this.uniqueRequestId = uniqueRequestId == null ? null : uniqueRequestId.trim();
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Date getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(Date responseDate) {
        this.responseDate = responseDate;
    }
}