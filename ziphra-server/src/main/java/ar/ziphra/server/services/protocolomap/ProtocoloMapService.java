package ar.ziphra.server.services.protocolomap;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import ar.ziphra.common.dto.EncryptKeysDTO;
import ar.ziphra.common.dto.GrupoDTO;
import ar.ziphra.common.dto.GrupoGralConfDTO;
import ar.ziphra.common.dto.GrupoUserConfDTO;
import ar.ziphra.common.dto.IdMessageDTO;
import ar.ziphra.common.dto.LockDTO;
import ar.ziphra.common.dto.MessageDTO;
import ar.ziphra.common.dto.MessageDetailDTO;
import ar.ziphra.common.dto.MyAccountConfDTO;
import ar.ziphra.common.dto.RequestIdDTO;
import ar.ziphra.common.dto.UserInvitationCodeDTO;
import ar.ziphra.common.dto.WrittingDTO;
import ar.ziphra.common.dto.request.ChangePasswordRequestDTO;
import ar.ziphra.common.dto.request.GrupoAddUserRequestDTO;
import ar.ziphra.common.dto.request.GrupoChangeUserRoleDTO;
import ar.ziphra.common.dto.request.GrupoInfoNicknameRequestDTO;
import ar.ziphra.common.dto.request.GrupoInvitationAcceptRequestDTO;
import ar.ziphra.common.dto.request.GrupoNewRequestDTO;
import ar.ziphra.common.dto.request.GrupoRemoveUserDTO;
import ar.ziphra.common.dto.request.LoginRequestDTO;
import ar.ziphra.common.dto.request.MyAccountNicknameRequestDTO;
import ar.ziphra.common.dto.request.PublicKeyByInvitationCodeRequestDTO;
import ar.ziphra.common.dto.request.RegisterUserRequestDTO;
import ar.ziphra.common.dto.request.ValidateUsernameDTO;
import ar.ziphra.common.enumeration.ExceptionReturnCode;
import ar.ziphra.common.enumeration.ProtocoloActionsEnum;
import ar.ziphra.common.enumeration.ProtocoloComponentsEnum;
import ar.ziphra.common.exceptions.ZiphraException;
import ar.ziphra.commonback.common.enumeration.ServerUrls;
import ar.ziphra.server.component.auth.AuthValidationService;
import ar.ziphra.server.component.encryptkeys.EncryptKeysService;
import ar.ziphra.server.component.encryptkeys.ZiphraRSAValidation;
import ar.ziphra.server.component.grupo.GrupoValidationService;
import ar.ziphra.server.component.message.MessageValidationService;
import ar.ziphra.server.component.myaccount.MyAccountValidationService;
import ar.ziphra.server.component.requestid.RequestIdValidationService;
import ar.ziphra.server.component.serverconf.ServerConfValidationService;

import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class ProtocoloMapService {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private final Map<ProtocoloKey, ProtocoloValue> map = new HashMap();

	public ProtocoloMapService(EncryptKeysService encryptKeysService, 
			GrupoValidationService grupoValidationService,
			MessageValidationService messageValidationService, 
			MyAccountValidationService myAccountValidationService,
			RequestIdValidationService requestIdValidationService,
			ServerConfValidationService serverConfValidationService, 
			ZiphraRSAValidation ziphraRSAValidation,
			AuthValidationService authValidationService) throws Exception {
		super();
		this.encryptKeysService = encryptKeysService;
		this.grupoValidationService = grupoValidationService;
		this.messageValidationService = messageValidationService;
		this.myAccountValidationService = myAccountValidationService;
		this.requestIdValidationService = requestIdValidationService;
		this.serverConfValidationService = serverConfValidationService;
		this.ziphraRSAValidation = ziphraRSAValidation;
		this.authValidationService = authValidationService;
		build();
	}
	

	private EncryptKeysService encryptKeysService;
	private GrupoValidationService grupoValidationService;
	private MessageValidationService messageValidationService;
	private MyAccountValidationService myAccountValidationService;
	private RequestIdValidationService requestIdValidationService;
	private ServerConfValidationService serverConfValidationService;
	private ZiphraRSAValidation ziphraRSAValidation;
	private AuthValidationService authValidationService;

		private synchronized void buildItem(ServerUrls url, ProtocoloComponentsEnum component, ProtocoloActionsEnum action,Object clazz, String methodString, Class<?> parameterType) throws Exception {
		buildItem( url,  component,  action, clazz, clazz.getClass().getMethod(methodString,parameterType),parameterType);		
	}
	private synchronized void buildItem(ServerUrls url, ProtocoloComponentsEnum component, ProtocoloActionsEnum action,Object clazz, String methodString) throws Exception {
		buildItem( url,  component,  action, clazz, clazz.getClass().getMethod(methodString),null);		
	}


	private synchronized void buildItem(ServerUrls url, ProtocoloComponentsEnum component, ProtocoloActionsEnum action,Object clazz, Method method, Class<?> parameterType) throws Exception {

		
		ProtocoloKey k =ProtocoloKey.build(url, component, action);
		
		if (map.containsKey(k)) {
			throw new Exception();
		}
		ProtocoloValue v = ProtocoloValue.build(clazz, method,parameterType);
		map.put(k, v);
		log.trace( "buildItem-> key: " + k.toString() + " value: " + v.toString());
	}

	private synchronized void build() throws Exception {

		//		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE, ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.AUTH_LOGIN, EncryptKeysService,"getPublicKeyByCodigoInvitacion");

		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_CHANGE_USER_ROLE,grupoValidationService,"changeUserRole",GrupoChangeUserRoleDTO.class);
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.ENCRYPT_KEYS,ProtocoloActionsEnum.ENCRYPT_KEYS_GET,encryptKeysService,"getPublicKeyByCodigoInvitacion",PublicKeyByInvitationCodeRequestDTO.class);
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_ACCEPT_INVITATION,grupoValidationService,"acceptInvitation",GrupoInvitationAcceptRequestDTO.class);
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_BLOCK_REMOTO,grupoValidationService,"blockGrupoRemoto",GrupoDTO.class);
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_DELETE,grupoValidationService,"delete",GrupoDTO.class);
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_GET_GRUPO_BY_ID,grupoValidationService,"getGrupoById",GrupoDTO.class);
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_GET_GRUPO_BY_IDS,grupoValidationService,"getGrupoByIds",GrupoDTO[].class);
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_GET_IDS_MY_GRUPOS,grupoValidationService,"getIdsMisGrupos");
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_SAVE_GENERAL_CONFIGURATION_LOCK,grupoValidationService,"saveGrupoGralConfLock",GrupoDTO.class);
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_LIST_MEMBERS,grupoValidationService,"getMembers",GrupoDTO.class);
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_LOGIN,grupoValidationService,"loginGrupo",GrupoDTO.class);
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_NEW_GRUPO,grupoValidationService,"newGrupo",GrupoNewRequestDTO.class);
		
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_REMOVE_OTHER,grupoValidationService,"removeOther",GrupoRemoveUserDTO.class);
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_REMOVE_ME,grupoValidationService,"removeMe",GrupoRemoveUserDTO.class);
		
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_SAVE_GENERAL_CONFIGURATION,grupoValidationService,"saveGrupoGeneralConfiguration",GrupoGralConfDTO.class);
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_SAVE_GRAL_CONF_PASSWORD,grupoValidationService,"saveGrupoGralConfPassword",GrupoDTO.class);
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_SAVE_GRUPO_USER_CONF,grupoValidationService,"saveGrupoUserConf",GrupoUserConfDTO.class);
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_SAVE_NICKNAME,grupoValidationService,"saveNickname",GrupoInfoNicknameRequestDTO.class);
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_SENT_INVITATION,grupoValidationService,"sentInvitation",GrupoAddUserRequestDTO.class);
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_STOP_WRITTING,grupoValidationService,"stopWritting",WrittingDTO.class);
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_WRITTING,grupoValidationService,"startWritting",WrittingDTO.class);
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MESSAGE,ProtocoloActionsEnum.MESSAGE_CHANGE_STATE,messageValidationService,"changeState",MessageDetailDTO.class);
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MESSAGE,ProtocoloActionsEnum.MESSAGE_DELETE_FOR_EVERYONE,messageValidationService,"deleteForEveryone",IdMessageDTO.class);
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MESSAGE,ProtocoloActionsEnum.MESSAGE_DELETE_FOR_ME,messageValidationService,"deleteForMe",IdMessageDTO.class);
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MESSAGE,ProtocoloActionsEnum.MESSAGE_EMPTY_LIST,messageValidationService,"emptyList",GrupoDTO.class);
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MESSAGE,ProtocoloActionsEnum.MESSAGE_GET_ALL_ID_MESSAGE_UNREAD,messageValidationService,"getAllidMessageUnreadMessages");
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MESSAGE,ProtocoloActionsEnum.MESSAGE_GET_ALL_ID_MESSAGE_DESTINY_SERVER,messageValidationService,"getAllidMessageDestinyServerMessages");
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MESSAGE,ProtocoloActionsEnum.MESSAGE_GET_ID_HISTORIAL,messageValidationService,"getHistorialId",IdMessageDTO.class);
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MESSAGE,ProtocoloActionsEnum.MESSAGE_GET_LOAD_MESSAGES,messageValidationService,"loadMessages",MessageDTO.class);
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MESSAGE,ProtocoloActionsEnum.MESSAGE_GET_MESSAGE,messageValidationService,"get",MessageDTO.class);
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MY_ACCOUNT,ProtocoloActionsEnum.MY_ACCOUNT_CLOSE_SESSION,myAccountValidationService,"closeSession");
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MY_ACCOUNT,ProtocoloActionsEnum.MY_ACCOUNT_INVITATION_CODE_GENERATOR,myAccountValidationService,"invitationCodeGenerator",EncryptKeysDTO.class);
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MY_ACCOUNT,ProtocoloActionsEnum.MY_ACCOUNT_IS_INVITATION_CODE_AVAILABLE,myAccountValidationService,"isInvitationCodeAvailable",String.class);
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MY_ACCOUNT,ProtocoloActionsEnum.MY_ACCOUNT_SAVE_CODE_AVAILABLE,myAccountValidationService,"saveCodeAvailable",UserInvitationCodeDTO.class);
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MY_ACCOUNT,ProtocoloActionsEnum.MY_ACCOUNT_SAVE_GENERAL_CONFIGURATION,myAccountValidationService,"saveMessageConf",MyAccountConfDTO.class);
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MY_ACCOUNT,ProtocoloActionsEnum.MY_ACCOUNT_SAVE_LOCK,myAccountValidationService,"saveLock",LockDTO.class);
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MY_ACCOUNT,ProtocoloActionsEnum.MY_ACCOUNT_SAVE_LOGIN_SKIP,myAccountValidationService,"saveLoginSkip",boolean.class);
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MY_ACCOUNT,ProtocoloActionsEnum.MY_ACCOUNT_SAVE_NICKNAME,myAccountValidationService,"saveNickname",MyAccountNicknameRequestDTO.class);
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MY_ACCOUNT,ProtocoloActionsEnum.MY_ACCOUNT_SAVE_PASSWORD,myAccountValidationService,"savePassword",ChangePasswordRequestDTO.class);
		buildItem(ServerUrls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.REQUEST_ID,ProtocoloActionsEnum.REQUEST_ID_PRIVATE_GET,requestIdValidationService,"getNewRequestIdPrivate",RequestIdDTO.class);
		buildItem(ServerUrls.CONSTANT_URL_PATH_FREE,ProtocoloComponentsEnum.SERVER_CONF_UNSECURE,ProtocoloActionsEnum.SERVER_CONF_UNSECURE_GET_TIME,serverConfValidationService,"getTime");
		buildItem(ServerUrls.CONSTANT_URL_PATH_FREE,ProtocoloComponentsEnum.PRIVACITY_RSA,ProtocoloActionsEnum.PRIVACITY_RSA_GET_PUBLIC_KEY,ziphraRSAValidation,"getPublicKeyToSend");
		buildItem(ServerUrls.CONSTANT_URL_PATH_FREE,ProtocoloComponentsEnum.SERVER_CONF_UNSECURE,ProtocoloActionsEnum.SERVER_CONF_UNSECURE_GET_GRAL_CONF,serverConfValidationService,"getSystemGralConf");
		buildItem(ServerUrls.CONSTANT_URL_PATH_PUBLIC,ProtocoloComponentsEnum.AUTH,ProtocoloActionsEnum.AUTH_LOGIN,authValidationService,"login", LoginRequestDTO.class);
		buildItem(ServerUrls.CONSTANT_URL_PATH_PUBLIC,ProtocoloComponentsEnum.AUTH,ProtocoloActionsEnum.AUTH_REGISTER,authValidationService,"registerUser", RegisterUserRequestDTO.class);
		buildItem(ServerUrls.CONSTANT_URL_PATH_PUBLIC,ProtocoloComponentsEnum.AUTH,ProtocoloActionsEnum.AUTH_VALIDATE_USERNAME,authValidationService,"validateUsername", ValidateUsernameDTO.class);
		buildItem(ServerUrls.CONSTANT_URL_PATH_PUBLIC,ProtocoloComponentsEnum.REQUEST_ID,ProtocoloActionsEnum.REQUEST_ID_PUBLIC_GET,requestIdValidationService,"getNewRequestIdPublic", RequestIdDTO.class);
		log.trace(this.toString());
	}


	@Override
	public String toString() {
		String r= "MapCompActMethService\n";
		
		for (Map.Entry<ProtocoloKey,ProtocoloValue> entry : map.entrySet())  
            r = r + entry.getKey() + 
                             ", " + entry.getValue() + "\n";
		
		return r;
	}
	

	public ProtocoloValue get (ServerUrls url, ProtocoloComponentsEnum comp, ProtocoloActionsEnum act ) throws ZiphraException {
		log.debug("protocolo pedido " +  url.name() + " " + ProtocoloKey.build(url, comp, act).toString())  ;


		ProtocoloValue r;
		try {
			r = map.get( ProtocoloKey.build(url, comp, act));
			log.debug("value: " +  r.toString())  ;
		} catch (Exception e) {
			log.error(ExceptionReturnCode.GENERAL_INVALID_ACCESS_PROTOCOL.getCode() + " - " +
					ExceptionReturnCode.GENERAL_INVALID_ACCESS_PROTOCOL.getDescription());

			throw new ZiphraException(ExceptionReturnCode.GENERAL_INVALID_ACCESS_PROTOCOL);
		}



		return r;
	}

//	public static void main(String[] args) throws Exception {
//
//		MapCompActMethService m = new MapCompActMethService();
//		m.build();
//
//	}
}
