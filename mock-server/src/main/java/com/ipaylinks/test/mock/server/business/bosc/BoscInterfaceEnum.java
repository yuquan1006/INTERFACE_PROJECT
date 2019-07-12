package com.ipaylinks.test.mock.server.business.bosc;

public enum BoscInterfaceEnum {
    IBPS_OPENACCT_000001("IBPS_OPENACCT_000001", "子账户开户"),
    IBPS_OPENACCT_000002("IBPS_OPENACCT_000002", "账户信息修改"),
    IBPS_OPENACCT_000004("IBPS_OPENACCT_000004", "账户信息查询"),
    IBPS_IBS_140002("IBPS_IBS_140002", "账户信息签约"),
    IBPS_IBS_110018("IBPS_IBS_110018", "代付交易状态查询");

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

    BoscInterfaceEnum(String code, String msg) {
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
