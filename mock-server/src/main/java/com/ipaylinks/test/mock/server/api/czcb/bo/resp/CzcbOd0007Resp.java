package com.ipaylinks.test.mock.server.api.czcb.bo.resp;

public class CzcbOd0007Resp {
    private String openId;
    private String bankUserId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getBankUserId() {
        return bankUserId;
    }

    public void setBankUserId(String bankUserId) {
        this.bankUserId = bankUserId;
    }

    @Override
    public String toString() {
        return "CzcbOd0007Resp{" +
                "openId='" + openId + '\'' +
                ", bankUserId='" + bankUserId + '\'' +
                '}';
    }
}
