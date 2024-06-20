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
import com.privacity.common.config.ConstantProtocolo;
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
import com.privacity.common.dto.UserInvitationCodeDTO;
import com.privacity.common.dto.WrittingDTO;
import com.privacity.common.dto.request.GrupoAddUserRequestDTO;
import com.privacity.common.dto.request.GrupoInvitationAcceptRequestDTO;
import com.privacity.common.dto.request.GrupoNewRequestDTO;
import com.privacity.common.dto.request.LoginRequestDTO;
import com.privacity.common.dto.request.MyAccountNicknameRequestDTO;
import com.privacity.common.dto.request.PublicKeyByInvitationCodeRequestDTO;
import com.privacity.common.dto.request.RequestEncryptDTO;
import com.privacity.server.component.common.ControllerBase;
import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.component.encryptkeys.EncryptKeysService;
import com.privacity.server.component.grupo.GrupoValidationService;
import com.privacity.server.component.message.MessageValidationService;
import com.privacity.server.component.model.request.GrupoBlockRemotoRequestLocalDTO;
import com.privacity.server.component.model.request.GrupoIdLocalDTO;
import com.privacity.server.component.model.request.GrupoInfoNicknameRequestLocalDTO;
import com.privacity.server.component.myaccount.MyAccountValidationService;
import com.privacity.server.component.requestid.RequestIdUtilService;
import com.privacity.server.component.requestid.RequestIdValidationService;
import com.privacity.server.component.usuario.UserUtilService;
import com.privacity.server.encrypt.PrivacityIdServices;
import com.privacity.server.security.UserDetailsImpl;
import com.privacity.server.util.LocalDateAdapter;


@RestController
@RequestMapping(path = "/private")

public class MainPrivateController extends ControllerBase{

	@Value("${serverconf.privacityIdAESOn}")
	private boolean encryptIds;
	
	private PrivacityIdServices privacityIdServices;
	private GrupoValidationService grupoValidationService;
	private MessageValidationService messageValidationService;
	private EncryptKeysService encryptKeysValidationService;
	private MyAccountValidationService myAccountValidationService;
	private UserUtilService	usuarioService;
	private RequestIdValidationService	requestIdValidationService;

	@Autowired @Lazy
	private FacadeComponent comps;
	
	public MainPrivateController(GrupoValidationService grupoValidationService,
			MessageValidationService messageValidationService, MyAccountValidationService myAccountValidationService,
			UserUtilService	usuarioService,EncryptKeysService encryptKeysValidationService,
			PrivacityIdServices privacityIdServices,
			RequestIdValidationService	requestIdValidationService,
			RequestIdUtilService requestIdUtil) throws Exception {
		super();
		this.usuarioService	= usuarioService;
		this.grupoValidationService = grupoValidationService;
		this.messageValidationService = messageValidationService;
		this.myAccountValidationService = myAccountValidationService;
		this.privacityIdServices = privacityIdServices;
		this.encryptKeysValidationService=encryptKeysValidationService;
		this.requestIdValidationService=requestIdValidationService;

		
		getMapaController().put(ConstantProtocolo.PROTOCOLO_COMPONENT_REQUEST_ID, requestIdValidationService);
		//getMapaMetodos().put(ConstantProtocolo.PROTOCOLO_ACTION_REQUEST_ID_PRIVATE_GET, RequestIdValidationService.class.getMethod("getNewRequestIdPrivate", RequestIdDTO.class));		
		
		getMapaController().put(ConstantProtocolo.PROTOCOLO_COMPONENT_ENCRYPT_KEYS, encryptKeysValidationService);
		getMapaController().put("/grupo", grupoValidationService);
		getMapaController().put("/message", messageValidationService);
		getMapaController().put("/myAccount", myAccountValidationService);
		
		getMapaMetodos().put(ConstantProtocolo.PROTOCOLO_ACTION_ENCRYPT_KEYS_GET, EncryptKeysService.class.getMethod("getPublicKeyByCodigoInvitacion", PublicKeyByInvitationCodeRequestDTO.class));
//		getMapaMetodos().put(Constant.PROTOCOLO_ACTION_ENCRYPT_KEYS_CREATE, EncryptKeysValidationService.class.getMethod("create", EncryptKeysDTO.class));
		
		getMapaMetodos().put(ConstantProtocolo.PROTOCOLO_ACTION_GRUPO_GET_IDS_MY_GRUPOS, GrupoValidationService.class.getMethod("getIdsMisGrupos"));
		getMapaMetodos().put(ConstantProtocolo.PROTOCOLO_ACTION_GRUPO_GET_GRUPO_BY_ID, GrupoValidationService.class.getMethod("getGrupoById",GrupoIdLocalDTO.class));
		
		getMapaMetodos().put(ConstantProtocolo.PROTOCOLO_ACTION_GRUPO_GET_GRUPO_BY_IDS, GrupoValidationService.class.getMethod("getGrupoByIds",GrupoIdLocalDTO[].class));
		
		getMapaMetodos().put(ConstantProtocolo.PROTOCOLO_ACTION_GRUPO_WRITTING, GrupoValidationService.class.getMethod("startWritting",WrittingDTO.class));
		getMapaMetodos().put(ConstantProtocolo.PROTOCOLO_ACTION_GRUPO_STOP_WRITTING, GrupoValidationService.class.getMethod("stopWritting",WrittingDTO.class));
//		getMapaMetodos().put(ConstantProtocolo.PROTOCOLO_ACTION_GRUPO_HOW_MANY_MEMBERS_ONLINE, GrupoValidationService.class.getMethod("getMembersOnline",IdDTO.class));
		
        
		getMapaMetodos().put("/grupo/newGrupo", GrupoValidationService.class.getMethod("newGrupo", GrupoNewRequestDTO.class));
//		getMapaMetodos().put("/grupo/listar/misGrupos", GrupoValidationService.class.getMethod("listarMisGrupos"));
//		getMapaMetodos().put("/grupo/initGrupo", GrupoValidationService.class.getMethod("initGrupo", String.class));
		getMapaMetodos().put("/grupo/sentInvitation", GrupoValidationService.class.getMethod("sentInvitation", GrupoAddUserRequestDTO.class));
		getMapaMetodos().put("/grupo/acceptInvitation", GrupoValidationService.class.getMethod("acceptInvitation", GrupoInvitationAcceptRequestDTO.class));
		getMapaMetodos().put("/grupo/removeMe", GrupoValidationService.class.getMethod("removeMe", GrupoIdLocalDTO.class));
		getMapaMetodos().put("/grupo/delete", GrupoValidationService.class.getMethod("delete", IdDTO.class));
		getMapaMetodos().put("/grupo/list/members", GrupoValidationService.class.getMethod("getMembers", GrupoDTO.class));
		getMapaMetodos().put(ConstantProtocolo.PROTOCOLO_ACTION_GRUPO_BLOCK_REMOTO, GrupoValidationService.class.getMethod("blockGrupoRemoto", GrupoBlockRemotoRequestLocalDTO.class));
		getMapaMetodos().put(ConstantProtocolo.PROTOCOLO_ACTION_GRUPO_SAVE_NICKNAME, GrupoValidationService.class.getMethod("saveNickname", GrupoInfoNicknameRequestLocalDTO.class));
	
		getMapaMetodos().put(ConstantProtocolo.PROTOCOLO_ACTION_GRUPO_SAVE_GENERAL_CONFIGURATION, GrupoValidationService.class.getMethod("saveGrupoGeneralConfiguration", GrupoGralConfDTO.class));
		getMapaMetodos().put("/grupo/saveGrupoUserConf", GrupoValidationService.class.getMethod("saveGrupoUserConf", GrupoUserConfDTO.class));
		getMapaMetodos().put("/grupo/gralConf/save/lock", GrupoValidationService.class.getMethod("saveGrupoGralConfLock", GrupoDTO.class));
		
		getMapaMetodos().put("/grupo/login", GrupoValidationService.class.getMethod("loginGrupo", GrupoDTO.class));
		
		
		getMapaMetodos().put(ConstantProtocolo.PROTOCOLO_ACTION_GRUPO_SAVE_GRAL_CONF_PASSWORD, GrupoValidationService.class.getMethod("saveGrupoGralConfPassword", GrupoDTO.class));		
		
		//getMapaMetodos().put("/grupo/getGrupoUserConf", GrupoValidationService.class.getMethod("getGrupoUserConf", GrupoDTO.class));
		
		
		//getMapaMetodos().put("/message/send", MessageValidationService.class.getMethod("send", MessageDTO.class));
		//getMapaMetodos().put("/message/sendAnonimo",  comps.service.message.getClass().getMethod("sendAnonimo", MessageDTO.class));
		getMapaMetodos().put("/message/emptyList", MessageValidationService.class.getMethod("emptyList", GrupoDTO.class));
		getMapaMetodos().put("/message/get/id/historial", MessageValidationService.class.getMethod("getHistorialId", IdMessageDTO.class));		
		
		
		getMapaMetodos().put("/message/deleteForMe", MessageValidationService.class.getMethod("deleteForMe", IdMessageDTO.class));
		getMapaMetodos().put("/message/deleteForEveryone", MessageValidationService.class.getMethod("deleteForEveryone", IdMessageDTO.class));
		
		getMapaMetodos().put(ConstantProtocolo.PROTOCOLO_ACTION_MESSAGE_GET_ALL_ID_MESSAGE_UNREAD, MessageValidationService.class.getMethod("getAllidMessageUnreadMessages"));		
		getMapaMetodos().put(ConstantProtocolo.PROTOCOLO_ACTION_MESSAGE_GET_MESSAGE, MessageValidationService.class.getMethod("get", MessageDTO.class));
		
		
		
		getMapaMetodos().put(ConstantProtocolo.PROTOCOLO_ACTION_MESSAGE_CHANGE_STATE, MessageValidationService.class.getMethod("changeState", MessageDetailDTO.class));
		
		getMapaMetodos().put("/message/get/loadMessages", MessageValidationService.class.getMethod("loadMessages", MessageDTO.class));
		
		getMapaMetodos().put("/myAccount/invitationCodeGenerator", MyAccountValidationService.class.getMethod("invitationCodeGenerator", EncryptKeysDTO.class));
		getMapaMetodos().put("/myAccount/isInvitationCodeAvailable", MyAccountValidationService.class.getMethod("isInvitationCodeAvailable", String.class));
		getMapaMetodos().put("/myAccount/saveCodeAvailable", MyAccountValidationService.class.getMethod("saveCodeAvailable", UserInvitationCodeDTO.class));

		getMapaMetodos().put("/myAccount/save/password", MyAccountValidationService.class.getMethod("savePassword", LoginRequestDTO.class));
		getMapaMetodos().put("/myAccount/save/nickname", MyAccountValidationService.class.getMethod("saveNickname", MyAccountNicknameRequestDTO.class));
		getMapaMetodos().put("/myAccount/save/lock", MyAccountValidationService.class.getMethod("saveLock", LockDTO.class));
		
		getMapaMetodos().put(ConstantProtocolo.PROTOCOLO_ACTION_MY_ACCOUNT_SAVE_GENERAL_CONFIGURATION, MyAccountValidationService.class.getMethod("saveMessageConf", MyAccountConfDTO.class));
		getMapaMetodos().put(ConstantProtocolo.PROTOCOLO_ACTION_MY_ACCOUNT_SAVE_LOGIN_SKIP, MyAccountValidationService.class.getMethod("saveLoginSkip", boolean.class));
		
	}



		

	@PostMapping("/entry")
	public ResponseEntity<String> inMain(@RequestBody String request) throws Exception {

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
                .create();
        
		RequestEncryptDTO requestDTO = gson.fromJson(request, RequestEncryptDTO.class);
		
		request = requestDTO.getRequest();

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
		
		if (showLog(p)) {
			System.out.println( " ================================================================================");
		}		
		
		ProtocoloDTO retornoFuncion = super.in(p);
		String retornoFuncionJson = gson.toJson(retornoFuncion);
		String retornoFuncionEncriptado = c.getAES(retornoFuncionJson);

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
	public PrivacityIdServices getPrivacityIdServices() {
		// TODO Auto-generated method stub
		return this.privacityIdServices;
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

		if (  request != null && ConstantProtocolo.PROTOCOLO_ACTION_MESSAGE_GET_MESSAGE.equals(request.getAction()) ) {
			return true;
		}

		if (  request != null && "/message/get/id/historial".equals(request.getAction()) ) {
			return true;
		}
		
		return true;
	}	

}
