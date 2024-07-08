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
import com.privacity.common.dto.EncryptKeysDTO;
import com.privacity.common.dto.GrupoDTO;
import com.privacity.common.dto.GrupoGralConfDTO;
import com.privacity.common.dto.GrupoUserConfDTO;
import com.privacity.common.dto.IdDTO;
import com.privacity.common.dto.IdMessageDTO;
import com.privacity.common.dto.LockDTO;
import com.privacity.common.dto.MessageDTO;
import com.privacity.common.dto.MessageDetailDTO;
import com.privacity.common.dto.MyAccountConfDTO;
import com.privacity.common.dto.ProtocoloDTO;
import com.privacity.common.dto.RequestIdDTO;
import com.privacity.common.dto.UserInvitationCodeDTO;
import com.privacity.common.dto.WrittingDTO;
import com.privacity.common.dto.request.GrupoAddUserRequestDTO;
import com.privacity.common.dto.request.GrupoInvitationAcceptRequestDTO;
import com.privacity.common.dto.request.GrupoNewRequestDTO;
import com.privacity.common.dto.request.LoginRequestDTO;
import com.privacity.common.dto.request.MyAccountNicknameRequestDTO;
import com.privacity.common.dto.request.PublicKeyByInvitationCodeRequestDTO;
import com.privacity.common.dto.request.RequestEncryptDTO;
import com.privacity.common.enumeration.ProtocoloActionsEnum;
import com.privacity.common.enumeration.ProtocoloComponentsEnum;
import com.privacity.server.common.enumeration.Urls;
import com.privacity.server.component.common.ControllerBase;
import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.component.encryptkeys.EncryptKeysService;
import com.privacity.server.component.grupo.GrupoValidationService;
import com.privacity.server.component.message.MessageValidationService;

import com.privacity.server.component.myaccount.MyAccountValidationService;
import com.privacity.server.component.requestid.RequestIdUtilService;
import com.privacity.server.component.requestid.RequestIdValidationService;
import com.privacity.server.component.usuario.UserUtilService;
import com.privacity.server.security.UserDetailsImpl;
import com.privacity.server.util.LocalDateAdapter;


@RestController
@RequestMapping(path = "/private")

public class PrivateController extends ControllerBase{

	@Value("${serverconf.privacityIdAESOn}")
	private boolean encryptIds;
	

	private GrupoValidationService grupoValidationService;
	private MessageValidationService messageValidationService;
	private EncryptKeysService encryptKeysValidationService;
	private MyAccountValidationService myAccountValidationService;
	private UserUtilService	usuarioService;
	private RequestIdValidationService	requestIdValidationService;

	@Autowired @Lazy
	private FacadeComponent comps;
	
	public PrivateController(GrupoValidationService grupoValidationService,
			MessageValidationService messageValidationService, MyAccountValidationService myAccountValidationService,
			UserUtilService	usuarioService,EncryptKeysService encryptKeysValidationService,
		
			RequestIdValidationService	requestIdValidationService,
			RequestIdUtilService requestIdUtil) throws Exception {
		super();
		this.usuarioService	= usuarioService;
		this.grupoValidationService = grupoValidationService;
		this.messageValidationService = messageValidationService;
		this.myAccountValidationService = myAccountValidationService;

		this.encryptKeysValidationService=encryptKeysValidationService;
		this.requestIdValidationService=requestIdValidationService;


	}





	@PostMapping("/entry")
	public ResponseEntity<String> inMain(@RequestBody String request) throws Exception {

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
                .create();
        
		RequestEncryptDTO requestDTO = gson.fromJson(request, RequestEncryptDTO.class);
		
		request = requestDTO.getRequest();

	
		 //AESToUse c = comps.service().usuarioSessionInfo().get(u.getUsername()).getSessionAESToUse();
		 //String requestDesencriptado=null;
//		try {
//			requestDesencriptado = comps.service().usuarioSessionInfo().decryptSessionAESServerIn(u.getUsername(),request, String.class.getName());	
//		}catch(Exception e) {
//			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("MAL SESSION ENCRYPT");
//		}
		
		
		 
        
		ProtocoloDTO p = comps.service().usuarioSessionInfo().decryptProtocolo(comps.requestHelper().getUsuarioUsername(), request, getUrl().name());
		
		if (showLog(p)) {
			System.out.println( " ================================================================================");
		}		
		
		ProtocoloDTO retornoFuncion = super.in(p);
		
		if ("null".equals(retornoFuncion.getObjectDTO() ) && retornoFuncion.getCodigoRespuesta() != null ){
			retornoFuncion.setObjectDTO(null);
			return ResponseEntity.ok().body(gson.toJson(retornoFuncion));
		}
		String retornoFuncionJson = gson.toJson(retornoFuncion);
		
		String retornoFuncionEncriptado = comps.service().usuarioSessionInfo().encryptSessionAESServerOut(comps.requestHelper().getUsuarioUsername(),retornoFuncionJson);

		if (showLog(p)) {
			
			if ( retornoFuncion.getMessageDTO() != null)
			{
				System.out.println( " Salida >>  " + retornoFuncion.toString());
			}
			else {
			System.out.println( " Salida >>  " + retornoFuncionJson);
			System.out.println( " ================================================================================");
			}
		}
		return ResponseEntity.ok().body(gson.toJson(retornoFuncionEncriptado));

	}


	@Override
	public boolean getEncryptIds() {
		return encryptIds;
	}





	@Override
	public boolean isSecure() {
		return true;
	}





	@Override
	public boolean isRequestId() {
		return true;
	}
	
	@Override
	public boolean showLog(ProtocoloDTO request) {
		if (  request != null && "/grupo/writting/start".equals(request.getAction()) ) {
			return false;
		}

		if (  request != null && "/grupo/writting/stop".equals(request.getAction()) ) {
			return false;
		}
		
		if (  request != null && "/grupo/writting/stop".equals(request.getAction()) ) {
			return true;
		}

		if (  request != null && ProtocoloActionsEnum.MESSAGE_GET_MESSAGE.equals(request.getAction()) ) {
			return true;
		}

		if (  request != null && "/message/get/id/historial".equals(request.getAction()) ) {
			return true;
		}
		
		return true;
	}	
	
	@Override
	public Urls getUrl() {
		return Urls.CONSTANT_URL_PATH_PRIVATE;
	}

}
