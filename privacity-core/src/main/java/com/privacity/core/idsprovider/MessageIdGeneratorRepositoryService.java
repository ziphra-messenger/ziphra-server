package com.privacity.core.idsprovider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.privacity.commonback.common.interfaces.KeyLockerCallback;
import com.privacity.core.repository.MessageRepository;

@Service
@Qualifier("MessageIdGeneratorRepositoryService")
public class MessageIdGeneratorRepositoryService extends MessageIdGeneratorAbstractService{
	@Autowired @Lazy
	private MessageRepository mr;
	
	MessageIdGeneratorServiceRepositoryCallback cb;
	
	public MessageIdGeneratorRepositoryService(MessageRepository mr) {
		this.mr=mr;
		this.cb = new MessageIdGeneratorServiceRepositoryCallback(mr);
	}

	@Override
	public KeyLockerCallback getCallback() {
		return cb;
	}

	@Override
	public MessageRepository getMessageRepository() {
		return mr;
	}

}
