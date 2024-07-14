package com.privacity.server.component.common.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.privacity.server.component.auth.AuthValidationService;
import com.privacity.server.component.grupo.GrupoValidationService;
import com.privacity.server.component.message.MessageValidationService;
import com.privacity.server.component.requestid.RequestIdValidationService;

import lombok.NoArgsConstructor;
@Component
@NoArgsConstructor
public class FacadeValidationService {
	@Autowired @Lazy
	private AuthValidationService auth;

	@Autowired @Lazy
	private RequestIdValidationService requestId;

	@Autowired @Lazy
	private GrupoValidationService grupo;
	
	@Autowired @Lazy
	private MessageValidationService message;
	

	
	public MessageValidationService message() {
		return message;
	}
	
	public GrupoValidationService grupo() {
		return grupo;
	}
	
	public RequestIdValidationService requestId() {
		return requestId;
	}
	
	public AuthValidationService auth() {
		return auth;
	}

}
