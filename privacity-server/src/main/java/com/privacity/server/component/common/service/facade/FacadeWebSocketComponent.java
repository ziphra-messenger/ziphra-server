package com.privacity.server.component.common.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.privacity.server.security.SocketSessionRegistry;
import com.privacity.server.websocket.WebSocketSenderClientService;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class FacadeWebSocketComponent {



	
	@Autowired
	@Lazy
	private SocketSessionRegistry socketSessionRegistry;
	

	
	@Autowired
	@Lazy
	private WebSocketSenderClientService sender;


	public SocketSessionRegistry socketSessionRegistry() {
		return socketSessionRegistry;
	}


	public WebSocketSenderClientService sender() {
		return sender;
	}


	
}
