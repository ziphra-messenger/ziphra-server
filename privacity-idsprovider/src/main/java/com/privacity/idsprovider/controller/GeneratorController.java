package com.privacity.idsprovider.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.privacity.commonback.common.utils.IdsProviderRestConstants;
import com.privacity.core.services.GrupoIdGeneratorService;
import com.privacity.core.services.MessageIdGeneratorService;

import lombok.extern.slf4j.Slf4j;



@RestController
@RequestMapping(path = IdsProviderRestConstants.GENERATOR)
@Slf4j
public class GeneratorController {


	private GrupoIdGeneratorService grupoIdGenerator;
	private MessageIdGeneratorService messageIdGenerator;
	
	public GeneratorController(MessageIdGeneratorService messageIdGenerator, GrupoIdGeneratorService grupoIdGenerator) {
		super();
		this.messageIdGenerator=messageIdGenerator;
		this.grupoIdGenerator=grupoIdGenerator;
	}
	@PostMapping(IdsProviderRestConstants.GENERATOR_GET_NEXT_MESSAGE_ID + "/{grupoid}")
	public long getIdMessageByGrupo(@PathVariable long grupoid) throws Exception {
		log.debug("entrada getIdMessageByGrupo: " + grupoid);
		return messageIdGenerator.getNextMessageId(grupoid);

	}

	@PostMapping(IdsProviderRestConstants.GENERATOR_GET_NEXT_GRUPO_ID)
	public long getNextGrupoId() throws Exception {
		log.debug("entrada getNextGrupoId");
		return grupoIdGenerator.getNextId();

	}


}
