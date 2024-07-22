package com.privacity.server.main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
import com.privacity.common.enumeration.ExceptionReturnCode;
import com.privacity.common.exceptions.PrivacityException;
import com.privacity.commonback.common.enumeration.Urls;
import com.privacity.commonback.common.interfaces.HealthCheckerInterface;
import com.privacity.core.model.Grupo;
import com.privacity.core.model.UserForGrupo;
import com.privacity.core.model.Usuario;
import com.privacity.server.component.common.ControllerBaseUtil;
import com.privacity.server.component.common.service.facade.FacadeComponent;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/private")
@Slf4j
public class SendPrivateController extends ControllerBaseUtil{
	@Autowired
	@Lazy
	private HealthCheckerInterface healthChecker;

	@Autowired @Lazy
	private FacadeComponent comps;
	@PostMapping("/send")
	public ResponseEntity<String> inMessage(@RequestParam String request, 
			/*@RequestParam("data") */ MultipartFile data) throws PrivacityException {

		if ( checkServersMessageId()!= null) return checkServersMessageId();
		if ( checkServersSessionManager()!= null) return checkServersSessionManager();
		if ( checkServersRequestId()!= null) return checkServersRequestId();


		String username = comps.requestHelper().getUsuarioUsername();

		ProtocoloDTO p = comps.service().usuarioSessionInfo().decryptProtocolo(username, request, getUrl().name());

		if (p.getMessageDTO().getMediaDTO() != null) {

			AESAllDTO aess = comps.service().usuarioSessionInfo().getAesDtoAll(username);
			AESDTO aesdto =aess.getSessionAESDTOServerIn();
			AESToUse c;
			try {
				c = new AESToUse(Integer.parseInt(aesdto.getBitsEncrypt()),
						Integer.parseInt(	 aesdto.iteration),
						aesdto.getSecretKeyAES(),
						aesdto.getSaltAES());


				byte[] dataDescr = c.getAESDecryptData(data.getBytes());
				p.getMessageDTO().getMediaDTO().setData(dataDescr);
			} catch (NumberFormatException e) {
				log.error(e.getMessage());
				throw new PrivacityException(ExceptionReturnCode.GENERAL_INVALID_ACCESS_PROTOCOL);
			} catch (Exception e) {
				log.error(e.getMessage());
				throw new PrivacityException(ExceptionReturnCode.ENCRYPT_PROCESS);
			}
		}

		ProtocoloDTO retornoFuncion = this.in(p);

		String retornoFuncionEncriptado = comps.service().usuarioSessionInfo().encryptProtocolo(
				username,  retornoFuncion, getUrl().name());	

		log.debug("ENCRIPTADO >>" + comps.util().string().cutString(retornoFuncionEncriptado));
		return ResponseEntity.ok().body( comps.util().gson().toJson(retornoFuncionEncriptado));

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected Object getDTOObject(String objectDTO, Class clazz) {

		return comps.util().gson().fromJson(objectDTO, clazz);
	}


	public ProtocoloDTO in(@RequestBody ProtocoloDTO request)  {

		log.debug("<<" + comps.util().string().cutString(request.toString()));

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

		log.debug(">>" +comps.util().string().cutString(p.toString()));
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
