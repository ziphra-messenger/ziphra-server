package com.privacity.server.services.protocolomap;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.privacity.common.dto.EncryptKeysDTO;
import com.privacity.common.dto.GrupoDTO;
import com.privacity.common.dto.GrupoGralConfDTO;
import com.privacity.common.dto.GrupoUserConfDTO;
import com.privacity.common.dto.IdMessageDTO;
import com.privacity.common.dto.LockDTO;
import com.privacity.common.dto.MessageDTO;
import com.privacity.common.dto.MessageDetailDTO;
import com.privacity.common.dto.MyAccountConfDTO;
import com.privacity.common.dto.RequestIdDTO;
import com.privacity.common.dto.UserInvitationCodeDTO;
import com.privacity.common.dto.WrittingDTO;
import com.privacity.common.dto.request.GrupoAddUserRequestDTO;
import com.privacity.common.dto.request.GrupoInfoNicknameRequestDTO;
import com.privacity.common.dto.request.GrupoInvitationAcceptRequestDTO;
import com.privacity.common.dto.request.GrupoNewRequestDTO;
import com.privacity.common.dto.request.LoginRequestDTO;
import com.privacity.common.dto.request.MyAccountNicknameRequestDTO;
import com.privacity.common.dto.request.PublicKeyByInvitationCodeRequestDTO;
import com.privacity.common.dto.request.RegisterUserRequestDTO;
import com.privacity.common.dto.request.ValidateUsernameDTO;
import com.privacity.common.enumeration.ProtocoloActionsEnum;
import com.privacity.common.enumeration.ProtocoloComponentsEnum;
import com.privacity.server.common.enumeration.Urls;
import com.privacity.server.component.auth.AuthValidationService;
import com.privacity.server.component.encryptkeys.EncryptKeysService;
import com.privacity.server.component.encryptkeys.PrivacityRSAValidation;
import com.privacity.server.component.grupo.GrupoValidationService;
import com.privacity.server.component.message.MessageValidationService;
import com.privacity.server.component.myaccount.MyAccountValidationService;
import com.privacity.server.component.requestid.RequestIdValidationService;
import com.privacity.server.component.serverconf.ServerConfValidationService;

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
			PrivacityRSAValidation privacityRSAValidation,
			AuthValidationService authValidationService) throws Exception {
		super();
		this.encryptKeysService = encryptKeysService;
		this.grupoValidationService = grupoValidationService;
		this.messageValidationService = messageValidationService;
		this.myAccountValidationService = myAccountValidationService;
		this.requestIdValidationService = requestIdValidationService;
		this.serverConfValidationService = serverConfValidationService;
		this.privacityRSAValidation = privacityRSAValidation;
		this.authValidationService = authValidationService;
		build();
	}
	

	private EncryptKeysService encryptKeysService;
	private GrupoValidationService grupoValidationService;
	private MessageValidationService messageValidationService;
	private MyAccountValidationService myAccountValidationService;
	private RequestIdValidationService requestIdValidationService;
	private ServerConfValidationService serverConfValidationService;
	private PrivacityRSAValidation privacityRSAValidation;
	private AuthValidationService authValidationService;

		private synchronized void buildItem(Urls url, ProtocoloComponentsEnum component, ProtocoloActionsEnum action,Object clazz, String methodString, Class<?> parameterType) throws Exception {
		buildItem( url,  component,  action, clazz, clazz.getClass().getMethod(methodString,parameterType),parameterType);		
	}
	private synchronized void buildItem(Urls url, ProtocoloComponentsEnum component, ProtocoloActionsEnum action,Object clazz, String methodString) throws Exception {
		buildItem( url,  component,  action, clazz, clazz.getClass().getMethod(methodString),null);		
	}


	private synchronized void buildItem(Urls url, ProtocoloComponentsEnum component, ProtocoloActionsEnum action,Object clazz, Method method, Class<?> parameterType) throws Exception {

		
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


		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.ENCRYPT_KEYS,ProtocoloActionsEnum.ENCRYPT_KEYS_GET,encryptKeysService,"getPublicKeyByCodigoInvitacion",PublicKeyByInvitationCodeRequestDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_ACCEPT_INVITATION,grupoValidationService,"acceptInvitation",GrupoInvitationAcceptRequestDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_BLOCK_REMOTO,grupoValidationService,"blockGrupoRemoto",GrupoDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_DELETE,grupoValidationService,"delete",GrupoDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_GET_GRUPO_BY_ID,grupoValidationService,"getGrupoById",GrupoDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_GET_GRUPO_BY_IDS,grupoValidationService,"getGrupoByIds",GrupoDTO[].class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_GET_IDS_MY_GRUPOS,grupoValidationService,"getIdsMisGrupos");
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_GRAL_CONF_SAVE_LOCK,grupoValidationService,"saveGrupoGralConfLock",GrupoDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_LIST_MEMBERS,grupoValidationService,"getMembers",GrupoDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_LOGIN,grupoValidationService,"loginGrupo",GrupoDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_NEW_GRUPO,grupoValidationService,"newGrupo",GrupoNewRequestDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_REMOVE_ME,grupoValidationService,"removeMe",GrupoDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_SAVE_GENERAL_CONFIGURATION,grupoValidationService,"saveGrupoGeneralConfiguration",GrupoGralConfDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_SAVE_GRAL_CONF_PASSWORD,grupoValidationService,"saveGrupoGralConfPassword",GrupoDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_SAVE_GRUPO_USER_CONF,grupoValidationService,"saveGrupoUserConf",GrupoUserConfDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_SAVE_NICKNAME,grupoValidationService,"saveNickname",GrupoInfoNicknameRequestDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_SENT_INVITATION,grupoValidationService,"sentInvitation",GrupoAddUserRequestDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_STOP_WRITTING,grupoValidationService,"stopWritting",WrittingDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_WRITTING,grupoValidationService,"startWritting",WrittingDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MESSAGE,ProtocoloActionsEnum.MESSAGE_CHANGE_STATE,messageValidationService,"changeState",MessageDetailDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MESSAGE,ProtocoloActionsEnum.MESSAGE_DELETE_FOR_EVERYONE,messageValidationService,"deleteForEveryone",IdMessageDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MESSAGE,ProtocoloActionsEnum.MESSAGE_DELETE_FOR_ME,messageValidationService,"deleteForMe",IdMessageDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MESSAGE,ProtocoloActionsEnum.MESSAGE_EMPTY_LIST,messageValidationService,"emptyList",GrupoDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MESSAGE,ProtocoloActionsEnum.MESSAGE_GET_ALL_ID_MESSAGE_UNREAD,messageValidationService,"getAllidMessageUnreadMessages");
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MESSAGE,ProtocoloActionsEnum.MESSAGE_GET_ID_HISTORIAL,messageValidationService,"getHistorialId",IdMessageDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MESSAGE,ProtocoloActionsEnum.MESSAGE_GET_LOAD_MESSAGES,messageValidationService,"loadMessages",MessageDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MESSAGE,ProtocoloActionsEnum.MESSAGE_GET_MESSAGE,messageValidationService,"get",MessageDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MY_ACCOUNT,ProtocoloActionsEnum.MY_ACCOUNT_CLOSE_SESSION,myAccountValidationService,"closeSession");
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MY_ACCOUNT,ProtocoloActionsEnum.MY_ACCOUNT_INVITATION_CODE_GENERATOR,myAccountValidationService,"invitationCodeGenerator",EncryptKeysDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MY_ACCOUNT,ProtocoloActionsEnum.MY_ACCOUNT_IS_INVITATION_CODE_AVAILABLE,myAccountValidationService,"isInvitationCodeAvailable",String.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MY_ACCOUNT,ProtocoloActionsEnum.MY_ACCOUNT_SAVE_CODE_AVAILABLE,myAccountValidationService,"saveCodeAvailable",UserInvitationCodeDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MY_ACCOUNT,ProtocoloActionsEnum.MY_ACCOUNT_SAVE_GENERAL_CONFIGURATION,myAccountValidationService,"saveMessageConf",MyAccountConfDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MY_ACCOUNT,ProtocoloActionsEnum.MY_ACCOUNT_SAVE_LOCK,myAccountValidationService,"saveLock",LockDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MY_ACCOUNT,ProtocoloActionsEnum.MY_ACCOUNT_SAVE_LOGIN_SKIP,myAccountValidationService,"saveLoginSkip",boolean.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MY_ACCOUNT,ProtocoloActionsEnum.MY_ACCOUNT_SAVE_NICKNAME,myAccountValidationService,"saveNickname",MyAccountNicknameRequestDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MY_ACCOUNT,ProtocoloActionsEnum.MY_ACCOUNT_SAVE_PASSWORD,myAccountValidationService,"savePassword",LoginRequestDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.REQUEST_ID,ProtocoloActionsEnum.REQUEST_ID_PRIVATE_GET,requestIdValidationService,"getNewRequestIdPrivate",RequestIdDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_FREE,ProtocoloComponentsEnum.SERVER_CONF_UNSECURE,ProtocoloActionsEnum.SERVER_CONF_UNSECURE_GET_TIME,serverConfValidationService,"getTime");
		buildItem(Urls.CONSTANT_URL_PATH_FREE,ProtocoloComponentsEnum.PRIVACITY_RSA,ProtocoloActionsEnum.PRIVACITY_RSA_GET_PUBLIC_KEY,privacityRSAValidation,"getPublicKeyToSend");
		buildItem(Urls.CONSTANT_URL_PATH_FREE,ProtocoloComponentsEnum.SERVER_CONF_UNSECURE,ProtocoloActionsEnum.SERVER_CONF_UNSECURE_GET_GRAL_CONF,serverConfValidationService,"getSystemGralConf");
		buildItem(Urls.CONSTANT_URL_PATH_PUBLIC,ProtocoloComponentsEnum.AUTH,ProtocoloActionsEnum.AUTH_LOGIN,authValidationService,"login", LoginRequestDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PUBLIC,ProtocoloComponentsEnum.AUTH,ProtocoloActionsEnum.AUTH_REGISTER,authValidationService,"registerUser", RegisterUserRequestDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PUBLIC,ProtocoloComponentsEnum.AUTH,ProtocoloActionsEnum.AUTH_VALIDATE_USERNAME,authValidationService,"validateUsername", ValidateUsernameDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PUBLIC,ProtocoloComponentsEnum.REQUEST_ID,ProtocoloActionsEnum.REQUEST_ID_PUBLIC_GET,requestIdValidationService,"getNewRequestIdPublic", RequestIdDTO.class);
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
	

	public ProtocoloValue get (Urls url, ProtocoloComponentsEnum comp, ProtocoloActionsEnum act ) {
		log.debug("protocolo pedido " +  ProtocoloKey.build(url, comp, act).toString()) ;
		return map.get( ProtocoloKey.build(url, comp, act));
	}

//	public static void main(String[] args) throws Exception {
//
//		MapCompActMethService m = new MapCompActMethService();
//		m.build();
//
//	}
}
