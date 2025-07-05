package com.privacity.commonback.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.privacity.common.annotations.PrivacityIdExclude;
import com.privacity.common.enumeration.ExceptionReturnCode;
import com.privacity.common.exceptions.PrivacityException;
import com.privacity.common.util.RandomGenerator;
import com.privacity.commonback.common.interfaces.PrivacityIdEncoderActionInterface;
import com.privacity.commonback.common.interfaces.impl.PrivacityIdDecryptEncoderActionInterfaceImpl;
import com.privacity.commonback.common.interfaces.impl.PrivacityIdEncryptEncoderActionInterfaceImpl;

import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;


@Data
@Slf4j
public class PrivacityIdEncoder {


	private static final String METHOD_PREFIX_GET = "get";

	private static final String METHOD_PREFIX_SET = "set";

	@Getter
	private long privacityIdOrderSeed;

	@Getter
	private MutateDigitUtil mutateDigitUtil;
	@Getter

	private MathBaseConverter mathBaseConverter;
	private MixBytesUtil mixBytesUtil;

	@Getter
	private long orderRamdomNumber;

	private final boolean encryptIds;


	private int base;

	private AESToUse aesToUse;


	public PrivacityIdEncoder(boolean encryptIds, AESToUse aesToUse,  long privacityIdOrderSeed,long orderRamdomNumber,int base,
			Map<String, String> porLetra ,  Map<String, String> porNro
			) throws PrivacityException {
		{
			this.aesToUse = aesToUse;
			this.encryptIds=encryptIds;
			mixBytesUtil = new MixBytesUtil();
			this.base=base;
			mutateDigitUtil = new MutateDigitUtil(base);
			mutateDigitUtil.setPorLetra(porLetra);
			mutateDigitUtil.setPorNro(porNro);

			mathBaseConverter = new MathBaseConverter();
			this.privacityIdOrderSeed = privacityIdOrderSeed;
			this.orderRamdomNumber=orderRamdomNumber;

			
		}
	}
	
	public PrivacityIdEncoder(AESToUse aesToUse) throws PrivacityException {
		this(true, aesToUse);
	}

	public PrivacityIdEncoder(boolean encryptIds, AESToUse aesToUse) throws PrivacityException {
		{
			this.aesToUse = aesToUse;
			this.encryptIds=encryptIds;
			mixBytesUtil = new MixBytesUtil();
			mutateDigitUtil = new MutateDigitUtil();
			mathBaseConverter = new MathBaseConverter();
			privacityIdOrderSeed = RandomGenerator.betweenTwoNumber(1483647,7483647);
			base= mutateDigitUtil.getBase();
			
		}
	}

	public Object encryptIds(Object g) throws PrivacityException  {
		separadorLog();
		log.debug("encryptIds");
		if (!encryptIds) return g;

		try {

			transformarEncriptarOut(null,null, g,new PrivacityIdEncryptEncoderActionInterfaceImpl());
			separadorLog();
			return g;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getAES obj: " + g + " salida: " + null + " error: " + e.getMessage() );
			throw new PrivacityException(ExceptionReturnCode.ENCRYPT_ID_PROCESS_FAIL);
		}
	}

	private void separadorLog() {
		log.debug("************************************");
	}

	public Object decryptIds(Object g) throws PrivacityException {
		separadorLog();
		log.debug("decryptIds");
		if (!encryptIds) return g;

		try {
			transformarEncriptarOut(null,null,g,new PrivacityIdDecryptEncoderActionInterfaceImpl());
			separadorLog();
			return g;
		} catch (Exception e) {
			e.printStackTrace();
			log.error("getAES obj: " + g + " salida: " + null + " error: " + e.getMessage() );
			throw new PrivacityException(ExceptionReturnCode.DECRYPT_ID_PROCESS_FAIL);
		}
	}



	public String getAES(String nivelTabs, String obj, String data) throws PrivacityException {
		log.debug(nivelTabs + "getAES obj: " + obj + " entrada: " + data );


		try {
			byte[] encriptado = aesToUse.getAESData(data.getBytes());
			String r = Base64.getEncoder().encodeToString(mixBytesUtil.mix(encriptado));
			log.debug(nivelTabs + "getAES obj: " + obj + " salida: " + r );
			return r; 
		} catch (Exception e) {
			e.printStackTrace();
			log.error(nivelTabs + "getAES obj: " + obj + " salida: " + null + " error: " + e.getMessage() );
			throw new PrivacityException(ExceptionReturnCode.DECRYPT_ID_PROCESS_FAIL);
		}
	}

	public String getAESDecrypt(String nivelTabs, String obj, String data) {

		log.trace("nivelTabs + getAESDecrypt obj: " + obj + " entrada: " + data );

		if (data == null) return null;
		if (data == "") return "";
		////////log.trace(data);

		try {
			String r = new String(aesToUse.getAESDecryptData(mixBytesUtil.unmix(Base64.getDecoder().decode(data))));
			log.trace(nivelTabs + "getAESDecrypt obj: " + obj + " salida: " + r );
			return r;
		} catch (Exception e) {
			
			log.trace(nivelTabs + "getAESDecrypt obj: " + obj + " salida: " + null + " error: " + e.getMessage() );
		}
		return null;
	}


	private Object transformarEncriptarOut(String path,String nivelTabs, Object objectEntrada,PrivacityIdEncoderActionInterface action) throws RuntimeException, ReflectiveOperationException, PrivacityException{
		if (path == null) {
			path="";
			nivelTabs="\t";
		}
		path= path + objectEntrada.getClass().getSimpleName();
		
		log.debug("**** " + path);
		log.debug(nivelTabs + "entrada objectEntrada: " + objectEntrada.getClass().getSimpleName() + " - " + objectEntrada);

		if (objectEntrada == null) return null;

		if ( objectEntrada.getClass().getEnumConstants() != null) return null;
		
		if (objectEntrada.getClass().isAnnotationPresent(PrivacityIdExclude.class)) {
			log.trace(nivelTabs + "PrivacityIdExclude por CLASE objectEntrada"  + objectEntrada);
			return objectEntrada;
		}
		
		if((objectEntrada.getClass().getName().equals("java.util.List"))|| 
				(objectEntrada.getClass().getName().equals("java.util.ArrayList"))){

			List list = (List)objectEntrada;
			for ( int k = 0 ; k < list.size() ; k++) {
				transformarEncriptarOut(path + ".("+ k+ ")",nivelTabs + "\t", list.get(k),action);
			}

		} else if ( objectEntrada.getClass().isArray()){
			Object[] lista = (Object[])objectEntrada;
			for ( int k = 0 ; k < lista.length ; k++) {
				transformarEncriptarOut(path + ".("+ k+ ")",nivelTabs + "\t", lista[k],action);
			}				
		}
		else  {
			for ( int i = 0 ; i < objectEntrada.getClass().getDeclaredFields().length ; i++) {
				Field field = objectEntrada.getClass().getDeclaredFields()[i];
				
				if (!field.isAnnotationPresent(PrivacityIdExclude.class)) {
				String fieldString = objectEntrada.getClass().getDeclaredFields()[i].toString();
				
				String fieldTypeName=  objectEntrada.getClass().getDeclaredFields()[i].getType().getName();
				String fieldName = objectEntrada.getClass().getDeclaredFields()[i].getName();

				log.trace("fieldTypeName: " + fieldTypeName);
				Method getMethod = null;
				String getMethodName= METHOD_PREFIX_GET + StringUtils.capitalize(fieldName);
				String getMethodReturnTypeName=null;
				try {
					getMethod = objectEntrada.getClass().getMethod(getMethodName);
					getMethodReturnTypeName=getMethod.getReturnType().getClass().getName();
				} catch (NoSuchMethodException e) {
					log.trace("getMethodName not exist: " + getMethodName);
				} 

				Method setMethod = null;
				String fieldValue=null;



				if( getMethod != null && String.class.equals( getMethod.getReturnType() )){
					setMethod = objectEntrada.getClass().getMethod(METHOD_PREFIX_SET + StringUtils.capitalize(fieldName),String.class);
					fieldValue=(String)getMethod.invoke(objectEntrada);

				}
				
				if ( getMethod != null && fieldString.contains("[]") && !fieldString.contains("byte[]")){
					Object[] lista = (Object[])getMethod.invoke(objectEntrada);
					if (lista == null) return null;
					for ( int k = 0 ; k < lista.length ; k++) {
						transformarEncriptarOut(path + ".("+ k+ ")",nivelTabs + "\t", lista[k],action);
					}	
				
				} else if (getMethod != null && (fieldTypeName.contains("com.privacity.common"))) {



					Object oget =  getMethod.invoke(objectEntrada);
					log.trace("Valor Object " + " " +oget );
					if (oget != null &&   !getMethodReturnTypeName.contains("byte")){
						log.trace("transformarEncriptarOut " + " " +oget );
						transformarEncriptarOut(path + ".", nivelTabs + "\t",oget,action);	
					}

				}else if (getMethod != null) {
					action.objectEntradaEncrypt(this,nivelTabs, objectEntrada, field, fieldName, setMethod, fieldValue);
	
					}
				}else {
					log.trace("PrivacityIdExclude por FIELD:"  + field.getName());
				}
				}
			}
		return objectEntrada;
	}


}    

