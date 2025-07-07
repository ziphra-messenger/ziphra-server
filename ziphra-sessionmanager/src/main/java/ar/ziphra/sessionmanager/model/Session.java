package ar.ziphra.sessionmanager.model;

import javax.persistence.Id;

//import org.springframework.data.mongodb.core.mapping.Document;

import ar.ziphra.common.dto.AESDTO;
import lombok.NoArgsConstructor;


//@Document
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

	public String ziphraIdSecretKeyAES;
	public String ziphraIdSaltAES;
	public String ziphraIdIteration;	
	public String ziphraIdBitsEncrypt;

	public String ziphraIdOrderSeed;
	public String orderRamdomNumber;
	public String base;
	public String mutateDigitPorLetra;
	public String mutateDigitPorNro;	
	
	public Session(String username, AESDTO in, AESDTO out, AESDTO ws, AESDTO id, 
			String ziphraIdOrderSeed,
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

		ziphraIdSecretKeyAES = id.getSecretKeyAES();
		ziphraIdSaltAES = id.getSaltAES();
		ziphraIdIteration = id.getIteration(); 
		ziphraIdBitsEncrypt = id.getBitsEncrypt();
		
		this.ziphraIdOrderSeed = ziphraIdOrderSeed;
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
	
	public AESDTO getAESDTOZiphraId() {
		
		return (new AESDTO()) 
		.setBitsEncrypt(ziphraIdBitsEncrypt)
		.setIteration(ziphraIdIteration)
		.setSecretKeyAES(ziphraIdSecretKeyAES)
		.setSaltAES(ziphraIdSaltAES);

	}
}
