package com.privacity.server.main;


import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.builder.ToStringExclude;
import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.Expose;

import lombok.Getter;


public class AESToUse {


	@Getter
	@Expose 
	private String secretKeyAES;
	@Getter
	@Expose 
	private String saltAES;
	@Expose 
	@Getter
	@Value("${privacity.security.encrypt.bits}")
	private int bitsEncrypt;
//	
	@Expose 
	@Getter
	@Value("${privacity.security.encrypt.iteration.count}")
	private int interationCount;
	@JsonIgnore
	@ToStringExclude
	private Cipher decrypt;
	@JsonIgnore
	
	@ToStringExclude
	private Cipher encrypt;

	public AESToUse(int bitsEncrypt2, int interationCount2 , String secretKeyAES, String saltAES) throws Exception {
		this.secretKeyAES = secretKeyAES;
		this.saltAES = saltAES;
		this.bitsEncrypt =bitsEncrypt2;
		this.interationCount=interationCount2;



		{
			byte[] iv = new byte[16];
			IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
			SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			KeySpec keySpec = new PBEKeySpec((secretKeyAES).toCharArray(), (saltAES).getBytes(), interationCount, bitsEncrypt);
			SecretKey secretKeyTemp = secretKeyFactory.generateSecret(keySpec);
			SecretKeySpec secretKey = new SecretKeySpec(secretKeyTemp.getEncoded(), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
			decrypt = cipher;
			
		}
		{
			byte[] iv = new byte[16];
			IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
			SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			KeySpec keySpec = new PBEKeySpec((secretKeyAES).toCharArray(), (saltAES).getBytes(), interationCount, bitsEncrypt);
			SecretKey secretKeyTemp = secretKeyFactory.generateSecret(keySpec);
			SecretKeySpec secretKey = new SecretKeySpec(secretKeyTemp.getEncoded(), "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
			encrypt=cipher;
		}
	}



	public String getAES(String data) {
		try {

			return Base64.getEncoder().encodeToString(encrypt.doFinal(data.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public byte[] getAESData(byte[] data) {
		try {

			return encrypt.doFinal(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getAESDecrypt(String data) throws Exception {
		
		
		if (data == null) return null;
		if (data == "") return "";

		String r = new String(decrypt.doFinal(Base64.getDecoder().decode(data)));
		//////System.out.println("Entrada: " + data + " Salida: " + r);
		return r;


	}

	public byte[] getAESDecryptData(byte[] data) throws Exception {
		
		
		if (data == null) return null;
		//if (data == "") return "";

		
		//////System.out.println("Entrada: " + data + " Salida: " + r);
		return decrypt.doFinal(data);


	}
	
	@Override
	public String toString() {
		return "AESToUse [secretKeyAES=" + secretKeyAES + ", saltAES=" + saltAES + ", bitsEncrypt=" + bitsEncrypt
				+ ", interationCount=" + interationCount + "]";
	}

}    

