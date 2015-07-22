package com.csnt.util.secret;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;

import com.csnt.util.validate.ValidateUtil;

@SuppressWarnings("restriction")
public class DecryptUtil {

	/**
	 * DES 解密算法，对称加密解密
	 * @param message 密文
	 * @param key 密钥
	 * @return 原文
	 */
	public static String DESDec(String message, String key){
		if(ValidateUtil.hasOneNullOrEmpty(message,key)){
			throw new RuntimeException("message or key is null!");
		}
		
		try {
			SecureRandom secureRandom = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(key.getBytes());
			
			SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(SecretConst.DES);
			
			SecretKey secretKey = secretKeyFactory.generateSecret(desKey);
			Cipher cipher = Cipher.getInstance(SecretConst.DES);
			
			cipher.init(Cipher.DECRYPT_MODE, secretKey,secureRandom);
			
			return new String(cipher.doFinal(base64ToByte(message)));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * AES 解密算法，对称加密解密
	 * @param message 密文
	 * @param key 密钥
	 * @return 原文
	 */
	public static String AESDec(String message, String key){
		if(ValidateUtil.hasOneNullOrEmpty(message,key)){
			throw new RuntimeException("message or key is null!");
		}
		try{
			KeyGenerator kgen = KeyGenerator.getInstance(SecretConst.AES);
			
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(key.getBytes());
			kgen.init(128,secureRandom);
			
			SecretKey secureKey = kgen.generateKey();
			
			byte[] enCodeFormat = secureKey.getEncoded();
			SecretKeySpec securKeySpec = new SecretKeySpec(enCodeFormat, "AES");
			
	        Cipher cipher = Cipher.getInstance("AES");// 创建密码器  
	        cipher.init(Cipher.DECRYPT_MODE, securKeySpec);// 初始化 
       
       return new String(cipher.doFinal(base64ToByte(message))); // 加密 
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Base64，简单编码，使文本不可读，并不算严格的加密程序。
	 * @param message
	 * @return
	 */
	public static String BASE64Dec(String message){
		if(ValidateUtil.isBlank(message)){
			throw new RuntimeException("message or key is null!");
		}try{
			return new String(base64ToByte(message));
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	/**
	 * 使用私玥解密
	 * @param message
	 * @param key
	 * @return
	 */
	public static String RSADecByPri(String message,String key){
		if(ValidateUtil.hasOneNullOrEmpty(message,key)){
			throw new RuntimeException("message or key is null!");
		}
		try{
			PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(base64ToByte(key));
			KeyFactory keyFactory = KeyFactory.getInstance(SecretConst.RSA);
			PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
			
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			
			return new String(cipher.doFinal(base64ToByte(message)));
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	private static byte[] base64ToByte(String message) throws IOException{
    	return new BASE64Decoder().decodeBuffer(message);
    }
}
