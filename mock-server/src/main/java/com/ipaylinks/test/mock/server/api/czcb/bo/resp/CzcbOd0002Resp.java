package com.ipaylinks.test.mock.server.api.czcb.bo.resp;

import java.util.List;

public class CzcbOd0002Resp {
    private String listTotalCount;
    private List<CzcbOd0002RespList> list;

    public String getListTotalCount() {
        return listTotalCount;
    }

    public void setListTotalCount(String listTotalCount) {
        this.listTotalCount = listTotalCount;
    }

    public List<CzcbOd0002RespList> getList() {
        return list;
    }

    public void setList(List<CzcbOd0002RespList> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "CzcbOd0002Resp{" +
                "listTotalCount='" + listTotalCount + '\'' +
                ", list=" + list +
                '}';
    }
}
