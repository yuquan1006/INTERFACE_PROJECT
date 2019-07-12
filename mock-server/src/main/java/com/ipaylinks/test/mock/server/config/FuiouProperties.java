package com.ipaylinks.test.mock.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FuiouProperties {
    @Value("${fuiou.xml.partnerId}")
    private String xmlPartnerId;

    @Value("${fuiou.xml.md5Key}")
    private String xmlMd5Key;

    @Value("${fuiou.json.partnerId}")
    private String jsonPartnerId;

    @Value("${fuiou.json.md5Key}")
    private String jsonMd5Key;

    public String getXmlPartnerId() {
        return xmlPartnerId;
    }

    public void setXmlPartnerId(String xmlPartnerId) {
        this.xmlPartnerId = xmlPartnerId;
    }

    public String getXmlMd5Key() {
        return xmlMd5Key;
    }

    public void setXmlMd5Key(String xmlMd5Key) {
        this.xmlMd5Key = xmlMd5Key;
    }

    public String getJsonPartnerId() {
        return jsonPartnerId;
    }

    public void setJsonPartnerId(String jsonPartnerId) {
        this.jsonPartnerId = jsonPartnerId;
    }

    public String getJsonMd5Key() {
        return jsonMd5Key;
    }

    public void setJsonMd5Key(String jsonMd5Key) {
        this.jsonMd5Key = jsonMd5Key;
    }
}
