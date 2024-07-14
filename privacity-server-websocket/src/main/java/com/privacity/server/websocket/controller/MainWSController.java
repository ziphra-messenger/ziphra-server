package com.privacity.server.websocket.controller;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.privacity.server.common.adapters.LocalDateAdapter;
import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.security.Usuario;
import com.privacity.server.websocket.model.WsMessage;
import com.privacity.server.websocket.service.WebSocketSenderService;



@RestController
@RequestMapping(path = "/entry")
public class MainWSController {
	
	Gson gson = new GsonBuilder()
			.setPrettyPrinting()
			.registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
			.create();

	   
	/*
	public static final String CONSTANT_URL_PATH_PRIVATE_DOWNLOAD_DATA = "/private/download/data";
	public static final String CONSTANT_URL_PATH_PRIVATE = "/private/entry";
	public static final String CONSTANT_URL_PATH_PRIVATE_SEND = "/private/send";
	public static final String CONSTANT_URL_PATH_PUBLIC = "/public/entry";
	public static final String CONSTANT_URL_PATH_FREE = "/free/entry";
	*/
	@Autowired
	@Lazy
	private FacadeComponent comps;
	
	@Autowired
	WebSocketSenderService ws;
		
	@PostMapping("/ws")
	public  String in(@RequestParam String msg) {
		
			ws.sender(gson.fromJson(msg, WsMessage.class));
			return "ok";
	}
	
	@PostMapping("/ws/get/online/grupo/{idGrupoS}")
	public  int getMembersOnlineByGrupo(@PathVariable String idGrupoS) throws Exception {
		
		Long idGrupo = Long.parseLong(idGrupoS);

		List<Usuario> users = comps.repo().userForGrupo().findByUsuariosForGrupoDeletedFalse(idGrupo);

		int count = 0;
		for (Usuario u : users) {

			Set<String> online = comps.webSocket().socketSessionRegistry().getSessionIds(u.getUsername());

			if (online.size() > 0) {
				count++;
			}
		}

		return count;
	}
}
