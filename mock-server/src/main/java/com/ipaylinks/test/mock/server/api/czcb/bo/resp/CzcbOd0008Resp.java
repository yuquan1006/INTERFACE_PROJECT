package com.ipaylinks.test.mock.server.api.czcb.bo.resp;

public class CzcbOd0008Resp {
    private String tradeStatus;

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    @Override
    public String toString() {
        return "CzcbOd0008Resp{" +
                "tradeStatus='" + tradeStatus + '\'' +
                '}';
    }
}
