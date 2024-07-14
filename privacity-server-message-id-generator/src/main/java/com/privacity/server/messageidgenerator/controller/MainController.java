package com.privacity.server.messageidgenerator.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.privacity.server.messageidgenerator.util.KeyLocker;



@RestController
@RequestMapping(path = "/entry")
public class MainController {

	private KeyLocker keyLocker;
	public MainController(KeyLocker keyLocker) {
		super();
		this.keyLocker = keyLocker;
	}
	@PostMapping("/get/{idGrupo}")

	public long getIdMessageByGrupo(@PathVariable long idGrupo) throws Exception {
		return keyLocker.doSynchronouslyOnlyForEqualKeys(idGrupo);

	}

}
