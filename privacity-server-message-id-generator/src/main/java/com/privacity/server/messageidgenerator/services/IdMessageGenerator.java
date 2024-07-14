package com.privacity.server.messageidgenerator.services;

import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.privacity.server.messageidgenerator.repositories.MessageIdSequenceRepository;

@Service
public class IdMessageGenerator  {
	
    private static final Logger log = Logger.getLogger(IdMessageGenerator.class.getCanonicalName());

	

	MessageIdSequenceRepository repo;

	public IdMessageGenerator(MessageIdSequenceRepository repo) {
		super();
		this.repo = repo;
	}

	public long getNextMessageId(Long idGrupo) {
		Long idm;
		Long seq0 = repo.getMaxIdGrupo(idGrupo);
		if (seq0 != null && seq0 != 0) {
			idm = seq0;
		}else {
			idm = 10000L;
		}

	
		
		return idm;
	
	}

	
	
}