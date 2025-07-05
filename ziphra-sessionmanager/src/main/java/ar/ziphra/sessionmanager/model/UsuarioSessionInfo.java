package ar.ziphra.sessionmanager.model;
import java.security.PublicKey;

import ar.ziphra.common.dto.AESAllDTO;
import ar.ziphra.commonback.common.utils.AESToUse;
import ar.ziphra.commonback.common.utils.ZiphraIdEncoder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(fluent = false, chain = true)
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
	
	private ZiphraIdEncoder ziphraIdEncoder;
	
	private RequestIdPrivate requestId=new RequestIdPrivate();

	public AESAllDTO getAESAllDTO() {
		AESAllDTO r = new AESAllDTO();
		r.setSessionAESDTOServerIn(this.getSessionAESServerIn().getAESDTO());
		r.setSessionAESDTOWS(this.getSessionAESWS().getAESDTO());
		return r.setSessionAESDTOServerOut(this.getSessionAESServerOut().getAESDTO());
		
	}

}
