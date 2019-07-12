package com.ipaylinks.test.mock.server.api.fuiou.bo.req;

import com.ipaylinks.test.mock.server.api.fuiou.bo.FuiouHead;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author Libo
 * Created by Libo on 2017/12/5.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "AP")
public class TransDetailsQueryRequest implements Serializable{
    private static final long serialVersionUID = -5873852833581051550L;
    @XmlElement(name = "head")
    private FuiouHead head;

    @XmlElement(name = "body")
    private TransDetailsQueryRequestBody body;

    public FuiouHead getHead() {
        return head;
    }

    public void setHead(FuiouHead head) {
        this.head = head;
    }

    public TransDetailsQueryRequestBody getBody() {
        return body;
    }

    public void setBody(TransDetailsQueryRequestBody body) {
        this.body = body;
    }
}
