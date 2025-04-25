package com.privacity.core.idsprovider;

import java.util.Random;

import com.privacity.commonback.common.interfaces.KeyLockerCallback;
import com.privacity.core.model.Message;
import com.privacity.core.repository.MessageRepository;

public class MessageIdGeneratorServiceRepositoryCallback implements KeyLockerCallback{
	
	

	private Random rand = new Random();

	public MessageRepository mp;
	
	public MessageIdGeneratorServiceRepositoryCallback(MessageRepository mp) {
		super();
		this.mp = mp;
	}
	@Override
	public Object actionInitValue(Object obj) {
		Long idm;

		idm =getLastValue(obj);
		return idm;
	}
	private Long getLastValue(Object obj) {
		Long seq0 = mp.getMaxId( (Long)obj);
		Long r;
		if (seq0 != null && seq0 != 0 && seq0  > Message.CONSTANT_ID_STARTS_AT) {
			r = seq0;
		}else {
			r = Message.CONSTANT_ID_STARTS_AT;
		}
		return r;
	}
	@Override
	public Object actionNewValue(Object obj) {
		
		Long lastIdRepository = getLastValue( (Long)obj);;
    	Long idm = (Long) obj;
    	if ( lastIdRepository >= idm ) {
    		idm=lastIdRepository;
    	}
		int n = rand.nextInt(50);
		idm = idm + n;
		
		return idm;
	}
	@Override
	public String getInfoForLog() {
		return "MessageIdGeneratorServiceRepositoryCallback";
	}
}
