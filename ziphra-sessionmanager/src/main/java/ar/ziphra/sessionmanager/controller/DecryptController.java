package ar.ziphra.sessionmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonSyntaxException;
import ar.ziphra.common.enumeration.ExceptionReturnCode;
import ar.ziphra.common.exceptions.ZiphraException;
import ar.ziphra.commonback.constants.SessionManagerRestConstants;
import ar.ziphra.sessionmanager.services.UtilFacade;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = SessionManagerRestConstants.DECRYPT)
@Slf4j
public class DecryptController {

	@Autowired
	@Lazy
	private UtilFacade uf;

	@PostMapping(SessionManagerRestConstants.DECRYPT_SERVER_IN)
	public String decryptSessionAESServerIn(@RequestParam String username, @RequestParam String obj, @RequestParam String className) throws ZiphraException, Exception  {
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
	public Object decryptZiphraIdEncoder(@RequestParam String username, @RequestParam String obj, @RequestParam String className) throws Exception  {
		log.debug("entrada - decryptZiphraIdEncoder");
		log.debug("username" + username);
		log.debug("className: " + className);
		log.debug("obj: " + uf.utilsString().cutString(obj));

		Object o;
		try {

			ObjectMapper mapper = new ObjectMapper();
			try {
				o = mapper.readValue(obj, Class.forName(className));
			} catch (JsonMappingException e) {
				e.printStackTrace();
				log.error(ExceptionReturnCode.DECRYPT_PROCESS.toShow(e));
				throw new ZiphraException(ExceptionReturnCode.DECRYPT_PROCESS);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				log.error(ExceptionReturnCode.DECRYPT_PROCESS.toShow(e));
				throw new ZiphraException(ExceptionReturnCode.DECRYPT_PROCESS);
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			throw new ZiphraException(ExceptionReturnCode.GENERAL_INVALID_SENT_DATA, e);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new ZiphraException(ExceptionReturnCode.GENERAL_INVALID_SENT_DATA, e);
		}
				
		Object r = uf.usuarioSessionInfo().get(username).getZiphraIdEncoder().decryptIds(o);
		log.trace("salida: " + uf.utilsString().cutStringToGson(r));
		return r;		
	}
}
