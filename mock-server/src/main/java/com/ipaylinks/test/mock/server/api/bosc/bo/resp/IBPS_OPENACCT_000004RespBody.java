package com.ipaylinks.test.mock.server.api.bosc.bo.resp;

public class IBPS_OPENACCT_000004RespBody {
    private String EacctNo;
    private String ProductParam;
    private String CustName;
    private String IdNo;
    private String WorkingBal;
    private String MobllePhone;
    private String BindCardNo;
    private String ReservedPhone;
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

    public String getProductParam() {
        return ProductParam;
    }

    public void setProductParam(String productParam) {
        ProductParam = productParam;
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

    public String getWorkingBal() {
        return WorkingBal;
    }

    public void setWorkingBal(String workingBal) {
        WorkingBal = workingBal;
    }

    public String getMobllePhone() {
        return MobllePhone;
    }

    public void setMobllePhone(String mobllePhone) {
        MobllePhone = mobllePhone;
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
        return "IBPS_OPENACCT_000004RespBody{" +
                "EacctNo='" + EacctNo + '\'' +
                ", ProductParam='" + ProductParam + '\'' +
                ", CustName='" + CustName + '\'' +
                ", IdNo='" + IdNo + '\'' +
                ", WorkingBal='" + WorkingBal + '\'' +
                ", MobllePhone='" + MobllePhone + '\'' +
                ", BindCardNo='" + BindCardNo + '\'' +
                ", ReservedPhone='" + ReservedPhone + '\'' +
                ", RespCode='" + RespCode + '\'' +
                ", RespMsg='" + RespMsg + '\'' +
                ", koalB64Cert='" + koalB64Cert + '\'' +
                ", Signature='" + Signature + '\'' +
                '}';
    }
}
