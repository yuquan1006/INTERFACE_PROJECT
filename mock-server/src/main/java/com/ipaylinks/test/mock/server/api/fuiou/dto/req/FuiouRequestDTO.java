package com.ipaylinks.test.mock.server.api.fuiou.dto.req;

import java.io.Serializable;

public class FuiouRequestDTO implements Serializable {
    private static final long serialVersionUID = 5702283562238339979L;
    private String reqStr;

    private String sign;

    public String getReqStr() {
        return reqStr;
    }

    public void setReqStr(String reqStr) {
        this.reqStr = reqStr;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "FuiouRequestDTO{" +
                "reqStr='" + reqStr + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }
}
