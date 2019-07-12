package com.ipaylinks.test.mock.server.api.czcb.bo.resp;

public class CzcbOd0005Resp {
    private String currencyID;
    private String resultRate;
    private String raleRate;

    public String getCurrencyID() {
        return currencyID;
    }

    public void setCurrencyID(String currencyID) {
        this.currencyID = currencyID;
    }

    public String getResultRate() {
        return resultRate;
    }

    public void setResultRate(String resultRate) {
        this.resultRate = resultRate;
    }

    public String getRaleRate() {
        return raleRate;
    }

    public void setRaleRate(String raleRate) {
        this.raleRate = raleRate;
    }

    @Override
    public String toString() {
        return "CzcbOd0005Resp{" +
                "currencyID='" + currencyID + '\'' +
                ", resultRate='" + resultRate + '\'' +
                ", raleRate='" + raleRate + '\'' +
                '}';
    }
}
