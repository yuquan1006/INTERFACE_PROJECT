package com.ipaylinks.test.mock.server.service.czcb.util;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

/**
 * AES加密工具
 * @author wb081
 * @version 1.0.0
 */
public class AESUtil {
    /**
     * 加密方式
     */
    public static final String KEY_ALGORITHM = "AES";
    /**
     * 安全随机签名算法
     */
    public static final String SECURE_ALGORITHM = "SHA1PRNG";
    /**
     * 加密算法
     */
    public static final String SECRYPT_ALGORITHM = "AES/ECB/PKCS5Padding";

    /**
     * 报文加密
     * @param randomKey 随机密钥
     * @param plainData 上送报文
     * @return 报文密文
     * @throws NoSuchAlgorithmException 
     * @throws NoSuchPaddingException 
     * @throws InvalidKeyException 
     * @throws UnsupportedEncodingException 
     * @throws BadPaddingException 
     * @throws IllegalBlockSizeException 
     * @throws NoSuchProviderException 
     */
    public static String encrypt(String plainData, String randomKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, NoSuchProviderException{
        KeyGenerator aesGen = KeyGenerator.getInstance(KEY_ALGORITHM);
        SecureRandom secureRandom = SecureRandom.getInstance(SECURE_ALGORITHM);
        secureRandom.setSeed(randomKey.getBytes());
        aesGen.init(128, secureRandom);
        SecretKey secretKey = aesGen.generateKey();
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);
        Cipher aesCipher = Cipher.getInstance(SECRYPT_ALGORITHM);
        aesCipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] aesData = aesCipher.doFinal(plainData.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeBase64String(aesData);
    }
    /**
     * BC数据加密
     * @param data
     * @param key
     * @return
     * @throws UnsupportedEncodingException
     * @throws DataLengthException
     * @throws IllegalStateException
     * @throws InvalidCipherTextException
     */
    public static String encryptWithBC(String data, String key) throws UnsupportedEncodingException, DataLengthException, IllegalStateException, InvalidCipherTextException  {
        ByteBuffer buffer = ByteBuffer.allocate(32);
        buffer.put(key.getBytes());
        KeyParameter kp = new KeyParameter(buffer.array());
        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
        CBCBlockCipher aes = new CBCBlockCipher(new AESEngine());
        PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(aes, new PKCS7Padding());
        cipher.init(true, kp);

        byte[] output = new byte[cipher.getOutputSize(bytes.length)];
        int len = cipher.processBytes(bytes, 0, bytes.length, output, 0);
        cipher.doFinal(output, len);

        return Base64.encodeBase64String(output);
    }
    /**
     * BC数据解密
     * @param data
     * @param key
     * @return
     * @throws DataLengthException
     * @throws IllegalStateException
     * @throws InvalidCipherTextException
     */
    public static String decryptWithBC(String data, String key) throws DataLengthException, IllegalStateException, InvalidCipherTextException {
        ByteBuffer buffer = ByteBuffer.allocate(32);
        buffer.put(key.getBytes());
        KeyParameter kp = new KeyParameter(buffer.array());

        byte[] bytes = Base64.decodeBase64(data);

        CBCBlockCipher aes = new CBCBlockCipher(new AESEngine());
        PaddedBufferedBlockCipher cipher = new PaddedBufferedBlockCipher(aes, new PKCS7Padding());
        cipher.init(false, kp);

        byte[] output = new byte[cipher.getOutputSize(bytes.length)];
        int len = cipher.processBytes(bytes, 0, bytes.length, output, 0);
        int len2 = cipher.doFinal(output, len);
        byte rawData[] = new byte[len+len2];
        System.arraycopy(output, 0, rawData, 0, rawData.length);
        String plainData = new String(rawData, StandardCharsets.UTF_8);
        return plainData;
    }
    /**
     * 报文解密
     * @param aesBase64
     * @param randomKey
     * @return
     * @throws NoSuchAlgorithmException 
     * @throws NoSuchPaddingException 
     * @throws InvalidKeyException 
     * @throws BadPaddingException 
     * @throws IllegalBlockSizeException 
     * @throws NoSuchProviderException 
     */
    public static String decrypt(String aesBase64, String randomKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException{
        byte[] aesData = Base64.decodeBase64(aesBase64);
        KeyGenerator aesGen = KeyGenerator.getInstance(KEY_ALGORITHM);
        SecureRandom secureRandom = SecureRandom.getInstance(SECURE_ALGORITHM);
        secureRandom.setSeed(randomKey.getBytes());
        aesGen.init(128, secureRandom);
        SecretKey secretKey = aesGen.generateKey();
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);
        Cipher aesCipher = Cipher.getInstance(SECRYPT_ALGORITHM,"BC");
        aesCipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] plain = aesCipher.doFinal(aesData);
        String plainData = new String(plain, StandardCharsets.UTF_8);
        return plainData;
    }

    /**
     * 生成随机密钥，一次一密
     * @return
     */
    public static String getRandomKey(){
        SecureRandom random = new SecureRandom();
        long randomKey = random.nextLong();
        return String.valueOf(randomKey);
    }
}
