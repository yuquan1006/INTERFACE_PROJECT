package com.ipaylinks.test.mock.server.api.bosc.bo.req;

public class IBPS_IBS_140002ReqBody {
    private String CustName;
    private String IdType;
    private String IdNo;
    private String ExpDay;
    private String EacctNo;
    private String BindCardNo;
    private String ReservedPhone;
    private String CardNo;
    private String Riskinfomobile;
    private String ExchangeCode;
    private String SignTypeNo;
    private String koalB64Cert;
    private String Signature;

    public String getCustName() {
        return CustName;
    }

    public void setCustName(String custName) {
        CustName = custName;
    }

    public String getIdType() {
        return IdType;
    }

    public void setIdType(String idType) {
        IdType = idType;
    }

    public String getIdNo() {
        return IdNo;
    }

    public void setIdNo(String idNo) {
        IdNo = idNo;
    }

    public String getExpDay() {
        return ExpDay;
    }

    public void setExpDay(String expDay) {
        ExpDay = expDay;
    }

    public String getEacctNo() {
        return EacctNo;
    }

    public void setEacctNo(String eacctNo) {
        EacctNo = eacctNo;
    }

    public String getBindCardNo() {
        return BindCardNo;
    }

    public void setBindCardNo(String bindCardNo) {
        BindCardNo = bindCardNo;
    }

    public String getReservedPhone() {
        return ReservedPhone;
    }

    public void setReservedPhone(String reservedPhone) {
        ReservedPhone = reservedPhone;
    }

    public String getCardNo() {
        return CardNo;
    }

    public void setCardNo(String cardNo) {
        CardNo = cardNo;
    }

    public String getRiskinfomobile() {
        return Riskinfomobile;
    }

    public void setRiskinfomobile(String riskinfomobile) {
        Riskinfomobile = riskinfomobile;
    }

    public String getExchangeCode() {
        return ExchangeCode;
    }

    public void setExchangeCode(String exchangeCode) {
        ExchangeCode = exchangeCode;
    }

    public String getSignTypeNo() {
        return SignTypeNo;
    }

    public void setSignTypeNo(String signTypeNo) {
        SignTypeNo = signTypeNo;
    }

    public String getKoalB64Cert() {
        return koalB64Cert;
    }

    public void setKoalB64Cert(String koalB64Cert) {
        this.koalB64Cert = koalB64Cert;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }

    @Override
    public String toString() {
        return "IBPS_IBS_140002ReqBody{" +
                "CustName='" + CustName + '\'' +
                ", IdType='" + IdType + '\'' +
                ", IdNo='" + IdNo + '\'' +
                ", ExpDay='" + ExpDay + '\'' +
                ", EacctNo='" + EacctNo + '\'' +
                ", BindCardNo='" + BindCardNo + '\'' +
                ", ReservedPhone='" + ReservedPhone + '\'' +
                ", CardNo='" + CardNo + '\'' +
                ", Riskinfomobile='" + Riskinfomobile + '\'' +
                ", ExchangeCode='" + ExchangeCode + '\'' +
                ", SignTypeNo='" + SignTypeNo + '\'' +
                ", koalB64Cert='" + koalB64Cert + '\'' +
                ", Signature='" + Signature + '\'' +
                '}';
    }
}
