package com.privacity.server.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.privacity.common.dto.AESAllDTO;
import com.privacity.common.dto.AESDTO;
import com.privacity.common.dto.MessageDTO;
import com.privacity.common.dto.ProtocoloDTO;
import com.privacity.server.common.enumeration.Urls;
import com.privacity.server.common.utils.UtilsString;
import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.exceptions.PrivacityException;
import com.privacity.server.model.Grupo;
import com.privacity.server.model.UserForGrupo;
import com.privacity.server.security.Usuario;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/private")
@Slf4j
public class SendPrivateController {


	@Autowired @Lazy
	private FacadeComponent comps;
	@PostMapping("/send")
	public ResponseEntity<String> inMessage(@RequestParam String request, 
			/*@RequestParam("data") */ MultipartFile data) throws PrivacityException {
		try {

			String username = comps.requestHelper().getUsuarioUsername();

			ProtocoloDTO p = comps.service().usuarioSessionInfo().decryptProtocolo(username, request, getUrl().name());

			if (p.getMessageDTO().getMediaDTO() != null) {

				AESAllDTO aess = comps.service().usuarioSessionInfo().getAesDtoAll(username);
				AESDTO aesdto =aess.getSessionAESDTOServerIn();
				AESToUse c = new AESToUse(Integer.parseInt(aesdto.getBitsEncrypt()),
						Integer.parseInt(	 aesdto.iteration),
						aesdto.getSecretKeyAES(),
						aesdto.getSaltAES());

				byte[] dataDescr = c.getAESDecryptData(data.getBytes());
				p.getMessageDTO().getMediaDTO().setData(dataDescr);

			}

			ProtocoloDTO retornoFuncion = this.in(p);

			String retornoFuncionEncriptado = comps.service().usuarioSessionInfo().encryptProtocolo(username, UtilsString.gsonToSend( retornoFuncion), getUrl().name());	

			log.debug("ENCRIPTADO >>" + UtilsString.shrinkString(retornoFuncionEncriptado));
			return ResponseEntity.ok().body(UtilsString.gsonToSend(retornoFuncionEncriptado));

		}catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);	
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Object getDTOObject(String objectDTO, Class clazz) {

		return UtilsString.gson().fromJson(objectDTO, clazz);
	}


	public ProtocoloDTO in(@RequestBody ProtocoloDTO request) throws Exception {

		log.debug("<<" + UtilsString.shrinkString(request.toString()));

		ProtocoloDTO p = new ProtocoloDTO();

		MessageDTO objetoRetorno=null;
		MessageDTO dtoObject=null;

		try {


			dtoObject =  request.getMessageDTO();

			Usuario usuarioLogged = comps.requestHelper().getUsuarioLogged();
			Grupo grupo = comps.requestHelper().setGrupoInUse(dtoObject.idGrupo);
			UserForGrupo ufg = comps.requestHelper().getUserForGrupoInUse();

			objetoRetorno =comps.validation().message().send(dtoObject,grupo,usuarioLogged,ufg);

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
		p.setComponent(request.getComponent());
		p.setAction(request.getAction());
		p.setMessageDTO(objetoRetorno);
		p.setAsyncId(request.getAsyncId());

		log.debug(">>" +UtilsString.shrinkString(p.toString()));
		return p;

	}	


	public boolean isSecure() {
		return true;
	}



	public boolean isRequestId() {
		return true;
	}


	public Urls getUrl() {
		return Urls.CONSTANT_URL_PATH_PRIVATE_SEND;
	}
}
