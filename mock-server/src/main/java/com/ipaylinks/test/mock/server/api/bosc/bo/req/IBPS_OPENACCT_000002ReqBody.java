package com.ipaylinks.test.mock.server.api.bosc.bo.req;

public class IBPS_OPENACCT_000002ReqBody {
    private String EacctNo;
    private String ProductCd;
    private String CustName;
    private String IdNo;
    private String BindCardNo;
    private String NewCardNo;
    private String NewReservedPhone;
    private String ReservedPhone;
    private String NewCustName;
    private String koalB64Cert;
    private String Signature;

    public String getEacctNo() {
        return EacctNo;
    }

    public void setEacctNo(String eacctNo) {
        EacctNo = eacctNo;
    }

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

    public String getBindCardNo() {
        return BindCardNo;
    }

    public void setBindCardNo(String bindCardNo) {
        BindCardNo = bindCardNo;
    }

    public String getNewCardNo() {
        return NewCardNo;
    }

    public void setNewCardNo(String newCardNo) {
        NewCardNo = newCardNo;
    }

    public String getNewReservedPhone() {
        return NewReservedPhone;
    }

    public void setNewReservedPhone(String newReservedPhone) {
        NewReservedPhone = newReservedPhone;
    }

    public String getReservedPhone() {
        return ReservedPhone;
    }

    public void setReservedPhone(String reservedPhone) {
        ReservedPhone = reservedPhone;
    }

    public String getNewCustName() {
        return NewCustName;
    }

    public void setNewCustName(String newCustName) {
        NewCustName = newCustName;
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
        return "IBPS_OPENACCT_000002ReqBody{" +
                "EacctNo='" + EacctNo + '\'' +
                ", ProductCd='" + ProductCd + '\'' +
                ", CustName='" + CustName + '\'' +
                ", IdNo='" + IdNo + '\'' +
                ", BindCardNo='" + BindCardNo + '\'' +
                ", NewCardNo='" + NewCardNo + '\'' +
                ", NewReservedPhone='" + NewReservedPhone + '\'' +
                ", ReservedPhone='" + ReservedPhone + '\'' +
                ", NewCustName='" + NewCustName + '\'' +
                ", koalB64Cert='" + koalB64Cert + '\'' +
                ", Signature='" + Signature + '\'' +
                '}';
    }
}
