package com.privacity.server.websocket.pool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.privacity.server.websocket.model.WsMessage;

@Service
public class WsQueue {
   
   private List<WsMessage> contents = Collections.synchronizedList(new ArrayList<WsMessage>());

   private final int MAX_SIZE = 100;
   
   public synchronized WsMessage get() {
      while (contents.size() == 0) {
         try {
        	 ////System.out.println ("wait vacia : " + contents.size());
        	 wait();
         }
         catch (InterruptedException e) {
        	 e.printStackTrace();
        	 ////System.out.println ("error cola");
         }
      }
 	  ////System.out.println ("error entrega elemento");
 	  WsMessage r = contents.get(0);
 	  contents.remove(0);
      notifyAll();
      return r;
   }
   public synchronized void put(WsMessage value) {
	   
	   while (contents.size() >= MAX_SIZE) {
         try {
            wait();
         }
         catch (InterruptedException e) { 
         } 
      }
      contents.add(value);
      ////System.out.println ("cola add element  size: " + contents.size());
      notifyAll();
   }
	
}
