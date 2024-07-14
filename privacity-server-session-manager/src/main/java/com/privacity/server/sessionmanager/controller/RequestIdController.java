package com.privacity.server.sessionmanager.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.privacity.common.dto.RequestIdDTO;
import com.privacity.server.common.utils.UtilsString;
import com.privacity.server.sessionmanager.services.RequestIdPublicService;
import com.privacity.server.sessionmanager.services.UsuarioSessionInfoService;
import com.privacity.server.sessionmanager.util.protocolomap.ProtocoloMapService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/entry/request")
@Slf4j
public class RequestIdController {

	@Autowired
	private UsuarioSessionInfoService usuarioSessionInfoService;
	@Autowired
	private RequestIdPublicService requestIdPublicService;


	@PostMapping("/id/get")
	public RequestIdDTO requestIdGet(@RequestParam String half) throws Throwable, Exception {
		log.trace("requestIdGet: " + half);
		return requestIdPublicService.get(half);

	}
	@PostMapping("/id/put")
	public String requestIdPut(@RequestParam String half, String requestId) throws Throwable, Exception {
		log.trace("requestIdPut half: " + half + " requestId: " + requestId);
		requestIdPublicService.put(half, UtilsString.gson().fromJson(requestId, RequestIdDTO.class));
		return "ok";
	}
	@PostMapping("/id/remove")
	public String requestIdRemove(@RequestParam String half) throws Throwable, Exception {
		log.trace("requestIdRemove: " + half);
		requestIdPublicService.remove(half);
		return "ok";
	}

	@PostMapping("/private/id/add")
	public void requestIdAdd(String username, String requestIdClientSide, String serverRequestIdDTO) throws Exception {
		log.trace("requestIdAdd username: " + username + " requestIdClientSide: " + requestIdClientSide  + " serverRequestIdDTO: " + serverRequestIdDTO);
		usuarioSessionInfoService.get(username).getRequestIds().put(requestIdClientSide,
				UtilsString.gson().fromJson(requestIdClientSide, RequestIdDTO.class));	
	}
	@PostMapping("/private/request/id/getAll")
	public Map requestIdGetAll(String username) throws Exception {
		log.trace("requestIdGetAll: " + username);
		return usuarioSessionInfoService.get(username).getRequestIds();	
	}

}
