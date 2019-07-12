package com.ipaylinks.test.mock.server.api.bosc.bo.resp;

public class IBPS_OPENACCT_000001RespBody {
    private String ProductCd;
    private String CustName;
    private String IdNo;
    private String RespCode;
    private String RespMsg;
    private String EacctNo;
    private String ShAppNo;
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

    public String getEacctNo() {
        return EacctNo;
    }

    public void setEacctNo(String eacctNo) {
        EacctNo = eacctNo;
    }

    public String getShAppNo() {
        return ShAppNo;
    }

    public void setShAppNo(String shAppNo) {
        ShAppNo = shAppNo;
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
        return "IBPS_OPENACCT_000001RespBody{" +
                "ProductCd='" + ProductCd + '\'' +
                ", CustName='" + CustName + '\'' +
                ", IdNo='" + IdNo + '\'' +
                ", RespCode='" + RespCode + '\'' +
                ", RespMsg='" + RespMsg + '\'' +
                ", EacctNo='" + EacctNo + '\'' +
                ", ShAppNo='" + ShAppNo + '\'' +
                ", koalB64Cert='" + koalB64Cert + '\'' +
                ", Signature='" + Signature + '\'' +
                '}';
    }
}
