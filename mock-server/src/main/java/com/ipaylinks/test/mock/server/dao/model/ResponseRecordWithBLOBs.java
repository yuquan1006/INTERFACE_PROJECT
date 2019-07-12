package com.ipaylinks.test.mock.server.dao.model;

public class ResponseRecordWithBLOBs extends ResponseRecord {
    private String requestContent;

    private String responseContent;

    public String getRequestContent() {
        return requestContent;
    }

    public void setRequestContent(String requestContent) {
        this.requestContent = requestContent == null ? null : requestContent.trim();
    }

    public String getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(String responseContent) {
        this.responseContent = responseContent == null ? null : responseContent.trim();
    }
}