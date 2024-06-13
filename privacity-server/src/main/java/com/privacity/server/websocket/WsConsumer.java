package com.privacity.server.websocket;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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

