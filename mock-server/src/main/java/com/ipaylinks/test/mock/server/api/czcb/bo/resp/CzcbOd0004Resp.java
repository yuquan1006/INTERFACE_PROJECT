package com.ipaylinks.test.mock.server.api.czcb.bo.resp;

public class CzcbOd0004Resp {
    private String amount;
    private String currencyID;
    private String resultRate;
    private String rmbAmount;
    private String forAmount;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

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

    public String getRmbAmount() {
        return rmbAmount;
    }

    public void setRmbAmount(String rmbAmount) {
        this.rmbAmount = rmbAmount;
    }

    public String getForAmount() {
        return forAmount;
    }

    public void setForAmount(String forAmount) {
        this.forAmount = forAmount;
    }

    @Override
    public String toString() {
        return "CzcbOd0004Resp{" +
                "amount='" + amount + '\'' +
                ", currencyID='" + currencyID + '\'' +
                ", resultRate='" + resultRate + '\'' +
                ", rmbAmount='" + rmbAmount + '\'' +
                ", forAmount='" + forAmount + '\'' +
                '}';
    }
}
