package com.privacity.server.component.common.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.privacity.server.websocket.configuration.SocketSessionRegistry;
import com.privacity.server.websocket.service.WebSocketSenderService;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class FacadeWebSocketComponent {



	
	@Autowired
	@Lazy
	private SocketSessionRegistry socketSessionRegistry;
	

	
	@Autowired
	@Lazy
	private WebSocketSenderService sender;


	public SocketSessionRegistry socketSessionRegistry() {
		return socketSessionRegistry;
	}


	public WebSocketSenderService sender() {
		return sender;
	}


	
}
