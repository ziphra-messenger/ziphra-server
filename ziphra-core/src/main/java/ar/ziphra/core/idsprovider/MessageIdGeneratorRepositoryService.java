package ar.ziphra.core.idsprovider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import ar.ziphra.commonback.common.interfaces.KeyLockerCallback;
import ar.ziphra.core.repository.MessageRepository;

//@Service  esto es solo para cuando se usa el id dentro del server
//hay q volver a ponerlo
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
