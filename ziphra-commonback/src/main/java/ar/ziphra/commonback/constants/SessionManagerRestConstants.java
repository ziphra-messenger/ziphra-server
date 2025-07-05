package ar.ziphra.commonback.constants;

public class SessionManagerRestConstants {

	public static final String ENCRYPT="/user/encryption";
	public static final String ENCRYPT_SERVER_OUT="/serverout";
	public static final String ENCRYPT_WS="/ws";
	public static final String ENCRYPT_IDS="/ids";
	
	public static final String DECRYPT="/user/decryption";
	public static final String DECRYPT_SERVER_IN="/serverin";
	public static final String DECRYPT_IDS="/ids";
	
	public static final String PROTOCOLO_DECRYPT="/protocolo/decrypt";
	public static final String PROTOCOLO_DECRYPT_SERVER_IN="/serverin";
	
	public static final String PROTOCOLO_ENCRYPT="/protocolo/encrypt";
	public static final String PROTOCOLO_ENCRYPT_SERVER_OUT="/serverout";
	public static final String PROTOCOLO_ENCRYPT_WS="/ws/user";
	public static final String PROTOCOLO_ENCRYPT_WS_USERLIST="/ws/userslist";

	public static final String TOKEN="/jwt";
	public static final String TOKEN_GET = "/get";
	
	public static final String SESSION="/session";
	public static final String SESSION_REMOVE="/remove";
	public static final String SESSION_GET_SERVER_IN="/get/serverin";
	public static final String SESSION_GET_SERVER_OUT="/get/serverout";
	public static final String SESSION_GET_WS="/get/ws";
	public static final String SESSION_GET_ALL="/get/all";
	
	public static final String REQUEST_ID="/requestid";
	public static final String REQUEST_ID_PUBLIC_GET="/public/get";
	public static final String REQUEST_ID_PUBLIC_ADD="/public/add";
	public static final String REQUEST_ID_PUBLIC_REMOVE="/public/remove";
	public static final String REQUEST_ID_PRIVATE_ADD="/private/add";
	public static final String REQUEST_ID_PRIVATE_GET_ALL="/private/getAll";
}
