package com.ipaylinks.test.mock.server.api.fuiou.bo.resp;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Libo
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "body")
public class TransDetailsQueryResponseBody implements Serializable{
    private static final long serialVersionUID = 3993051446029587520L;
    @XmlElement(name = "rspCode")
    private String rspCode;

    @XmlElement(name = "rspDesc")
    private String rspDesc;

    @XmlElement(name = "currentPage")
    private String currentPage;

    @XmlElement(name = "totalPage")
    private String totalPage;

    @XmlElement(name = "totalRecord")
    private String totalRecord;

    @XmlElement(name = "pageSize")
    private String pageSize;

    @XmlElementWrapper(name="resultSet")
    @XmlElement(name="result")
    private List<TransDetailsQueryResult> resultSet;

    public String getRspCode() {
        return rspCode;
    }

    public void setRspCode(String rspCode) {
        this.rspCode = rspCode;
    }

    public String getRspDesc() {
        return rspDesc;
    }

    public void setRspDesc(String rspDesc) {
        this.rspDesc = rspDesc;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public String getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(String totalPage) {
        this.totalPage = totalPage;
    }

    public String getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(String totalRecord) {
        this.totalRecord = totalRecord;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public List<TransDetailsQueryResult> getResultSet() {
        return resultSet;
    }

    public void setResultSet(List<TransDetailsQueryResult> resultSet) {
        this.resultSet = resultSet;
    }
}
