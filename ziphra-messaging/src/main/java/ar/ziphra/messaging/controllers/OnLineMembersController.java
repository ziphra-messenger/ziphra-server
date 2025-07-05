package ar.ziphra.messaging.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.ziphra.common.dto.MembersQuantityDTO;
import ar.ziphra.commonback.constants.MessagingRestConstants;
import ar.ziphra.core.util.UtilsStringComponent;
import ar.ziphra.messaging.listeners.STOMPConnectEventListener;
import ar.ziphra.messaging.services.WebSocketSenderService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = MessagingRestConstants.ONLINE)
@Slf4j
public class OnLineMembersController {

	@Autowired
	@Lazy
	private UtilsStringComponent utilStrings;
	
	@Autowired
	WebSocketSenderService ws;
	@Autowired
	STOMPConnectEventListener connectEventListener;
		
	@PostMapping(MessagingRestConstants.ONLINE_GET_GRUPO_MEMBERSQUANTITY)
	public MembersQuantityDTO getMembersOnlineByGrupo(String idGrupo) throws Exception {
		log.trace("getMembersOnlineByGrupo: " + idGrupo);
		return ws.getMembersOnlineByGrupo(idGrupo);
	}
	
	@PostMapping(MessagingRestConstants.ONLINE_REFRESH_FOR_USERNAME)
	public  String getMembersOnlineRefresh(String username) throws Exception {
		log.trace("getMembersOnlineRefresh: " + username);
		connectEventListener.online( username,  true);
		return "ok";
	}
}
