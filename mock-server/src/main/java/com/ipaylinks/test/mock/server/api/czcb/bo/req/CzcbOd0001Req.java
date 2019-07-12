package com.ipaylinks.test.mock.server.api.czcb.bo.req;

public class CzcbOd0001Req {
    private CzcbReqPublicData publicData;
    private String openId;
    private String acctNo;

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

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    @Override
    public String toString() {
        return "CzcbOd0001Req{" +
                "publicData=" + publicData +
                ", openId='" + openId + '\'' +
                ", acctNo='" + acctNo + '\'' +
                '}';
    }
}
