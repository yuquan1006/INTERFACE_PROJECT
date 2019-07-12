package com.ipaylinks.test.mock.server.api.bosc.dto.resp;

import java.io.Serializable;
import java.util.Map;

public class BoscRespDTO implements Serializable {
    private static final long serialVersionUID = 8944875263983548508L;

    public BoscRespDTO() {
    }

    public BoscRespDTO(Map<String, Map<String, String>> document) {
        this.document = document;
    }

    private Map<String, Map<String, String>> document;

    public Map<String, Map<String, String>> getDocument() {
        return document;
    }

    public void setDocument(Map<String, Map<String, String>> document) {
        this.document = document;
    }
}
