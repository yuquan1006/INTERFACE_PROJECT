package com.ipaylinks.test.mock.server.api.fuiou.bo.req;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FuiouOrderQueryPageRequest {
    private String mchntCd;
    private String subCustNo;
    private String backNotifyUrl;
    private String pageNo;
    private String startTime;
    private String endTime;
    private String orderNo;
    private String orderId;
    private String pageSize;
    private String txnTp;
    private String md5;

    public String getMchntCd() {
        return mchntCd;
    }

    public void setMchntCd(String mchntCd) {
        this.mchntCd = mchntCd;
    }

    public String getSubCustNo() {
        return subCustNo;
    }

    public void setSubCustNo(String subCustNo) {
        this.subCustNo = subCustNo;
    }

    public String getBackNotifyUrl() {
        return backNotifyUrl;
    }

    public void setBackNotifyUrl(String backNotifyUrl) {
        this.backNotifyUrl = backNotifyUrl;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getTxnTp() {
        return txnTp;
    }

    public void setTxnTp(String txnTp) {
        this.txnTp = txnTp;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    @Override
    public String toString() {
        return "FuiouOrderQueryPageRequest{" +
                "mchntCd='" + mchntCd + '\'' +
                ", subCustNo='" + subCustNo + '\'' +
                ", backNotifyUrl='" + backNotifyUrl + '\'' +
                ", pageNo='" + pageNo + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", orderId='" + orderId + '\'' +
                ", pageSize='" + pageSize + '\'' +
                ", txnTp='" + txnTp + '\'' +
                ", md5='" + md5 + '\'' +
                '}';
    }

    public String getSignData(String md5Key, String separator) {
        List<String> signData = new ArrayList<>();
        signData.add(mchntCd);
        signData.add(backNotifyUrl);
        signData.add(pageNo);
        signData.add(startTime);
        signData.add(endTime);
        signData.add(orderNo);
        signData.add(orderId);
        signData.add(pageSize);
        signData.add(txnTp);
        signData.add(md5Key);
        return StringUtils.join(signData, separator);
    }
}
