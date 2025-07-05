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
public class PrivacityIdDecryptEncoderActionInterfaceImpl implements PrivacityIdEncoderActionInterface {

	@Override
	public void objectEntradaEncrypt(PrivacityIdEncoder pIdE, String nivelTabs, Object objectEntrada, Field field, String fieldName, Method setMethod,
			String fieldValue) throws PrivacityException {
		if (field.isAnnotationPresent(PrivacityId.class)) {

			if (fieldValue != null){

				try {
					log.debug(nivelTabs+ "Desc obj: " + fieldName + " entrada: " + fieldValue );
					String newValue=pIdE.getAESDecrypt(nivelTabs, fieldName,fieldValue);
					setMethod.invoke(objectEntrada,newValue );
					fieldValue = newValue;
					log.debug(nivelTabs+ "Desc Order obj: " + fieldName + " salida: " + newValue );
				} catch (IllegalAccessException e) {
					log.error(ExceptionReturnCode.DECRYPT_ID_PROCESS_FAIL.getToShow(fieldName, e));
					e.printStackTrace();
					throw new PrivacityException(ExceptionReturnCode.DECRYPT_ID_PROCESS_FAIL,e);

				} catch (IllegalArgumentException e) {
					log.error(ExceptionReturnCode.DECRYPT_ID_PROCESS_FAIL.getToShow(fieldName, e));
					e.printStackTrace();
					throw new PrivacityException(ExceptionReturnCode.DECRYPT_ID_PROCESS_FAIL,e);

				} catch (InvocationTargetException e) {
					log.error(ExceptionReturnCode.DECRYPT_ID_PROCESS_FAIL.getToShow(fieldName, e));
					e.printStackTrace();
					throw new PrivacityException(ExceptionReturnCode.DECRYPT_ID_PROCESS_FAIL,e);

				}
			}
		}
		if ( field.isAnnotationPresent(PrivacityIdOrder.class)) {
			if (fieldValue!= null){
				try {
					String obj = fieldName;

					String newId = fieldValue ;
					log.debug(nivelTabs+ "Desc Order obj: " + obj + " entrada: " + newId );
					String newIdS = pIdE.getMutateDigitUtil().unmutate(newId);
					log.trace(nivelTabs+ "Desc Order obj: " + obj + " unmutate: " + newIdS );
					long newIdL = pIdE.getMathBaseConverter().convertirBase10(pIdE.getBase(), newIdS);
					log.trace(nivelTabs+ "Desc Order obj: " + obj + " convertirBase10: " + newIdL );
					newIdL = newIdL - pIdE.getPrivacityIdOrderSeed() - pIdE.getOrderRamdomNumber();
					log.trace(nivelTabs+ "Desc Order obj: " + obj + " seed-ramdom: " + newIdL );
					setMethod.invoke (objectEntrada, newIdL+"" );
					log.debug(nivelTabs+ "Desc Order obj: " + obj + " salida: " + newIdL );
				} catch (Exception e) {
					log.error(ExceptionReturnCode.DECRYPT_ID_PROCESS_FAIL.getToShow(fieldName, e));
					e.printStackTrace();
					throw new PrivacityException(ExceptionReturnCode.DECRYPT_ID_PROCESS_FAIL,e);

				} 
			}

		}
	}
}
