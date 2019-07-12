package com.ipaylinks.test.mock.server.util.function;

import java.security.MessageDigest;

/**
 * @author QiuHua Yang
 * @version Created on 2017/7/20
 */
public class MD5Function {
    public static String sign(String str, String charset) {
        String result = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes(charset));

            byte[] bytes = messageDigest.digest();
            StringBuilder stringBuilder = new StringBuilder();
            for (byte aByte : bytes) {
                String hex = Integer.toHexString(0xFF & aByte);
                if (hex.length() == 1) {
                    stringBuilder.append("0");
                    stringBuilder.append(hex);
                } else {
                    stringBuilder.append(hex);
                }
            }
            result = stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
