package com.ipaylinks.test.mock.server.api;

public enum NotifyTemplateEnum {
    FUIOU_TRANSFER_NOTIFY("fuiou-transferNotify", "富友-转账至银行卡通知"),
    FUIOU_NOTIFY_NOTIFY("fuiou-notifyNotify", "富友-文件上传通知接口通知"),
    FUIOU_SUBMIT_ORDER_NOTIFY("fuiou-submitOrderNotify", "富友-跨境收汇订单提交接口通知");

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

    NotifyTemplateEnum(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "NotifyTemplateEnum{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                "} " + super.toString();
    }
}
