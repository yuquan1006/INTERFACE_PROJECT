package com.ipaylinks.test.mock.server.api.czcb.bo.req;

public class CzcbOd0005Req {
    private CzcbReqPublicData publicData;
    private String openId;
    private String currencyID;

    public CzcbReqPublicData getPublicData() {
        return publicData;
    }

    public void setPublicData(CzcbReqPublicData publicData) {
        this.publicData = publicData;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getCurrencyID() {
        return currencyID;
    }

    public void setCurrencyID(String currencyID) {
        this.currencyID = currencyID;
    }

    @Override
    public String toString() {
        return "CzcbOd0005Req{" +
                "publicData=" + publicData +
                ", openId='" + openId + '\'' +
                ", currencyID='" + currencyID + '\'' +
                '}';
    }
}
