package com.privacity.server.main;

import java.time.LocalDateTime;

import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.privacity.common.enumeration.ProtocoloComponentsEnum;import com.privacity.common.enumeration.ProtocoloActionsEnum;
import com.privacity.common.dto.ProtocoloDTO;
import com.privacity.common.dto.ProtocoloWrapperDTO;
import com.privacity.common.dto.RequestIdDTO;
import com.privacity.common.dto.request.LoginRequestDTO;
import com.privacity.common.dto.request.RegisterUserRequestDTO;
import com.privacity.common.dto.request.ValidateUsernameDTO;
import com.privacity.server.common.util.AESToUse;
import com.privacity.server.component.auth.AuthValidationService;
import com.privacity.server.component.common.ControllerBase;
import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.util.LocalDateAdapter;


@RestController
@RequestMapping(path = "/public")
public class PublicController extends ControllerBase{

	@Value("${serverconf.privacityIdAESOn}")
	private boolean encryptIds;
	
	@Value("${serverconf.privacityIdAes.bits}")
	private int bitsEncrypt;
	
//	@Value("${privacity.security.encrypt.iteration.count}")
//	private int interationCount;
	
	private FacadeComponent comps;
	
	public PublicController(FacadeComponent comps) throws Exception {
		
		this.comps = comps;
		getMapaController().put(ProtocoloComponentsEnum.AUTH, comps.validation().auth());
		getMapaMetodos().put(ProtocoloActionsEnum.AUTH_LOGIN, comps.validation().auth().getClass().getMethod(AuthValidationService.METHOD_ACTION_AUTH_LOGIN, LoginRequestDTO.class));
		getMapaMetodos().put(ProtocoloActionsEnum.AUTH_REGISTER, comps.validation().auth().getClass().getMethod(AuthValidationService.METHOD_ACTION_AUTH_REGISTER, RegisterUserRequestDTO.class));
		getMapaMetodos().put(ProtocoloActionsEnum.AUTH_VALIDATE_USERNAME, comps.validation().auth().getClass().getMethod(AuthValidationService.METHOD_ACTION_AUTH_VALIDATE_USERNAME, ValidateUsernameDTO.class));

		getMapaController().put(ProtocoloComponentsEnum.REQUEST_ID, comps.validation().requestId());
		getMapaMetodos().put(ProtocoloActionsEnum.REQUEST_ID_PUBLIC_GET, comps.validation().requestId().getClass().getMethod("getNewRequestIdPublic", RequestIdDTO.class));		

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
		
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
                .create();
        
        ProtocoloDTO retornoJson = gson.fromJson(protocoloJson, ProtocoloDTO.class);
		
		ProtocoloDTO retornoBase = super.in(retornoJson);
		System.out.println( " Salida >>  " + retornoBase);
		String retorno = gson.toJson(aes.getAES(gson.toJson(retornoBase)));

		return ResponseEntity.ok().body(retorno);
	}


	
	@Override
	public boolean getEncryptIds() {
		return encryptIds;
	}

	@Override
	public boolean isSecure() {
		return false;
	}
	@Override
	public boolean isRequestId() {
		return true;
	}
	

}
