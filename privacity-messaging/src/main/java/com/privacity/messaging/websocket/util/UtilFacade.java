package com.privacity.messaging.websocket.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.privacity.core.repository.UserForGrupoRepository;
import com.privacity.core.util.UtilsStringComponent;
import com.privacity.messaging.configuration.SocketSessionRegistry;
import com.privacity.messaging.configuration.UsuarioSessionInfoService;
import com.privacity.messaging.websocket.service.WebSocketSenderService;

import lombok.Getter;
import lombok.experimental.Accessors;

@Service
@Accessors(fluent = true, chain = false)
@Getter
public class UtilFacade {
	
	@Autowired
	@Lazy
	private UsuarioSessionInfoService usuarioSessionInfo;

	@Autowired
	@Lazy
	private WebSocketSenderService webSocketSender;

	@Autowired
	@Lazy
	private UserForGrupoRepository userForGrupoRepository;
	
	@Autowired
	@Lazy
	private UtilsStringComponent utilsString;
	
	@Autowired
	@Lazy
	private SocketSessionRegistry socketSessionRegistry;

	public Gson gson() {
		 		return utilsString.gson();
	}

}
