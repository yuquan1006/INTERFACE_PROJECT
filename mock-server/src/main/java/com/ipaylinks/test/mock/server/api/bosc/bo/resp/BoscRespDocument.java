package com.ipaylinks.test.mock.server.api.bosc.bo.resp;

public class BoscRespDocument<T> {
    private BoscRespHead head;
    private T body;

    public BoscRespHead getHead() {
        return head;
    }

    public void setHead(BoscRespHead head) {
        this.head = head;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
