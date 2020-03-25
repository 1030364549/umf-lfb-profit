package com.umf.utils;

import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class EncryUtil {
	
	private char[] hexDigits = { '0', '1', '2', '3', '4', '5','6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	private BASE64Encoder base64Encoder=new BASE64Encoder();
	private BASE64Decoder base64Decoder=new BASE64Decoder();
	private String aesCode = "AES";
	private String encoded = "GBK";

	/**
	 * 
	 *********************************************************.<br>
	 * [����] encryptAesBase64 <br>
	 * [����] AES����-���ܽ����Base64�����ʾ <br>
	 * [����] ���ݡ���Կ <br>
	 * [����] String <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��13�� ����4:18:18 <br>
	 *********************************************************.<br>
	 */
	public String encryptAesBase64(String data, String key) throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance(aesCode);
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(key.getBytes(encoded));
		kgen.init(128, secureRandom);
		Cipher cipher = Cipher.getInstance(aesCode);
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), aesCode));
		return encryptBase64(cipher.doFinal(data.getBytes(encoded)));
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [����] encryptBase64 <br>
	 * [����] Base64���� <br>
	 * [����] ���� <br>
	 * [����] String <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��13�� ����4:20:01 <br>
	 *********************************************************.<br>
	 */
	public String encryptBase64(byte[] data) {
		return base64Encoder.encode(data);
	}
	
	/**
	 * 
	 *********************************************************.<br>
	 * [����] decryptAesBase64 <br>
	 * [����] AES����-������Base64�����ʾ <br>
	 * [����] ���ݡ���Կ <br>
	 * [����] String <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��13�� ����4:19:24 <br>
	 *********************************************************.<br>
	 */
	public String decryptAesBase64(String data, String key) throws Exception { 
		KeyGenerator kgen = KeyGenerator.getInstance(aesCode);  
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG" );   
        secureRandom.setSeed(key.getBytes(encoded));
        kgen.init(128, secureRandom);  
        Cipher cipher = Cipher.getInstance(aesCode);  
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(kgen.generateKey().getEncoded(), aesCode));  
        byte[] decryptBytes = cipher.doFinal(decryptBase64(data));  
        return new String(decryptBytes,encoded);  
    }  
	
	/**
	 * 
	 *********************************************************.<br>
	 * [����] decryptBase64 <br>
	 * [����] Base64���� <br>
	 * [����] ���� <br>
	 * [����] byte[] <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��13�� ����4:19:52 <br>
	 *********************************************************.<br>
	 */
	public byte[] decryptBase64(String data) throws Exception {  
        return  base64Decoder.decodeBuffer(data);  
    }

	/**
	 * 
	 *********************************************************.<br>
	 * [����] encryptMD5 <br>
	 * [����] MD5���� <br>
	 * [����] ���� <br>
	 * [����] String <br>
	 * [����] UMF
	 * [ʱ��] 2019��8��13�� ����4:20:53 <br>
	 *********************************************************.<br>
	 */
	public String encryptMD5(String sourceStr) throws Exception {
		String resultStr = "";
		byte[] temp = (sourceStr).getBytes();
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(temp);
		byte[] b = md5.digest();
		for (int i = 0; i < b.length; i++) {
			char[] ob = new char[2];
			ob[0] = hexDigits[(b[i] >>> 4) & 0X0F];
			ob[1] = hexDigits[b[i] & 0X0F];
			resultStr += new String(ob);
		}
		return resultStr;	
	}
}
