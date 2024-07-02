package com.privacity.server.main;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.privacity.common.RolesAllowed;
import com.privacity.common.dto.MessageDTO;
import com.privacity.common.dto.ProtocoloDTO;
import com.privacity.common.dto.request.RequestEncryptDTO;
import com.privacity.common.enumeration.ExceptionReturnCode;
import com.privacity.common.enumeration.GrupoRolesEnum;
import com.privacity.server.common.exceptions.ValidationException;
import com.privacity.server.common.interfaces.GrupoRoleInterface;
import com.privacity.server.common.interfaces.UserForGrupoRoleInterface;
import com.privacity.server.common.interfaces.UsuarioRoleInterface;
import com.privacity.server.common.model.Grupo;
import com.privacity.server.common.model.UserForGrupo;
import com.privacity.server.common.model.Usuario;
import com.privacity.server.common.util.AESToUse;
import com.privacity.server.component.common.ControllerBase;
import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.component.message.MessageValidationService;
import com.privacity.server.security.UserDetailsImpl;
import com.privacity.server.session.PrivacityIdServices;
import com.privacity.server.util.LocalDateAdapter;


@RestController
@RequestMapping(path = "/private")

public class SendPrivateController {

	@Value("${serverconf.privacityIdAESOn}")
	private boolean encryptIds;

	

	@Autowired @Lazy
	private FacadeComponent comps;


		
//
//	public SendPrivateController(PrivacityIdServices privacityIdServices, MessageValidationService messageValidationService) {
//		super();
//		this.privacityIdServices = privacityIdServices;
//		this.messageValidationService = messageValidationService;
//	}


	@PostMapping("/send")
	public ResponseEntity<String> inMessage(@RequestParam String request, 
			/*@RequestParam("data") */ MultipartFile data) throws Exception {
		
		
		
		/*
		InputStream fin =data.getInputStream();
		int i;
        try {
            //Leer bytes hasta que se encuentre el EOF
            //EOF es un concepto para determinar el final de un archivo
            do {
                i=fin.read();
                if (i !=-1) System.out.print((char)i);
            }while (i!=-1); //Cuando i es igual a -1, se ha alcanzado el final del archivo
        }catch (IOException exc){
            System.out.println("Error al leer el archivo");
        }

        try {
            fin.close();
            //Cerrar el archivo
        }catch (IOException exc){
            System.out.println("Error cerrando el archivo.");
        }
        */
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
                .create();
        //request = gson.fromJson(request, String.class);
		
		 
		
		Authentication auth = SecurityContextHolder
	            .getContext()
	            .getAuthentication();
		UserDetailsImpl u = (UserDetailsImpl) auth.getPrincipal();
	    
		 AESToUse c = comps.service().usuarioSessionInfo().get(u.getUsername()).getSessionAESToUse();
		
		 String requestDesencriptado=null;
		try {
			requestDesencriptado = c.getAESDecrypt(request);	
		}catch(javax.crypto.BadPaddingException e) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("MAL SESSION ENCRYPT");
		}
		
		ProtocoloDTO p = gson.fromJson(requestDesencriptado, ProtocoloDTO.class);
		
		if (p.getMessageDTO().getMediaDTO() != null) {
			byte[] dataDescr = c.getAESDecryptData(data.getBytes());
			p.getMessageDTO().getMediaDTO().setData(dataDescr);
			
		}
		
		

		
		ProtocoloDTO retornoFuncion = this.in(p);
		

		
		String retornoFuncionJson = gson.toJson(retornoFuncion);
		if (showLog()) System.out.println(">>" + retornoFuncionJson);
		String retornoFuncionEncriptado = comps.service().usuarioSessionInfo().get(u.getUsername()).getSessionAESToUseServerEncrypt().getAES(retornoFuncionJson);

		
		if (showLog()) System.out.println("ENCRIPTADO >>" + retornoFuncionEncriptado);
		return ResponseEntity.ok().body(gson.toJson(retornoFuncionEncriptado));

	}




	public boolean getEncryptIds() {
		return encryptIds;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Object getDTOObject(String objectDTO, Class clazz) {
		if (showLog());////System.out.println("objectDTO:" + objectDTO + "clazz:" + clazz.getName());
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
                .create();
		return gson.fromJson(objectDTO, clazz);
	}

	public ProtocoloDTO in(@RequestBody ProtocoloDTO request) throws Exception {

		if (showLog()) System.out.println("<<" + request.toString());
		
		ProtocoloDTO p = new ProtocoloDTO();

		// tomo el dto a ejecutar
		MessageDTO objetoRetorno=null;
		MessageDTO dtoObject=null;

		try {

			
				 dtoObject =  request.getMessageDTO();
					comps.service().usuarioSessionInfo().decryptIds(comps.util().usuario().getUsernameLogged(), dtoObject);

					//////////////////
					
						Usuario usuarioLogged = comps.util().usuario().getUsuarioLoggedValidate();


						Grupo grupo = null;
						Optional<Grupo> grupoO = comps.repo().grupo().findById(
								
								comps.util().grupo().convertIdGrupoStringToLong((dtoObject).getIdGrupo()));
						
						if (grupoO.isPresent()) {
					
							grupo =grupoO.get();

							}
						
					

						
						UserForGrupo ufg = comps.repo().userForGrupo().findByIdPrimitive(
								grupo.getIdGrupo(),

								usuarioLogged.getIdUser());
						
						
					
					//////////
				
					invokeUnderTrace(grupo,usuarioLogged,ufg);
				objetoRetorno =comps.validation().message().send(dtoObject,grupo,usuarioLogged,ufg);
					
				
			



			//				if(getEncryptIds()) {
			//					if ( mapaMetodos.get(request.getAction()).getParameterTypes().length != 0) {
			//						 getPrivacityIdServices().transformarDesencriptarOut(dtoObject);
			//					}
			//				}

		} catch (Exception e) {
			e.printStackTrace();
			
			if ( e.getCause() != null) {
				p.setCodigoRespuesta(e.getCause().getMessage());
			}else if( e.getMessage() != null && !e.getMessage().equals("")) {
				p.setCodigoRespuesta(e.getMessage());
			}else {
				p.setCodigoRespuesta("ERROR SIN CLASIFICAR");
			}
			
		} 


			comps.service().usuarioSessionInfo().encryptIds(comps.util().usuario().getUsernameLogged(), objetoRetorno);
			




	//	if(getEncryptIds()) {
	//		objetoRetorno = getPrivacityIdServices().transformarDesencriptarOut(getMapaMetodos().get(request.getAction()).invoke(getMapaController().get(request.getComponent()), dtoObject));
	//	}else {
	//		objetoRetorno = getMapaMetodos().get(request.getAction()).invoke(getMapaController().get(request.getComponent()), dtoObject);
	//	}	
	// armo la devolucion
			
    
	p.setComponent(request.getComponent());
	p.setAction(request.getAction());
	p.setMessageDTO(objetoRetorno);
	p.setAsyncId(request.getAsyncId());

	if (showLog()) System.out.println(">>" + p.toString());
	return p;

}	


	public boolean isSecure() {
		return true;
	}



	public boolean isRequestId() {
		return true;
	}


	
	public boolean showLog() {
		return true;
	}

	   protected void invokeUnderTrace(  Grupo g, Usuario u, UserForGrupo ufg ) 
			      throws ValidationException {
			        System.out.println("----->AppConfigurationMethodRolValidationInterceptor");
			        




			        		
			        		if ( g == null ) {
			        			throw new ValidationException(ExceptionReturnCode.GRUPO_NOT_EXISTS);	
			        		}
			        		
							
	

							
		
			        	

			        		
			        		if ( ufg == null ) {
			        			throw new ValidationException(ExceptionReturnCode.GRUPO_USER_IS_NOT_IN_THE_GRUPO);	
			        		}
							

			        
			        
			        

			        
			        if (ufg.getRole().equals(GrupoRolesEnum.READONLY))
			        	throw new ValidationException(ExceptionReturnCode.GRUPO_ROLE_NOT_ALLOW_THIS_ACTION );
			        }


}
