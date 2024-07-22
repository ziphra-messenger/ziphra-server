package com.privacity.messaging.websocket.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.privacity.common.dto.MembersQuantityDTO;
import com.privacity.commonback.constants.MessagingRestConstants;
import com.privacity.core.util.UtilsStringComponent;
import com.privacity.messaging.websocket.listeners.STOMPConnectEventListener;
import com.privacity.messaging.websocket.service.WebSocketSenderService;

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
		return ws.getMembersOnlineByGrupo(idGrupo);
	}
	
	@PostMapping(MessagingRestConstants.ONLINE_REFRESH_FOR_USERNAME)
	public  String getMembersOnlineRefresh(String username) throws Exception {
		connectEventListener.online( username,  true);
		return "ok";
	}
}
