package com.privacity.server.dao.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privacity.common.util.RandomGenerator;
import com.privacity.server.common.model.MessageIdSequence;
import com.privacity.server.component.message.MessageIdSequenceRepository;
import com.privacity.server.dao.interfaces.MessgeIdInterfaceDAO;

@Service
public class MessageIdLocal implements MessgeIdInterfaceDAO {

	@Autowired
	MessageIdSequenceRepository repo;
	@Override
	public synchronized long getNextMessageId(Long idGrupo) {
		Long idm;
		MessageIdSequence seq;
		Optional<MessageIdSequence> seqO = repo.findById(idGrupo);
		if (seqO.isPresent()) {
			seq = seqO.get();
			idm = seq.getIdMessage();
			
		}else {
			seq = new MessageIdSequence();
			seq.setIdGrupo(idGrupo);
			idm = 10000L;
			repo.save(seq);
		}
		idm = idm + RandomGenerator.betweenTwoNumber(2,67);
		seq.setIdMessage(idm);
		repo.save(seq);
		return idm;
	
	}

}
