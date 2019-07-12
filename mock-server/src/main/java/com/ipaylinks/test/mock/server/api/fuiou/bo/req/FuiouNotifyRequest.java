package com.ipaylinks.test.mock.server.api.fuiou.bo.req;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FuiouNotifyRequest {
    private String mchntCd;
    private String backNotifyUrl;
    private String fileTp;
    private String fileNm;
    private String orderNo;
    private String md5;

    public String getMchntCd() {
        return mchntCd;
    }

    public void setMchntCd(String mchntCd) {
        this.mchntCd = mchntCd;
    }

    public String getBackNotifyUrl() {
        return backNotifyUrl;
    }

    public void setBackNotifyUrl(String backNotifyUrl) {
        this.backNotifyUrl = backNotifyUrl;
    }

    public String getFileTp() {
        return fileTp;
    }

    public void setFileTp(String fileTp) {
        this.fileTp = fileTp;
    }

    public String getFileNm() {
        return fileNm;
    }

    public void setFileNm(String fileNm) {
        this.fileNm = fileNm;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    @Override
    public String toString() {
        return "FuiouNotifyRequest{" +
                "mchntCd='" + mchntCd + '\'' +
                ", backNotifyUrl='" + backNotifyUrl + '\'' +
                ", fileTp='" + fileTp + '\'' +
                ", fileNm='" + fileNm + '\'' +
                ", orderNo='" + orderNo + '\'' +
                ", md5='" + md5 + '\'' +
                '}';
    }

    public String getSignData(String md5Key, String separator) {
        List<String> signData = new ArrayList<>();
        signData.add(mchntCd);
        signData.add(backNotifyUrl);
        signData.add(fileTp);
        signData.add(fileNm);
        signData.add(orderNo);
        signData.add(md5Key);
        return StringUtils.join(signData, separator);
    }
}
