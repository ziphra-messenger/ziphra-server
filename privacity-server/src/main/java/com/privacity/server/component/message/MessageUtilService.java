package com.privacity.server.component.message;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.privacity.common.dto.MessageDTO;
import com.privacity.common.enumeration.ExceptionReturnCode;
import com.privacity.common.exceptions.ValidationException;
import com.privacity.core.model.Message;
import com.privacity.core.model.MessageDetail;
import com.privacity.server.component.common.service.facade.FacadeComponent;

@Service
public class MessageUtilService {
	
	@Autowired @Lazy
	private FacadeComponent comps;

	public MessageDTO[] convertArrayListToArray(List<MessageDetail> l) throws ValidationException {
		MessageDTO[] r = new MessageDTO[l.size()];
		for (int i= 0 ; i < l.size() ; i++) {
			MessageDTO dto = new MessageDTO();
			dto.setIdMessage(l.get(i).getMessageDetailId().getMessage().getMessageId().getIdMessage().toString());
			dto.setIdGrupo(l.get(i).getMessageDetailId().getMessage().getMessageId().getGrupo().getIdGrupo().toString());

			r[i] = dto;
		}
		return r;
	}
	
	
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
