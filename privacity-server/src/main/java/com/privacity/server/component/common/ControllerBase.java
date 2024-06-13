package com.privacity.server.component.common;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.privacity.common.config.ConstantProtocolo;
import com.privacity.common.dto.MessageDTO;
import com.privacity.common.dto.ProtocoloDTO;
import com.privacity.common.enumeration.ExceptionReturnCode;
import com.privacity.server.component.requestid.RequestIdUtilService;
import com.privacity.server.encrypt.PrivacityIdServices;
import com.privacity.server.exceptions.ValidationException;
import com.privacity.server.util.LocalDateAdapter;

public abstract class ControllerBase {

	private Map<String,Method> mapaMetodos = new HashMap<String,Method>();
	private Map<String,Object> mapaController = new HashMap<String,Object>();

	@Autowired
	@Lazy
	private RequestIdUtilService requestIdUtil;
	
	protected Map<String, Method> getMapaMetodos() {
		return mapaMetodos;
	}

	protected Map<String, Object> getMapaController() {
		return mapaController;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Object getDTOObject(ProtocoloDTO request, String objectDTO, Class clazz) {
		
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
                .create();
		
        if (showLog(request)) System.out.println("getDTOObject Base :" + objectDTO + "clazz:" + clazz.getName());
		
		return gson.fromJson(objectDTO, clazz);
	}


	public ProtocoloDTO in(@RequestBody ProtocoloDTO request) throws Exception {

		if (showLog(request)) System.out.println("<<" + request.toString());

		ProtocoloDTO p = new ProtocoloDTO();

		// tomo el dto a ejecutar
		Object objetoRetorno=null;
		Object dtoObject=null;

		try {
			if ( this.isSecure() && (this.isRequestId()) && !request.getAction().equals(ConstantProtocolo.PROTOCOLO_ACTION_REQUEST_ID_PRIVATE_GET)) {
				requestIdUtil.existsRequestId(request.getRequestIdDTO(), true) ;
			}else if ( !this.isSecure() && (this.isRequestId()) && !request.getAction().equals(ConstantProtocolo.PROTOCOLO_ACTION_REQUEST_ID_PUBLIC_GET)) {
				requestIdUtil.existsRequestId(request.getRequestIdDTO(),false) ;
			}
			
			if (showLog(request)) System.out.println("Action Base = " + request.getAction());
			if (showLog(request)) System.out.println("Component Base = " + request.getComponent());
//			if (showLog()) System.out.println("MapaMetodos = " + getMapaMetodos());
//			if (showLog()) System.out.println("MapaController = " + getMapaController());
			
			if ( getMapaMetodos().get(request.getAction()).getParameterTypes().length == 0) {
				
				objetoRetorno = getMapaMetodos().get(request.getAction()).invoke(getMapaController().get(request.getComponent()));

			}else {
				dtoObject =  getDTOObject(request, request.getObjectDTO(),getMapaMetodos().get(request.getAction()).getParameterTypes()[0]);
				
				if(getEncryptIds()) {
					getPrivacityIdServices().transformarDesencriptarOut(dtoObject);
					getPrivacityIdServices().transformarDesencriptarOutOrder(dtoObject);
					
					objetoRetorno =getMapaMetodos().get(request.getAction()).invoke(getMapaController().get(request.getComponent()),  dtoObject);
				}else {
					objetoRetorno = getMapaMetodos().get(request.getAction()).invoke(getMapaController().get(request.getComponent()), dtoObject);
				}	
				
			}



			//				if(getEncryptIds()) {
			//					if ( mapaMetodos.get(request.getAction()).getParameterTypes().length != 0) {
			//						 getPrivacityIdServices().transformarDesencriptarOut(dtoObject);
			//					}
			//				}

		} catch (Exception e) {
			e.printStackTrace();
			
			p.setCodigoRespuesta(e.getCause() + " | ");	
			if (e.getCause() != null) {
				p.setCodigoRespuesta(p.getCodigoRespuesta() + e.getCause().getMessage());	
			}
			
		} 

	

			if(getEncryptIds()) {
				getPrivacityIdServices().transformarEncriptarOutOrder(objetoRetorno);
				getPrivacityIdServices().transformarEncriptarOut(objetoRetorno);
			}

	//	if(getEncryptIds()) {
	//		objetoRetorno = getPrivacityIdServices().transformarDesencriptarOut(getMapaMetodos().get(request.getAction()).invoke(getMapaController().get(request.getComponent()), dtoObject));
	//	}else {
	//		objetoRetorno = getMapaMetodos().get(request.getAction()).invoke(getMapaController().get(request.getComponent()), dtoObject);
	//	}	
	// armo la devolucion
			
    
	
	
	p.setComponent(request.getComponent());
	p.setAction(request.getAction());
	
	if (objetoRetorno instanceof MessageDTO ) {
		p.setMessageDTO((MessageDTO)objetoRetorno);
	}else {
		
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
                .create();
        
		String retornoJson = gson.toJson(objetoRetorno);
		//if (showLog()) System.out.println("OBJETO RETORNO Base >>" + retornoJson);


		if (showLog(request)) System.out.println("ProtocoloDTO Retorno Base prettyJsonString >> " + retornoJson);
        
		p.setObjectDTO(retornoJson);	
	}
	
	p.setAsyncId(request.getAsyncId());
	//if (showLog()) System.out.println(">>" + p.toString());
	return p;

}	

public abstract boolean getEncryptIds();
public abstract boolean isSecure();

public abstract PrivacityIdServices getPrivacityIdServices();

public static void main(String...strings ) {

	//String a = new Gson().toJson("wCddwFHXqIgLzTB/6ntQLlfIKO3t92onltnujNtNads=");
	
	Gson gson = new GsonBuilder()
	        .setLenient()
	        .create();
	String b =gson.fromJson("wCddwFHXqIgLzTB/6ntQLlfIKO3t92onltnujNtNads=", String.class);
	////System.out.println(b);
}

	public abstract boolean isRequestId();
	
	
	protected boolean showLog(ProtocoloDTO request) {
		return true;
	}	
}