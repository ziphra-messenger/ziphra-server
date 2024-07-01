package com.privacity.server.component.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.privacity.common.RolesAllowed;
import com.privacity.common.enumeration.ProtocoloComponentsEnum;import com.privacity.common.enumeration.ProtocoloActionsEnum;
import com.privacity.common.dto.MessageDTO;
import com.privacity.common.dto.ProtocoloDTO;
import com.privacity.common.enumeration.ExceptionReturnCode;
import com.privacity.common.interfaces.GrupoRoleInterface;
import com.privacity.common.interfaces.UserForGrupoRoleInterface;
import com.privacity.common.interfaces.UsuarioRoleInterface;
import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.component.model.request.GrupoIdLocalDTO;
import com.privacity.server.component.requestid.RequestIdUtilService;
import com.privacity.server.exceptions.ValidationException;
import com.privacity.server.model.Grupo;
import com.privacity.server.model.UserForGrupo;
import com.privacity.server.security.Usuario;
import com.privacity.server.util.LocalDateAdapter;

public abstract class ControllerBase {

	private Map<ProtocoloActionsEnum,Method> mapaMetodos = new HashMap<ProtocoloActionsEnum,Method>();
	private Map<ProtocoloComponentsEnum,Object> mapaController = new HashMap<ProtocoloComponentsEnum,Object>();

	@Autowired
	@Lazy
	private RequestIdUtilService requestIdUtil;

	@Autowired
	@Lazy
	private FacadeComponent comps;


	protected Map<ProtocoloActionsEnum, Method> getMapaMetodos() {
		return mapaMetodos;
	}

	protected Map<ProtocoloComponentsEnum, Object> getMapaController() {
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
			if (getMapaMetodos().get(request.getAction()) == null) {
				throw new ValidationException(ExceptionReturnCode.GENERAL_INVALID_ACCESS_PROTOCOL);
			}
			if ( this.isSecure() && (this.isRequestId()) && !request.getAction().equals(ProtocoloActionsEnum.PROTOCOLO_ACTION_REQUEST_ID_PRIVATE_GET)) {
				requestIdUtil.existsRequestId(request.getRequestIdDTO(), true) ;
			}else if ( !this.isSecure() && (this.isRequestId()) && !request.getAction().equals(ProtocoloActionsEnum.PROTOCOLO_ACTION_REQUEST_ID_PUBLIC_GET)) {
				requestIdUtil.existsRequestId(request.getRequestIdDTO(),false) ;
			}

			if (showLog(request)) System.out.println("Action Base = " + request.getAction());
			if (showLog(request)) System.out.println("Component Base = " + request.getComponent());
			//			if (showLog()) System.out.println("MapaMetodos = " + getMapaMetodos());
			//			if (showLog()) System.out.println("MapaController = " + getMapaController());

			if (   getMapaMetodos().get(request.getAction()).getParameterTypes() != null &&
					getMapaMetodos().get(request.getAction()).getParameterTypes().length == 0) {

				objetoRetorno = getMapaMetodos().get(request.getAction()).invoke(getMapaController().get(request.getComponent()));

			}else {
				dtoObject =  getDTOObject(request, request.getObjectDTO(),getMapaMetodos().get(request.getAction()).getParameterTypes()[0]);

				if ( this.isSecure() ) {
					comps.service().usuarioSessionInfo().get().getPrivacityIdServices().decryptIds(dtoObject);

				}
				if (dtoObject instanceof UsuarioRoleInterface) {
					((UsuarioRoleInterface) dtoObject).setUsuarioLoggued(comps.util().usuario().getUsuarioLoggedValidate());
				}

				if (dtoObject instanceof GrupoRoleInterface) {

					Optional<Grupo> grupoO = comps.repo().grupo().findById(

							comps.util().grupo().convertIdGrupoStringToLong(((GrupoRoleInterface) dtoObject).getIdGrupo()));

					if (grupoO.isPresent()) {


						((GrupoRoleInterface) dtoObject).setGrupo(grupoO.get()

								);	}
				}

				if (dtoObject instanceof UserForGrupoRoleInterface) {
					((UserForGrupoRoleInterface) dtoObject).setUserForGrupo(
							comps.repo().userForGrupo().findByIdPrimitive(
									comps.util().grupo().convertIdGrupoStringToLong(((GrupoRoleInterface) dtoObject).getIdGrupo())
									,
									((UsuarioRoleInterface) dtoObject).getUsuarioLoggued().getIdUser()));


				}
				Annotation[] ann = getMapaMetodos().get(request.getAction()).getAnnotations();
				for (int i=0; i < ann.length ; i++) {
					if (ann[i] instanceof RolesAllowed) {
						invokeUnderTrace(dtoObject, ann[i]);
						break;
					}
				}

				System.out.println("dtoObject deserializado = " + dtoObject.toString());
				if (showLog(request)) {
					if ( dtoObject instanceof GrupoIdLocalDTO[]) {
						for (int i = 0 ; i < ((GrupoIdLocalDTO[]) dtoObject).length ; i ++) {
							System.out.println("dtoObject array deserializado = " +  ((GrupoIdLocalDTO[]) dtoObject)[i].toString());
						}
					}

				}
				objetoRetorno =getMapaMetodos().get(request.getAction()).invoke(getMapaController().get(request.getComponent()),  dtoObject);

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

			comps.service().usuarioSessionInfo().get().getPrivacityIdServices().encryptIds(objetoRetorno);
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

	protected void invokeUnderTrace(Object o, Annotation ann2) 
			throws ValidationException {
		System.out.println("----->AppConfigurationMethodRolValidationInterceptor");

		System.out.println("-----> " + o.toString());

		//        Gson gson = new GsonBuilder()
		//                .setPrettyPrinting()
		//                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
		//                .create();
		//        String retornoJson = gson.toJson(o);
		//        System.out.println("-----> " +retornoJson);



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

}