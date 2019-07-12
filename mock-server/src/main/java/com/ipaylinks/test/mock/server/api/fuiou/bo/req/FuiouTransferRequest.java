package com.ipaylinks.test.mock.server.api.fuiou.bo.req;

import com.ipaylinks.test.mock.server.api.fuiou.bo.FuiouHead;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "AP")
public class FuiouTransferRequest implements Serializable {
    private static final long serialVersionUID = -5548308368772434953L;
    @XmlElement(name = "head")
    private FuiouHead head;

    @XmlElement(name = "body")
    private FuiouTransferRequestBody body;

    public FuiouHead getHead() {
        return head;
    }

    public void setHead(FuiouHead head) {
        this.head = head;
    }

    public FuiouTransferRequestBody getBody() {
        return body;
    }

    public void setBody(FuiouTransferRequestBody body) {
        this.body = body;
    }
}