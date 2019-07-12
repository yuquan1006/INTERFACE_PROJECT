package com.ipaylinks.test.mock.server.business.fuiou;

public enum FuiouInterfaceEnum {
    TRANSFER("011", "转账至银行卡"),
    TRANS_DETAILS_QUERY("003", "交易明细查询"),
    NOTIFY("notify.fuiou", "文件上传通知接口"),
    SUBMIT_ORDER("submitOrder.fuiou", "跨境收汇订单提交接口"),
    ORDER_QUERY_PAGE("orderQueryPage.fuiou", "订单分页查询接口");

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

    FuiouInterfaceEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "FuiouInterfaceEnums{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                "} " + super.toString();
    }
}
