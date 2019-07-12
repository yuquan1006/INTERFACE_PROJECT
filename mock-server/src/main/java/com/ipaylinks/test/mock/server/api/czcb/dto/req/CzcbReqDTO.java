package com.ipaylinks.test.mock.server.api.czcb.dto.req;

import java.io.Serializable;

public class CzcbReqDTO implements Serializable {
    private static final long serialVersionUID = 1015590309239025905L;
    private String randomKeyEncrypted;
    private String requestId;
    private String channelId;
    private String interfaceId;
    private String timestamp;
    private String signature;
    private String bizData;

    public String getRandomKeyEncrypted() {
        return randomKeyEncrypted;
    }

    public void setRandomKeyEncrypted(String randomKeyEncrypted) {
        this.randomKeyEncrypted = randomKeyEncrypted;
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

    public String getBizData() {
        return bizData;
    }

    public void setBizData(String bizData) {
        this.bizData = bizData;
    }

    @Override
    public String toString() {
        return "CzcbReqDTO{" +
                "randomKeyEncrypted='" + randomKeyEncrypted + '\'' +
                ", requestId='" + requestId + '\'' +
                ", channelId='" + channelId + '\'' +
                ", interfaceId='" + interfaceId + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", signature='" + signature + '\'' +
                ", bizData='" + bizData + '\'' +
                '}';
    }
}
