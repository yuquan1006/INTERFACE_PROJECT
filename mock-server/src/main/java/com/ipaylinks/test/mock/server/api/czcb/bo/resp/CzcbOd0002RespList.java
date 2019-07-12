package com.ipaylinks.test.mock.server.api.czcb.bo.resp;

public class CzcbOd0002RespList {
    private String type;
    private String amount;
    private String tradeTime;
    private String tradeDesc;
    private String tradeName;
    private String tradeNo;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getTradeDesc() {
        return tradeDesc;
    }

    public void setTradeDesc(String tradeDesc) {
        this.tradeDesc = tradeDesc;
    }

    public String getTradeName() {
        return tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    @Override
    public String toString() {
        return "CzcbOd0002RespList{" +
                "type='" + type + '\'' +
                ", amount='" + amount + '\'' +
                ", tradeTime='" + tradeTime + '\'' +
                ", tradeDesc='" + tradeDesc + '\'' +
                ", tradeName='" + tradeName + '\'' +
                ", tradeNo='" + tradeNo + '\'' +
                '}';
    }
}
