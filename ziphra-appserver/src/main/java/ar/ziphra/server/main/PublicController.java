package ar.ziphra.server.main;


import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ziphra.common.dto.AESDTO;
import ar.ziphra.common.dto.ProtocoloDTO;
import ar.ziphra.common.dto.ProtocoloWrapperDTO;
import ar.ziphra.commonback.common.enumeration.ServerUrls;
import ar.ziphra.commonback.common.utils.AESToUse;
import ar.ziphra.server.component.common.ControllerBase;
import ar.ziphra.server.component.common.service.facade.FacadeComponent;

import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping(path = "/public")
@Slf4j
public class PublicController extends ControllerBase{

	
	@Value("${serverconf.ziphraIdAes.bits}")
	private int bitsEncrypt;
	
	private FacadeComponent comps;
	
	public PublicController(FacadeComponent comps) throws Exception {
		this.comps = comps;
	}
	
	@PostMapping("/entry")
	public ResponseEntity<String> in(@RequestBody ProtocoloWrapperDTO protocoloWrapperDTO) throws Exception {

		//comps.common().ziphraRSA().desencrypt(Base64.decode(protocoloWrapperDTO.getAesEncripted().getSaltAES())protocoloWrapperDTO.getAesEncripted().getSaltAES()));
		//comps.common().ziphraRSA().desencrypt(Base64.decode(protocoloWrapperDTO.getAesEncripted().getSaltAES());		
		String salt = comps.common().ziphraRSA().desencrypt(Base64.decode(protocoloWrapperDTO.getAesEncripted().getSaltAES()));
		String key = comps.common().ziphraRSA().desencrypt(Base64.decode(protocoloWrapperDTO.getAesEncripted().getSecretKeyAES()));
		String interationCount = comps.common().ziphraRSA().desencrypt(Base64.decode(protocoloWrapperDTO.getAesEncripted().getIteration()));
		
		AESToUse aes = new AESToUse((new AESDTO()) 
		.setBitsEncrypt(bitsEncrypt+"")
		.setIteration(interationCount)
		.setSecretKeyAES(key)
		.setSaltAES(salt)); 
				

		String protocoloJson = aes.getAESDecrypt(protocoloWrapperDTO.getProtocoloDTO());
		
        ProtocoloDTO retornoJson = comps.util().gson().fromJson(protocoloJson, ProtocoloDTO.class);
		
		ProtocoloDTO retornoBase = super.in(retornoJson);

		String retorno = comps.util().string().gsonToSendCompress(aes.getAES(comps.util().string().gsonToSend(retornoBase)));
		log.debug ( " Salida >>  " + comps.util().string().cutString(retorno));
		return ResponseEntity.ok().body(retorno);
	}

	@Override
	public boolean isSecure() {
		return false;
	}
	@Override
	public boolean isRequestId() {
		return true;
	}
	
	@Override
	public ServerUrls getUrl() {
		return ServerUrls.CONSTANT_URL_PATH_PUBLIC;
	}
}
