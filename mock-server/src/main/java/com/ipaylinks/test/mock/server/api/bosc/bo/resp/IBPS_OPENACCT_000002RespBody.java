package com.ipaylinks.test.mock.server.api.bosc.bo.resp;

public class IBPS_OPENACCT_000002RespBody {
    private String EacctNo;
    private String ProductCd;
    private String IdNo;
    private String NewCardNo;
    private String NewReservedPhone;
    private String NewCustName;
    private String RespCode;
    private String RespMsg;
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

    public String getIdNo() {
        return IdNo;
    }

    public void setIdNo(String idNo) {
        IdNo = idNo;
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

    public String getNewCustName() {
        return NewCustName;
    }

    public void setNewCustName(String newCustName) {
        NewCustName = newCustName;
    }

    public String getRespCode() {
        return RespCode;
    }

    public void setRespCode(String respCode) {
        RespCode = respCode;
    }

    public String getRespMsg() {
        return RespMsg;
    }

    public void setRespMsg(String respMsg) {
        RespMsg = respMsg;
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
        return "IBPS_OPENACCT_000002RespBody{" +
                "EacctNo='" + EacctNo + '\'' +
                ", ProductCd='" + ProductCd + '\'' +
                ", IdNo='" + IdNo + '\'' +
                ", NewCardNo='" + NewCardNo + '\'' +
                ", NewReservedPhone='" + NewReservedPhone + '\'' +
                ", NewCustName='" + NewCustName + '\'' +
                ", RespCode='" + RespCode + '\'' +
                ", RespMsg='" + RespMsg + '\'' +
                ", koalB64Cert='" + koalB64Cert + '\'' +
                ", Signature='" + Signature + '\'' +
                '}';
    }
}
