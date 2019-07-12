package com.ipaylinks.test.mock.server.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BoscProperties {
     /*客户端使用  客户端私钥加签 和 服务端公钥验签*/
    /**
     * 客户端私钥
     */
    @Value("${bosc.clientPrivateKeyPath}")
    private String clientPrivateKeyPath;
    /**
     * 客户端私钥 密码
     */
    @Value("${bosc.clientPrivateKeyPassword}")
    private String clientPrivateKeyPassword;
    /**
     * 服务端公钥
     */
    @Value("${bosc.serverPublicKeyPath}")
    private String serverPublicKeyPath;

    /*服务端使用  服务端私钥加签 和 客户端公钥验签*/
    /**
     * 服务端私钥
     */
    @Value("${bosc.serverPrivateKeyPath}")
    private String serverPrivateKeyPath;
    /**
     * 服务端私钥
     */
    @Value("${bosc.serverPrivateKeyPassword}")
    private String serverPrivateKeyPassword;
    /**
     * 客户端公钥
     */
    @Value("${bosc.clientPublicKeyPath}")
    private String clientPublicKeyPath;

    public String getClientPrivateKeyPath() {
        return clientPrivateKeyPath;
    }

    public void setClientPrivateKeyPath(String clientPrivateKeyPath) {
        this.clientPrivateKeyPath = clientPrivateKeyPath;
    }

    public String getClientPrivateKeyPassword() {
        return clientPrivateKeyPassword;
    }

    public void setClientPrivateKeyPassword(String clientPrivateKeyPassword) {
        this.clientPrivateKeyPassword = clientPrivateKeyPassword;
    }

    public String getServerPublicKeyPath() {
        return serverPublicKeyPath;
    }

    public void setServerPublicKeyPath(String serverPublicKeyPath) {
        this.serverPublicKeyPath = serverPublicKeyPath;
    }

    public String getServerPrivateKeyPath() {
        return serverPrivateKeyPath;
    }

    public void setServerPrivateKeyPath(String serverPrivateKeyPath) {
        this.serverPrivateKeyPath = serverPrivateKeyPath;
    }

    public String getServerPrivateKeyPassword() {
        return serverPrivateKeyPassword;
    }

    public void setServerPrivateKeyPassword(String serverPrivateKeyPassword) {
        this.serverPrivateKeyPassword = serverPrivateKeyPassword;
    }

    public String getClientPublicKeyPath() {
        return clientPublicKeyPath;
    }

    public void setClientPublicKeyPath(String clientPublicKeyPath) {
        this.clientPublicKeyPath = clientPublicKeyPath;
    }
}
