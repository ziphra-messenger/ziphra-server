package ar.ziphra.core.idsprovider;

import java.util.Random;

import ar.ziphra.commonback.common.interfaces.KeyLockerCallback;
import ar.ziphra.core.model.Message;
import ar.ziphra.core.repository.MessageRepository;

public class MessageIdGeneratorServiceCallback implements KeyLockerCallback{
	

	public MessageRepository mp;
	
	public MessageIdGeneratorServiceCallback(MessageRepository mp) {
		super();
		this.mp = mp;
	}
	private Random rand = new Random();
	
	@Override
	public Object actionInitValue(Object obj) {
		Long idm;
		Long seq0 = mp.getMaxId( (Long)obj);;
		if (seq0 != null && seq0 != 0 && seq0  > Message.CONSTANT_ID_STARTS_AT) {
			idm = seq0;
		}else {
			idm = Message.CONSTANT_ID_STARTS_AT;
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
		return "MessageIdGeneratorServiceCallback";
	}
}
