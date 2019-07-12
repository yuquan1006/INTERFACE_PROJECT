package com.ipaylinks.test.mock.server.api.fuiou.bo.notify;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "notify04")
public class FuiouTransferNotifyRequestBody04 {
    @XmlElement(name = "eicSsn")
    private String eicSsn;
    @XmlElement(name = "fuiouTransNo")
    private String fuiouTransNo;
    @XmlElement(name = "amt")
    private String amt;

    public String getEicSsn() {
        return eicSsn;
    }

    public void setEicSsn(String eicSsn) {
        this.eicSsn = eicSsn;
    }

    public String getFuiouTransNo() {
        return fuiouTransNo;
    }

    public void setFuiouTransNo(String fuiouTransNo) {
        this.fuiouTransNo = fuiouTransNo;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }
}
