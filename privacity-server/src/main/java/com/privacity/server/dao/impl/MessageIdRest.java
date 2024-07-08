package com.privacity.server.dao.impl;

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

    
      public long getNextMessageId(Long idGrupo) {    	


    	  RestTemplate rest = new RestTemplate();

    	  MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
    	  //map.add("idGrupo", "idGrupo=");


    	  Long idMessage = rest.postForObject("http://localhost:8081/entry/get/" + idGrupo, map, Long.class);
    	  
        return idMessage;
        

    }

      public static void main(String[] args) {
    	  MessageIdRest m = new MessageIdRest();
    	  while( true) {
    		  m.getNextMessageId(123L);
    	  }
	}

}