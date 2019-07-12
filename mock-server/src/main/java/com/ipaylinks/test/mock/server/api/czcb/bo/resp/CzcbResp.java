package com.ipaylinks.test.mock.server.api.czcb.bo.resp;

public class CzcbResp<T> {
    private String desc;
    private String interfaceId;
    private String timestamp;
    private String signature;
    private String requestId;
    private String code;
    private T bizData;
    private String channelId;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getBizData() {
        return bizData;
    }

    public void setBizData(T bizData) {
        this.bizData = bizData;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    @Override
    public String toString() {
        return "CzcbResp{" +
                "desc='" + desc + '\'' +
                ", interfaceId='" + interfaceId + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", signature='" + signature + '\'' +
                ", requestId='" + requestId + '\'' +
                ", code='" + code + '\'' +
                ", bizData=" + bizData +
                ", channelId='" + channelId + '\'' +
                '}';
    }
}
