package com.privacity.server.component.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.privacity.common.RolesAllowed;
import com.privacity.common.dto.MessageDTO;
import com.privacity.common.dto.ProtocoloDTO;
import com.privacity.common.enumeration.ExceptionReturnCode;
import com.privacity.common.enumeration.ProtocoloActionsEnum;
import com.privacity.common.enumeration.ProtocoloComponentsEnum;
import com.privacity.common.interfaces.GrupoRoleInterface;
import com.privacity.common.interfaces.IdGrupoInterface;
import com.privacity.common.interfaces.UserForGrupoRoleInterface;
import com.privacity.common.interfaces.UsuarioRoleInterface;
import com.privacity.server.common.enumeration.Urls;
import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.component.requestid.RequestIdUtilService;
import com.privacity.server.exceptions.ValidationException;
import com.privacity.server.model.Grupo;
import com.privacity.server.model.UserForGrupo;
import com.privacity.server.security.Usuario;
import com.privacity.server.services.protocolomap.ProtocoloValue;
import com.privacity.server.util.LocalDateAdapter;

public abstract class ControllerBase {
	
	private static final Logger log = Logger.getLogger(ControllerBase.class.getCanonicalName());

	private Map<ProtocoloActionsEnum,Method> mapaMetodos = new HashMap<ProtocoloActionsEnum,Method>();
	private Map<ProtocoloComponentsEnum,Object> mapaController = new HashMap<ProtocoloComponentsEnum,Object>();

	@Autowired
	@Lazy
	private RequestIdUtilService requestIdUtil;

	@Autowired
	@Lazy
	private FacadeComponent comps;



	protected Map<ProtocoloComponentsEnum, Object> getMapaController() {
		return mapaController;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Object getDTOObject(ProtocoloDTO request, String objectDTO, Class clazz) {

		Gson gson = new GsonBuilder()
				.setPrettyPrinting()
				.registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
				.create();

		log.fine("getDTOObject Base :" + objectDTO + "clazz:" + clazz.getName());

		return gson.fromJson(objectDTO, clazz);
	}


	public ProtocoloDTO in(@RequestBody ProtocoloDTO request) throws Exception {

		log.fine("<<" + request.toString());
		
		ProtocoloValue value = comps.service().protocoloMap().get(getUrl(),  request.getComponent(),  request.getAction());
		ProtocoloDTO p = new ProtocoloDTO();

		// tomo el dto a ejecutar
		Object objetoRetorno=null;
		Object dtoObject=null;


		try {
			if (value == null) {
				throw new ValidationException(ExceptionReturnCode.GENERAL_INVALID_ACCESS_PROTOCOL);
			}
			if ( this.isSecure() && (this.isRequestId()) && !request.getAction().equals(ProtocoloActionsEnum.REQUEST_ID_PRIVATE_GET)) {
				requestIdUtil.existsRequestId(request.getRequestIdDTO(), true) ;
			}else if ( !this.isSecure() && (this.isRequestId()) && !request.getAction().equals(ProtocoloActionsEnum.REQUEST_ID_PUBLIC_GET)) {
				requestIdUtil.existsRequestId(request.getRequestIdDTO(),false) ;
			}

			log.fine("URL Base = " + request.getComponent());
			log.fine("COMPONENT Base = " + request.getComponent());
			log.fine("ACTION Base = " + request.getAction());
			
			if (   value.getParametersType() == null) {

				log.fine("Invoke 1 = " + value.getMethod().getName() + " " + value.getClazz().toString());
				objetoRetorno = value.getMethod().invoke(value.getClazz());
			}else {
				dtoObject =  getDTOObject(request, request.getObjectDTO(), value.getParametersType());

				if ( this.isSecure() ) {
					
//					if( getUrl().equals( Urls.CONSTANT_URL_PATH_PRIVATE)) 
//					{
						dtoObject =  getDTOObject(request, request.getObjectDTO(), value.getParametersType());						
//					}else {
//						
//					
//					dtoObject=comps.service().usuarioSessionInfo().privacityIdServiceDecrypt(
//							comps.requestHelper().getUsuarioUsername()
//							, dtoObject
//							,value.getParametersType().getName());
//					}

				}
//				if (dtoObject instanceof UsuarioRoleInterface) {
//					((UsuarioRoleInterface) dtoObject).setUsuarioLoggued(comps.requestHelper().getUsuarioLogged());
//				}
//
//				if (dtoObject instanceof GrupoRoleInterface) {
//
//					Optional<Grupo> grupoO = comps.repo().grupo().findById(
//
//							comps.util().grupo().convertIdGrupoStringToLong(((GrupoRoleInterface) dtoObject).getIdGrupo()));
//
//					if (grupoO.isPresent()) {
//
//
//						((GrupoRoleInterface) dtoObject).setGrupo(grupoO.get()
//
//								);	}
//				}
//
//				if (dtoObject instanceof UserForGrupoRoleInterface) {
//					((UserForGrupoRoleInterface) dtoObject).setUserForGrupo(
//							comps.repo().userForGrupo().findByIdPrimitive(
//									comps.util().grupo().convertIdGrupoStringToLong(((GrupoRoleInterface) dtoObject).getIdGrupo())
//									,
//									((UsuarioRoleInterface) dtoObject).getUsuarioLoggued().getIdUser()));
//
//
//				}
//				Annotation[] ann =value.getAnnotations();
//				for (int i=0; i < ann.length ; i++) {
//					if (ann[i] instanceof RolesAllowed) {
//						invokeUnderTrace(dtoObject, ann[i]);
//						break;
//					}
//				}
//
//				log.fine("dtoObject deserializado = " + dtoObject.toString());
//				{
//
//
//				}
				log.fine("Invoke 2 = " + value.getMethod().getName() + " " + value.getClazz().toString());
				
				log.fine("Invoke 2 parameter = " + ((dtoObject==null) ? "null" : dtoObject.toString()));
				
				if (dtoObject!= null && dtoObject instanceof IdGrupoInterface) {
					comps.requestHelper().setGrupoInUse((IdGrupoInterface)dtoObject);
				}
				objetoRetorno =value.getMethod().invoke(value.getClazz(),  dtoObject);
			}



			//				if(getEncryptIds()) {
			//					if ( mapaMetodos.get(request.getAction()).getParameterTypes().length != 0) {
			//						 getPrivacityIdServices().transformarDesencriptarOut(dtoObject);
			//					}
			//				}




		} catch (Exception e) {
			e.printStackTrace();
			p.setObjectDTO(null);
			if ( e.getCause() != null) {
				p.setCodigoRespuesta(e.getCause().getMessage());
			}else if( e.getMessage() != null && !e.getMessage().equals("")) {
				if (e instanceof ValidationException) {
					p.setMensajeRespuesta(((ValidationException)e).getDescription());

				}
				p.setCodigoRespuesta(e.getMessage());
				//p.setMensajeRespuesta(e.getCause());
			}else {
				p.setMensajeRespuesta("UNCLASSIFIED_ERROR");
				p.setCodigoRespuesta("G_000");
			}

		} 


		if ( this.isSecure()){


			
			objetoRetorno= comps.service().usuarioSessionInfo().privacityIdServiceEncrypt2(
					comps.requestHelper().getUsuarioUsername()
					, objetoRetorno
					,value.getReturnType().getName());
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

			String retornoJson=null;
			if (objetoRetorno != null) {
				retornoJson = gson.toJson(objetoRetorno);	
			}

			//if (showLog()) log.fine("OBJETO RETORNO Base >>" + retornoJson);


			log.fine("ProtocoloDTO Retorno Base prettyJsonString >> " + retornoJson);

			p.setObjectDTO(retornoJson);	
		}

		p.setAsyncId(request.getAsyncId());
		//if (showLog()) log.fine(">>" + p.toString());
		return p;

	}	

	public abstract boolean getEncryptIds();
	public abstract boolean isSecure();




	public static void main(String...strings ) {

		//String a = new Gson().toJson("wCddwFHXqIgLzTB/6ntQLlfIKO3t92onltnujNtNads=");

		Gson gson = new GsonBuilder()
				.setLenient()
				.create();
		String b =gson.fromJson("wCddwFHXqIgLzTB/6ntQLlfIKO3t92onltnujNtNads=", String.class);
		////log.fine(b);
	}

	public abstract boolean isRequestId();


	protected boolean showLog(ProtocoloDTO request) {
		return true;
	}	

	protected void invokeUnderTrace(Object o, Annotation ann2) 
			throws ValidationException {
		log.fine("----->AppConfigurationMethodRolValidationInterceptor");

		log.fine("-----> " + o.toString());

		//        Gson gson = new GsonBuilder()
		//                .setPrettyPrinting()
		//                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
		//                .create();
		//        String retornoJson = gson.toJson(o);
		//        log.fine("-----> " +retornoJson);



		RolesAllowed ann = (( RolesAllowed)ann2);



		Grupo g=null;
		Usuario u = null;
		UserForGrupo ufg = null;


		if (o instanceof GrupoRoleInterface) {
			g = ((GrupoRoleInterface)o).getGrupo();

			if ( g == null ) {
				throw new ValidationException(ExceptionReturnCode.GRUPO_NOT_EXISTS);	
			}


		}
		if (o instanceof UsuarioRoleInterface) {
			u = ((UsuarioRoleInterface)o).getUsuarioLoggued();


		}

		if (o instanceof UserForGrupoRoleInterface) {
			ufg = ((UserForGrupoRoleInterface)o).getUserForGrupo();

			if ( ufg == null ) {
				throw new ValidationException(ExceptionReturnCode.GRUPO_USER_IS_NOT_IN_THE_GRUPO);	
			}

		}





		boolean isOk= false;

		for (int i=0; i < ann.value().length ; i++) {
			if (ann.value()[i].equals(ufg.getRole())){
				isOk=true;
				break;
			}
		}
		if (!isOk) {
			throw new ValidationException(ExceptionReturnCode.GRUPO_ROLE_NOT_ALLOW_THIS_ACTION );
		}








	}
	public abstract Urls getUrl();
}