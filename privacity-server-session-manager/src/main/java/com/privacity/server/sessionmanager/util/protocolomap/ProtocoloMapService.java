package com.privacity.server.sessionmanager.util.protocolomap;

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
import com.privacity.common.dto.response.SaveGrupoGralConfLockResponseDTO;
import com.privacity.common.enumeration.ProtocoloActionsEnum;
import com.privacity.common.enumeration.ProtocoloComponentsEnum;
import com.privacity.server.sessionmanager.enumeration.Urls;

import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class ProtocoloMapService {

	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private final Map<ProtocoloKey, ProtocoloValue> map = new HashMap();

	public ProtocoloMapService() throws Exception {
		build();
	}

	private synchronized void buildItem(Urls url, ProtocoloComponentsEnum component, ProtocoloActionsEnum action) throws Exception {
		buildItem( url,  component,  action, null);		
	}


	private synchronized void buildItem(Urls url, ProtocoloComponentsEnum component, ProtocoloActionsEnum action, Class<?> parameterType) throws Exception {


		ProtocoloKey k =ProtocoloKey.build(url, component, action);

		if (map.containsKey(k)) {
			throw new Exception();
		}
		ProtocoloValue v = ProtocoloValue.build(parameterType);
		map.put(k, v);
		
		log.trace( "buildItem-> key: " + k.toString() + " value: " + v.toString());
	}

	private synchronized void build() throws Exception {

		//		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE, ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.AUTH_LOGIN, EncryptKeysService,"getPublicKeyByCodigoInvitacion");


		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.ENCRYPT_KEYS,ProtocoloActionsEnum.ENCRYPT_KEYS_GET,PublicKeyByInvitationCodeRequestDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_ACCEPT_INVITATION,GrupoInvitationAcceptRequestDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_BLOCK_REMOTO,GrupoDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_DELETE,GrupoDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_GET_GRUPO_BY_ID,GrupoDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_GET_GRUPO_BY_IDS,GrupoDTO[].class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_GET_IDS_MY_GRUPOS);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_GRAL_CONF_SAVE_LOCK,GrupoDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_LIST_MEMBERS,GrupoDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_LOGIN,GrupoDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_NEW_GRUPO,GrupoNewRequestDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_REMOVE_ME,GrupoDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_SAVE_GENERAL_CONFIGURATION,GrupoGralConfDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_SAVE_GRAL_CONF_PASSWORD,GrupoDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_SAVE_GRUPO_USER_CONF,GrupoUserConfDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_SAVE_NICKNAME,GrupoInfoNicknameRequestDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_SENT_INVITATION,GrupoAddUserRequestDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_STOP_WRITTING,WrittingDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_WRITTING,WrittingDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MESSAGE,ProtocoloActionsEnum.MESSAGE_CHANGE_STATE,MessageDetailDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MESSAGE,ProtocoloActionsEnum.MESSAGE_DELETE_FOR_EVERYONE,IdMessageDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MESSAGE,ProtocoloActionsEnum.MESSAGE_DELETE_FOR_ME,IdMessageDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MESSAGE,ProtocoloActionsEnum.MESSAGE_EMPTY_LIST,GrupoDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MESSAGE,ProtocoloActionsEnum.MESSAGE_GET_ALL_ID_MESSAGE_UNREAD);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MESSAGE,ProtocoloActionsEnum.MESSAGE_GET_ID_HISTORIAL,IdMessageDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MESSAGE,ProtocoloActionsEnum.MESSAGE_GET_LOAD_MESSAGES,MessageDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MESSAGE,ProtocoloActionsEnum.MESSAGE_GET_MESSAGE,MessageDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MY_ACCOUNT,ProtocoloActionsEnum.MY_ACCOUNT_CLOSE_SESSION);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MY_ACCOUNT,ProtocoloActionsEnum.MY_ACCOUNT_INVITATION_CODE_GENERATOR,EncryptKeysDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MY_ACCOUNT,ProtocoloActionsEnum.MY_ACCOUNT_IS_INVITATION_CODE_AVAILABLE,String.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MY_ACCOUNT,ProtocoloActionsEnum.MY_ACCOUNT_SAVE_CODE_AVAILABLE,UserInvitationCodeDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MY_ACCOUNT,ProtocoloActionsEnum.MY_ACCOUNT_SAVE_GENERAL_CONFIGURATION,MyAccountConfDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MY_ACCOUNT,ProtocoloActionsEnum.MY_ACCOUNT_SAVE_LOCK,LockDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MY_ACCOUNT,ProtocoloActionsEnum.MY_ACCOUNT_SAVE_LOGIN_SKIP,Boolean.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MY_ACCOUNT,ProtocoloActionsEnum.MY_ACCOUNT_SAVE_NICKNAME,MyAccountNicknameRequestDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.MY_ACCOUNT,ProtocoloActionsEnum.MY_ACCOUNT_SAVE_PASSWORD,LoginRequestDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE,ProtocoloComponentsEnum.REQUEST_ID,ProtocoloActionsEnum.REQUEST_ID_PRIVATE_GET,RequestIdDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_FREE,ProtocoloComponentsEnum.SERVER_CONF_UNSECURE,ProtocoloActionsEnum.SERVER_CONF_UNSECURE_GET_TIME);
		buildItem(Urls.CONSTANT_URL_PATH_FREE,ProtocoloComponentsEnum.PRIVACITY_RSA,ProtocoloActionsEnum.PRIVACITY_RSA_GET_PUBLIC_KEY);
		buildItem(Urls.CONSTANT_URL_PATH_FREE,ProtocoloComponentsEnum.SERVER_CONF_UNSECURE,ProtocoloActionsEnum.SERVER_CONF_UNSECURE_GET_GRAL_CONF);
		buildItem(Urls.CONSTANT_URL_PATH_PUBLIC,ProtocoloComponentsEnum.AUTH,ProtocoloActionsEnum.AUTH_LOGIN, LoginRequestDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PUBLIC,ProtocoloComponentsEnum.AUTH,ProtocoloActionsEnum.AUTH_REGISTER, RegisterUserRequestDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PUBLIC,ProtocoloComponentsEnum.AUTH,ProtocoloActionsEnum.AUTH_VALIDATE_USERNAME, ValidateUsernameDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PUBLIC,ProtocoloComponentsEnum.REQUEST_ID,ProtocoloActionsEnum.REQUEST_ID_PUBLIC_GET, RequestIdDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE_SEND,ProtocoloComponentsEnum.MESSAGE,ProtocoloActionsEnum.MESSAGE_SEND, MessageDTO.class);
		
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE_WS,ProtocoloComponentsEnum.MESSAGE,ProtocoloActionsEnum.MESSAGE_DELETE_FOR_EVERYONE,IdMessageDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE_WS,ProtocoloComponentsEnum.MESSAGE,ProtocoloActionsEnum.MESSAGE_CHANGE_STATE,MessageDetailDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE_WS,ProtocoloComponentsEnum.MESSAGE,ProtocoloActionsEnum.MESSAGE_SEND, MessageDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE_WS,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_SAVE_GENERAL_CONFIGURATION,GrupoGralConfDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE_WS,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_SAVE_GRAL_CONF_PASSWORD,GrupoDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE_WS,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_STOP_WRITTING,WrittingDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE_WS,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_WRITTING,WrittingDTO.class);	
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE_WS,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_ACCEPT_INVITATION,GrupoInvitationAcceptRequestDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE_WS,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_HOW_MANY_MEMBERS_ONLINE,GrupoDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE_WS,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_SAVE_GENERAL_CONFIGURATION_LOCK,SaveGrupoGralConfLockResponseDTO.class);
		
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE_WS,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_INVITATION_RECIVED,GrupoDTO.class);
		buildItem(Urls.CONSTANT_URL_PATH_PRIVATE_WS,ProtocoloComponentsEnum.GRUPO,ProtocoloActionsEnum.GRUPO_BLOCK_REMOTO,GrupoDTO.class);

		
		//log.finest(this.toString());
		
		



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
