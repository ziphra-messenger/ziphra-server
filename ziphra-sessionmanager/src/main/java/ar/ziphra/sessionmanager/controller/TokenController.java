package ar.ziphra.sessionmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ziphra.common.dto.JWTDTO;
import ar.ziphra.commonback.constants.SessionManagerRestConstants;
import ar.ziphra.sessionmanager.services.UtilFacade;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = SessionManagerRestConstants.TOKEN)
@Slf4j
public class TokenController {

	@Autowired
	@Lazy
	private UtilFacade uf;
	
	@PostMapping(SessionManagerRestConstants.TOKEN_GET)
	public JWTDTO getSecretJWT() throws Throwable, Exception {
		log.debug("getSecretJWT");
		JWTDTO rO = uf.JWT().get();
		log.debug("JWTDTO: " + rO.toString());
		String rJ = uf.gson().toJson(rO);
		log.trace("JSON Salida: " + rJ);
		return rO; 

	}

}
