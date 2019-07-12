package com.ipaylinks.test.mock.server.api.czcb.bo.req;

public class CzcbOa0005Req {
    private CzcbReqPublicData publicData;
    private String openId;
    private String bankUserId;
    private String customerName;
    private String idNo;
    private String frontImg;
    private String backImg;

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

    public String getFrontImg() {
        return frontImg;
    }

    public void setFrontImg(String frontImg) {
        this.frontImg = frontImg;
    }

    public String getBackImg() {
        return backImg;
    }

    public void setBackImg(String backImg) {
        this.backImg = backImg;
    }

    @Override
    public String toString() {
        return "CzcbOa0005Req{" +
                "publicData=" + publicData +
                ", openId='" + openId + '\'' +
                ", bankUserId='" + bankUserId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", idNo='" + idNo + '\'' +
                ", frontImg='" + frontImg + '\'' +
                ", backImg='" + backImg + '\'' +
                '}';
    }
}
