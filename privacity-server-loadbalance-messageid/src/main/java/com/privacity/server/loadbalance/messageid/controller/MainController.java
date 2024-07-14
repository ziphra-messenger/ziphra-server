package com.privacity.server.loadbalance.messageid.controller;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.privacity.server.loadbalance.messageid.services.ForwardManagerService;



@RestController
@RequestMapping(path = "/entry")
public class MainController {

	private ForwardManagerService f;
	public MainController(ForwardManagerService f) {
		super();
		this.f = f;
	}
	@PostMapping("/get/{idGrupo}")

	public long getIdMessageByGrupo(@PathVariable long idGrupo) throws Exception {
		if (idGrupo%2 == 0) {
			return getNextMessageId(f.getPar(), idGrupo);
		}else {
			return getNextMessageId(f.getImpar(), idGrupo);	
		}
		

	}

    public long getNextMessageId(String destino, Long idGrupo) {    	


  	  RestTemplate rest = new RestTemplate();

  	  MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
  	  //map.add("idGrupo", "idGrupo=");


  	  Long idMessage = rest.postForObject(destino + "/entry/get/" + idGrupo, map, Long.class);
  	  
      return idMessage;
      

  }
}
