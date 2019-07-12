package com.ipaylinks.test.mock.server.api.czcb.bo.req;

public class CzcbOd0004Req {
    private CzcbReqPublicData publicData;
    private String openId;
    private String nraAcctNo;
    private String currencyID;
    private String settleType;
    private String amount;
    private String tradeNoJD;
    private String remark;
    private String remark1;

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

    public String getNraAcctNo() {
        return nraAcctNo;
    }

    public void setNraAcctNo(String nraAcctNo) {
        this.nraAcctNo = nraAcctNo;
    }

    public String getCurrencyID() {
        return currencyID;
    }

    public void setCurrencyID(String currencyID) {
        this.currencyID = currencyID;
    }

    public String getSettleType() {
        return settleType;
    }

    public void setSettleType(String settleType) {
        this.settleType = settleType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTradeNoJD() {
        return tradeNoJD;
    }

    public void setTradeNoJD(String tradeNoJD) {
        this.tradeNoJD = tradeNoJD;
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

    @Override
    public String toString() {
        return "CzcbOd0004Req{" +
                "publicData=" + publicData +
                ", openId='" + openId + '\'' +
                ", nraAcctNo='" + nraAcctNo + '\'' +
                ", currencyID='" + currencyID + '\'' +
                ", settleType='" + settleType + '\'' +
                ", amount='" + amount + '\'' +
                ", tradeNoJD='" + tradeNoJD + '\'' +
                ", remark='" + remark + '\'' +
                ", remark1='" + remark1 + '\'' +
                '}';
    }
}
