package com.ipaylinks.test.mock.server.dao.model;

import java.util.Date;

public class NotifyRecord {
    private Integer id;

    private String accessPoint;

    private String notifyTemplateName;

    private String uniqueRequestId;

    private String notifyUrl;

    private Date notifyRequestDate;

    private Date notifyResponseDate;

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

    public String getNotifyTemplateName() {
        return notifyTemplateName;
    }

    public void setNotifyTemplateName(String notifyTemplateName) {
        this.notifyTemplateName = notifyTemplateName == null ? null : notifyTemplateName.trim();
    }

    public String getUniqueRequestId() {
        return uniqueRequestId;
    }

    public void setUniqueRequestId(String uniqueRequestId) {
        this.uniqueRequestId = uniqueRequestId == null ? null : uniqueRequestId.trim();
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl == null ? null : notifyUrl.trim();
    }

    public Date getNotifyRequestDate() {
        return notifyRequestDate;
    }

    public void setNotifyRequestDate(Date notifyRequestDate) {
        this.notifyRequestDate = notifyRequestDate;
    }

    public Date getNotifyResponseDate() {
        return notifyResponseDate;
    }

    public void setNotifyResponseDate(Date notifyResponseDate) {
        this.notifyResponseDate = notifyResponseDate;
    }
}