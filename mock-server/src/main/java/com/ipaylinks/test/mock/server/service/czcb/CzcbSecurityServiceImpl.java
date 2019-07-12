package com.ipaylinks.test.mock.server.service.czcb;

import com.alibaba.fastjson.JSONObject;

import com.ipaylinks.test.mock.server.service.czcb.exception.CzcbException;
import com.ipaylinks.test.mock.server.service.czcb.util.AlgorithmUtil;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 京东金融签名验签实现
 * @author wb081
 *
 */
public class CzcbSecurityServiceImpl implements CzcbSecurityService {
//	private Logger logger = LoggerFactory.getLogger(CzcbSecurityServiceImpl.class);

	public CzcbSecurityServiceImpl() {
	}

	public CzcbSecurityServiceImpl(String privateKey, String publicKey) {
		this.privateKey = privateKey;
		this.publicKey = publicKey;
	}

	/**
	 * 私钥
	 */
	private String privateKey;
	/**
	 * 公钥
	 */
	private String publicKey;
	/**
	 * 签名字段
	 */
	private static final String SIGNATURE_FIELD = "signature";
	/**
	 * 加密字段
	 */
	public static final String ENCRYPT_FIELD = "bizData";
	
	/**
	 * 随即加密因子字段
	 */
	private static final String ENCRYPT_RANDOM_KEY_FIELD = "randomKeyEncrypted";

	@Override
	public String sign(String data) throws CzcbException {
		 try {
		    JSONObject origData =JSONObject.parseObject(encrypt(data));
        	origData = origData.fluentRemove(SIGNATURE_FIELD);
//        	logger.info( "客户端签名原文："+getNatureSortedJSONObject(origData));
        	String signData = AlgorithmUtil.sign(getNatureSortedJSONObject(origData), privateKey);
        	origData.put(SIGNATURE_FIELD, signData);
        	return origData.toJSONString();
		} catch (GeneralSecurityException e) {
//			logger.error( "签名失败，原因=>"+e.getMessage(),e);
			throw new CzcbException("签名失败");
		} 
	}

	@Override
	public boolean verify(String data) {
		
        try {
//        	logger.info( "验证签名原文："+data);
        	JSONObject request = JSONObject.parseObject(data);
        	
            //验证签名
            String signData = request.getString(SIGNATURE_FIELD);
            request = request.fluentRemove(SIGNATURE_FIELD);
			return AlgorithmUtil.verify(getNatureSortedJSONObject(request), publicKey, signData);
		} catch (Exception e) {
//			logger.error( "验证签名失败，原因=>"+e.getMessage(),e);
		}
		return false;
	}

	 /**
     * 字典排序
     * @param origJSONObject
     * @return
     */
    private String getNatureSortedJSONObject(JSONObject origJSONObject){
        Map<String, Object> treeMap = new TreeMap<>();
        Set<String> sendJOKeySet = origJSONObject.keySet();
		for (String sendJOKey : sendJOKeySet) {
			treeMap.put(sendJOKey, origJSONObject.get(sendJOKey));
		}
        return new JSONObject(treeMap).toJSONString();
    }

	@Override
	public String decrypt(String data) throws CzcbException {
		try {
			JSONObject origData =JSONObject.parseObject(data);
			
			String randomKeyEncrypted = origData.getString(ENCRYPT_RANDOM_KEY_FIELD);
//			logger.info( "随机数密文："+randomKeyEncrypted);
			String randomKey = AlgorithmUtil.decryptWithRSA(randomKeyEncrypted, privateKey);
//			logger.info( "随机数原文："+randomKey);
//			logger.info( "业务数据密文："+origData.getString(ENCRYPT_FIELD));
			String bizData = AlgorithmUtil.decryptWithAES(origData.getString(ENCRYPT_FIELD), randomKey);
			
//			logger.info( "业务数据原文："+bizData);
			origData.put(ENCRYPT_FIELD, bizData);
			origData = origData.fluentRemove(ENCRYPT_RANDOM_KEY_FIELD);
			return origData.toJSONString();
		} catch (GeneralSecurityException | DataLengthException | InvalidCipherTextException | IllegalStateException e) {
//			logger.error( "业务数据解密失败，原因=>"+e.getMessage(),e);
			throw new CzcbException("业务数据解密失败");
		}
	}

	@Override
	public String encrypt(String data) throws CzcbException {
		
		try {
			JSONObject origData =JSONObject.parseObject(data);
			String aesRandomKey = AlgorithmUtil.getAESRandomKey();
	    	String randomKeyEncrypted = AlgorithmUtil.encryptWithRSA(aesRandomKey, publicKey);
	    	String  parameterJsonEncrypted = AlgorithmUtil.encryptWithAES(origData.getJSONObject(ENCRYPT_FIELD).toJSONString(), aesRandomKey);
	    	origData.put(ENCRYPT_RANDOM_KEY_FIELD, randomKeyEncrypted);
	    	origData.put(ENCRYPT_FIELD, parameterJsonEncrypted);
	    	return origData.toJSONString();
		} catch (GeneralSecurityException | UnsupportedEncodingException | DataLengthException | InvalidCipherTextException | IllegalStateException e) {
//			logger.error( "业务加密失败，原因=>"+e.getMessage(),e);
			throw new CzcbException("业务加密失败");
		}

	}
}
