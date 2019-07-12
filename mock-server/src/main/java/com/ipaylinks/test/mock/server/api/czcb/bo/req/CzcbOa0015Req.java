package com.ipaylinks.test.mock.server.api.czcb.bo.req;

public class CzcbOa0015Req {
    private CzcbReqPublicData publicData;
    private String openId;
    private String bankUserId;
    private String customerName;
    private String idNo;
    private String bindCard;
    private String bindTel;
    private String tradeNoJD;
    private String activeCode;

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

    public String getBankUserId() {
        return bankUserId;
    }

    public void setBankUserId(String bankUserId) {
        this.bankUserId = bankUserId;
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

    public String getTradeNoJD() {
        return tradeNoJD;
    }

    public void setTradeNoJD(String tradeNoJD) {
        this.tradeNoJD = tradeNoJD;
    }

    public String getActiveCode() {
        return activeCode;
    }

    public void setActiveCode(String activeCode) {
        this.activeCode = activeCode;
    }

    @Override
    public String toString() {
        return "CzcbOa0015Req{" +
                "publicData=" + publicData +
                ", openId='" + openId + '\'' +
                ", bankUserId='" + bankUserId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", idNo='" + idNo + '\'' +
                ", bindCard='" + bindCard + '\'' +
                ", bindTel='" + bindTel + '\'' +
                ", tradeNoJD='" + tradeNoJD + '\'' +
                ", activeCode='" + activeCode + '\'' +
                '}';
    }
}
