package com.ipaylinks.test.mock.server.api.czcb.bo.req;

public class CzcbOa0003Req {
    private CzcbReqPublicData publicData;
    private String openId;
    private String customerName;
    private String idNo;

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

    @Override
    public String toString() {
        return "CzcbOa0003Req{" +
                "publicData=" + publicData +
                ", openId='" + openId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", idNo='" + idNo + '\'' +
                '}';
    }
}
