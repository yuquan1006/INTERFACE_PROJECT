package com.ipaylinks.test.mock.server.api.czcb.bo.resp;

public class CzcbOa0003Resp {
    private String openId;
    private String isReg;
    private String bankUserId;
    private String bankCardNo;
    private String telNo;
    private String bankName;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getIsReg() {
        return isReg;
    }

    public void setIsReg(String isReg) {
        this.isReg = isReg;
    }

    public String getBankUserId() {
        return bankUserId;
    }

    public void setBankUserId(String bankUserId) {
        this.bankUserId = bankUserId;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    @Override
    public String toString() {
        return "CzcbOa0003Resp{" +
                "openId='" + openId + '\'' +
                ", isReg='" + isReg + '\'' +
                ", bankUserId='" + bankUserId + '\'' +
                ", bankCardNo='" + bankCardNo + '\'' +
                ", telNo='" + telNo + '\'' +
                ", bankName='" + bankName + '\'' +
                '}';
    }
}
