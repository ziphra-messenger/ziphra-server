package ar.ziphra.appserver.component.message;


import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import ar.ziphra.common.dto.IdMessageDTO;
import ar.ziphra.common.dto.MediaDTO;
import ar.ziphra.common.dto.MessageDTO;
import ar.ziphra.common.dto.MessageDetailDTO;
import ar.ziphra.common.dto.ProtocoloDTO;
import ar.ziphra.common.enumeration.MessageState;
import ar.ziphra.common.enumeration.ProtocoloActionsEnum;
import ar.ziphra.common.enumeration.ProtocoloComponentsEnum;
import ar.ziphra.common.exceptions.ProcessException;
import ar.ziphra.common.exceptions.ZiphraException;
import ar.ziphra.core.model.Grupo;
import ar.ziphra.core.model.Media;
import ar.ziphra.core.model.Message;
import ar.ziphra.core.model.MessageDetail;
import ar.ziphra.core.model.MessageDetailId;
import ar.ziphra.core.model.MessageId;
import ar.ziphra.core.model.Usuario;
import ar.ziphra.appserver.component.common.service.facade.FacadeComponent;
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
		log.trace("getNormal: ", grupo.getIdGrupo(), " " + idMessage );


		MessageDTO response = new MessageDTO();
		response.setIdGrupo(grupo.getIdGrupo()+"");
		response.setText(m.getText());
		response.setSecretKeyPersonal(m.isSecretKeyPersonal());
		response.setIdMessage(idMessage+"");
		response.setMessagesDetail(new MessageDetailDTO[1]);
		response.setBlackMessage(m.isBlackMessage());
		response.setAnonimo(m.isAnonimo());
		response.setTimeMessage(m.getTimeMessage());
		response.setSystemMessage(m.isSystemMessage());

		response.setUsuarioCreacion(comps.common().mapper().doitForGrupo(grupo, m.getUserCreation(),m.isChangeNicknameToRandom()));
		response.setChangeNicknameToRandom(grupo.getGralConf().isRandomNickname());

		



		Set<MessageDetail> details;

		if (!m.getMessageId().getGrupo().getGralConf().isHideMessageReadState()) { 
			
			details = m.getMessagesDetail();
			
		}else {
			MessageDetailId mdid = new MessageDetailId();
			mdid.setMessage(m);
			mdid.setUserDestino(comps.requestHelper().getUsuarioLogged());
			Optional<MessageDetail> userDetail = comps.repo().messageDetail().findById(mdid);
			
			Set<MessageDetail> s = new HashSet<MessageDetail>() ;
			if (userDetail.isPresent()) {
				
				s.add(userDetail.get());
				
			}
			details=s;		
			
		}


		
		response.setMessagesDetail(new MessageDetailDTO[details.size()]);
		
		int i=0;
		for ( MessageDetail d : details) {

			response.getMessagesDetail()[i] = new MessageDetailDTO();
			//			if ( u.getUsername().equals(d.getMessageDetailId().getUserDestino())) {
			//				response.getMessagesDetailDTO()[i].setText(d.getText());	
			//			}

			response.getMessagesDetail()[i].setIdGrupo(grupo.getIdGrupo()+"");
			response.getMessagesDetail()[i].setIdMessage(idMessage+"");
			//response.getMessagesDetailDTO()[i].setIdMessageDetail(d.getMessageDetailId().getIdMessageDetail()+"");

			response.getMessagesDetail()[i].setUsuarioDestino(
					comps.common().mapper().doitForGrupo(grupo, d.getMessageDetailId().getUserDestino(),m.isChangeNicknameToRandom())
					);
			
			if (d.isHideRead() && d.getState().equals(MessageState.DESTINY_READ) && d.getMessageDetailId().getUserDestino().getIdUser().equals( comps.requestHelper().getUsuarioId())) {
				response.getMessagesDetail()[i].setEstado(MessageState.DESTINY_DELIVERED);
				response.setHideMessageDetails(false);
			}else {
				response.getMessagesDetail()[i].setEstado(d.getState());
				response.setHideMessageDetails(d.isHideRead());
			}

			

			i++;
		}

	
		Media media = m.getMedia();
		MediaDTO mediaDTO = comps.common().mapper().doit(media,true);
		response.setMedia(mediaDTO);
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
	public MessageDTO[] getAllidMessageDestinyServerMessages() throws Exception {
		Usuario u = comps.requestHelper().getUsuarioLogged();
		List<MessageDetail> l = comps.repo().messageDetail().getAllidMessageDestinyServerMessages(u.getIdUser());

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

	public MessageDetailDTO changeState(Usuario u, Message m, MessageDetail md, MessageState state, MessageDetailDTO request, MessageState oldState) throws Exception {


		if (m.isTimeMessage() && state.equals(MessageState.DESTINY_READ)) {
			md.setDeleted(true);
		}
		md.setState(state);

		boolean hide=false;
		if (md.isHideRead() || m.getMessageId().getGrupo().getGralConf().isHideMessageReadState()) {
			hide = true;
		}
		
		MessageDetailDTO retornoServer = comps.common().mapper().doit(md);
		retornoServer.setHideRead(hide);

			


		comps.repo().messageDetail().save(md);	
			
		
				
			MessageDetailDTO retornoWS = comps.common().mapper().doit(md);
			
			if  (hide && md.getState().equals(MessageState.DESTINY_READ)  ) {
				retornoWS.setEstado(MessageState.DESTINY_DELIVERED);
				
				if (!oldState.equals(MessageState.DESTINY_DELIVERED) ) {
					ProtocoloDTO p = comps.webSocket().sender().buildProtocoloDTO(
							ProtocoloComponentsEnum.MESSAGE,
							ProtocoloActionsEnum.MESSAGE_CHANGE_STATE ,
							retornoWS);

					
					comps.webSocket().sender().senderToGrupo(p, comps.requestHelper().getGrupoInUse().getIdGrupo(), comps.requestHelper().getUsuarioUsername());

				}
				
				
			}else{
				ProtocoloDTO p = comps.webSocket().sender().buildProtocoloDTO(
						ProtocoloComponentsEnum.MESSAGE,
						ProtocoloActionsEnum.MESSAGE_CHANGE_STATE ,
						retornoWS);

				
				comps.webSocket().sender().senderToGrupo(p, comps.requestHelper().getGrupoInUse().getIdGrupo(), comps.requestHelper().getUsuarioUsername());

			}
		
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
		//idm.setIdMessage(comps.factory().idsGenerator().getNextMessageId());

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

		m.setChangeNicknameToRandom(m.getMessageId().getGrupo().getGralConf().isRandomNickname());

		
		if (m.isAnonimo()) {
			m.setUserCreation(comps.util().usuario().getUsuarioAnonimo());
		}
		comps.repo().message().save(m);
		
		MessageDTO responseServer=null;
		
		if (!m.getMessageId().getGrupo().getGralConf().isHideMessageDetails() || m.isAnonimo()) {
			responseServer = comps.common().mapper().doit(m);
			MessageDTO responseWs = comps.common().mapper().doit(m);
			responseWs.setChangeNicknameToRandom(m.getMessageId().getGrupo().getGralConf().isRandomNickname());
		ProtocoloDTO p = comps.webSocket().sender().buildProtocoloDTO(ProtocoloComponentsEnum.MESSAGE,ProtocoloActionsEnum.MESSAGE_RECIVIED,responseWs);

		if (m.isSystemMessage() ) {
			comps.webSocket().sender().senderToGrupo(p, comps.requestHelper().getGrupoInUse().getIdGrupo());
		}else {
			comps.webSocket().sender().senderToGrupo(p, comps.requestHelper().getGrupoInUse().getIdGrupo(), comps.requestHelper().getUsuarioUsername());
		}
			
		}else {
			

			
			for (MessageDetail d : m.getMessagesDetail()) {
				MessageDTO responseWs = comps.common().mapper().doit(m);
				MessageDetailDTO[] md = new MessageDetailDTO[1];
				md[0]=comps.common().mapper().doit(d);
				responseWs.setMessagesDetail(md);

					if ((d.getMessageDetailId().getUserDestino().getIdUser().longValue() != comps.requestHelper().getUsuarioId().longValue())) {
					ProtocoloDTO p = comps.webSocket().sender().buildProtocoloDTO(ProtocoloComponentsEnum.MESSAGE,ProtocoloActionsEnum.MESSAGE_RECIVIED,responseWs);
					
					
					if (m.isSystemMessage() ) {
						comps.webSocket().sender().senderToUser(p, d.getMessageDetailId().getUserDestino());
					}else {
						comps.webSocket().sender().senderToUser(p, d.getMessageDetailId().getUserDestino());
					}
				}else {
					responseServer= responseWs;
				}

			}
			
		}
		return comps.common().mapper().doitWithOutTextAndMedia(responseServer);
		

	}	

	public MessageDTO deleteForEveryone(Long idUsuario, Message message) throws ZiphraException, IOException {

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

