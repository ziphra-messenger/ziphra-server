package ar.ziphra.commonback.common.interfaces.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import ar.ziphra.common.annotations.ZiphraId;
import ar.ziphra.common.annotations.ZiphraIdOrder;
import ar.ziphra.common.enumeration.ExceptionReturnCode;
import ar.ziphra.common.exceptions.ZiphraException;
import ar.ziphra.commonback.common.interfaces.ZiphraIdEncoderActionInterface;
import ar.ziphra.commonback.common.utils.ZiphraIdEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ZiphraIdDecryptEncoderActionInterfaceImpl implements ZiphraIdEncoderActionInterface {

	@Override
	public void objectEntradaEncrypt(ZiphraIdEncoder pIdE, String nivelTabs, Object objectEntrada, Field field, String fieldName, Method setMethod,
			String fieldValue) throws ZiphraException {
		if (field.isAnnotationPresent(ZiphraId.class)) {

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
					throw new ZiphraException(ExceptionReturnCode.DECRYPT_ID_PROCESS_FAIL,e);

				} catch (IllegalArgumentException e) {
					log.error(ExceptionReturnCode.DECRYPT_ID_PROCESS_FAIL.getToShow(fieldName, e));
					e.printStackTrace();
					throw new ZiphraException(ExceptionReturnCode.DECRYPT_ID_PROCESS_FAIL,e);

				} catch (InvocationTargetException e) {
					log.error(ExceptionReturnCode.DECRYPT_ID_PROCESS_FAIL.getToShow(fieldName, e));
					e.printStackTrace();
					throw new ZiphraException(ExceptionReturnCode.DECRYPT_ID_PROCESS_FAIL,e);

				}
			}
		}
		if ( field.isAnnotationPresent(ZiphraIdOrder.class)) {
			if (fieldValue!= null){
				try {
					String obj = fieldName;

					String newId = fieldValue ;
					log.debug(nivelTabs+ "Desc Order obj: " + obj + " entrada: " + newId );
					String newIdS = pIdE.getMutateDigitUtil().unmutate(newId);
					log.trace(nivelTabs+ "Desc Order obj: " + obj + " unmutate: " + newIdS );
					long newIdL = pIdE.getMathBaseConverter().convertirBase10(pIdE.getBase(), newIdS);
					log.trace(nivelTabs+ "Desc Order obj: " + obj + " convertirBase10: " + newIdL );
					newIdL = newIdL - pIdE.getZiphraIdOrderSeed() - pIdE.getOrderRamdomNumber();
					log.trace(nivelTabs+ "Desc Order obj: " + obj + " seed-ramdom: " + newIdL );
					setMethod.invoke (objectEntrada, newIdL+"" );
					log.debug(nivelTabs+ "Desc Order obj: " + obj + " salida: " + newIdL );
				} catch (Exception e) {
					log.error(ExceptionReturnCode.DECRYPT_ID_PROCESS_FAIL.getToShow(fieldName, e));
					e.printStackTrace();
					throw new ZiphraException(ExceptionReturnCode.DECRYPT_ID_PROCESS_FAIL,e);

				} 
			}

		}
	}
}
