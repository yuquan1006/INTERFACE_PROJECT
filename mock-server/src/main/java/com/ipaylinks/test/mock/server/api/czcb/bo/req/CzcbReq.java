package com.ipaylinks.test.mock.server.api.czcb.bo.req;

public class CzcbReq<T> {
    private String requestId;
    private String channelId;
    private String interfaceId;
    private String timestamp;
    private String signature;
    private T bizData;

    public CzcbReq() {
    }

    public CzcbReq(T bizData) {
        this.bizData = bizData;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(String interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public T getBizData() {
        return bizData;
    }

    public void setBizData(T bizData) {
        this.bizData = bizData;
    }

    @Override
    public String toString() {
        return "CzcbReq{" +
                "requestId='" + requestId + '\'' +
                ", channelId='" + channelId + '\'' +
                ", interfaceId='" + interfaceId + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", signature='" + signature + '\'' +
                ", bizData=" + bizData +
                '}';
    }
}
