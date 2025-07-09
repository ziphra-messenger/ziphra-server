package ar.ziphra.appserver.services.protocolomap;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import lombok.Data;

@Data
public class ProtocoloValue {

	private Object clazz;
	private Method method;
	private Class<?>parametersType;
	private Annotation[] annotations;
	private Class<?> returnType;
	public ProtocoloValue(Object clazz, Method method, Class<?> parametersType) {
		super();
		this.clazz = clazz;
		this.method = method;
		this.parametersType =parametersType;
		this.annotations = method.getAnnotations();
		this.returnType = method.getReturnType();
	}

	public static ProtocoloValue build(Object clazz, Method method, Class<?> parametersType) {
		return new ProtocoloValue(clazz,method, parametersType);
	}

	@Override
	public String toString() {
		return "Value [clazz=" + clazz.getClass().getSimpleName() + ", method=" + method.getName() + ", parametersType=" + (( parametersType==null) ? "none" : parametersType.getSimpleName()) + ", returnType="
				+ returnType.getSimpleName() + "]";
	}




}
