package com.ipaylinks.test.mock.server.api.czcb.bo.req;

public class CzcbOd0008Req {
    private CzcbReqPublicData publicData;
    private String openId;
    private String tradeNoJD;

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

    public String getTradeNoJD() {
        return tradeNoJD;
    }

    public void setTradeNoJD(String tradeNoJD) {
        this.tradeNoJD = tradeNoJD;
    }

    @Override
    public String toString() {
        return "CzcbOd0008Req{" +
                "publicData=" + publicData +
                ", openId='" + openId + '\'' +
                ", tradeNoJD='" + tradeNoJD + '\'' +
                '}';
    }
}
