package com.ipaylinks.test.mock.server.api;

public enum ResponseTemplateEnum {
    FUIOU_TRANSFER("fuiou-transfer", "富友-转账至银行卡"),
    FUIOU_TRANS_DETAILS_QUERY("fuiou-transDetailsQuery", "富友-交易明细查询"),
    FUIOU_NOTIFY("fuiou-notify", "富友-文件上传通知接口"),
    FUIOU_SUBMIT_ORDER("fuiou-submitOrder", "富友-跨境收汇订单提交接口"),
    FUIOU_ORDER_QUERY_PAGE("fuiou-orderQueryPage", "富友-订单分页查询接口"),
    CZCB_OD0007("czcb-od0007", "稠州银行-内部帐子户开户接口 od0007"),
    CZCB_OA0003("czcb-oa0003", "稠州银行-内部帐子户信息查询 oa0003"),
    CZCB_OA0005("czcb-oa0005", "稠州银行-身份证正反面影像传输接口（暂不使用） oa0005"),
    CZCB_OA0015("czcb-oa0015", "稠州银行-子账号换绑卡 oa0015"),
    CZCB_OD0001("czcb-od0001", "稠州银行-账户余额查询 od0001"),
    CZCB_OD0002("czcb-od0002", "稠州银行-账户明细 od0002"),
    CZCB_OD0004("czcb-od0004", "稠州银行-合作机构账户结汇 od0004"),
    CZCB_OD0005("czcb-od0005", "稠州银行-汇率查询 od0005"),
    CZCB_OD0003("czcb-od0003", "稠州银行-合作机构账户清分（对私业务）od0003"),
    CZCB_OD0006("czcb-od0006", "稠州银行-内部帐子户提现 od0006"),
    CZCB_OD0008("czcb-od0008", "稠州银行-订单状态查询 od0008"),
    CZCB_OD0009("czcb-od0009", "稠州银行-合作机构账户清分（对公业务）od0009"),
    BOSC_OPENACCT000001("bosc-openacct000001", "子账户开户"),
    BOSC_OPENACCT000002("bosc-openacct000002", "账户信息修改"),
    BOSC_OPENACCT000004("bosc-openacct000004", "账户信息查询"),
    BOSC_IBS140002("bosc-ibs140002", "账户信息签约"),
    BOSC_IBS110018("bosc-ibs110018", "代付交易状态查询"),
    AE_AUTHORIZATION("ae-authorization", "AE卡组织-预授权接口"),
    AE_REVERSAL_ADVICE("ae-reversalAdvice", "AE卡组织-撤销通知接口"),
    ;

    private String name;
    private String desc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    ResponseTemplateEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "ResponseTemplateEnum{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                "} " + super.toString();
    }
}
