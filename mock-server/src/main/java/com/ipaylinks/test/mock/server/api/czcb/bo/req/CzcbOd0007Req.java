package com.ipaylinks.test.mock.server.api.czcb.bo.req;

public class CzcbOd0007Req {
    private CzcbReqPublicData publicData;
    private String openId;
    private String customerName;
    private String idNo;
    private String bindCard;
    private String bindTel;

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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getBindCard() {
        return bindCard;
    }

    public void setBindCard(String bindCard) {
        this.bindCard = bindCard;
    }

    public String getBindTel() {
        return bindTel;
    }

    public void setBindTel(String bindTel) {
        this.bindTel = bindTel;
    }


    @Override
    public String toString() {
        return "CzcbOd0007Req{" +
                "publicData=" + publicData +
                ", openId='" + openId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", idNo='" + idNo + '\'' +
                ", bindCard='" + bindCard + '\'' +
                ", bindTel='" + bindTel + '\'' +
                '}';
    }
}
