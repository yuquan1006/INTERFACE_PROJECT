package com.ipaylinks.test.mock.server.api.bosc.bo.resp;

public class IBPS_IBS_110018RespBody {
    private String SerialNo;
    private String RetCode;
    private String RetMsg;
    private String koalB64Cert;
    private String Signature;

    public String getSerialNo() {
        return SerialNo;
    }

    public void setSerialNo(String serialNo) {
        SerialNo = serialNo;
    }

    public String getRetCode() {
        return RetCode;
    }

    public void setRetCode(String retCode) {
        RetCode = retCode;
    }

    public String getRetMsg() {
        return RetMsg;
    }

    public void setRetMsg(String retMsg) {
        RetMsg = retMsg;
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
        return "IBPS_IBS_110018RespBody{" +
                "SerialNo='" + SerialNo + '\'' +
                ", RetCode='" + RetCode + '\'' +
                ", RetMsg='" + RetMsg + '\'' +
                ", koalB64Cert='" + koalB64Cert + '\'' +
                ", Signature='" + Signature + '\'' +
                '}';
    }
}
