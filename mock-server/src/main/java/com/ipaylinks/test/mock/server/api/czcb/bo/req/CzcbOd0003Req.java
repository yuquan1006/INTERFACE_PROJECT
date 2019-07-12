package com.ipaylinks.test.mock.server.api.czcb.bo.req;

public class CzcbOd0003Req {
    private CzcbReqPublicData publicData;
    private String openId;
    private String acctNo;
    private String amount;
    private String bankCard;
    private String customerName;
    private String tradeNoJD;
    private String forAcctNo;
    private String forAcctName;
    private String forAcctCtryCode;
    private String remark;
    private String remark1;
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

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTradeNoJD() {
        return tradeNoJD;
    }

    public void setTradeNoJD(String tradeNoJD) {
        this.tradeNoJD = tradeNoJD;
    }

    public String getForAcctNo() {
        return forAcctNo;
    }

    public void setForAcctNo(String forAcctNo) {
        this.forAcctNo = forAcctNo;
    }

    public String getForAcctName() {
        return forAcctName;
    }

    public void setForAcctName(String forAcctName) {
        this.forAcctName = forAcctName;
    }

    public String getForAcctCtryCode() {
        return forAcctCtryCode;
    }

    public void setForAcctCtryCode(String forAcctCtryCode) {
        this.forAcctCtryCode = forAcctCtryCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getExchgTradeNo() {
        return exchgTradeNo;
    }

    public void setExchgTradeNo(String exchgTradeNo) {
        this.exchgTradeNo = exchgTradeNo;
    }

    @Override
    public String toString() {
        return "CzcbOd0003Req{" +
                "publicData=" + publicData +
                ", openId='" + openId + '\'' +
                ", acctNo='" + acctNo + '\'' +
                ", amount='" + amount + '\'' +
                ", bankCard='" + bankCard + '\'' +
                ", customerName='" + customerName + '\'' +
                ", tradeNoJD='" + tradeNoJD + '\'' +
                ", forAcctNo='" + forAcctNo + '\'' +
                ", forAcctName='" + forAcctName + '\'' +
                ", forAcctCtryCode='" + forAcctCtryCode + '\'' +
                ", remark='" + remark + '\'' +
                ", remark1='" + remark1 + '\'' +
                ", exchgTradeNo='" + exchgTradeNo + '\'' +
                '}';
    }
}
