package com.privacity.server.sessioninfo.controller;

import java.security.PublicKey;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.privacity.common.dto.AESDTO;
import com.privacity.common.dto.MyAccountConfDTO;
import com.privacity.common.dto.RequestIdDTO;


import lombok.Data;

@Data
public class UsuarioSessionInfo {
	


	
	
	private String sessionId="113123";
	
//	private AESDTO sessionAESDTOToSend;
//	private EncryptKeys encryptKeys;
	
//	private RSA rsa;
	private PublicKey publicKey;
//	private String publicKeyToSend;
//	private String privateKeyToSend;

	
	private AESDTO sessionAES;
	
	private ConcurrentMap<String,RequestIdDTO> requestIds = new ConcurrentHashMap<String,RequestIdDTO>();


}
