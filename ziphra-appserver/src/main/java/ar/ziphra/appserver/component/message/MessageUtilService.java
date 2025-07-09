package ar.ziphra.appserver.component.message;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import ar.ziphra.common.dto.MessageDTO;
import ar.ziphra.common.enumeration.ExceptionReturnCode;
import ar.ziphra.common.exceptions.ValidationException;
import ar.ziphra.core.model.Message;
import ar.ziphra.core.model.MessageDetail;
import ar.ziphra.appserver.component.common.service.facade.FacadeComponent;

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
