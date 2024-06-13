package com.privacity.server.main;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.privacity.common.config.ConstantProtocolo;
import com.privacity.common.dto.ProtocoloDTO;
import com.privacity.server.component.common.ControllerBase;
import com.privacity.server.component.encryptkeys.PrivacityRSAValidation;
import com.privacity.server.component.serverconf.ServerConfValidationService;
import com.privacity.server.encrypt.PrivacityIdServices;


@RestController
@RequestMapping(path = "/free")
public class FreeController extends ControllerBase{
	
	@SuppressWarnings("unused")
	private ServerConfValidationService serverConfValidationService;

	@SuppressWarnings("unused")
	private PrivacityRSAValidation privacityRSAValidation;
	
	public FreeController(
			ServerConfValidationService serverConfValidationService,
			PrivacityRSAValidation privacityRSAValidation) throws Exception {
		
		this.serverConfValidationService = serverConfValidationService;
		this.privacityRSAValidation = privacityRSAValidation;

		getMapaController().put(ConstantProtocolo.PROTOCOLO_COMPONENT_SERVER_CONF_UNSECURE, serverConfValidationService);
		getMapaMetodos().put(ConstantProtocolo.PROTOCOLO_ACTION_SERVER_CONF_UNSECURE_GET_TIME, serverConfValidationService.getClass().getMethod(
				"getTime"));	
		
		getMapaController().put(ConstantProtocolo.PROTOCOLO_COMPONENT_PRIVACITY_RSA, privacityRSAValidation);
		getMapaMetodos().put(ConstantProtocolo.PROTOCOLO_ACTION_PRIVACITY_RSA_GET_PUBLIC_KEY, privacityRSAValidation.getClass().getMethod(
				"getPublicKeyToSend"));	

		getMapaController().put(ConstantProtocolo.PROTOCOLO_COMPONENT_SERVER_CONF_UNSECURE, serverConfValidationService);
		getMapaMetodos().put(ConstantProtocolo.PROTOCOLO_ACTION_SERVER_CONF_UNSECURE_GET_GRAL_CONF, serverConfValidationService.getClass().getMethod(
				"getSystemGralConf"));
		
		
	
	}

	@PostMapping("/entry")
	public ProtocoloDTO in(@RequestBody ProtocoloDTO request) throws Exception {
		
		return super.in(request);

	}

	@Override
	public PrivacityIdServices getPrivacityIdServices() {
		return null;
	}

	
	@Override
	public boolean getEncryptIds() {
		return false;
	}

	@Override
	public boolean isSecure() {
		return false;
	}

	@Override
	public boolean isRequestId() {
		return false;
	}
	@Override
	public boolean showLog(ProtocoloDTO request) {
		return false;
	}	
}
