package com.privacity.server.main;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.privacity.common.adapters.LocalDateAdapter;
import com.privacity.common.dto.AESAllDTO;
import com.privacity.common.dto.AESDTO;
import com.privacity.common.dto.MessageDTO;
import com.privacity.common.dto.ProtocoloDTO;
import com.privacity.common.dto.request.RequestEncryptDTO;
import com.privacity.commonback.common.enumeration.Urls;
import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.component.message.MessageValidationService;


@RestController
@RequestMapping(path = "/private/download")

public class DownloadDataPrivateController {


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
		
		 String requestDesencriptado=null;
		try {
			requestDesencriptado = comps.service().usuarioSessionInfo().decryptSessionAESServerIn(comps.requestHelper().getUsuarioUsername(),request.getRequest(), String.class.getName());
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		ProtocoloDTO p = gson.fromJson(requestDesencriptado, ProtocoloDTO.class);
		MessageDTO retornoFuncion = this.in(p);
		
		 AESAllDTO aess = comps.service().usuarioSessionInfo().getAesDtoAll(comps.requestHelper().getUsuarioUsername());
			AESDTO aesdto =aess.getSessionAESDTOServerOut();
			AESToUse c = new AESToUse(Integer.parseInt(aesdto.getBitsEncrypt()),
Integer.parseInt(	 aesdto.iteration),
					aesdto.getSecretKeyAES(),
					aesdto.getSaltAES());
		 
		 byte[] retorno = c.getAESData(retornoFuncion.getMediaDTO().getData());

		return ResponseEntity.status(HttpStatus.OK).body(retorno);

	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Object getDTOObject(String objectDTO, Class clazz) {
		return comps.util().gson().fromJson(objectDTO, clazz);
	}

	public MessageDTO in(@RequestBody ProtocoloDTO request) throws Exception {

		MessageDTO objetoRetorno=null;
			
				MessageDTO dtoObject =  request.getMessageDTO();
				dtoObject = (MessageDTO) comps.service().usuarioSessionInfo().privacityIdServiceDecrypt(
						comps.requestHelper().getUsuarioUsername()
						,dtoObject, MessageDTO.class.getName());
				
				objetoRetorno = messageValidationService.getDataMedia(dtoObject);

				objetoRetorno = (MessageDTO) comps.service().usuarioSessionInfo().privacityIdServiceDecrypt(
						comps.requestHelper().getUsuarioUsername()
						,objetoRetorno, MessageDTO.class.getName());

	return objetoRetorno;

}	


	public boolean isSecure() {
		return true;
	}



	public boolean isRequestId() {
		return true;
	}



	   public Urls getUrl() {
			return Urls.CONSTANT_URL_PATH_PRIVATE_DOWNLOAD_DATA;
					 
		}	 
}
