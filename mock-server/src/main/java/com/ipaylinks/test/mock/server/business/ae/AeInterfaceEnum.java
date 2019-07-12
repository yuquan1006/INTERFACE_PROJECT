package com.ipaylinks.test.mock.server.business.ae;

public enum AeInterfaceEnum {
    AE_AUTHORIZATION("1100", "AE卡组织-预授权接口"),
    AE_REVERSAL_ADVICE("1420", "AE卡组织-撤销通知接口"),
    ;

    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    AeInterfaceEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "BoscInterfaceEnum{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                "} " + super.toString();
    }
}
