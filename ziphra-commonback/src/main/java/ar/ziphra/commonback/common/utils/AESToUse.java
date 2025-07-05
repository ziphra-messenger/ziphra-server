package ar.ziphra.commonback.common.utils;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ar.ziphra.common.dto.AESDTO;
import ar.ziphra.common.enumeration.ExceptionReturnCode;
import ar.ziphra.common.exceptions.ZiphraException;
import ar.ziphra.common.interfaces.AESToUseInterface;

import lombok.Getter;


public class AESToUse implements AESToUseInterface{

    private static final Logger log = Logger.getLogger(AESToUse.class.getCanonicalName());
    
	@Getter
	private String secretKeyAES;
	@Getter
	private String saltAES;
	@Getter
	@Value("${ziphra.security.encrypt.bits}")
	private int bitsEncrypt;
//	
	@Getter
	@Value("${ziphra.security.encrypt.iteration.count}")
	private int interationCount;
	@JsonIgnore
	
	private Cipher decrypt;
	@JsonIgnore

	private Cipher encrypt;
	
	@Getter
	private AESDTO AESDTO;

	public AESToUse(AESDTO aes) throws ZiphraException  {
		this(Integer.parseInt( aes.getBitsEncrypt()),Integer.parseInt( aes.getIteration()),aes.getSecretKeyAES(),aes.getSaltAES());
		
	}
	
	private AESToUse(int bitsEncrypt2, int interationCount2 , String secretKeyAES2, String saltAES2) throws ZiphraException  {
		this.secretKeyAES = secretKeyAES2;
		this.saltAES = saltAES2;
		this.bitsEncrypt =bitsEncrypt2;
		this.interationCount=interationCount2;
		

		this.AESDTO= new AESDTO();
		this.AESDTO.setSecretKeyAES(secretKeyAES);
		this.AESDTO.setSaltAES(saltAES2);
		this.AESDTO.setIteration(interationCount2 +"");
		this.AESDTO.setBitsEncrypt(bitsEncrypt+"");

		log.finest("Aes Generado: " + this.AESDTO.toString());
		try {
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
		} catch (InvalidKeyException e) {
			e.printStackTrace();
			throw new ZiphraException(ExceptionReturnCode.ENCRYPT_AES_CREATION_FAIL, e);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new ZiphraException(ExceptionReturnCode.ENCRYPT_AES_CREATION_FAIL, e);
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
			throw new ZiphraException(ExceptionReturnCode.ENCRYPT_AES_CREATION_FAIL, e);
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
			throw new ZiphraException(ExceptionReturnCode.ENCRYPT_AES_CREATION_FAIL, e);
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
			throw new ZiphraException(ExceptionReturnCode.ENCRYPT_AES_CREATION_FAIL, e);
		}
	}


	public String getAES(byte[] data) {
		try {
		return Base64.getEncoder().encodeToString(encrypt.doFinal(data));
	} catch (Exception e) {
		e.printStackTrace();
	}
	return null;
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

