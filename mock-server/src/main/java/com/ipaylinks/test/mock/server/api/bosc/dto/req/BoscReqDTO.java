package com.ipaylinks.test.mock.server.api.bosc.dto.req;

import java.io.Serializable;
import java.util.Map;

public class BoscReqDTO implements Serializable {
    private static final long serialVersionUID = -3710888535808358397L;
    private Map<String, Map<String, String>> document;

    public Map<String, Map<String, String>> getDocument() {
        return document;
    }

    public void setDocument(Map<String, Map<String, String>> document) {
        this.document = document;
    }
}
