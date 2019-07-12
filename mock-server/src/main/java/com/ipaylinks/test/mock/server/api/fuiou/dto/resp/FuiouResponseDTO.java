package com.ipaylinks.test.mock.server.api.fuiou.dto.resp;

import com.ipaylinks.test.mock.server.api.fuiou.bo.FuiouHead;
import com.ipaylinks.test.mock.server.api.fuiou.bo.resp.FuiouTransferResponseBody;

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
public class FuiouResponseDTO<T> implements Serializable{
    private static final long serialVersionUID = -1L;

    @XmlElement(name = "head")
    private FuiouHead head;

    @XmlElement(name = "body")
    private T body;

    @XmlElement(name = "sign")
    private String sign;

    public FuiouHead getHead() {
        return head;
    }

    public void setHead(FuiouHead head) {
        this.head = head;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
