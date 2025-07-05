package ar.ziphra.server.component.messagedetail;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import ar.ziphra.common.enumeration.ExceptionReturnCode;
import ar.ziphra.common.enumeration.MessageState;
import ar.ziphra.common.exceptions.ValidationException;
import ar.ziphra.core.model.Message;
import ar.ziphra.core.model.MessageDetail;
import ar.ziphra.core.model.MessageDetailId;
import ar.ziphra.core.model.UserForGrupo;
import ar.ziphra.core.model.Usuario;
import ar.ziphra.server.component.common.service.facade.FacadeComponent;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class MessageDetailUtil {
	
	@Autowired @Lazy
	private FacadeComponent comps;


	
	public MessageDetail getMessageDetail(Message message, Usuario usuario) throws ValidationException {
		
		Optional<MessageDetail> mOptional;
		
		MessageDetailId id = new MessageDetailId();
		id.setMessage(message);
		id.setUserDestino(usuario);
		
		mOptional = comps.repo().messageDetail().findById(id);
		
		if (!mOptional.isPresent()) {
			log.debug(ExceptionReturnCode.MESSAGEDETAIL_NOT_EXISTS.getToShow(id.toString()));
			throw new ValidationException(ExceptionReturnCode.MESSAGEDETAIL_NOT_EXISTS);	
		}
		
		
		return mOptional.get();
	}
	
	public MessageDetail getMessageDetailValidateTimeMessage(Message message, Usuario usuario) throws ValidationException {
		
		MessageDetail r = getMessageDetail(message,usuario);
		if (message.isTimeMessage() 
				&& r.getState().equals(MessageState.DESTINY_READ)) {
			throw new ValidationException(ExceptionReturnCode.MESSAGEDETAIL_NOT_EXISTS_TIME_MESSAGE);
			
		}
		
		if (r.isDeleted()) {
			throw new ValidationException(ExceptionReturnCode.MESSAGEDETAIL_IS_DELETED);			
		}
		return r;
	}
	
	public Set<MessageDetail> generateMessagesDetail(Long idGrupo, Message message, List<UserForGrupo> usersForGrupo) {
		Set<MessageDetail> r = new HashSet<MessageDetail>();

		
		
		usersForGrupo.stream().forEach(destino ->
		extracted(message, r, destino.getUserForGrupoId().getUser())
		);		
		
//		for ( Usuario destino : usuarios) {
//						
//			extracted(message, r, destino);
//		}
		
		return r;
	}

	private static void extracted(Message message, Set<MessageDetail> r, Usuario destino) {
		MessageDetail md = new MessageDetail();
		if ( destino.getUsername().equals(message.getUserCreation().getUsername())){
			md.setState(MessageState.MY_MESSAGE_SENT);
		}else {
			md.setState(MessageState.DESTINY_SERVER);
		}

		md.setMessageDetailId(new MessageDetailId(destino, message) );
 
		r.add(md);
	}

}
