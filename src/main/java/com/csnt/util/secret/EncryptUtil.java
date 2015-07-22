package com.csnt.util.secret;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.csnt.util.validate.ValidateUtil;

/**
 * 加密工具，包含各种加密方式，对称加密非对称加密等等。
 * @author bishion@163.com
 *
 */
@SuppressWarnings("restriction")
public class EncryptUtil {

	/**
	 * DES 加密算法，对称加密
	 * @param message 原文
	 * @param key 密钥
	 * @return
	 */
	public static String DESEnc(String message, String key){
		if(ValidateUtil.hasOneNullOrEmpty(message,key)){
			throw new RuntimeException("message or key is null!");
		}
		
		try {
			SecureRandom secureRandom = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(key.getBytes());
			
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(SecretConst.DES);
			SecretKey secureKey = keyFactory.generateSecret(desKey);
			
			Cipher cipher = Cipher.getInstance(SecretConst.DES);
			
			cipher.init(Cipher.ENCRYPT_MODE, secureKey, secureRandom);
			
			return new String(EncryptUtil.base64ToStr(cipher.doFinal(message.getBytes())));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * AES加密，对称加密算法
	 * @param message 原文
	 * @param key 密钥
	 * @return
	 */
	public static String AESEnc(String message, String key){
		if(ValidateUtil.hasOneNullOrEmpty(message,key)){
			throw new RuntimeException("message or key is null!");
		}
		
		try {
			KeyGenerator kgen = KeyGenerator.getInstance(SecretConst.AES);
			
			SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
			secureRandom.setSeed(key.getBytes());
			kgen.init(128,secureRandom);
			
			SecretKey secureKey = kgen.generateKey();
			
			byte[] enCodeFormat = secureKey.getEncoded();
			SecretKeySpec securKeySpec = new SecretKeySpec(enCodeFormat, "AES");
			
			Cipher cipher = Cipher.getInstance(SecretConst.AES);
			
			cipher.init(Cipher.ENCRYPT_MODE, securKeySpec);
			
			return EncryptUtil.base64ToStr(cipher.doFinal(message.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Base64，简单编码，使文本不可读，并不算严格的加密程序。
	 * @param message
	 * @return
	 */
	public static String BASE64Enc(String message){
		if(ValidateUtil.isBlank(message)){
			throw new RuntimeException("message or key is null!");
		}
		return EncryptUtil.base64ToStr(message.getBytes());
	}
	
	/**
	 * MD5加密，原则上不能解密，只是作为数字签名使用，可以用来当作密码加密使用
	 * @param message
	 * @return
	 */
	public static String MD5Enc(String message){
		if(ValidateUtil.isBlank(message)){
			throw new RuntimeException("message or key is null!");
		}
		MessageDigest messageDigest;
		try {
			
			messageDigest = MessageDigest.getInstance(SecretConst.MD5);
			messageDigest.update(message.getBytes());
			return EncryptUtil.base64ToStr(messageDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * SHA加密，原则上不能解密，只是作为数字签名使用，可以用来当作密码加密使用
	 * @param message
	 * @return
	 */
	public static String SHAEnc(String message){
		if(ValidateUtil.isBlank(message)){
			throw new RuntimeException("message or key is null!");
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(SecretConst.SHA);
			messageDigest.update(message.getBytes());
			return EncryptUtil.base64ToStr(messageDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public static String RSAEncByPub(String message, String key){
		if(ValidateUtil.hasOneNullOrEmpty(message,key)){
			throw new RuntimeException("message or key is null!");
		}
		try{
			X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(base64ToByte(key));
			KeyFactory keyFactory= KeyFactory.getInstance(SecretConst.RSA);
			PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
			
			Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			
			return base64ToStr(cipher.doFinal(message.getBytes()));
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}
	/**
	 * @param message
	 * @param key
	 * @return
	 */
	public static String HMACEnc(String message,String key){
		if(ValidateUtil.hasOneNullOrEmpty(message,key)){
			throw new RuntimeException("message or key is null!");
		}
		try {
			SecretKey secretKey = new SecretKeySpec(key.getBytes(), SecretConst.HMAC);
			Mac mac = Mac.getInstance(secretKey.getAlgorithm());
			mac.init(secretKey);
			return EncryptUtil.base64ToStr(mac.doFinal(message.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
    
    
	private static String base64ToStr(byte[] message){
    	return new BASE64Encoder().encode(message); 
    }
	private static byte[] base64ToByte(String message) throws IOException{
    	return new BASE64Decoder().decodeBuffer(message);
    }
}
