package com.ipaylinks.test.mock.server.api.fuiou.bo.req;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FuiouSubmitOrderRequest {
    private String mchntCd;
    private String subCustNo;
    private String orderId;
    private String backNotifyUrl;
    private String txnTp;
    private String settleAccountsTp;

    private String outAcntNo;
    private String outAcntNm;
    private String outAcntBankNm;
    private String outCurCd;

    private String inAcntNo;
    private String inAcntNm;
    private String inAcntBankNm;

    private String bankNo;
    private String cityNo;
    private String countryCd;
    private String orderTp;
    private String orderAmt;
    private String ver;
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBackNotifyUrl() {
        return backNotifyUrl;
    }

    public void setBackNotifyUrl(String backNotifyUrl) {
        this.backNotifyUrl = backNotifyUrl;
    }

    public String getTxnTp() {
        return txnTp;
    }

    public void setTxnTp(String txnTp) {
        this.txnTp = txnTp;
    }

    public String getSettleAccountsTp() {
        return settleAccountsTp;
    }

    public void setSettleAccountsTp(String settleAccountsTp) {
        this.settleAccountsTp = settleAccountsTp;
    }

    public String getOutAcntNo() {
        return outAcntNo;
    }

    public void setOutAcntNo(String outAcntNo) {
        this.outAcntNo = outAcntNo;
    }

    public String getOutAcntNm() {
        return outAcntNm;
    }

    public void setOutAcntNm(String outAcntNm) {
        this.outAcntNm = outAcntNm;
    }

    public String getOutAcntBankNm() {
        return outAcntBankNm;
    }

    public void setOutAcntBankNm(String outAcntBankNm) {
        this.outAcntBankNm = outAcntBankNm;
    }

    public String getOutCurCd() {
        return outCurCd;
    }

    public void setOutCurCd(String outCurCd) {
        this.outCurCd = outCurCd;
    }

    public String getInAcntNo() {
        return inAcntNo;
    }

    public void setInAcntNo(String inAcntNo) {
        this.inAcntNo = inAcntNo;
    }

    public String getInAcntNm() {
        return inAcntNm;
    }

    public void setInAcntNm(String inAcntNm) {
        this.inAcntNm = inAcntNm;
    }

    public String getInAcntBankNm() {
        return inAcntBankNm;
    }

    public void setInAcntBankNm(String inAcntBankNm) {
        this.inAcntBankNm = inAcntBankNm;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getCityNo() {
        return cityNo;
    }

    public void setCityNo(String cityNo) {
        this.cityNo = cityNo;
    }

    public String getCountryCd() {
        return countryCd;
    }

    public void setCountryCd(String countryCd) {
        this.countryCd = countryCd;
    }

    public String getOrderTp() {
        return orderTp;
    }

    public void setOrderTp(String orderTp) {
        this.orderTp = orderTp;
    }

    public String getOrderAmt() {
        return orderAmt;
    }

    public void setOrderAmt(String orderAmt) {
        this.orderAmt = orderAmt;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    @Override
    public String toString() {
        return "FuiouSubmitOrderRequest{" +
                "mchntCd='" + mchntCd + '\'' +
                ", subCustNo='" + subCustNo + '\'' +
                ", orderId='" + orderId + '\'' +
                ", backNotifyUrl='" + backNotifyUrl + '\'' +
                ", txnTp='" + txnTp + '\'' +
                ", settleAccountsTp='" + settleAccountsTp + '\'' +
                ", outAcntNo='" + outAcntNo + '\'' +
                ", outAcntNm='" + outAcntNm + '\'' +
                ", outAcntBankNm='" + outAcntBankNm + '\'' +
                ", outCurCd='" + outCurCd + '\'' +
                ", inAcntNo='" + inAcntNo + '\'' +
                ", inAcntNm='" + inAcntNm + '\'' +
                ", inAcntBankNm='" + inAcntBankNm + '\'' +
                ", bankNo='" + bankNo + '\'' +
                ", cityNo='" + cityNo + '\'' +
                ", countryCd='" + countryCd + '\'' +
                ", orderTp='" + orderTp + '\'' +
                ", orderAmt='" + orderAmt + '\'' +
                ", ver='" + ver + '\'' +
                ", md5='" + md5 + '\'' +
                '}';
    }

    public String getSignData(String md5Key, String separator) {
        List<String> signData = new ArrayList<>();
        signData.add(mchntCd);
        signData.add(subCustNo);
        signData.add(orderId);
        signData.add(backNotifyUrl);
        signData.add(txnTp);
        signData.add(settleAccountsTp);
        signData.add(outAcntNo);
        signData.add(outAcntNm);
        signData.add(outAcntBankNm);
        signData.add(outCurCd);
        signData.add(inAcntNo);
        signData.add(inAcntNm);
        signData.add(inAcntBankNm);
        signData.add(bankNo);
        signData.add(cityNo);
        signData.add(countryCd);
        signData.add(orderTp);
        signData.add(orderAmt);
        signData.add(ver);
        signData.add(md5Key);
        return StringUtils.join(signData, separator);
    }
}
