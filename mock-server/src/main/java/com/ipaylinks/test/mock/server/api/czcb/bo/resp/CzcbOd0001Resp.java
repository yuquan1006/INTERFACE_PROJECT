package com.ipaylinks.test.mock.server.api.czcb.bo.resp;

public class CzcbOd0001Resp {
    private String balance;
    private String availBal;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getAvailBal() {
        return availBal;
    }

    public void setAvailBal(String availBal) {
        this.availBal = availBal;
    }

    @Override
    public String toString() {
        return "CzcbOd0001Resp{" +
                "balance='" + balance + '\'' +
                ", availBal='" + availBal + '\'' +
                '}';
    }
}
