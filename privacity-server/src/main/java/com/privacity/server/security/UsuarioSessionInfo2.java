package com.privacity.server.security;

import java.io.Serializable;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

import com.privacity.common.dto.AESDTO;
import com.privacity.common.dto.RequestIdDTO;
import com.privacity.server.common.util.AESToUse;
import com.privacity.server.session.PrivacityIdServices;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@ToString
public class UsuarioSessionInfo2 implements Serializable{
	
	private static final long serialVersionUID = -4076483467987157286L;

	//private Usuario usuarioDB;
	
	private Long usuarioId;
	private String username;
	private String sessionId;
	


	private AESDTO sessionAESDTOToSend;
//	private EncryptKeys encryptKeys;
	
//	private RSA rsa;
	//private PublicKey publicKey;
//	private String publicKeyToSend;
//	private String privateKeyToSend;
	private AESToUse sessionAESToUse;
	private AESDTO sessionAES;
	//private AESToUse sessionAESToUseWS;
//	private AESToUse sessionAESToUseServerEncrypt;
//	
//	private PrivacityIdServices privacityIdServices;
	
	//private Map<String,RequestIdDTO> requestIds = new HashMap<String,RequestIdDTO>();


//	public MyAccountConf getMyAccountConf() {
//		// TODO Auto-generated method stub
//		return usuarioDB.getMyAccountConf();
//	}



}
