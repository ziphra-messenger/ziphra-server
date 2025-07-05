package com.privacity.commonback.common.interfaces.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.privacity.common.annotations.PrivacityId;
import com.privacity.common.annotations.PrivacityIdOrder;
import com.privacity.common.enumeration.ExceptionReturnCode;
import com.privacity.common.exceptions.PrivacityException;
import com.privacity.commonback.common.interfaces.PrivacityIdEncoderActionInterface;
import com.privacity.commonback.common.utils.PrivacityIdEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PrivacityIdEncryptEncoderActionInterfaceImpl implements PrivacityIdEncoderActionInterface {

	@Override
	public void objectEntradaEncrypt(PrivacityIdEncoder pIdE, String nivelTabs, Object objectEntrada, Field field, String fieldName, Method setMethod,
			String fieldValue) throws PrivacityException {
		if ( field.isAnnotationPresent(PrivacityIdOrder.class)) {
			log.trace(fieldName);
			if (fieldValue != null){

				try {

					log.debug("Encrypt Order obj: " + fieldName + " entrada: " + fieldValue );
					Long newId = Long.parseLong(fieldValue) + pIdE.getPrivacityIdOrderSeed();
					newId = newId + pIdE.getOrderRamdomNumber();
					log.trace("Encrypt Order obj: " + fieldName + " seed+ramdom: " + newId );
					String newIdS = pIdE.getMathBaseConverter().convertirBaseN(pIdE.getBase(), newId);
					log.trace("Encrypt Order obj: " + fieldName + " convertirBase "+ pIdE.getBase() + ": " + newIdS );
					newIdS = pIdE.getMutateDigitUtil().mutate(newIdS);
					log.trace("Encrypt Order obj: " + fieldName + " mutate: " + newIdS );
					setMethod.invoke(objectEntrada, newIdS );
					fieldValue=newIdS;
					log.debug("Encrypt Order obj: " + fieldName + " salida: " + newIdS );

				} catch (Exception e) {
					log.error(ExceptionReturnCode.ENCRYPT_ID_PROCESS_FAIL.getToShow(fieldName, e));
					e.printStackTrace();
					throw new PrivacityException(ExceptionReturnCode.ENCRYPT_ID_PROCESS_FAIL,e);

				} 
			}}
		if ( field.isAnnotationPresent(PrivacityId.class)) {

			log.trace(fieldName);

			log.trace("fieldValue: " + fieldValue);
			if (fieldValue != null){
				try {
					log.debug("Encrypt obj: " + fieldName + " entrada: " + fieldValue );
					String salida=pIdE.getAES(nivelTabs,fieldName,fieldValue);
					setMethod.invoke(objectEntrada,salida);
					log.debug("Encrypt obj: " + fieldName + " salida: " + salida );
				} catch (IllegalAccessException e) {
					log.error(ExceptionReturnCode.ENCRYPT_ID_PROCESS_FAIL.getToShow(fieldName, e));
					e.printStackTrace();
					throw new PrivacityException(ExceptionReturnCode.ENCRYPT_ID_PROCESS_FAIL,e);
				} catch (IllegalArgumentException e) {
					log.error(ExceptionReturnCode.ENCRYPT_ID_PROCESS_FAIL.getToShow(fieldName, e));
					e.printStackTrace();
					throw new PrivacityException(ExceptionReturnCode.ENCRYPT_ID_PROCESS_FAIL,e);
				} catch (InvocationTargetException e) {
					log.error(ExceptionReturnCode.ENCRYPT_ID_PROCESS_FAIL.getToShow(fieldName, e));
					e.printStackTrace();
					throw new PrivacityException(ExceptionReturnCode.ENCRYPT_ID_PROCESS_FAIL,e);
				}



			}
		}
	}


}
