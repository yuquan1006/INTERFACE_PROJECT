package com.ipaylinks.test.mock.server.business.czcb;

public enum CzcbInterfaceEnum {
    OD0007("od0007", "内部帐子户开户接口 od0007"),
    OA0003("oa0003", "内部帐子户信息查询 oa0003"),
    OA0005("oa0005", "身份证正反面影像传输接口 oa0005"),
    OA0015("oa0015", "子账号换绑卡 oa0015"),
    OD0001("od0001", "账户余额查询 od0001"),
    OD0002("od0002", "账户明细 od0002"),
    OD0004("od0004", "合作机构账户结汇 od0004"),
    OD0005("od0005", "汇率查询 od0005"),
    OD0003("od0003", "合作机构账户清分（对私业务）od0003"),
    OD0006("od0006", "内部帐子户提现 od0006"),
    OD0008("od0008", "订单状态查询 od0008"),
    OD0009("od0009", "合作机构账户清分（对公业务）od0009");

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

    CzcbInterfaceEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "CzcbInterfaceEnum{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                "} " + super.toString();
    }
}
