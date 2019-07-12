package com.ipaylinks.test.mock.server.api.fuiou.bo.notify;

public class FuiouNotifyNotifyRequest {
    private String reqCd;
    private String reqDesc;
    private String fileNm;
    private String orderNo;

    public String getReqCd() {
        return reqCd;
    }

    public void setReqCd(String reqCd) {
        this.reqCd = reqCd;
    }

    public String getReqDesc() {
        return reqDesc;
    }

    public void setReqDesc(String reqDesc) {
        this.reqDesc = reqDesc;
    }

    public String getFileNm() {
        return fileNm;
    }

    public void setFileNm(String fileNm) {
        this.fileNm = fileNm;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    @Override
    public String toString() {
        return "FuiouNotifyNotifyRequest{" +
                "reqCd='" + reqCd + '\'' +
                ", reqDesc='" + reqDesc + '\'' +
                ", fileNm='" + fileNm + '\'' +
                ", orderNo='" + orderNo + '\'' +
                '}';
    }
}
