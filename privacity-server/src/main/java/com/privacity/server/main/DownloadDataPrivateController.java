package com.privacity.server.main;

import java.time.LocalDateTime;

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
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.privacity.common.dto.MessageDTO;
import com.privacity.common.dto.ProtocoloDTO;
import com.privacity.common.dto.request.RequestEncryptDTO;
import com.privacity.server.common.util.AESToUse;
import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.component.message.MessageValidationService;
import com.privacity.server.security.UserDetailsImpl;
import com.privacity.server.util.LocalDateAdapter;


@RestController
@RequestMapping(path = "/private/download")

public class DownloadDataPrivateController {

	@Value("${serverconf.privacityIdAESOn}")
	private boolean encryptIds;

	private MessageValidationService messageValidationService;
	

	@Autowired @Lazy
	private FacadeComponent comps;


		

	public DownloadDataPrivateController(MessageValidationService messageValidationService) {
		super();
	
		this.messageValidationService = messageValidationService;
	}



	@PostMapping("/data")
	public ResponseEntity<byte[]> getData(@RequestBody String requestO) throws Exception {
		
		
	
        Gson gson = new GsonBuilder()
                //.setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
                .create();
        RequestEncryptDTO request = gson.fromJson(requestO, RequestEncryptDTO.class);
		
		Authentication auth = SecurityContextHolder
	            .getContext()
	            .getAuthentication();
		UserDetailsImpl u = (UserDetailsImpl) auth.getPrincipal();
	    
		 AESToUse c = comps.service().usuarioSessionInfo().get(u.getUsername()).getSessionAESToUse();
		
		 String requestDesencriptado=null;
		try {
			requestDesencriptado = c.getAESDecrypt(request.getRequest());	
		}catch(javax.crypto.BadPaddingException e) {
			e.printStackTrace();
			return null;
		}
		
		ProtocoloDTO p = gson.fromJson(requestDesencriptado, ProtocoloDTO.class);
		
		 MessageDTO retornoFuncion = this.in(p);
		
		byte[] retorno = c.getAESData(retornoFuncion.getMediaDTO().getData());
		

		return ResponseEntity.status(HttpStatus.OK).body(retorno);

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

	public MessageDTO in(@RequestBody ProtocoloDTO request) throws Exception {


		MessageDTO objetoRetorno=null;



			
				MessageDTO dtoObject =  request.getMessageDTO();
				
				comps.service().usuarioSessionInfo().decryptIds(comps.util().usuario().getUsernameLogged(), dtoObject);
				
				objetoRetorno = messageValidationService.getDataMedia(dtoObject);
					


				comps.service().usuarioSessionInfo().encryptIds(comps.util().usuario().getUsernameLogged(), objetoRetorno);

	

	//	if(getEncryptIds()) {
	//		objetoRetorno = getPrivacityIdServices().transformarDesencriptarOut(getMapaMetodos().get(request.getAction()).invoke(getMapaController().get(request.getComponent()), dtoObject));
	//	}else {
	//		objetoRetorno = getMapaMetodos().get(request.getAction()).invoke(getMapaController().get(request.getComponent()), dtoObject);
	//	}	
	// armo la devolucion
			


	if (showLog()); ////System.out.println(">>" + p.toString());
	return objetoRetorno;

}	


	public boolean isSecure() {
		return true;
	}



	public boolean isRequestId() {
		return true;
	}


	
	public boolean showLog() {
		return false;
	}



}
