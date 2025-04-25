package com.privacity.core.idsprovider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.privacity.commonback.common.interfaces.KeyLockerCallback;
import com.privacity.core.repository.MessageRepository;

@Service
@Qualifier("MessageIdGeneratorService")
public class MessageIdGeneratorService extends MessageIdGeneratorAbstractService{
	@Autowired @Lazy
	private MessageRepository mr;
	
	MessageIdGeneratorServiceCallback cb;
	
	public MessageIdGeneratorService(MessageRepository mr) {
		this.mr=mr;
		this.cb = new MessageIdGeneratorServiceCallback(mr);
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
