package com.ipaylinks.test.mock.server.api.czcb.bo.req;

public class CzcbOd0002Req {
    private CzcbReqPublicData publicData;
    private String openId;
    private String acctNo;
    private String pageIndex;
    private String pageCount;
    private String beginDate;
    private String endDate;

    public CzcbReqPublicData getPublicData() {
        return publicData;
    }

    public void setPublicData(CzcbReqPublicData publicData) {
        this.publicData = publicData;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getAcctNo() {
        return acctNo;
    }

    public void setAcctNo(String acctNo) {
        this.acctNo = acctNo;
    }

    public String getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(String pageIndex) {
        this.pageIndex = pageIndex;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "CzcbOd0002Req{" +
                "publicData=" + publicData +
                ", openId='" + openId + '\'' +
                ", acctNo='" + acctNo + '\'' +
                ", pageIndex='" + pageIndex + '\'' +
                ", pageCount='" + pageCount + '\'' +
                ", beginDate='" + beginDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}
