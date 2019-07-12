package com.ipaylinks.test.mock.server.config;

import com.ipaylinks.test.mock.server.service.bosc.BoscCertificateUtils;
import com.ipaylinks.test.mock.server.service.bosc.BoscSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.PrivateKey;
import java.security.PublicKey;

@Configuration
public class BoscSecurityConfig {
    @Autowired
    private BoscProperties boscProperties;

    @Bean(name = "serverBoscSecurityService")
    public BoscSecurityService serverBoscSecurityService(){
        //服务端私钥
        PrivateKey privateKey = BoscCertificateUtils.getPrivateKey(boscProperties.getServerPrivateKeyPath(), boscProperties.getServerPrivateKeyPassword(), BoscCertificateUtils.KEY_STORE_TYPE_PKCS12);
        //客户端公钥
        PublicKey publicKey = BoscCertificateUtils.getPublicKey(boscProperties.getClientPublicKeyPath());
        return new BoscSecurityService(privateKey,publicKey);
    }

    @Bean(name = "clientBoscSecurityService")
    public BoscSecurityService clientBoscSecurityService(){
        //客户端私钥
        PrivateKey privateKey = BoscCertificateUtils.getPrivateKey(boscProperties.getClientPrivateKeyPath(), boscProperties.getClientPrivateKeyPassword(), BoscCertificateUtils.KEY_STORE_TYPE_PKCS12);
        //服务端公钥
        PublicKey publicKey = BoscCertificateUtils.getPublicKey(boscProperties.getServerPublicKeyPath());
        return new BoscSecurityService(privateKey,publicKey);
    }
}
