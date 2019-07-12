package com.ipaylinks.test.mock.server.service.bosc;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.security.*;

public class BoscSecurityService {
    public static final String SIGNATURE_ALGORITHM_SHA1 = "SHA1withRSA";
    public static final String SIGNATURE_ALGORITHM_SHA256 = "SHA256withRSA";

    public BoscSecurityService() {
    }

    public BoscSecurityService(PrivateKey privateKey, PublicKey publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    private PrivateKey privateKey;
    private PublicKey publicKey;

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public String sign(byte[] tobeSignedData, String algorithm) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance(algorithm);
        signature.initSign(privateKey);
        signature.update(tobeSignedData);
        BASE64Encoder b64Enc = new BASE64Encoder();
        return b64Enc.encode(signature.sign());
    }

    public  boolean verify(byte[] tobeVerifiedData, String sign, String algorithm){
        try {
            Signature signature = Signature.getInstance(algorithm);
            signature.initVerify(publicKey);
            signature.update(tobeVerifiedData);
            BASE64Decoder b64Dec = new BASE64Decoder();
            return signature.verify(b64Dec.decodeBuffer(sign));
        } catch (Exception e) {
            throw new RuntimeException("验签异常", e);
        }
    }
}
