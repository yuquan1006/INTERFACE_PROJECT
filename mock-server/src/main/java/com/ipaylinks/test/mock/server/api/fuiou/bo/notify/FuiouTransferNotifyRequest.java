package com.ipaylinks.test.mock.server.api.fuiou.bo.notify;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "AP")
public class FuiouTransferNotifyRequest implements Serializable {
    private static final long serialVersionUID = -2818327701555236218L;

    @XmlElement(name = "body")
    private FuiouTransferNotifyRequestBody body;

    @XmlElement(name = "sign")
    private String sign;

    public FuiouTransferNotifyRequestBody getBody() {
        return body;
    }

    public void setBody(FuiouTransferNotifyRequestBody body) {
        this.body = body;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
