package ar.ziphra.commonback.common.interfaces;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import ar.ziphra.common.exceptions.ZiphraException;
import ar.ziphra.commonback.common.utils.ZiphraIdEncoder;

public interface ZiphraIdEncoderActionInterface {
	void objectEntradaEncrypt(ZiphraIdEncoder pIdE,String nivelTabs,  Object objectEntrada, Field field, String fieldName, Method setMethod,
			String fieldValue) throws ZiphraException;
}
