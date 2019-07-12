package com.ipaylinks.test.mock.server.api.bosc.bo.req;

public class BoscReqHead {
    private String HeadTranCode;
    private String HeadBusinessId;
    private String HeadReqDate;
    private String HeadReqTime;
    private String HeadSndOrgan;
    private String HeadMsgVersion;
    private String HeadRevOrgan;

    public String getHeadTranCode() {
        return HeadTranCode;
    }

    public void setHeadTranCode(String headTranCode) {
        HeadTranCode = headTranCode;
    }

    public String getHeadBusinessId() {
        return HeadBusinessId;
    }

    public void setHeadBusinessId(String headBusinessId) {
        HeadBusinessId = headBusinessId;
    }

    public String getHeadReqDate() {
        return HeadReqDate;
    }

    public void setHeadReqDate(String headReqDate) {
        HeadReqDate = headReqDate;
    }

    public String getHeadReqTime() {
        return HeadReqTime;
    }

    public void setHeadReqTime(String headReqTime) {
        HeadReqTime = headReqTime;
    }

    public String getHeadSndOrgan() {
        return HeadSndOrgan;
    }

    public void setHeadSndOrgan(String headSndOrgan) {
        HeadSndOrgan = headSndOrgan;
    }

    public String getHeadMsgVersion() {
        return HeadMsgVersion;
    }

    public void setHeadMsgVersion(String headMsgVersion) {
        HeadMsgVersion = headMsgVersion;
    }

    public String getHeadRevOrgan() {
        return HeadRevOrgan;
    }

    public void setHeadRevOrgan(String headRevOrgan) {
        HeadRevOrgan = headRevOrgan;
    }

    @Override
    public String toString() {
        return "BoscReqHead{" +
                "HeadTranCode='" + HeadTranCode + '\'' +
                ", HeadBusinessId='" + HeadBusinessId + '\'' +
                ", HeadReqDate='" + HeadReqDate + '\'' +
                ", HeadReqTime='" + HeadReqTime + '\'' +
                ", HeadSndOrgan='" + HeadSndOrgan + '\'' +
                ", HeadMsgVersion='" + HeadMsgVersion + '\'' +
                ", HeadRevOrgan='" + HeadRevOrgan + '\'' +
                '}';
    }
}
