package com.privacity.common.config;

public class ConstantProtocolo {

	public final static String PROTOCOLO_COMPONENT_REQUEST_ID = "/requestId";
	public final static String PROTOCOLO_ACTION_REQUEST_ID_PUBLIC_GET = "/requestId/getNewRequestIdPublic";
	public final static String PROTOCOLO_ACTION_REQUEST_ID_PRIVATE_GET = "/requestId/getNewRequestIdPrivate";
	
	public final static String PROTOCOLO_COMPONENT_PRIVACITY_RSA = "/privacityRSA";
	public final static String PROTOCOLO_ACTION_PRIVACITY_RSA_GET_PUBLIC_KEY = "/privacityRSA/getPublicKey";
	
	public final static String PROTOCOLO_COMPONENT_SERVER_CONF_UNSECURE= "/serverConf";
	public final static String PROTOCOLO_ACTION_SERVER_CONF_UNSECURE_GET_TIME = "/serverConf/getTime";
	public final static String PROTOCOLO_ACTION_SERVER_CONF_UNSECURE_GET_GRAL_CONF = "/serverConf/getGralConf";
	
	public final static String PROTOCOLO_COMPONENT_ENCRYPT_KEYS = "/encryptkeys";
	public final static String PROTOCOLO_ACTION_ENCRYPT_KEYS_GET = "/encryptkeys/get";
	public final static String PROTOCOLO_ACTION_ENCRYPT_KEYS_CREATE = "/encryptkeys/create";
	
	public final static String PROTOCOLO_COMPONENT_AUTH = "/auth";
	public final static String PROTOCOLO_ACTION_AUTH_LOGIN = "/auth/login";
	public final static String PROTOCOLO_ACTION_AUTH_REGISTER = "/auth/register";
	public final static String PROTOCOLO_ACTION_AUTH_VALIDATE_USERNAME = "/auth/validateUsername";
	
	public final static String PROTOCOLO_COMPONENT_MESSAGE = "/message"; 
	public final static String PROTOCOLO_ACTION_MESSAGE_RECIVIED = "/message/recivied";
	public final static String PROTOCOLO_ACTION_MESSAGE_CHANGE_STATE = "/message/changeState";
	public final static String PROTOCOLO_ACTION_MESSAGE_GET_ALL_ID_MESSAGE_UNREAD = "/message/getAllidMessageUnreadMessages";
	public static final String PROTOCOLO_ACTION_MESSAGE_GET_MESSAGE = "/message/get";

	public final static String PROTOCOLO_COMPONENT_MY_ACCOUNT = "/myAccount";
	public static final String PROTOCOLO_ACTION_MY_ACCOUNT_SAVE_GENERAL_CONFIGURATION = "/myAccount/save/generalConfiguration";
	public static final String PROTOCOLO_ACTION_MY_ACCOUNT_SAVE_PASSWORD = "/myAccount/save/password";
	public static final String PROTOCOLO_ACTION_MY_ACCOUNT_SAVE_NICKNAME = "/myAccount/save/nickname";
	public static final String PROTOCOLO_ACTION_MY_ACCOUNT_SAVE_LOCK = "/myAccount/save/lock";
	public static final String PROTOCOLO_ACTION_MY_ACCOUNT_SAVE_LOGIN_SKIP = "/myAccount/save/loginSkip";
	
	
	public final static String PROTOCOLO_COMPONENT_GRUPO = "/grupo";
	public final static String PROTOCOLO_ACTION_GRUPO_SAVE_GRAL_CONF_PASSWORD = "/grupo/save/gralConfPassword";
	public final static String PROTOCOLO_ACTION_GRUPO_STOP_WRITTING = "/grupo/writting/stop";
	public final static String PROTOCOLO_ACTION_GRUPO_WRITTING = "/grupo/writting/start";
	public final static String PROTOCOLO_ACTION_GRUPO_BLOCK_REMOTO = "/grupo/blockGrupoRemoto";
	 
	public static final String PROTOCOLO_ACTION_GRUPO_SAVE_NICKNAME = "/grupo/save/nickname";
	public final static String PROTOCOLO_ACTION_GRUPO_HOW_MANY_MEMBERS_ONLINE = "/grupo/online/count";
	
	
	public final static String PROTOCOLO_ACTION_GRUPO_GET_IDS_MY_GRUPOS = "/grupo/get/idsMisGrupos";
	public final static String PROTOCOLO_ACTION_GRUPO_GET_GRUPO_BY_ID = "/grupo/get/byId";
	public final static String PROTOCOLO_ACTION_GRUPO_GET_GRUPO_BY_IDS = "/grupo/get/byIds";

	public final static String PROTOCOLO_ACTION_GRUPO_UPDATE_GRUPO = "/grupo/update";
	
	/* olds */	
	public final static String PROTOCOLO_ACTION_GRUPO_NEW_GRUPO = "/grupo/newGrupo";
	public static final String PROTOCOLO_ACTION_GRUPO_REMOVE_USER = "/grupo/remove/user";
	public static final String PROTOCOLO_ACTION_GRUPO_ADDUSER_ADDME = "/grupo/addUser/addMe";
	public static final String PROTOCOLO_ACTION_GRUPO_INVITATION_RECIVED = "/grupo/invitation/recived";
	public static final String PROTOCOLO_ACTION_GRUPO_SAVE_GENERAL_CONFIGURATION = "/grupo/save/generalConfiguration";
	public static final String PROTOCOLO_ACTION_GRUPO_SAVE_GENERAL_CONFIGURATION_LOCK = "/grupo/gralConf/save/lock";
	
	
	public final static String PROTOCOLO_ACTION_GRUPO_LIST_MY_GRUPOS = "/grupo/listar/misGrupos";
///	

	public final static int MESSAGE_SEND_TIME_VALUE = 30000;

}
