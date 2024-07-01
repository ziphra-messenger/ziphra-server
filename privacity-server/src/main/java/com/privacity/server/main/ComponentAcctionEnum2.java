package com.privacity.server.main;

public enum ComponentAcctionEnum2 {

	PROTOCOLO_COMPONENT_REQUEST_ID("/requestId"),
	PROTOCOLO_ACTION_REQUEST_ID_PUBLIC_GET("/requestId/getNewRequestIdPublic"),
	PROTOCOLO_ACTION_REQUEST_ID_PRIVATE_GET("/requestId/getNewRequestIdPrivate"),

	PROTOCOLO_COMPONENT_PRIVACITY_RSA("/privacityRSA"),
	PROTOCOLO_ACTION_PRIVACITY_RSA_GET_PUBLIC_KEY("/privacityRSA/getPublicKey"),

	PROTOCOLO_COMPONENT_SERVER_CONF_UNSECURE("/serverConf"),
	PROTOCOLO_ACTION_SERVER_CONF_UNSECURE_GET_TIME("/serverConf/getTime"),
	PROTOCOLO_ACTION_SERVER_CONF_UNSECURE_GET_GRAL_CONF("/serverConf/getGralConf"),

	PROTOCOLO_COMPONENT_ENCRYPT_KEYS("/encryptkeys"),
	PROTOCOLO_ACTION_ENCRYPT_KEYS_GET("/encryptkeys/get"),
	PROTOCOLO_ACTION_ENCRYPT_KEYS_CREATE("/encryptkeys/create"),

	PROTOCOLO_COMPONENT_AUTH("/auth"),
	PROTOCOLO_ACTION_AUTH_LOGIN("/auth/login"),
	PROTOCOLO_ACTION_AUTH_REGISTER("/auth/register"),
	PROTOCOLO_ACTION_AUTH_VALIDATE_USERNAME("/auth/validateUsername"),

	PROTOCOLO_COMPONENT_MESSAGE("/message"),
	PROTOCOLO_ACTION_MESSAGE_RECIVIED("/message/recivied"),
	PROTOCOLO_ACTION_MESSAGE_CHANGE_STATE("/message/changeState"),
	PROTOCOLO_ACTION_MESSAGE_GET_ALL_ID_MESSAGE_UNREAD("/message/getAllidMessageUnreadMessages"),
	PROTOCOLO_ACTION_MESSAGE_GET_MESSAGE("/message/get"),

	PROTOCOLO_COMPONENT_MY_ACCOUNT("/myAccount"),
	PROTOCOLO_ACTION_MY_ACCOUNT_SAVE_GENERAL_CONFIGURATION("/myAccount/save/generalConfiguration"),
	PROTOCOLO_ACTION_MY_ACCOUNT_SAVE_PASSWORD("/myAccount/save/password"),
	PROTOCOLO_ACTION_MY_ACCOUNT_SAVE_NICKNAME("/myAccount/save/nickname"),
	PROTOCOLO_ACTION_MY_ACCOUNT_SAVE_LOCK("/myAccount/save/lock"),
	PROTOCOLO_ACTION_MY_ACCOUNT_SAVE_LOGIN_SKIP("/myAccount/save/loginSkip"),
	PROTOCOLO_ACTION_MY_ACCOUNT_CLOSE_SESSION("/myAccount/closeSession"),

	PROTOCOLO_COMPONENT_GRUPO("/grupo"),
	PROTOCOLO_ACTION_GRUPO_SAVE_GRAL_CONF_PASSWORD("/grupo/save/gralConfPassword"),
	PROTOCOLO_ACTION_GRUPO_STOP_WRITTING("/grupo/writting/stop"),
	PROTOCOLO_ACTION_GRUPO_WRITTING("/grupo/writting/start"),
	PROTOCOLO_ACTION_GRUPO_BLOCK_REMOTO("/grupo/blockGrupoRemoto"),

	PROTOCOLO_ACTION_GRUPO_SAVE_NICKNAME("/grupo/save/nickname"),
	PROTOCOLO_ACTION_GRUPO_HOW_MANY_MEMBERS_ONLINE("/grupo/online/count"),


	PROTOCOLO_ACTION_GRUPO_GET_IDS_MY_GRUPOS("/grupo/get/idsMisGrupos"),
	PROTOCOLO_ACTION_GRUPO_GET_GRUPO_BY_ID("/grupo/get/byId"),
	PROTOCOLO_ACTION_GRUPO_GET_GRUPO_BY_IDS("/grupo/get/byIds"),

	PROTOCOLO_ACTION_GRUPO_UPDATE_GRUPO("/grupo/update"),
	PROTOCOLO_ACTION_GRUPO_NEW_GRUPO("/grupo/newGrupo"),
	PROTOCOLO_ACTION_GRUPO_REMOVE_USER("/grupo/remove/user"),
	PROTOCOLO_ACTION_GRUPO_ADDUSER_ADDME("/grupo/addUser/addMe"),
	PROTOCOLO_ACTION_GRUPO_INVITATION_RECIVED("/grupo/invitation/recived"),
	PROTOCOLO_ACTION_GRUPO_SAVE_GENERAL_CONFIGURATION("/grupo/save/generalConfiguration"),
	PROTOCOLO_ACTION_GRUPO_SAVE_GENERAL_CONFIGURATION_LOCK("/grupo/gralConf/save/lock"),
	PROTOCOLO_ACTION_GRUPO_LIST_MY_GRUPOS("/grupo/listar/misGrupos");



	private final String name;       

	private ComponentAcctionEnum2(String s) {
		name = s;
	}

	public boolean equalsName(String otherName) {
		// (otherName == null) check is not needed because name.equals(null) returns false 
		return name.equals(otherName);
	}

	public String toString() {
		return this.name;
	}


}
