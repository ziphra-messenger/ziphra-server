package com.privacity.commonback.common.interfaces;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.privacity.common.exceptions.PrivacityException;
import com.privacity.commonback.common.utils.PrivacityIdEncoder;

public interface PrivacityIdEncoderActionInterface {
	void objectEntradaEncrypt(PrivacityIdEncoder pIdE,String nivelTabs,  Object objectEntrada, Field field, String fieldName, Method setMethod,
			String fieldValue) throws PrivacityException;
}
