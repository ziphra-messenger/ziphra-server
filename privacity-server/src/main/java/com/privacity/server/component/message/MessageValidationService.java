package com.privacity.server.component.message;

import java.util.Date;
import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.privacity.common.config.ConstantProtocolo;
import com.privacity.common.dto.GrupoDTO;
import com.privacity.common.dto.IdMessageDTO;
import com.privacity.common.dto.MediaDTO;
import com.privacity.common.dto.MessageDTO;
import com.privacity.common.dto.MessageDetailDTO;
import com.privacity.common.dto.ProtocoloDTO;
import com.privacity.common.enumeration.ExceptionReturnCode;
import com.privacity.common.enumeration.GrupoRolesEnum;
import com.privacity.common.enumeration.MessageState;
import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.exceptions.PrivacityException;
import com.privacity.server.exceptions.ProcessException;
import com.privacity.server.exceptions.ValidationException;
import com.privacity.server.model.Grupo;
import com.privacity.server.model.Message;
import com.privacity.server.model.MessageDetail;
import com.privacity.server.model.UserForGrupo;
import com.privacity.server.security.Usuario;
import com.privacity.server.websocket.WsMessage;
import com.privacity.server.websocket.WsQueue;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

@Service
@AllArgsConstructor
@Log	

public class MessageValidationService {
	
	@Autowired @Lazy
	private FacadeComponent comps;
	
	   @Autowired 
	   @Lazy
	   private WsQueue q;
	
	public void deleteForMe(IdMessageDTO request) throws ValidationException, ProcessException {
		Usuario usuarioLogged = comps.util().usuario().getUsuarioLoggedValidate();
		
		Long idGrupo = comps.util().grupo().convertIdGrupoStringToLong(request.getIdGrupo());
		Long idMessage = comps.util().message().convertIdMessageStringToLong(request.getIdMessage());

		Message message = comps.util().message().getMessage(idGrupo,idMessage);

		MessageDetail detail = comps.util().messageDetail().getMessageDetail(message, usuarioLogged);
		
		if (detail.isDeleted()) {
			throw new ValidationException(ExceptionReturnCode.MESSAGEDETAIL_IS_DELETED);
		}
		//comps.process().message().deleteForMe(detail);
		
		
		ProtocoloDTO p = comps.webSocket().sender().buildProtocoloDTO(
				ConstantProtocolo.PROTOCOLO_COMPONENT_MESSAGE,
				"/message/deleteForEveryone");
		
		IdMessageDTO mRemove = new IdMessageDTO();
		p.setMessageDTO(new MessageDTO());
		p.getMessageDTO().setIdGrupo(message.getMessageId().getGrupo().getIdGrupo()+"");
		p.getMessageDTO().setIdMessage(message.getMessageId().getIdMessage()+"");
		
				q.put(new WsMessage(usuarioLogged.getUsername() ,p ));	
			
		
		
		//return request;
		
	}
	
	public void deleteForEveryone(IdMessageDTO request) throws PrivacityException {
		Usuario usuarioLogged = comps.util().usuario().getUsuarioLoggedValidate();

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
		String username = comps.util().usuario().getUsernameLogged();
		Usuario usuarioLogged = comps.service().usuarioSessionInfo().get(username).getUsuarioDB();

		Long idGrupo = comps.util().grupo().convertIdGrupoStringToLong(request.getIdGrupo());
		Long idMessage = comps.util().message().convertIdMessageStringToLong(request.getIdMessage());
		
		Grupo grupo = comps.util().userForGrupo().getValidation(usuarioLogged, idGrupo).getUserForGrupoId().getGrupo();
		
		Message m = comps.util().message().getMessage(idGrupo,idMessage);

		
		comps.util().messageDetail().getMessageDetailValidateTimeMessage(m, usuarioLogged);
		
		return comps.process().message().get(grupo,idMessage, m,usuarioLogged);
	}
	
	public MessageDTO getDataMedia(MessageDTO request) throws Exception {
		String username = comps.util().usuario().getUsernameLogged();
		Usuario usuarioLogged = comps.service().usuarioSessionInfo().get(username).getUsuarioDB();

		Long idGrupo = comps.util().grupo().convertIdGrupoStringToLong(request.getIdGrupo());
		Long idMessage = comps.util().message().convertIdMessageStringToLong(request.getIdMessage());
		
		comps.util().userForGrupo().getValidation(usuarioLogged, idGrupo).getUserForGrupoId().getGrupo();
		
		Message m = comps.util().message().getMessage(idGrupo,idMessage);

		
		comps.util().messageDetail().getMessageDetailValidateTimeMessage(m, usuarioLogged);
		
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
		comps.process().message().emptyList(idGrupo);
	}
	
	public MessageDetailDTO changeState(MessageDetailDTO request) throws Exception {
		
		Usuario usuarioLogged = comps.util().usuario().getUsuarioLoggedValidate();
		
		Long idGrupo = comps.util().grupo().convertIdGrupoStringToLong(request.getIdGrupo());
		Long idMessage = comps.util().message().convertIdMessageStringToLong(request.getIdMessage());
		
		//Grupo grupo = comps.util().grupo().getGrupoByIdValidation(request.getIdGrupo());
//		comps.util().userForGrupo().getValidation(usuarioLogged, grupo.getIdGrupo());
		
		Message m = comps.util().message().getMessage(idGrupo, idMessage);
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
