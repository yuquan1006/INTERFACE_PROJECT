package com.ipaylinks.test.mock.server.config;

import com.ipaylinks.test.mock.server.service.czcb.CzcbSecurityService;
import com.ipaylinks.test.mock.server.service.czcb.CzcbSecurityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CzcbSecurityConfig {
    @Autowired
    private CzcbProperties czcbProperties;

    @Bean(name = "clientCzcbSecurityService")
    public CzcbSecurityService clientCzcbSecurityService(){
        return new CzcbSecurityServiceImpl(czcbProperties.getClientPrivateKey(),czcbProperties.getServerPublicKey());
    }

    @Bean(name = "serverCzcbSecurityService")
    public CzcbSecurityService serverCzcbSecurityService(){
        return new CzcbSecurityServiceImpl(czcbProperties.getServerPrivateKey(),czcbProperties.getClientPublicKey());
    }
}
