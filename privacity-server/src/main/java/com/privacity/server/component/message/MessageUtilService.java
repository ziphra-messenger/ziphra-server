package com.privacity.server.component.message;

import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.privacity.common.enumeration.ExceptionReturnCode;
import com.privacity.server.common.exceptions.ValidationException;
import com.privacity.server.common.model.Grupo;
import com.privacity.server.common.model.Message;
import com.privacity.server.common.model.MessageId;
import com.privacity.server.component.common.service.facade.FacadeComponent;

@Service
public class MessageUtilService {
	
	@Autowired @Lazy
	private FacadeComponent comps;


	public Long convertIdMessageStringToLong(String idMessage) throws ValidationException {
		Long r;
		try {
			r = Long.parseLong(idMessage);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new ValidationException(ExceptionReturnCode.MESSAGE_MESSAGEID_BADFORMAT);
		}
		return r;
	}
	
	public Message getMessage(long grupo, long idMessage) throws ValidationException {
		
		Message m;
		
		m = comps.repo().message().findByIdPrimitive(grupo, idMessage);

		if ( m==null) {
			throw new ValidationException(ExceptionReturnCode.MESSAGE_NOT_EXISTS);	
		}
			
		
		return m;
	}
	
	
}
