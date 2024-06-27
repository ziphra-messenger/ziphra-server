package com.privacity.server.security;

import java.security.PublicKey;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.privacity.common.dto.AESDTO;
import com.privacity.common.dto.RequestIdDTO;
import com.privacity.server.main.AESToUse;
import com.privacity.server.model.MyAccountConf;

import lombok.Data;

@Data
public class UsuarioSessionInfo {
	

	private Usuario usuarioDB;
	
	
	private String sessionId;
	
//	private AESDTO sessionAESDTOToSend;
//	private EncryptKeys encryptKeys;
	
//	private RSA rsa;
	private PublicKey publicKey;
//	private String publicKeyToSend;
//	private String privateKeyToSend;
	private AESToUse sessionAESToUse;
	private AESDTO sessionAES;
	
	private ConcurrentMap<String,RequestIdDTO> requestIds = new ConcurrentHashMap<String,RequestIdDTO>();

	public MyAccountConf getMyAccountConf() {
		// TODO Auto-generated method stub
		return usuarioDB.getMyAccountConf();
	};

}
