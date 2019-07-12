package com.ipaylinks.test.mock.server.api.bosc.bo.req;

public class IBPS_OPENACCT_000004ReqBody {
    private String EacctNo;
    private String ProductParam;
    private String SubAcctNo;
    private String IdNo;
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

    public String getSubAcctNo() {
        return SubAcctNo;
    }

    public void setSubAcctNo(String subAcctNo) {
        SubAcctNo = subAcctNo;
    }

    public String getIdNo() {
        return IdNo;
    }

    public void setIdNo(String idNo) {
        IdNo = idNo;
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
        return "IBPS_OPENACCT_000004ReqBody{" +
                "EacctNo='" + EacctNo + '\'' +
                ", ProductParam='" + ProductParam + '\'' +
                ", SubAcctNo='" + SubAcctNo + '\'' +
                ", IdNo='" + IdNo + '\'' +
                ", koalB64Cert='" + koalB64Cert + '\'' +
                ", Signature='" + Signature + '\'' +
                '}';
    }
}
