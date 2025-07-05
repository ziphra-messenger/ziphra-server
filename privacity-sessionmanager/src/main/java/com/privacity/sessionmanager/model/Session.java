package com.privacity.sessionmanager.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.privacity.common.dto.AESDTO;

import lombok.NoArgsConstructor;


@Document
@NoArgsConstructor
public class Session {

	@Id
	public String username;

	public String sessionAESDTOServerInSecretKeyAES;
	public String sessionAESDTOServerInSaltAES;
	public String sessionAESDTOServerInIteration;
	public String sessionAESDTOServerInBitsEncrypt;

	public String sessionAESDTOServerWSSecretKeyAES;
	public String sessionAESDTOServerWSSaltAES;
	public String sessionAESDTOServerWSIteration;
	public String sessionAESDTOServerWSBitsEncrypt;

	public String sessionAESDTOServerOutSecretKeyAES;
	public String sessionAESDTOServerOutSaltAES;
	public String sessionAESDTOServerOutIteration;	
	public String sessionAESDTOServerOutBitsEncrypt;

	public String privacityIdSecretKeyAES;
	public String privacityIdSaltAES;
	public String privacityIdIteration;	
	public String privacityIdBitsEncrypt;

	public String privacityIdOrderSeed;
	public String orderRamdomNumber;
	public String base;
	public String mutateDigitPorLetra;
	public String mutateDigitPorNro;	
	
	public Session(String username, AESDTO in, AESDTO out, AESDTO ws, AESDTO id, 
			String privacityIdOrderSeed,
			String orderRamdomNumber,
			String base,
			String mutateDigitPorLetra,
			String mutateDigitPorNro			
			) {
		this.username=username;
		sessionAESDTOServerInSecretKeyAES = in.getSecretKeyAES();
		sessionAESDTOServerInSaltAES = in.getSaltAES();
		sessionAESDTOServerInIteration = in.getIteration(); 
		sessionAESDTOServerInBitsEncrypt = in.getBitsEncrypt();

		sessionAESDTOServerWSSecretKeyAES = ws.getSecretKeyAES();
		sessionAESDTOServerWSSaltAES = ws.getSaltAES();
		sessionAESDTOServerWSIteration = ws.getIteration(); 
		sessionAESDTOServerWSBitsEncrypt = ws.getBitsEncrypt();

		sessionAESDTOServerOutSecretKeyAES = out.getSecretKeyAES();
		sessionAESDTOServerOutSaltAES = out.getSaltAES();
		sessionAESDTOServerOutIteration = out.getIteration(); 
		sessionAESDTOServerOutBitsEncrypt = out.getBitsEncrypt();

		privacityIdSecretKeyAES = id.getSecretKeyAES();
		privacityIdSaltAES = id.getSaltAES();
		privacityIdIteration = id.getIteration(); 
		privacityIdBitsEncrypt = id.getBitsEncrypt();
		
		this.privacityIdOrderSeed = privacityIdOrderSeed;
		this.orderRamdomNumber = orderRamdomNumber;
		this.base = base;
		this.mutateDigitPorLetra = mutateDigitPorLetra;
		this.mutateDigitPorNro = mutateDigitPorNro;

	}
	
	public AESDTO getAESDTOWS() {
		return (new AESDTO()) 
		.setBitsEncrypt(sessionAESDTOServerWSBitsEncrypt)
		.setIteration(sessionAESDTOServerWSIteration)
		.setSecretKeyAES(sessionAESDTOServerWSSecretKeyAES)
		.setSaltAES(sessionAESDTOServerWSSaltAES);
	}

	public AESDTO getAESDTOServerIn() {
		
		return (new AESDTO()) 
		.setBitsEncrypt(sessionAESDTOServerInBitsEncrypt)
		.setIteration(sessionAESDTOServerInIteration)
		.setSecretKeyAES(sessionAESDTOServerInSecretKeyAES)
		.setSaltAES(sessionAESDTOServerInSaltAES);
		
	}
	public AESDTO getAESDTOServerOut() {
		
		return (new AESDTO()) 
		.setBitsEncrypt(sessionAESDTOServerOutBitsEncrypt)
		.setIteration(sessionAESDTOServerOutIteration)
		.setSecretKeyAES(sessionAESDTOServerOutSecretKeyAES)
		.setSaltAES(sessionAESDTOServerOutSaltAES);

	}
	
	public AESDTO getAESDTOPrivacityId() {
		
		return (new AESDTO()) 
		.setBitsEncrypt(privacityIdBitsEncrypt)
		.setIteration(privacityIdIteration)
		.setSecretKeyAES(privacityIdSecretKeyAES)
		.setSaltAES(privacityIdSaltAES);

	}
}
