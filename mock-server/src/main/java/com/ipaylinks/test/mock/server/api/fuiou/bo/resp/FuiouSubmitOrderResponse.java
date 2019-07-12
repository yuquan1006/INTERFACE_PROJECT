package com.ipaylinks.test.mock.server.api.fuiou.bo.resp;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FuiouSubmitOrderResponse {
    private String rspCd;
    private String rspDesc;
    private List<String> errorList;
    private String mchntCd;

    private String subCustNo;
    private String orderId;
    private String orderNo;
    private String payPrice;
    private String amtEstimate;
    private String calcDesc;
    private String outCurCd;
    private String chargeMoney;
    private String outTxnAmt;
    private String bankBicCode;
    private String bankNm;
    private String bankAddress;
    private String fuiouAcntNo;
    private String fuiouAcntNm;
    private String md5;

    public String getRspCd() {
        return rspCd;
    }

    public void setRspCd(String rspCd) {
        this.rspCd = rspCd;
    }

    public String getRspDesc() {
        return rspDesc;
    }

    public void setRspDesc(String rspDesc) {
        this.rspDesc = rspDesc;
    }

    public List<String> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<String> errorList) {
        this.errorList = errorList;
    }

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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(String payPrice) {
        this.payPrice = payPrice;
    }

    public String getAmtEstimate() {
        return amtEstimate;
    }

    public void setAmtEstimate(String amtEstimate) {
        this.amtEstimate = amtEstimate;
    }

    public String getCalcDesc() {
        return calcDesc;
    }

    public void setCalcDesc(String calcDesc) {
        this.calcDesc = calcDesc;
    }

    public String getOutCurCd() {
        return outCurCd;
    }

    public void setOutCurCd(String outCurCd) {
        this.outCurCd = outCurCd;
    }

    public String getChargeMoney() {
        return chargeMoney;
    }

    public void setChargeMoney(String chargeMoney) {
        this.chargeMoney = chargeMoney;
    }

    public String getOutTxnAmt() {
        return outTxnAmt;
    }

    public void setOutTxnAmt(String outTxnAmt) {
        this.outTxnAmt = outTxnAmt;
    }

    public String getBankBicCode() {
        return bankBicCode;
    }

    public void setBankBicCode(String bankBicCode) {
        this.bankBicCode = bankBicCode;
    }

    public String getBankNm() {
        return bankNm;
    }

    public void setBankNm(String bankNm) {
        this.bankNm = bankNm;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getFuiouAcntNo() {
        return fuiouAcntNo;
    }

    public void setFuiouAcntNo(String fuiouAcntNo) {
        this.fuiouAcntNo = fuiouAcntNo;
    }

    public String getFuiouAcntNm() {
        return fuiouAcntNm;
    }

    public void setFuiouAcntNm(String fuiouAcntNm) {
        this.fuiouAcntNm = fuiouAcntNm;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    @Override
    public String toString() {
        return "FuiouSubmitOrderResponse{" +
                "rspCd='" + rspCd + '\'' +
                ", rspDesc='" + rspDesc + '\'' +
                ", errorList=" + errorList +
                ", mchntCd='" + mchntCd + '\'' +
                ", subCustNo='" + subCustNo + '\'' +
                ", orderId='" + orderId + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", payPrice='" + payPrice + '\'' +
                ", amtEstimate='" + amtEstimate + '\'' +
                ", calcDesc='" + calcDesc + '\'' +
                ", outCurCd='" + outCurCd + '\'' +
                ", chargeMoney='" + chargeMoney + '\'' +
                ", outTxnAmt='" + outTxnAmt + '\'' +
                ", bankBicCode='" + bankBicCode + '\'' +
                ", bankNm='" + bankNm + '\'' +
                ", bankAddress='" + bankAddress + '\'' +
                ", fuiouAcntNo='" + fuiouAcntNo + '\'' +
                ", fuiouAcntNm='" + fuiouAcntNm + '\'' +
                ", md5='" + md5 + '\'' +
                '}';
    }

    public String getSignData(String md5Key, String separator) {
        List<String> signData = new ArrayList<>();
        signData.add(mchntCd);
        signData.add(subCustNo);
        signData.add(orderId);
        signData.add(orderNo);
        signData.add(payPrice);
        signData.add(amtEstimate);
        signData.add(calcDesc);
        signData.add(outCurCd);
        signData.add(chargeMoney);
        signData.add(outTxnAmt);
        signData.add(bankBicCode);
        signData.add(bankNm);
        signData.add(bankAddress);
        signData.add(fuiouAcntNo);
        signData.add(fuiouAcntNm);
        signData.add(md5Key);
        return StringUtils.join(signData, separator);
    }
}
