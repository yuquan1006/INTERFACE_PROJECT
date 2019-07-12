package com.ipaylinks.test.mock.server.api.fuiou.bo.resp;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class FuiouNotifyResponse {
    private String rspCd;
    private String rspDesc;
    private List<String> errorList;
    private String mchntCd;
    private String fileTp;
    private String fileNm;
    private String processSt;
    private String note;
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

    public List<String> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<String> errorList) {
        this.errorList = errorList;
    }

    public String getMchntCd() {
        return mchntCd;
    }

    public void setMchntCd(String mchntCd) {
        this.mchntCd = mchntCd;
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

    public String getProcessSt() {
        return processSt;
    }

    public void setProcessSt(String processSt) {
        this.processSt = processSt;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    @Override
    public String toString() {
        return "FuiouNotifyResponse{" +
                "rspCd='" + rspCd + '\'' +
                ", rspDesc='" + rspDesc + '\'' +
                ", errorList=" + errorList +
                ", mchntCd='" + mchntCd + '\'' +
                ", fileTp='" + fileTp + '\'' +
                ", fileNm='" + fileNm + '\'' +
                ", processSt='" + processSt + '\'' +
                ", note='" + note + '\'' +
                ", md5='" + md5 + '\'' +
                '}';
    }

    public String getSignData(String md5Key, String separator) {
        List<String> signData = new ArrayList<>();
        signData.add(mchntCd);
        signData.add(fileTp);
        signData.add(fileNm);
        signData.add(processSt);
        signData.add(note);
        signData.add(md5Key);
        return StringUtils.join(signData, separator);
    }
}
