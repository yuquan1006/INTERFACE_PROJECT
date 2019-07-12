package com.ipaylinks.test.mock.server.api.fuiou.bo.resp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author Libo
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "result")
public class TransDetailsQueryResult implements Serializable{
    private static final long serialVersionUID = 2407162408345686674L;
    @XmlElement(name = "transNo")
    private String transNo;

    @XmlElement(name = "fuiouTransNo")
    private String fuiouTransNo;

    @XmlElement(name = "accountName")
    private String accountName;

    @XmlElement(name = "accountId")
    private String accountId;

    @XmlElement(name = "transTime")
    private String transTime;

    @XmlElement(name = "transType")
    private String transType;

    @XmlElement(name = "amt")
    private String amt;

    @XmlElement(name = "actualAmt")
    private String actualAmt;

    @XmlElement(name = "fee")
    private String fee;

    @XmlElement(name = "oppositeId")
    private String oppositeId;

    @XmlElement(name = "oppositeName")
    private String oppositeName;

    @XmlElement(name = "oppoiteBankCardNo")
    private String oppoiteBankCardNo;

    @XmlElement(name = "freezeSt")
    private String freezeSt;

    @XmlElement(name = "transSt")
    private String transSt;

    @XmlElement(name = "inOutSt")
    private String inOutSt;

    @XmlElement(name = "inOut")
    private String inOut;

    @XmlElement(name = "reason")
    private String reason;

    @XmlElement(name = "remark")
    private String remark;

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public String getFuiouTransNo() {
        return fuiouTransNo;
    }

    public void setFuiouTransNo(String fuiouTransNo) {
        this.fuiouTransNo = fuiouTransNo;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getTransTime() {
        return transTime;
    }

    public void setTransTime(String transTime) {
        this.transTime = transTime;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getActualAmt() {
        return actualAmt;
    }

    public void setActualAmt(String actualAmt) {
        this.actualAmt = actualAmt;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getOppositeId() {
        return oppositeId;
    }

    public void setOppositeId(String oppositeId) {
        this.oppositeId = oppositeId;
    }

    public String getOppositeName() {
        return oppositeName;
    }

    public void setOppositeName(String oppositeName) {
        this.oppositeName = oppositeName;
    }

    public String getOppoiteBankCardNo() {
        return oppoiteBankCardNo;
    }

    public void setOppoiteBankCardNo(String oppoiteBankCardNo) {
        this.oppoiteBankCardNo = oppoiteBankCardNo;
    }

    public String getFreezeSt() {
        return freezeSt;
    }

    public void setFreezeSt(String freezeSt) {
        this.freezeSt = freezeSt;
    }

    public String getTransSt() {
        return transSt;
    }

    public void setTransSt(String transSt) {
        this.transSt = transSt;
    }

    public String getInOutSt() {
        return inOutSt;
    }

    public void setInOutSt(String inOutSt) {
        this.inOutSt = inOutSt;
    }

    public String getInOut() {
        return inOut;
    }

    public void setInOut(String inOut) {
        this.inOut = inOut;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
