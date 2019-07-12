package com.ipaylinks.test.mock.server.api.fuiou.bo.resp;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FuiouOrderQueryPageResponse {
    private String rspCd;
    private String rspDesc;
    private String mchntCd;
    private String hasNextPage;
    private String pageNo;
    private String pageSize;
    private List<FuiouOrderQueryPageResponseOrderLog> orderLogList;
    private String md5;

    public String getRspCd() {
        return rspCd;
    }

    public void setRspCd(String rspCd) {
        this.rspCd = rspCd;
    }

    public String getRspDesc() {
        return rspDesc;
    }

    public void setRspDesc(String rspDesc) {
        this.rspDesc = rspDesc;
    }

    public String getMchntCd() {
        return mchntCd;
    }

    public void setMchntCd(String mchntCd) {
        this.mchntCd = mchntCd;
    }

    public String getHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(String hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public List<FuiouOrderQueryPageResponseOrderLog> getOrderLogList() {
        return orderLogList;
    }

    public void setOrderLogList(List<FuiouOrderQueryPageResponseOrderLog> orderLogList) {
        this.orderLogList = orderLogList;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    @Override
    public String toString() {
        return "FuiouOrderQueryPageResponse{" +
                "rspCd='" + rspCd + '\'' +
                ", rspDesc='" + rspDesc + '\'' +
                ", mchntCd='" + mchntCd + '\'' +
                ", hasNextPage='" + hasNextPage + '\'' +
                ", pageNo='" + pageNo + '\'' +
                ", pageSize='" + pageSize + '\'' +
                ", orderLogList=" + orderLogList +
                ", md5='" + md5 + '\'' +
                '}';
    }

    public String getSignData(String md5Key, String separator) {
        List<String> signData = new ArrayList<>();
        signData.add(mchntCd);
        signData.add(hasNextPage);
        signData.add(pageNo);
        signData.add(pageSize);
        signData.add(md5Key);
        return StringUtils.join(signData, separator);
    }
}
