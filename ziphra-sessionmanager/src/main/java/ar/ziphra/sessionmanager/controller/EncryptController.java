package ar.ziphra.sessionmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.ziphra.common.exceptions.ZiphraException;
import ar.ziphra.commonback.common.utils.AESToUse;
import ar.ziphra.commonback.constants.SessionManagerRestConstants;
import ar.ziphra.sessionmanager.services.UtilFacade;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = SessionManagerRestConstants.ENCRYPT)
@Slf4j
public class EncryptController {

	@Autowired
	@Lazy
	private UtilFacade uf;

	@PostMapping(SessionManagerRestConstants.ENCRYPT_IDS)
	public Object encryptZiphraIdEncoder(@RequestParam String username, @RequestParam String obj, @RequestParam String className) throws Throwable, Exception {
		log.debug("entrada - encryptZiphraIdEncoder");
		log.debug("username: " + username);
		log.debug("className: " + className);
		log.debug("obj: " + uf.utilsString().cutString(obj));
		
		if ("void".equals(className)) return null;

		Object o = uf.gson().fromJson(obj, Class.forName(className));
		return  uf.usuarioSessionInfo().get(username).getZiphraIdEncoder().encryptIds(o);
	}

	@PostMapping(SessionManagerRestConstants.ENCRYPT_WS)
	public String encryptSessionAESServerWS(@RequestParam String username, @RequestParam String obj) throws Throwable, Exception {
		return encrypt("encryptSessionAESServerWS", uf.usuarioSessionInfo().get(username).getSessionAESWS(), username, obj);

	}

	@PostMapping(SessionManagerRestConstants.ENCRYPT_SERVER_OUT)
	public String encryptSessionAESServerOut(@RequestParam String username, @RequestParam String obj) throws ZiphraException  {
		return encrypt("encryptSessionAESServerOut", uf.usuarioSessionInfo().get(username).getSessionAESServerOut(), username, obj);
	}

	private String encrypt(String metodo, AESToUse aes, String username, String obj) throws ZiphraException{
		log.debug("entrada - " + metodo);
		log.debug("username" + username);
		log.debug("obj: " + uf.utilsString().cutString(obj));
		
		String objD = aes.getAES(obj);
		return objD;

	}

	
}
