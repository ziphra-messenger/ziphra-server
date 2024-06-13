package com.privacity.server.component.common.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.privacity.server.component.grupo.GrupoProcessService;
import com.privacity.server.component.message.MessageProcessService;
import com.privacity.server.security.SocketSessionRegistry;
import com.privacity.server.websocket.STOMPConnectEventListener;
import com.privacity.server.websocket.WebSocketSenderService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class FacadeWebSocketComponent {


	@Autowired
	@Lazy
	private SimpMessagingTemplate simpMessagingTemplate;
	
	@Autowired
	@Lazy
	private SocketSessionRegistry socketSessionRegistry;
	
	@Autowired
	@Lazy
    private STOMPConnectEventListener stompConnectEventListener;
	
	@Autowired
	@Lazy
	private WebSocketSenderService sender;

	public SimpMessagingTemplate simpMessagingTemplate() {
		return simpMessagingTemplate;
	}

	public SocketSessionRegistry socketSessionRegistry() {
		return socketSessionRegistry;
	}

	public STOMPConnectEventListener stompConnectEventListener() {
		return stompConnectEventListener;
	}

	public WebSocketSenderService sender() {
		return sender;
	}


	
}
