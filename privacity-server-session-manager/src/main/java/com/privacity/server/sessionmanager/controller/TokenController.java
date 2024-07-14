package com.privacity.server.sessionmanager.controller;

import java.time.LocalDateTime;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.privacity.server.common.adapters.LocalDateAdapter;
import com.privacity.server.sessionmanager.services.JWTKeyGenerator;
import com.privacity.server.sessionmanager.services.UsuarioSessionInfoService;
import com.privacity.server.sessionmanager.util.protocolomap.ProtocoloMapService;

@RestController
@RequestMapping(path = "/jwt")
public class TokenController {
	private static final int MAX_LOG = 2000;
	private static final Logger log = Logger.getLogger(TokenController.class.getCanonicalName());
	@Autowired
	UsuarioSessionInfoService usuarioSessionInfoService;
	@Autowired
	JWTKeyGenerator jwt;
	Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
			.create();
	@Autowired
	private ProtocoloMapService map;

	@PostMapping("/get")
	public String requestIdGet() throws Throwable, Exception {
		
		return gson.toJson(jwt.get());

	}

}
