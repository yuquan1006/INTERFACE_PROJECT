package com.ipaylinks.test.mock.server.api.fuiou.bo.req;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author Libo
 *  date 2018/12/21
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "body")
public class FuiouTransferRequestBody implements Serializable {
    private static final long serialVersionUID = -5227348705613245655L;
    @XmlElement(name = "backNotifyUrl")
    private String backNotifyUrl;

    @XmlElement(name = "bankCardNo")
    private String bankCardNo;

    @XmlElement(name = "oppositeName")
    private String oppositeName;

    @XmlElement(name = "oppositeIdNo")
    private String oppositeIdNo;

    @XmlElement(name = "oppositeMobile")
    private String oppositeMobile;

    @XmlElement(name = "bankCardTp")
    private String bankCardTp;

    @XmlElement(name = "bankNo")
    private String bankNo;

    @XmlElement(name = "cityNo")
    private String cityNo;

    @XmlElement(name = "amt")
    private String amt;

    @XmlElement(name = "remark")
    private String remark;

    @XmlElement(name = "isNeedReview")
    private String isNeedReview;

    @XmlElement(name = "bankId")
    private String bankId;

    @XmlElement(name = "isNotify")
    private String isNotify;

    public String getBackNotifyUrl() {
        return backNotifyUrl;
    }

    public void setBackNotifyUrl(String backNotifyUrl) {
        this.backNotifyUrl = backNotifyUrl;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getOppositeName() {
        return oppositeName;
    }

    public void setOppositeName(String oppositeName) {
        this.oppositeName = oppositeName;
    }

    public String getOppositeIdNo() {
        return oppositeIdNo;
    }

    public void setOppositeIdNo(String oppositeIdNo) {
        this.oppositeIdNo = oppositeIdNo;
    }

    public String getOppositeMobile() {
        return oppositeMobile;
    }

    public void setOppositeMobile(String oppositeMobile) {
        this.oppositeMobile = oppositeMobile;
    }

    public String getBankCardTp() {
        return bankCardTp;
    }

    public void setBankCardTp(String bankCardTp) {
        this.bankCardTp = bankCardTp;
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

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getIsNeedReview() {
        return isNeedReview;
    }

    public void setIsNeedReview(String isNeedReview) {
        this.isNeedReview = isNeedReview;
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getIsNotify() {
        return isNotify;
    }

    public void setIsNotify(String isNotify) {
        this.isNotify = isNotify;
    }
}
