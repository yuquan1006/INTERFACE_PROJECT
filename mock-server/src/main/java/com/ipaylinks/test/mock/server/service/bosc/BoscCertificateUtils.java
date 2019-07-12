package com.ipaylinks.test.mock.server.service.bosc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

public class BoscCertificateUtils {
    public static final String KEY_STORE_TYPE_JKS = "JKS";
    public static final String KEY_STORE_TYPE_PKCS12 = "PKCS12";
    private static final String CERTIFICATE_TYPE_X509 = "X.509";

    /**
     * 获取公钥
     */
    public static PublicKey getPublicKey(String publicKeyPath) {
        InputStream publicKeyInputStream = null;
        try {
            publicKeyInputStream = new FileInputStream(publicKeyPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        PublicKey publicKey = null;
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance(CERTIFICATE_TYPE_X509);
            X509Certificate x509Certificate = (X509Certificate) certificateFactory.generateCertificate(publicKeyInputStream);
            publicKey = x509Certificate.getPublicKey();
        } catch (CertificateException e) {
            if (publicKeyInputStream != null) {
                try {
                    publicKeyInputStream.close();
                } catch (IOException ioE) {
                    throw new RuntimeException("输入流关闭异常", ioE);
                }
            }
            throw new RuntimeException("获取公钥异常", e);
        }

        if (publicKey == null) {
            throw new RuntimeException("公钥不存在");
        }

        return publicKey;
    }

    /**
     * 获取私钥
     */
    public static PrivateKey getPrivateKey(String privateKeyPath, String keyPassword, String keyStoreType) {
        InputStream privateKeyInputStream = null;
        try {
            privateKeyInputStream = new FileInputStream(privateKeyPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        PrivateKey privateKey = null;
        try {
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(privateKeyInputStream, keyPassword.toCharArray());

            Enumeration<String> keyStoreAliases = keyStore.aliases();
            String keyAlias;
            while (keyStoreAliases.hasMoreElements()) {
                keyAlias = keyStoreAliases.nextElement();
                if (keyStore.isKeyEntry(keyAlias)) {
                    privateKey= (PrivateKey) keyStore.getKey(keyAlias, keyPassword.toCharArray());
                    break;
                }
            }
        } catch (Exception e) {
            if (privateKeyInputStream != null) {
                try {
                    privateKeyInputStream.close();
                } catch (IOException ioE) {
                    throw new RuntimeException("输入流关闭异常", ioE);
                }
            }
            throw new RuntimeException("获取私钥异常", e);
        }

        if (privateKey == null) {
            throw new RuntimeException("私钥不存在");
        }

        return privateKey;
    }

}
