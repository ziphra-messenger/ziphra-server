package com.privacity.sessionmanager.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.privacity.common.dto.RequestIdDTO;
import com.privacity.common.exceptions.PrivacityException;
import com.privacity.commonback.constants.SessionManagerRestConstants;
import com.privacity.sessionmanager.services.UtilFacade;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = SessionManagerRestConstants.REQUEST_ID)
@Slf4j
public class RequestIdController {

	@Autowired
	@Lazy
	private UtilFacade uf;

	@PostMapping(SessionManagerRestConstants.REQUEST_ID_PUBLIC_GET)
	public RequestIdDTO requestIdPublicGet(@RequestParam String half){
		log.trace("requestIdPublicGet: " + half);
		return uf.requestIdPublic().get(half);

	}
	@PostMapping(SessionManagerRestConstants.REQUEST_ID_PUBLIC_ADD)
	public String requestIdPublicAdd(@RequestParam String half, @RequestParam String requestId) {
		log.trace("requestIdPublicAdd half: " + half + " requestId: " + requestId);
		uf.requestIdPublic().put(half, uf.gson().fromJson(requestId, RequestIdDTO.class));
		return "ok";
	}
	@PostMapping(SessionManagerRestConstants.REQUEST_ID_PUBLIC_REMOVE)
	public String requestIdPublicRemove(@RequestParam String half) {
		log.trace("requestIdPublicRemove: " + half);
		uf.requestIdPublic().remove(half);
		return "ok";
	}

	@PostMapping(SessionManagerRestConstants.REQUEST_ID_PRIVATE_ADD)
	public void requestIdPrivateAdd(@RequestParam String username, @RequestParam String requestIdClientSide, @RequestParam String serverRequestIdDTO) throws PrivacityException, PrivacityException{
		log.trace("requestIdPrivateAdd username: " + username + " requestIdClientSide: " + requestIdClientSide  + " serverRequestIdDTO: " + serverRequestIdDTO);
		uf.usuarioSessionInfo().get(username).getRequestId().put(requestIdClientSide,
				uf.gson().fromJson(requestIdClientSide, RequestIdDTO.class));	
	}
	
	@PostMapping(SessionManagerRestConstants.REQUEST_ID_PRIVATE_GET_ALL)
	public Map<String,RequestIdDTO> requestIdPrivateGetAll(@RequestParam String username) throws PrivacityException {
		log.trace("requestIdPrivateGetAll: " + username);
		return uf.usuarioSessionInfo().get(username).getRequestId().getAll();
	}

}
