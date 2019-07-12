package com.ipaylinks.test.mock.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CzcbProperties {
     /*客户端使用  客户端私钥加签 和 服务端公钥验签*/
    /**
     * 客户端私钥
     */
    @Value("${czcb.clientPrivateKey}")
    private String clientPrivateKey;
    /**
     * 服务端公钥
     */
    @Value("${czcb.serverPublicKey}")
    private String serverPublicKey;

    /*服务端使用  服务端私钥加签 和 客户端公钥验签*/
    /**
     * 服务端私钥
     */
    @Value("${czcb.serverPrivateKey}")
    private String serverPrivateKey;
    /**
     * 客户端公钥
     */
    @Value("${czcb.clientPublicKey}")
    private String clientPublicKey;

    public String getClientPrivateKey() {
        return clientPrivateKey;
    }

    public void setClientPrivateKey(String clientPrivateKey) {
        this.clientPrivateKey = clientPrivateKey;
    }

    public String getServerPublicKey() {
        return serverPublicKey;
    }

    public void setServerPublicKey(String serverPublicKey) {
        this.serverPublicKey = serverPublicKey;
    }

    public String getServerPrivateKey() {
        return serverPrivateKey;
    }

    public void setServerPrivateKey(String serverPrivateKey) {
        this.serverPrivateKey = serverPrivateKey;
    }

    public String getClientPublicKey() {
        return clientPublicKey;
    }

    public void setClientPublicKey(String clientPublicKey) {
        this.clientPublicKey = clientPublicKey;
    }
}
