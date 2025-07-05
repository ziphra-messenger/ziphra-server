package com.privacity.sessionmanager.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.privacity.sessionmanager.repositories.SessionRepository;
import com.privacity.sessionmanager.util.protocolomap.ProtocoloMapService;

import lombok.Getter;
import lombok.experimental.Accessors;

@Service
@Accessors(fluent = true, chain = false)
@Getter
public class UtilFacade {
	
	@Autowired
	@Lazy
	private UsuarioSessionInfoService usuarioSessionInfo;
	
	@Autowired
	@Lazy
	private RequestIdPublicService requestIdPublic;
	
	@Autowired
	@Lazy
	private  SessionRepository sessionRepository;
	
	@Autowired
	@Lazy
	private JWTKeyGeneratorService JWT;
	
	@Autowired
	@Lazy
	private UtilsStringService utilsString;
	
	@Autowired
	@Lazy
	private ProtocoloMapService map;

	public Gson gson() {
		 		return utilsString.gson();
	}

}
