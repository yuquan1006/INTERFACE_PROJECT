package com.ipaylinks.test.mock.server.service.czcb;

import com.ipaylinks.test.mock.server.service.czcb.exception.CzcbException;

/**
 * 签名验签类抽象类
 * @author wb081
 * @version 1.0.0
 */
public interface CzcbSecurityService {

	 /**
     * 签名
     * @param data 待签名数据
     * @return
     * @throws CzcbException
     */
     String sign(String data) throws CzcbException;
    
    
    /**
     * 解密
     * @param data 待解密数据
     * @return
     * @throws CzcbException
     */
    String decrypt(String data) throws CzcbException;
    
    /**
     * 加密
     * @param data 待加密数据
     * @return
     * @throws CzcbException
     */
    String encrypt(String data) throws CzcbException;

    /**
     * 请求报文验签
     * @param data
     * @return
     * @throws CzcbException
     */
    boolean verify(String data);

}
