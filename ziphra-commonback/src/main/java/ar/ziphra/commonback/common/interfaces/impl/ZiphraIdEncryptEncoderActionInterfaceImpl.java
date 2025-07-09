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
public class ZiphraIdEncryptEncoderActionInterfaceImpl implements ZiphraIdEncoderActionInterface {

	@Override
	public void objectEntradaEncrypt(ZiphraIdEncoder pIdE, String nivelTabs, Object objectEntrada, Field field, String fieldName, Method setMethod,
			String fieldValue) throws ZiphraException {
		if ( field.isAnnotationPresent(ZiphraIdOrder.class)) {
			log.trace(fieldName);
			if (fieldValue != null){

				try {

					log.debug("Encrypt Order obj: " + fieldName + " entrada: " + fieldValue );
					Long newId = Long.parseLong(fieldValue) + pIdE.getZiphraIdOrderSeed();
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
					throw new ZiphraException(ExceptionReturnCode.ENCRYPT_ID_PROCESS_FAIL,e);

				} 
			}}
		if ( field.isAnnotationPresent(ZiphraId.class)) {

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
					throw new ZiphraException(ExceptionReturnCode.ENCRYPT_ID_PROCESS_FAIL,e);
				} catch (IllegalArgumentException e) {
					log.error(ExceptionReturnCode.ENCRYPT_ID_PROCESS_FAIL.getToShow(fieldName, e));
					e.printStackTrace();
					throw new ZiphraException(ExceptionReturnCode.ENCRYPT_ID_PROCESS_FAIL,e);
				} catch (InvocationTargetException e) {
					log.error(ExceptionReturnCode.ENCRYPT_ID_PROCESS_FAIL.getToShow(fieldName, e));
					e.printStackTrace();
					throw new ZiphraException(ExceptionReturnCode.ENCRYPT_ID_PROCESS_FAIL,e);
				}



			}
		}
	}


}
