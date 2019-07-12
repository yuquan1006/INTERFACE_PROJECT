package com.ipaylinks.test.mock.server.api.fuiou.bo.notify;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "body")
public class FuiouTransferNotifyRequestBody implements Serializable {
    private static final long serialVersionUID = 7546967636013573791L;
    @XmlElement(name = "rspCode")
    private String rspCode;

    @XmlElement(name = "rspDesc")
    private String rspDesc;

    @XmlElement(name = "notifyType")
    private String notifyType;

    @XmlElement(name = "notify04")
    private FuiouTransferNotifyRequestBody04 notify04;

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

    public String getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(String notifyType) {
        this.notifyType = notifyType;
    }

    public FuiouTransferNotifyRequestBody04 getNotify04() {
        return notify04;
    }

    public void setNotify04(FuiouTransferNotifyRequestBody04 notify04) {
        this.notify04 = notify04;
    }
}
