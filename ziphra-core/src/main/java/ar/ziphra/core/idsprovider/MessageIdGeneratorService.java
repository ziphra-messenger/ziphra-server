package ar.ziphra.core.idsprovider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import ar.ziphra.commonback.common.interfaces.KeyLockerCallback;
import ar.ziphra.core.repository.MessageRepository;

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
