package com.privacity.core.idsprovider;
import com.privacity.common.exceptions.PrivacityException;
import com.privacity.commonback.common.interfaces.KeyLockerCallback;
import com.privacity.commonback.common.utils.KeyLocker;
import com.privacity.core.interfaces.MessageIdIdsGeneratorInterface;
import com.privacity.core.repository.MessageRepository;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public abstract class MessageIdGeneratorAbstractService  implements MessageIdIdsGeneratorInterface {

	private KeyLocker keyLocker=null;
	
	private boolean refreshing=false;

	public void refesh() throws PrivacityException {
		
		refreshing=true;
		log.warn("Refresh:" + refreshing);
		if (keyLocker == null) {pc();}
		keyLocker.doSynchronouslyOnlyForAllKeys();
		
	}

	public void pc() {
		log.info("Instanciando: MessageIdGeneratorService");
		keyLocker= new KeyLocker(getCallback());
	}
	public abstract KeyLockerCallback getCallback();
	public abstract MessageRepository getMessageRepository();
	
	public Long getNextMessageIdFromRepository(Long idGrupo) throws PrivacityException {
		Long r = getMessageRepository().getMaxId(idGrupo)+1;
		log.info("getNextMessageIdFromRepository: " +r );
		return r;	
	}
	public Long getNextMessageId(Long idGrupo) throws PrivacityException {
		if (keyLocker == null) {pc();}
		if (refreshing) {
			keyLocker.doSynchronouslyOnlyForAllKeys();
			refreshing=false;
			log.warn("Refresh:" + refreshing);
		
		}

		return (Long) keyLocker.doSynchronouslyOnlyForEqualKeys(idGrupo);
	}
	



	
	
}