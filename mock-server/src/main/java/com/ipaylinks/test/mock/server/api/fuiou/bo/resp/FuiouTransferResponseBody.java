package com.ipaylinks.test.mock.server.api.fuiou.bo.resp;

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
public class FuiouTransferResponseBody implements Serializable{
    private static final long serialVersionUID = -1L;

    @XmlElement(name = "rspCode")
    private String rspCode;

    @XmlElement(name = "rspDesc")
    private String rspDesc;

    @XmlElement(name = "fuiouTransNo")
    private String fuiouTransNo;

    @XmlElement(name = "amt")
    private String amt;

    public String getRspCode() {
        return rspCode;
    }

    public void setRspCode(String rspCode) {
        this.rspCode = rspCode;
    }

    public String getRspDesc() {
        return rspDesc;
    }

    public void setRspDesc(String rspDesc) {
        this.rspDesc = rspDesc;
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
