package com.privacity.server.websocket.pool;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.privacity.server.websocket.model.WsMessage;
import com.privacity.server.websocket.service.WebSocketSenderService;

@Component
public class WsConsumer extends Thread {
	  @Autowired 
	   private WsQueue q;

	   @Autowired 
	   private WebSocketSenderService sender;

	   
	    @PostConstruct
	    public void postConstruct() {
	        this.start();
	    }
	   
		@Override
		public void run()  {
			////System.out.println(" ****** Comenzando WS TASK");
	         while (true) {
	            WsMessage msg = q.get();

	            new Runnable() {
					@Override
					public void run() {
						sender.sender(msg);
					}}.run();;
	            
	         }
	    }

}

