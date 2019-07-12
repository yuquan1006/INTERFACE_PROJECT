package com.ipaylinks.test.mock.server.api.bosc.bo.resp;

public class BoscRespHead {
    private String HeadRspDate;
    private String HeadRspTime;
    private String HeadSndOrgan;
    private String HeadRevOrgan;
    private String HeadBusinessId;
    private String HeadRetCode;
    private String HeadRetMsg;
    private String HeadMsgVersion;
    private String HeadTranCode;

    public String getHeadRspDate() {
        return HeadRspDate;
    }

    public void setHeadRspDate(String headRspDate) {
        HeadRspDate = headRspDate;
    }

    public String getHeadRspTime() {
        return HeadRspTime;
    }

    public void setHeadRspTime(String headRspTime) {
        HeadRspTime = headRspTime;
    }

    public String getHeadSndOrgan() {
        return HeadSndOrgan;
    }

    public void setHeadSndOrgan(String headSndOrgan) {
        HeadSndOrgan = headSndOrgan;
    }

    public String getHeadRevOrgan() {
        return HeadRevOrgan;
    }

    public void setHeadRevOrgan(String headRevOrgan) {
        HeadRevOrgan = headRevOrgan;
    }

    public String getHeadBusinessId() {
        return HeadBusinessId;
    }

    public void setHeadBusinessId(String headBusinessId) {
        HeadBusinessId = headBusinessId;
    }

    public String getHeadRetCode() {
        return HeadRetCode;
    }

    public void setHeadRetCode(String headRetCode) {
        HeadRetCode = headRetCode;
    }

    public String getHeadRetMsg() {
        return HeadRetMsg;
    }

    public void setHeadRetMsg(String headRetMsg) {
        HeadRetMsg = headRetMsg;
    }

    public String getHeadMsgVersion() {
        return HeadMsgVersion;
    }

    public void setHeadMsgVersion(String headMsgVersion) {
        HeadMsgVersion = headMsgVersion;
    }

    public String getHeadTranCode() {
        return HeadTranCode;
    }

    public void setHeadTranCode(String headTranCode) {
        HeadTranCode = headTranCode;
    }

    @Override
    public String toString() {
        return "BoscRespHead{" +
                "HeadRspDate='" + HeadRspDate + '\'' +
                ", HeadRspTime='" + HeadRspTime + '\'' +
                ", HeadSndOrgan='" + HeadSndOrgan + '\'' +
                ", HeadRevOrgan='" + HeadRevOrgan + '\'' +
                ", HeadBusinessId='" + HeadBusinessId + '\'' +
                ", HeadRetCode='" + HeadRetCode + '\'' +
                ", HeadRetMsg='" + HeadRetMsg + '\'' +
                ", HeadMsgVersion='" + HeadMsgVersion + '\'' +
                ", HeadTranCode='" + HeadTranCode + '\'' +
                '}';
    }
}
