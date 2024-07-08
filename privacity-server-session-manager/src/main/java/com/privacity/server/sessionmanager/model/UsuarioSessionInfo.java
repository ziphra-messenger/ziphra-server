package com.privacity.server.sessionmanager.model;
import java.security.PublicKey;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.privacity.common.dto.AESAllDTO;
import com.privacity.common.dto.RequestIdDTO;
import com.privacity.server.sessionmanager.services.PrivacityIdServices;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioSessionInfo {
	
	private String username;
	private String sessionId;
	
//	private AESDTO sessionAESDTOToSend;
//	private EncryptKeys encryptKeys;
	
//	private RSA rsa;
	private PublicKey publicKey;
//	private String publicKeyToSend;
//	private String privateKeyToSend;
	private AESToUse sessionAESServerIn;
	private AESToUse sessionAESWS;
	private AESToUse sessionAESServerOut;
	
	private PrivacityIdServices privacityIdServices;
	
	private ConcurrentMap<String,RequestIdDTO> requestIds = new ConcurrentHashMap<String,RequestIdDTO>();

	public AESAllDTO getAESAllDTO() {
		AESAllDTO r = new AESAllDTO();
		r.setSessionAESDTOServerIn(this.getSessionAESServerIn().getAESDTO());
		r.setSessionAESDTOWS(this.getSessionAESWS().getAESDTO());
		r.setSessionAESDTOServerOut(this.getSessionAESServerOut().getAESDTO());
		return r;
	}

}
