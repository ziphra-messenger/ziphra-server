package com.privacity.server.component.message;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.privacity.common.dto.GrupoDTO;
import com.privacity.common.dto.IdMessageDTO;
import com.privacity.common.dto.MediaDTO;
import com.privacity.common.dto.MessageDTO;
import com.privacity.common.dto.MessageDetailDTO;
import com.privacity.common.enumeration.ExceptionReturnCode;
import com.privacity.common.enumeration.GrupoRolesEnum;
import com.privacity.common.enumeration.MessageState;
import com.privacity.common.exceptions.PrivacityException;
import com.privacity.common.exceptions.ProcessException;
import com.privacity.common.exceptions.ValidationException;
import com.privacity.core.model.Grupo;
import com.privacity.core.model.Message;
import com.privacity.core.model.MessageDetail;
import com.privacity.core.model.UserForGrupo;
import com.privacity.core.model.Usuario;
import com.privacity.server.component.common.service.facade.FacadeComponent;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@Service
@AllArgsConstructor
@Log	

public class MessageValidationService {
	
	@Autowired @Lazy
	private FacadeComponent comps;
	

	
	public void deleteForMe(IdMessageDTO request) throws ValidationException, ProcessException {
		comps.requestHelper().setGrupoInUse(request);
		Usuario usuarioLogged = comps.requestHelper().getUsuarioLogged();
		
		Long idMessage = comps.util().message().convertIdMessageStringToLong(request.getIdMessage());

		Message message = comps.util().message().getMessage(comps.requestHelper().getGrupoInUse().getIdGrupo(),idMessage);

		MessageDetail detail = comps.util().messageDetail().getMessageDetail(message, usuarioLogged);
		
		if (detail.isDeleted()) {
			throw new ValidationException(ExceptionReturnCode.MESSAGEDETAIL_IS_DELETED);
		}
		//comps.process().message().deleteForMe(detail);
		
//		IdMessageDTO mRemove = new IdMessageDTO();
//		p.setMessageDTO(new MessageDTO());
//		p.getMessageDTO().setIdGrupo(message.getMessageId().getGrupo().getIdGrupo()+"");
//		p.getMessageDTO().setIdMessage(message.getMessageId().getIdMessage()+"");
//		
//		ProtocoloDTO p = comps.webSocket().sender().buildProtocoloDTO(
//				ProtocoloComponentsEnum.MESSAGE,
//				ProtocoloActionsEnum.MESSAGE_DELETE_FOR_EVERYONE,
//				mRemove);
//		comps.webSocket().sender().senderToGrupo(p, true, true);
		
		//return request;
		
	}
	
	public void deleteForEveryone(IdMessageDTO request) throws PrivacityException, IOException {
		Usuario usuarioLogged = comps.requestHelper().getUsuarioLogged();

		Long idGrupo = comps.util().grupo().convertIdGrupoStringToLong(request.getIdGrupo());
		Long idMessage = comps.util().message().convertIdMessageStringToLong(request.getIdMessage());

		Message message = comps.util().message().getMessage(idGrupo,idMessage);		


		Grupo g = comps.util().grupo().getGrupoByIdValidation(request.getIdGrupo());
		
		GrupoRolesEnum rol = comps.util().usuario().getRoleForGrupo(usuarioLogged, g);
		
		
		if (!usuarioLogged.getUsername().equals(message.getUserCreation().getUsername())
				&& !rol.equals(GrupoRolesEnum.ADMIN)
				) {
			throw new ValidationException(ExceptionReturnCode.MESSAGE_NOT_MESSAGE_CREATOR);
		}
		

		comps.process().message().deleteForEveryone(usuarioLogged.getIdUser(), message);
		
	}
	
	public MessageDTO get(MessageDTO request) throws Exception {
		
		log.fine("Get Message Reply idParentReply: grupo -> "  + request.getIdGrupo() + " message: -> " + request.getIdMessage() );
		
		String username = comps.requestHelper().getUsuarioUsername();
		Usuario usuarioLogged = comps.requestHelper().getUsuarioLogged();

		Long idGrupo = comps.util().grupo().convertIdGrupoStringToLong(request.getIdGrupo());
		Long idMessage = comps.util().message().convertIdMessageStringToLong(request.getIdMessage());
		
		Grupo grupo = comps.util().userForGrupo().getValidation(usuarioLogged, idGrupo).getUserForGrupoId().getGrupo();
		
		Message m = comps.util().message().getMessage(idGrupo,idMessage);

		
		comps.util().messageDetail().getMessageDetailValidateTimeMessage(m, usuarioLogged);
		
		return comps.process().message().get(grupo,idMessage, m,usuarioLogged);
	}
	
	public MessageDTO getDataMedia(MessageDTO request) throws Exception {

		Grupo grupo = comps.requestHelper().setGrupoInUse(request);
		Long idMessage = comps.util().message().convertIdMessageStringToLong(request.getIdMessage());
		
		comps.util().userForGrupo().getValidation(comps.requestHelper().getUsuarioLogged(),grupo.getIdGrupo()).getUserForGrupoId().getGrupo(); 
		
		Message m = comps.util().message().getMessage(grupo.getIdGrupo(),idMessage);

		
		comps.util().messageDetail().getMessageDetailValidateTimeMessage(m, comps.requestHelper().getUsuarioLogged());
		
		MessageDTO r = new MessageDTO();
		r.setMediaDTO(new MediaDTO());
		r.getMediaDTO().setData(m.getMedia().getData());
		return r;
	}
	
	public MessageDTO[] getAllidMessageUnreadMessages() throws Exception {
		return comps.process().message().getAllidMessageUnreadMessages();
	}
	
	public MessageDTO[] getHistorialId(IdMessageDTO request) throws Exception {
		return comps.process().message().getHistorialId(request);
	}
	
	public void emptyList(GrupoDTO grupo) throws Exception {
		//comps.process().message().emptyList(grupo.getIdGrupo());
	}
	
	public void deleteAllMyMessageForEverybodyByGrupo(String idGrupo) throws Exception {
		log.fine("Procesando deleteAllMyMessageForEverybodyByGrupo: grupo -> "  + idGrupo);
		comps.process().message().emptyList(idGrupo);
	}
	
	public MessageDetailDTO changeState(MessageDetailDTO request) throws Exception {
		log.fine("Procesando changeState (" + request.getEstado().name() +   " ): grupo -> "  + request.getIdGrupo() + " message: -> " + request.getIdMessage() );
		Grupo g = comps.requestHelper().setGrupoInUse(request);
		Usuario usuarioLogged = comps.requestHelper().getUsuarioLogged();

		Long idMessage = comps.util().message().convertIdMessageStringToLong(request.getIdMessage());
		
		//Grupo grupo = comps.util().grupo().getGrupoByIdValidation(request.getIdGrupo());
//		comps.util().userForGrupo().getValidation(usuarioLogged, grupo.getIdGrupo());
		
		Message m = comps.util().message().getMessage(g.getIdGrupo(), idMessage);
		
		MessageDetail md = comps.util().messageDetail().getMessageDetail(m, usuarioLogged);
		
		MessageState state =request.getEstado();
		
		return comps.process().message().changeState(usuarioLogged, m, md, state,request);
	}
	
	public MessageDTO[] loadMessages(MessageDTO request) throws Exception {
		return comps.process().message().loadMessages(request);
	}
	
	
	

	public MessageDTO send(MessageDTO request, Grupo grupo, Usuario usuarioLogged, UserForGrupo ufg) throws Exception {
		
		Date inicio = new Date();
		
		List<UserForGrupo> usersForGrupo = comps.repo().userForGrupo().findByForGrupo(grupo.getIdGrupo());
		Message m = comps.common().mapper().doit(request, usuarioLogged,grupo,usersForGrupo, true);
		MessageDTO r = comps.process().message().send(usuarioLogged.getIdUser(), m, grupo.getIdGrupo());
		
		Date fin = new Date();
		
		double total = fin.getTime() - inicio.getTime();
		
		log.info("total:>> " + (total/1000));
		return r;
	}


}

