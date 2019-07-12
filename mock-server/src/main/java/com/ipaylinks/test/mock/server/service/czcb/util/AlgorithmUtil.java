package com.ipaylinks.test.mock.server.service.czcb.util;

import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;


/**
 * 集成加密工具
 * @author wb081
 * @version 1.0.0
 */
public class AlgorithmUtil {

    public static String getAESRandomKey() {
    	SecureRandom random = new SecureRandom();
        long randomKey = random.nextLong();
        return String.valueOf(randomKey);
    }

    public static String encryptWithRSA(String data, String key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException  {
        return RSAUtil.encryptByPublicKey(data, key);
    }

    public static String encryptWithAES(String data, String key) throws UnsupportedEncodingException, DataLengthException, IllegalStateException, InvalidCipherTextException{
        return AESUtil.encryptWithBC(data, key);
    }

    public static String decryptWithRSA(String data, String key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException  {
        return new String(RSAUtil.decryptByPrivateKey(data, key), StandardCharsets.UTF_8);
    }

    public static String decryptWithAES(String data, String key) throws  DataLengthException, IllegalStateException, InvalidCipherTextException{
        return AESUtil.decryptWithBC(data, key);
    }

    public static String sign(String data, String key) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException  {
        return RSAUtil.sign(data.getBytes(StandardCharsets.UTF_8), key);
    }

    public static boolean verify(String data, String key, String signData) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, SignatureException  {
        return RSAUtil.verify(data.getBytes(StandardCharsets.UTF_8), key, signData);
    }

}
