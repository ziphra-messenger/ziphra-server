package com.privacity.core.services;
import java.util.Random;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.privacity.common.exceptions.PrivacityException;
import com.privacity.commonback.common.interfaces.KeyLockerCallback;
import com.privacity.commonback.common.utils.KeyLocker;
import com.privacity.core.interfaces.IdsGeneratorMessageIdInterface;
import com.privacity.core.util.RepositoryFacade;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MessageIdGeneratorService implements KeyLockerCallback, IdsGeneratorMessageIdInterface {

	private KeyLocker keyLocker;
	private Random rand = new Random();
	@Autowired @Lazy
	private RepositoryFacade repos;	

	public MessageIdGeneratorService() {
		super();
		log.info("Instanciando: MessageIdGeneratorService");
	}
	@PostConstruct
	public void pc() {
		keyLocker= new KeyLocker(this);
	}

	public Long getNextMessageId(Long idGrupo) throws PrivacityException {
		return (Long) keyLocker.doSynchronouslyOnlyForEqualKeys(idGrupo);
	}
	

	@Override
	public Object actionInitValue(Object obj) {
		Long idm;
		Long seq0 = repos.message().getMaxId( (Long)obj);;
		if (seq0 != null && seq0 != 0) {
			idm = seq0;
		}else {
			idm = 10000L;
		}
		return idm;
	}
	@Override
	public Object actionNewValue(Object obj) {
    	Long idm = (Long) obj;
    	
		int n = rand.nextInt(50);
		idm = idm + n;
		
		return idm;
	}
	@Override
	public String getInfoForLog() {
		return "MessageIdGeneratorService";
	}

	
	
}