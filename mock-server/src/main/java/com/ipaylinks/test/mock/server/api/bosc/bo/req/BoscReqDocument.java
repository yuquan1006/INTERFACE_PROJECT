package com.ipaylinks.test.mock.server.api.bosc.bo.req;

public class BoscReqDocument<T> {
    public BoscReqDocument() {
    }

    public BoscReqDocument(T body) {
        this.body = body;
    }

    private BoscReqHead head;
    private T body;

    public BoscReqHead getHead() {
        return head;
    }

    public void setHead(BoscReqHead head) {
        this.head = head;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "BoscReqDocument{" +
                "head=" + head +
                ", body=" + body +
                '}';
    }
}
