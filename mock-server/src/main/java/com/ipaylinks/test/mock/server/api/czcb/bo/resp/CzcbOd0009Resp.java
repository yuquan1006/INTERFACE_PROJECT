package com.ipaylinks.test.mock.server.api.czcb.bo.resp;

public class CzcbOd0009Resp {
    private String bankTradeNo;
    private String tradeStatus;
    private String tradeTimeBank;

    public String getBankTradeNo() {
        return bankTradeNo;
    }

    public void setBankTradeNo(String bankTradeNo) {
        this.bankTradeNo = bankTradeNo;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public String getTradeTimeBank() {
        return tradeTimeBank;
    }

    public void setTradeTimeBank(String tradeTimeBank) {
        this.tradeTimeBank = tradeTimeBank;
    }

    @Override
    public String toString() {
        return "CzcbOd0009Resp{" +
                "bankTradeNo='" + bankTradeNo + '\'' +
                ", tradeStatus='" + tradeStatus + '\'' +
                ", tradeTimeBank='" + tradeTimeBank + '\'' +
                '}';
    }
}
