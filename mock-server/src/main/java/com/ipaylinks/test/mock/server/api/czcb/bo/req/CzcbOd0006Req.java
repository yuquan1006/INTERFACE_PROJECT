package com.ipaylinks.test.mock.server.api.czcb.bo.req;

public class CzcbOd0006Req {
    private CzcbReqPublicData publicData;
    private String openId;
    private String bankUserId;
    private String customerName;
    private String idNo;
    private String amount;
    private String bankCard;
    private String tradeNoJD;
    private String exchgTradeNo;

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getTradeNoJD() {
        return tradeNoJD;
    }

    public void setTradeNoJD(String tradeNoJD) {
        this.tradeNoJD = tradeNoJD;
    }

    public String getExchgTradeNo() {
        return exchgTradeNo;
    }

    public void setExchgTradeNo(String exchgTradeNo) {
        this.exchgTradeNo = exchgTradeNo;
    }

    @Override
    public String toString() {
        return "CzcbOd0006Req{" +
                "publicData=" + publicData +
                ", openId='" + openId + '\'' +
                ", bankUserId='" + bankUserId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", idNo='" + idNo + '\'' +
                ", amount='" + amount + '\'' +
                ", bankCard='" + bankCard + '\'' +
                ", tradeNoJD='" + tradeNoJD + '\'' +
                ", exchgTradeNo='" + exchgTradeNo + '\'' +
                '}';
    }
}
