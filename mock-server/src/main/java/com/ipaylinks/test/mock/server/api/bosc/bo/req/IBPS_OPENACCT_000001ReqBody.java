package com.ipaylinks.test.mock.server.api.bosc.bo.req;

public class IBPS_OPENACCT_000001ReqBody {
    private String ProductCd;
    private String CustName;
    private String IdNo;
    private String ExpDay;
    private String MobilePhone;
    private String BindCardNo;
    private String ReservedPhone;
    private String koalB64Cert;
    private String Signature;

    public String getProductCd() {
        return ProductCd;
    }

    public void setProductCd(String productCd) {
        ProductCd = productCd;
    }

    public String getCustName() {
        return CustName;
    }

    public void setCustName(String custName) {
        CustName = custName;
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

    public String getMobilePhone() {
        return MobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        MobilePhone = mobilePhone;
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
        return "IBPS_OPENACCT_000001ReqBody{" +
                "ProductCd='" + ProductCd + '\'' +
                ", CustName='" + CustName + '\'' +
                ", IdNo='" + IdNo + '\'' +
                ", ExpDay='" + ExpDay + '\'' +
                ", MobilePhone='" + MobilePhone + '\'' +
                ", BindCardNo='" + BindCardNo + '\'' +
                ", ReservedPhone='" + ReservedPhone + '\'' +
                ", koalB64Cert='" + koalB64Cert + '\'' +
                ", Signature='" + Signature + '\'' +
                '}';
    }
}
