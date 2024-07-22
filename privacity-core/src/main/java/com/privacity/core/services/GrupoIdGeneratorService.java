package com.privacity.core.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.privacity.core.util.RepositoryFacade;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GrupoIdGeneratorService  {
	
	private Long lastId;
	
	public GrupoIdGeneratorService(RepositoryFacade repos) {
		
		super();
		log.info("Instanciando: GrupoIdGeneratorService");
		log.info("Getting maxId");
		lastId = repos.grupo().getMaxId();
		if (lastId == null) {
			lastId=1L;
		}
	}
	

	public synchronized long getNextId() {
		lastId++;
		log.debug("Grupo Id Generado: " + lastId);
		return lastId;
	
	}

	
	
}