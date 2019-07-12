package com.ipaylinks.test.mock.server.api.fuiou.bo.req;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author Libo
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "body")
public class TransDetailsQueryRequestBody implements Serializable{
    private static final long serialVersionUID = -1L;

    @XmlElement(name = "oppositeId")
    private String oppositeId;

    @XmlElement(name = "startTime")
    private String startTime;

    @XmlElement(name = "endTime")
    private String endTime;

    @XmlElement(name = "fuiouTransNo")
    private String fuiouTransNo;

    @XmlElement(name = "transPage")
    private String transPage;

    @XmlElement(name = "transRows")
    private String transRows;

    @XmlElement(name = "transType")
    private String transType;

    @XmlElement(name = "transSt")
    private String transSt;

    @XmlElement(name = "inOut")
    private String inOut;

    @XmlElement(name = "transNo")
    private String transNo;

    @XmlElement(name = "order_id")
    private String orderId;

    public String getOppositeId() {
        return oppositeId;
    }

    public void setOppositeId(String oppositeId) {
        this.oppositeId = oppositeId;
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

    public String getFuiouTransNo() {
        return fuiouTransNo;
    }

    public void setFuiouTransNo(String fuiouTransNo) {
        this.fuiouTransNo = fuiouTransNo;
    }

    public String getTransPage() {
        return transPage;
    }

    public void setTransPage(String transPage) {
        this.transPage = transPage;
    }

    public String getTransRows() {
        return transRows;
    }

    public void setTransRows(String transRows) {
        this.transRows = transRows;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getTransSt() {
        return transSt;
    }

    public void setTransSt(String transSt) {
        this.transSt = transSt;
    }

    public String getInOut() {
        return inOut;
    }

    public void setInOut(String inOut) {
        this.inOut = inOut;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
