package ar.ziphra.messaging.handlers;

import java.lang.reflect.Type;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

import ar.ziphra.core.model.Message;

import lombok.extern.java.Log;
@Log
public class MyStompSessionHandler implements StompSessionHandler {

	@Override
	public void afterConnected(
	  StompSession session, StompHeaders connectedHeaders) {
	    session.subscribe("/topic/greetings", this);
	   
	}
	@Override
	public void handleFrame(StompHeaders headers, Object payload) {
	    Message msg = (Message) payload;
	    log.info("Received : " + msg.getText()+ " from : " + payload.toString());
	}
	@Override
	public Type getPayloadType(StompHeaders headers) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload,
			Throwable exception) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void handleTransportError(StompSession session, Throwable exception) {
		// TODO Auto-generated method stub
		
	}

}
