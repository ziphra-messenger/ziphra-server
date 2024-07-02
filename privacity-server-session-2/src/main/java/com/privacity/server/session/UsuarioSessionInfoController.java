package com.privacity.server.session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping(path = "/entry")
public class UsuarioSessionInfoController{

	@Qualifier("uuu")
	@Autowired
	@Lazy
	UsuarioSessionInfoService usuarioSessionInfoService;
	
	
	public UsuarioSessionInfoController() {
		super();

	}


	@PostMapping("/gett")
	@GetMapping("/gett")
	public UsuarioSessionInfo in(@RequestParam(value = "username", defaultValue = "World") String username) throws Exception {
			return usuarioSessionInfoService.get(username, true);

	}

	
}
