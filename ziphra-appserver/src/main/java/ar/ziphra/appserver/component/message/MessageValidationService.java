package ar.ziphra.appserver.component.message;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import ar.ziphra.common.dto.GrupoDTO;
import ar.ziphra.common.dto.IdMessageDTO;
import ar.ziphra.common.dto.MediaDTO;
import ar.ziphra.common.dto.MessageDTO;
import ar.ziphra.common.dto.MessageDetailDTO;
import ar.ziphra.common.enumeration.ExceptionReturnCode;
import ar.ziphra.common.enumeration.GrupoRolesEnum;
import ar.ziphra.common.enumeration.MediaTypeEnum;
import ar.ziphra.common.enumeration.MessageState;
import ar.ziphra.common.enumeration.RulesConfEnum;
import ar.ziphra.common.exceptions.ProcessException;
import ar.ziphra.common.exceptions.ValidationException;
import ar.ziphra.common.exceptions.ZiphraException;
import ar.ziphra.core.model.Grupo;
import ar.ziphra.core.model.Message;
import ar.ziphra.core.model.MessageDetail;
import ar.ziphra.core.model.UserForGrupo;
import ar.ziphra.core.model.Usuario;
import ar.ziphra.appserver.component.common.service.facade.FacadeComponent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
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
	
	public void deleteForEveryone(IdMessageDTO request) throws ZiphraException, IOException {
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
		
		log.trace("Get Message Reply idParentReply: grupo -> "  + request.getIdGrupo() + " message: -> " + request.getIdMessage() );
		
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
		r.setMedia(new MediaDTO());
		r.getMedia().setData(m.getMedia().getData());
		return r;
	}
	
	public MessageDTO[] getAllidMessageUnreadMessages() throws Exception {
		return comps.process().message().getAllidMessageUnreadMessages();
	}
	public MessageDTO[] getAllidMessageDestinyServerMessages() throws Exception {
		return comps.process().message().getAllidMessageDestinyServerMessages();
	}
	public MessageDTO[] getHistorialId(IdMessageDTO request) throws Exception {
		return comps.process().message().getHistorialId(request);
	}
	
	public void emptyList(GrupoDTO grupo) throws Exception {
		//comps.process().message().emptyList(grupo.getIdGrupo());
	}
	
	public void deleteAllMyMessageForEverybodyByGrupo(String idGrupo) throws Exception {
		log.trace("Procesando deleteAllMyMessageForEverybodyByGrupo: grupo -> "  + idGrupo);
		comps.process().message().emptyList(idGrupo);
	}
	
	public MessageDetailDTO changeState(MessageDetailDTO request) throws Exception {
		log.trace("Procesando changeState (" + request.getEstado().name() +   " ): grupo -> "  + request.getIdGrupo() + " message: -> " + request.getIdMessage() );
		Grupo g = comps.requestHelper().setGrupoInUse(request);
		Usuario usuarioLogged = comps.requestHelper().getUsuarioLogged();

		Long idMessage = comps.util().message().convertIdMessageStringToLong(request.getIdMessage());
		
		//Grupo grupo = comps.util().grupo().getGrupoByIdValidation(request.getIdGrupo());
//		comps.util().userForGrupo().getValidation(usuarioLogged, grupo.getIdGrupo());
		
		Message m = comps.util().message().getMessage(g.getIdGrupo(), idMessage);
		
		MessageDetail md = comps.util().messageDetail().getMessageDetail(m, usuarioLogged);
		MessageState oldState = md.getState();
		md.setHideRead(request.isHideRead());
		MessageState state =request.getEstado();
		
		return comps.process().message().changeState(usuarioLogged, m, md, state,request,oldState);
	}
	
	public MessageDTO[] loadMessages(MessageDTO request) throws Exception {
		return comps.process().message().loadMessages(request);
	}
	

	public MessageDTO send(MessageDTO request, Grupo grupo, Usuario usuarioLogged, UserForGrupo ufg) throws Exception {
		
		comps.requestHelper().setGrupoInUse(grupo);
		if ( (comps.requestHelper().getUserForGrupoInUse().getRole().equals(GrupoRolesEnum.READONLY) )) {
			
			log.error(ExceptionReturnCode.GRUPO_USER_CANT_SEND_MESSAGE_READ_ONLY.toShow("send"));
			throw new ValidationException(ExceptionReturnCode.GRUPO_USER_CANT_SEND_MESSAGE_READ_ONLY);
		}
			
		if ( request.getMedia() != null && request.getMedia().getMediaType().equals(MediaTypeEnum.AUDIO_MESSAGE)) { 
			if (grupo.getGralConf().isBlockAudioMessages()) {
				throw new ValidationException(ExceptionReturnCode.GRUPO_GENERAL_CONF__VALIDATION__AUDIO_MESSAGE__NOT_ALLOW);
			}
		}
		if (grupo.getGralConf().isBlockResend()) {
			request.setBlockResend(true);
		}
		
		if (request.getMedia() != null) {
			if (MediaTypeEnum.IMAGE.equals(request.getMedia().getMediaType())
					||MediaTypeEnum.VIDEO.equals(request.getMedia().getMediaType())
					) {
				if (grupo.getGralConf().isBlackMessageAttachMandatory()
						|| comps.requestHelper().getUsuarioLogged().getMyAccountConf().isBlackMessageAttachMandatory()
						|| comps.requestHelper().getGrupoUserConfInUse().getBlackMessageAttachMandatory().equals(RulesConfEnum.ON)
						) {
					request.setBlackMessage(true);
				}
			}
	}
		if (grupo.getGralConf().isHideMessageReadState()) {
			request.setHideMessageReadState(true);
		}
		if (grupo.getGralConf().getAnonimo().equals(RulesConfEnum.MANDATORY)) {
			request.setAnonimo(true);
		}
		if (request.isAnonimo() &&     
				grupo.getGralConf().getAnonimo().equals(RulesConfEnum.BLOCK)) {
			throw new ValidationException(ExceptionReturnCode.GRUPO_GENERAL_CONF__VALIDATION__ANONIMO_MESSAGE__NOT_ALLOW);
		}
		
		List<UserForGrupo> usersForGrupo = comps.repo().userForGrupo().findByForGrupo(grupo.getIdGrupo());
		Message m = comps.common().mapper().doit(request, usuarioLogged,grupo,usersForGrupo, true);

		if (m.getParentResend() != null) {
			if (m.getParentResend().isBlockResend()) {
				throw new ValidationException(ExceptionReturnCode.MESSAGE_IS_CONFIGURATED_TO_BLOCK_RESEND);
			}
		}
		MessageDTO r = comps.process().message().send(usuarioLogged.getIdUser(), m, grupo.getIdGrupo());
		
		return r;
	}


}

