package com.privacity.server.component.message;


import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.privacity.common.dto.IdMessageDTO;
import com.privacity.common.dto.MediaDTO;
import com.privacity.common.dto.MessageDTO;
import com.privacity.common.dto.MessageDetailDTO;
import com.privacity.common.dto.ProtocoloDTO;
import com.privacity.common.enumeration.MessageState;
import com.privacity.common.enumeration.ProtocoloActionsEnum;
import com.privacity.common.enumeration.ProtocoloComponentsEnum;
import com.privacity.common.exceptions.PrivacityException;
import com.privacity.common.exceptions.ProcessException;
import com.privacity.core.model.Grupo;
import com.privacity.core.model.Media;
import com.privacity.core.model.Message;
import com.privacity.core.model.MessageDetail;
import com.privacity.core.model.MessageId;
import com.privacity.core.model.Usuario;
import com.privacity.server.component.common.service.facade.FacadeComponent;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class MessageProcessService {

	@Autowired @Lazy
	private FacadeComponent comps;



	public MessageDTO get(Grupo grupo, Long idMessage, Message m, Usuario u) throws Exception {

		return getNormal(grupo,idMessage,m);
	}



	private MessageDTO getNormal(Grupo grupo, Long idMessage, Message m) throws Exception {



		MessageDTO response = new MessageDTO();
		response.setIdGrupo(grupo.getIdGrupo()+"");
		response.setText(m.getText());
		response.setSecretKeyPersonal(m.isSecretKeyPersonal());
		response.setIdMessage(idMessage+"");
		response.setMessagesDetailDTO(new MessageDetailDTO[1]);
		response.setBlackMessage(m.isBlackMessage());
		response.setAnonimo(m.isAnonimo());
		response.setTimeMessage(m.getTimeMessage());
		response.setSystemMessage(m.isSystemMessage());

		response.setUsuarioCreacion(comps.common().mapper().doitForGrupo(grupo, m.getUserCreation()));
		Set<MessageDetail> details = m.getMessagesDetail();

		response.setMessagesDetailDTO(new MessageDetailDTO[details.size()]);



		int i=0;
		for ( MessageDetail d : details) {

			response.getMessagesDetailDTO()[i] = new MessageDetailDTO();
			//			if ( u.getUsername().equals(d.getMessageDetailId().getUserDestino())) {
			//				response.getMessagesDetailDTO()[i].setText(d.getText());	
			//			}

			response.getMessagesDetailDTO()[i].setIdGrupo(grupo.getIdGrupo()+"");
			response.getMessagesDetailDTO()[i].setIdMessage(idMessage+"");
			//response.getMessagesDetailDTO()[i].setIdMessageDetail(d.getMessageDetailId().getIdMessageDetail()+"");

			response.getMessagesDetailDTO()[i].setUsuarioDestino(
					comps.common().mapper().doitForGrupo(grupo, d.getMessageDetailId().getUserDestino())
					);

			response.getMessagesDetailDTO()[i].setEstado(d.getState());

			i++;
		}

	
		Media media = m.getMedia();
		MediaDTO mediaDTO = comps.common().mapper().doit(media,true);
		response.setMediaDTO(mediaDTO);
		if (media != null) {
			log.trace(media.toString());
		}


		return response;
	}

	public MessageDTO[] getHistorialId(IdMessageDTO id) throws Exception {

		if ( id.getIdMessage() == null ) {
			id.setIdMessage("9123372036854775807");
		}

		Usuario u = comps.requestHelper().getUsuarioLogged();
		List<MessageDetail> l = comps.repo().messageDetail().getHistorial(u.getIdUser(),id.convertIdGrupoToLong(),id.convertIdMessageToLong(), 
				PageRequest.of(0, 10));


		MessageDTO[] r = comps.util().message().convertArrayListToArray(l);

		return r;
	}

	public MessageDTO[] getAllidMessageUnreadMessages() throws Exception {
		Usuario u = comps.requestHelper().getUsuarioLogged();
		List<MessageDetail> l = comps.repo().messageDetail().getAllidMessageUnreadMessages(u.getIdUser());

		MessageDTO[] r = comps.util().message().convertArrayListToArray(l);

		return r;
	}

	public void emptyList(String idGrupo) throws Exception {
		Usuario u = comps.requestHelper().getUsuarioLogged();

		comps.repo().messageDetail().deleteAllMessageDetailByGrupoAndUser(Long.parseLong(idGrupo), u.getIdUser());

	}

	public void deleteAllMyMessageForEverybodyByGrupo(String idGrupo) throws Exception {
		Usuario u = comps.requestHelper().getUsuarioLogged();

		comps.repo().messageDetail().deleteAllMyMessageDetailForEverybodyByGrupo(Long.parseLong(idGrupo), u.getIdUser());
		comps.repo().message().deleteAllMyMessageForEverybodyByGrupo(Long.parseLong(idGrupo), u.getIdUser());
	}

	public MessageDetailDTO changeState(Usuario u, Message m, MessageDetail md, MessageState state, MessageDetailDTO request) throws Exception {


		if (m.isTimeMessage() && state.equals(MessageState.DESTINY_READED)) {
			md.setDeleted(true);
		}
		md.setState(state);


		MessageDetailDTO retornoServer = comps.common().mapper().doit(md);
		MessageDetailDTO retornoWS = comps.common().mapper().doit(md);

		comps.repo().messageDetail().save(md);		

		ProtocoloDTO p = comps.webSocket().sender().buildProtocoloDTO(
				ProtocoloComponentsEnum.MESSAGE,
				ProtocoloActionsEnum.MESSAGE_CHANGE_STATE ,
				retornoWS);

		comps.webSocket().sender().senderToGrupo(p, comps.requestHelper().getGrupoInUse().getIdGrupo(), comps.requestHelper().getUsuarioUsername());

		return retornoServer;


	}
	public MessageDTO[] loadMessages(MessageDTO request) throws Exception {
		return null;
	}

	public Message buildMessage(Message m) throws Exception {
		Media media = m.getMedia();
		m.setMedia(media);

		MessageId idm = new MessageId();
		idm.setGrupo(comps.requestHelper().getGrupoInUse());
		idm.setIdMessage(comps.factory().messageIdSequence().get());

		m.setMessageId(idm);

		return m;

	}	

	public MessageDTO send(Long idUsuario, Message request, Long idGrupo) throws Exception {
		return sendNormal(idUsuario, request, idGrupo);


	}

	public void deleteForMe(MessageDetail detail) throws ProcessException {

		detail.setDeleted(true);
		comps.repo().messageDetail().save(detail);

	}

	public MessageDTO sendNormal(Long idUsuario, Message m ,Long idGrupo) throws Exception {
		return sendNormal (idUsuario, m, idGrupo,null);
	}
	public MessageDTO sendNormal(Long idUsuario, Message m, Long idGrupo, Object objecto) throws Exception {
		Media media = m.getMedia();
		m.setMedia(media);

		MessageDTO responseServer = comps.common().mapper().doit(m);

		MessageDTO responseWs = comps.common().mapper().doit(m);
		comps.repo().message().save(m);

		ProtocoloDTO p = comps.webSocket().sender().buildProtocoloDTO(ProtocoloComponentsEnum.MESSAGE,ProtocoloActionsEnum.MESSAGE_RECIVIED,responseWs);

		if (m.isSystemMessage() ) {
			comps.webSocket().sender().senderToGrupo(p, comps.requestHelper().getGrupoInUse().getIdGrupo());
		}else {
			comps.webSocket().sender().senderToGrupo(p, comps.requestHelper().getGrupoInUse().getIdGrupo(), comps.requestHelper().getUsuarioUsername());
		}

		return comps.common().mapper().doitWithOutTextAndMedia(responseServer);

	}	

	public MessageDTO deleteForEveryone(Long idUsuario, Message message) throws PrivacityException, IOException {

		MessageDTO mRemoveWS = new MessageDTO();
		mRemoveWS.setIdGrupo(message.getMessageId().getGrupo().getIdGrupo()+"");
		mRemoveWS.setIdMessage(message.getMessageId().getIdMessage()+"");

		MessageDTO mRemoveReturn = new MessageDTO();
		mRemoveReturn.setIdGrupo(message.getMessageId().getGrupo().getIdGrupo()+"");
		mRemoveReturn.setIdMessage(message.getMessageId().getIdMessage()+"");


		comps.repo().messageDetail().deleteLogicByMessageDetailIdMessage(message.getMessageId().getGrupo().getIdGrupo(), message.getMessageId().getIdMessage());
		comps.repo().message().deleteLogic(message.getMessageId().getGrupo().getIdGrupo(), message.getMessageId().getIdMessage());

		ProtocoloDTO p = comps.webSocket().sender().buildProtocoloDTO(ProtocoloComponentsEnum.MESSAGE,ProtocoloActionsEnum.MESSAGE_DELETE_FOR_EVERYONE,mRemoveWS);
		comps.webSocket().sender().senderToGrupo(p, comps.requestHelper().getGrupoInUse().getIdGrupo(), comps.requestHelper().getUsuarioUsername());

		mRemoveReturn.setIdGrupo(message.getMessageId().getGrupo().getIdGrupo()+"");
		mRemoveReturn.setIdMessage(message.getMessageId().getIdMessage()+"");

		return mRemoveReturn;

	}    	

}

