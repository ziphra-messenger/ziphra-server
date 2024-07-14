package com.privacity.server.dao.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.privacity.server.dao.interfaces.MessgeIdInterfaceDAO;


/**
 * Created by baiguantao on 2017/8/4.
 * User session record class
 */
@Service
public class MessageIdRest  implements MessgeIdInterfaceDAO {


    @Value("${com.privacity.server.service.messageidrest}")
    private String url;
    
      public long getNextMessageId(Long idGrupo) {    	


    	  RestTemplate rest = new RestTemplate();

    	  MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
    	 // map.add("idGrupo", "idGrupo=");


    	  Long idMessage = rest.postForObject(url + idGrupo, map, Long.class);
    	  
        return idMessage;
        

    }

//      public static void main(String[] args) throws Exception {
//
//Random rand = new Random();
//
//// Obtain a number between [0 - 49].
//
//
//    	  MessageIdRest m = new MessageIdRest();
//    	  while( true) {
//    		 System.out.println(m.getNextMessageId(  new Long(  rand.nextInt(6) )));
//    		 Thread.sleep(2000);
//    	  }
//	}

}