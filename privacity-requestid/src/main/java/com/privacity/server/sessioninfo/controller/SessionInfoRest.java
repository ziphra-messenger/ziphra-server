package com.privacity.server.sessioninfo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
class SessionInfoRest {

//	@Autowired
//  UsuarioSessionInfoService usuarioSessionInfoService;



	@RequestMapping(value = "/put", method = RequestMethod.PUT)
  public void put(@RequestBody UsuarioSessionInfo u) {
    System.out.println(u.toString());
  }

	@RequestMapping(value = "/test", method = RequestMethod.GET)
  public String get2() throws Exception {
	  return "hola"; // //usuarioSessionInfoService.get(user);

  }

}