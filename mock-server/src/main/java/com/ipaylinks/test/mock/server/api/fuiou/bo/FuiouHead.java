package com.ipaylinks.test.mock.server.api.fuiou.bo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author libo
 *  date 2018/12/21
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "head")
public class FuiouHead implements Serializable {
    private static final long serialVersionUID = -6160096719519721137L;
    @XmlElement(name = "partnerId")
    private String partnerId;

    @XmlElement(name = "transNo")
    private String transNo;

    @XmlElement(name = "timeStamp")
    private String timeStamp;

    @XmlElement(name = "messageVersion")
    private String messageVersion;

    @XmlElement(name = "dataDirection")
    private String dataDirection;

    @XmlElement(name = "messageCode")
    private String messageCode;

    @XmlElement(name = "encryptionCode")
    private String encryptionCode;

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMessageVersion() {
        return messageVersion;
    }

    public void setMessageVersion(String messageVersion) {
        this.messageVersion = messageVersion;
    }

    public String getDataDirection() {
        return dataDirection;
    }

    public void setDataDirection(String dataDirection) {
        this.dataDirection = dataDirection;
    }

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getEncryptionCode() {
        return encryptionCode;
    }

    public void setEncryptionCode(String encryptionCode) {
        this.encryptionCode = encryptionCode;
    }
}
