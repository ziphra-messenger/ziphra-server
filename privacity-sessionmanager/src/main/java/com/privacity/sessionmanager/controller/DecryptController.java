package com.privacity.sessionmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonSyntaxException;
import com.privacity.common.enumeration.ExceptionReturnCode;
import com.privacity.common.exceptions.PrivacityException;
import com.privacity.commonback.constants.SessionManagerRestConstants;
import com.privacity.sessionmanager.services.UtilFacade;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = SessionManagerRestConstants.DECRYPT)
@Slf4j
public class DecryptController {

	@Autowired
	@Lazy
	private UtilFacade uf;

	@PostMapping(SessionManagerRestConstants.DECRYPT_SERVER_IN)
	public String decryptSessionAESServerIn(@RequestParam String username, @RequestParam String obj, @RequestParam String className) throws PrivacityException, Exception  {
		log.debug("entrada - decryptSessionAESServerIn");
		log.debug("username" + username);
		log.debug("className: " + className);
		log.debug("obj: " + uf.utilsString().cutString(obj));
		
		String r = uf.usuarioSessionInfo().get(username).getSessionAESServerIn().getAESDecrypt(obj);
		log.trace(r);
		
		log.trace("salida: " + uf.utilsString().cutString(r));
		return r;		
	}
	
	@PostMapping(SessionManagerRestConstants.DECRYPT_IDS)
	public Object decryptPrivacityIdEncoder(@RequestParam String username, @RequestParam String obj, @RequestParam String className) throws PrivacityException  {
		log.debug("entrada - decryptPrivacityIdEncoder");
		log.debug("username" + username);
		log.debug("className: " + className);
		log.debug("obj: " + uf.utilsString().cutString(obj));

		Object o;
		try {
			o = uf.gson().fromJson(obj, Class.forName(className));
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw new PrivacityException(ExceptionReturnCode.GENERAL_INVALID_SENT_DATA, e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new PrivacityException(ExceptionReturnCode.GENERAL_INVALID_SENT_DATA, e);
		}
				
		Object r = uf.usuarioSessionInfo().get(username).getPrivacityIdEncoder().decryptIds(o);
		log.trace("salida: " + uf.utilsString().cutStringToGson(r));
		return r;		
	}
}
