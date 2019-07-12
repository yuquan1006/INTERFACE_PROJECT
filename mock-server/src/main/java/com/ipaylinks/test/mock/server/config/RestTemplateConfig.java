package com.ipaylinks.test.mock.server.config;

import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

//@Configuration
public class RestTemplateConfig {
    private static final String HTTP = "http";
    private static final String HTTPS = "https";

    private static final RestTemplate INSTANCE = new RestTemplate(new HttpComponentsClientHttpRequestFactory(getHttpClient()));

    public static RestTemplate getRestTemplate() {
        return INSTANCE;
    }

//    @Bean
//    public RestTemplate restTemplate(){
//        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(getHttpClient()));
//    }

    private static CloseableHttpClient getHttpClient(){
        return HttpClients.custom()
                .setConnectionManager(getConnManager() )
                .setConnectionManagerShared(true)
                .setDefaultRequestConfig(defaultRequestConfig())
                .build();
    }

    private static PoolingHttpClientConnectionManager getConnManager(){
        PoolingHttpClientConnectionManager connManager = null;
        try {
            // 全部信任 不做身份鉴定
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                    return true;
                }
            }).build();
            ConnectionSocketFactory sslSf = new SSLConnectionSocketFactory(sslContext, new String[]{"SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.2"}, null, NoopHostnameVerifier.INSTANCE);
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register(HTTP, PlainConnectionSocketFactory.getSocketFactory())
                    .register(HTTPS, sslSf)
                    .build();

            connManager  = new PoolingHttpClientConnectionManager(registry);
            //max connection
            connManager .setMaxTotal(200);
            connManager.setDefaultMaxPerRoute(20);
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            e.printStackTrace();
        }
        return connManager;
    }

    private static RequestConfig defaultRequestConfig(){
        // Create global request configuration
        return RequestConfig.custom()
                .setCookieSpec(CookieSpecs.DEFAULT)
                .setExpectContinueEnabled(true)
                .setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
                .setProxyPreferredAuthSchemes(Collections.singletonList(AuthSchemes.BASIC))
                .setSocketTimeout(60000)
                .setConnectTimeout(60000)
                .setConnectionRequestTimeout(60000)
                .build();
    }

}
