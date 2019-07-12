package com.ipaylinks.test.mock.server.dao.model;

public class NotifyRecordWithBLOBs extends NotifyRecord {
    private String notifyRequestContent;

    private String notifyResponseContent;

    public String getNotifyRequestContent() {
        return notifyRequestContent;
    }

    public void setNotifyRequestContent(String notifyRequestContent) {
        this.notifyRequestContent = notifyRequestContent == null ? null : notifyRequestContent.trim();
    }

    public String getNotifyResponseContent() {
        return notifyResponseContent;
    }

    public void setNotifyResponseContent(String notifyResponseContent) {
        this.notifyResponseContent = notifyResponseContent == null ? null : notifyResponseContent.trim();
    }
}