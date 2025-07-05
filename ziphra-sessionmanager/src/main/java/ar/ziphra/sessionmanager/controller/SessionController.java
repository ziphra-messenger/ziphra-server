package ar.ziphra.sessionmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ziphra.common.dto.AESAllDTO;
import ar.ziphra.common.dto.AESDTO;
import ar.ziphra.common.exceptions.ZiphraException;
import ar.ziphra.commonback.constants.SessionManagerRestConstants;
import ar.ziphra.sessionmanager.services.UtilFacade;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = SessionManagerRestConstants.SESSION)
@Slf4j
public class SessionController {

	@Autowired
	@Lazy
	private UtilFacade uf;

	@PostMapping(SessionManagerRestConstants.SESSION_REMOVE)
	public void removeSession(String username){
		log.trace("removeSession: " + username);
		uf.usuarioSessionInfo().remove(username);	
	}

	@PostMapping(SessionManagerRestConstants.SESSION_GET_SERVER_IN)
	public AESDTO getAesDtoSessionIn(String username) throws ZiphraException  {
		log.trace("getAesDtoSessionIn -> " + username);
		return uf.usuarioSessionInfo().get(username).getSessionAESServerIn().getAESDTO();
	}

	@PostMapping(SessionManagerRestConstants.SESSION_GET_SERVER_OUT)
	public AESDTO getSessionAESServerOut(String username) throws ZiphraException  {
		log.trace("getSessionAESServerOut -> " + username);
		return uf.usuarioSessionInfo().get(username).getSessionAESServerOut().getAESDTO();

	}

	@PostMapping(SessionManagerRestConstants.SESSION_GET_WS)
	public AESDTO getAesDtoSessionWs(String username) throws ZiphraException {
		log.trace("getAesDtoSessionWs -> " + username);
		return uf.usuarioSessionInfo().get(username).getSessionAESWS().getAESDTO();

	}

	@PostMapping(SessionManagerRestConstants.SESSION_GET_ALL)
	public AESAllDTO getAesDtoAll(String username) throws ZiphraException {
		log.trace("getAesDtoAll -> " + username);
		return uf.usuarioSessionInfo().get(username).getAESAllDTO();

	}
}
