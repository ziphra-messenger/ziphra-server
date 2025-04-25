package com.privacity.sessionmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.privacity.common.exceptions.PrivacityException;
import com.privacity.commonback.common.utils.AESToUse;
import com.privacity.commonback.constants.SessionManagerRestConstants;
import com.privacity.sessionmanager.services.UtilFacade;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = SessionManagerRestConstants.ENCRYPT)
@Slf4j
public class EncryptController {

	@Autowired
	@Lazy
	private UtilFacade uf;

	@PostMapping(SessionManagerRestConstants.ENCRYPT_IDS)
	public Object encryptPrivacityIdEncoder(@RequestParam String username, @RequestParam String obj, @RequestParam String className) throws Throwable, Exception {
		log.debug("entrada - encryptPrivacityIdEncoder");
		log.debug("username: " + username);
		log.debug("className: " + className);
		log.debug("obj: " + uf.utilsString().cutString(obj));
		
		if ("void".equals(className)) return null;

		Object o = uf.gson().fromJson(obj, Class.forName(className));
		return  uf.usuarioSessionInfo().get(username).getPrivacityIdEncoder().encryptIds(o);
	}

	@PostMapping(SessionManagerRestConstants.ENCRYPT_WS)
	public String encryptSessionAESServerWS(@RequestParam String username, @RequestParam String obj) throws Throwable, Exception {
		return encrypt("encryptSessionAESServerWS", uf.usuarioSessionInfo().get(username).getSessionAESWS(), username, obj);

	}

	@PostMapping(SessionManagerRestConstants.ENCRYPT_SERVER_OUT)
	public String encryptSessionAESServerOut(@RequestParam String username, @RequestParam String obj) throws PrivacityException  {
		return encrypt("encryptSessionAESServerOut", uf.usuarioSessionInfo().get(username).getSessionAESServerOut(), username, obj);
	}

	private String encrypt(String metodo, AESToUse aes, String username, String obj) throws PrivacityException{
		log.debug("entrada - " + metodo);
		log.debug("username" + username);
		log.debug("obj: " + uf.utilsString().cutString(obj));
		
		String objD = aes.getAES(obj);
		return objD;

	}

	
}
