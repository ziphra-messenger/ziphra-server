package com.privacity.server.main;

import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.privacity.common.dto.ProtocoloDTO;
import com.privacity.common.dto.ProtocoloWrapperDTO;
import com.privacity.commonback.common.enumeration.Urls;
import com.privacity.server.component.common.ControllerBase;
import com.privacity.server.component.common.service.facade.FacadeComponent;

import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping(path = "/public")
@Slf4j
public class PublicController extends ControllerBase{

	
	@Value("${serverconf.privacityIdAes.bits}")
	private int bitsEncrypt;
	
	private FacadeComponent comps;
	
	public PublicController(FacadeComponent comps) throws Exception {
		this.comps = comps;
	}
	
	@PostMapping("/entry")
	public ResponseEntity<String> in(@RequestBody ProtocoloWrapperDTO protocoloWrapperDTO) throws Exception {

		//comps.common().privacityRSA().desencrypt(Base64.decode(protocoloWrapperDTO.getAesEncripted().getSaltAES())protocoloWrapperDTO.getAesEncripted().getSaltAES()));
		//comps.common().privacityRSA().desencrypt(Base64.decode(protocoloWrapperDTO.getAesEncripted().getSaltAES());		
		String salt = comps.common().privacityRSA().desencrypt(Base64.decode(protocoloWrapperDTO.getAesEncripted().getSaltAES()));
		String key = comps.common().privacityRSA().desencrypt(Base64.decode(protocoloWrapperDTO.getAesEncripted().getSecretKeyAES()));
		String interationCount = comps.common().privacityRSA().desencrypt(Base64.decode(protocoloWrapperDTO.getAesEncripted().getIteration()));
		
		AESToUse aes = new AESToUse(bitsEncrypt,Integer.parseInt(interationCount) ,key,salt);	
		String protocoloJson = aes.getAESDecrypt(protocoloWrapperDTO.protocoloDTO);
		
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
	public Urls getUrl() {
		return Urls.CONSTANT_URL_PATH_PUBLIC;
	}
}
