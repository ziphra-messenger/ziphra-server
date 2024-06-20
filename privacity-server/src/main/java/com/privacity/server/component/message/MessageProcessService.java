package com.privacity.server.component.message;


import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.privacity.common.config.ConstantProtocolo;
import com.privacity.common.dto.GrupoDTO;
import com.privacity.common.dto.IdDTO;
import com.privacity.common.dto.IdMessageDTO;
import com.privacity.common.dto.MediaDTO;
import com.privacity.common.dto.MessageDTO;
import com.privacity.common.dto.MessageDetailDTO;
import com.privacity.common.dto.ProtocoloDTO;
import com.privacity.common.dto.response.SaveGrupoGralConfLockResponseDTO;
import com.privacity.common.enumeration.MessageState;
import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.exceptions.PrivacityException;
import com.privacity.server.exceptions.ProcessException;
import com.privacity.server.model.Grupo;
import com.privacity.server.model.Media;
import com.privacity.server.model.Message;
import com.privacity.server.model.MessageDetail;
import com.privacity.server.security.Usuario;
import com.privacity.server.util.PrivacityLogguer;
import com.privacity.server.websocket.WsMessage;
import com.privacity.server.websocket.WsQueue;

import lombok.extern.java.Log;


@Service
@Log
public class MessageProcessService {

	@Autowired @Lazy
	private FacadeComponent comps;
	
	   @Autowired 
	   @Lazy
	   private WsQueue q;
	
		@Autowired @Lazy
		private PrivacityLogguer privacityLogguer;
		
	public MessageDTO get(Grupo grupo, Long idMessage, Message m, Usuario u) throws Exception {

//		if (m.isAnonimo()) {
//			return getAnonimo(grupo,idMessage, m,u);
//		}else {
//			return getNormal(grupo,idMessage,m);
//		}
		return getNormal(grupo,idMessage,m);
	}
	
	private MessageDTO getAnonimo(Grupo grupo, Long idMessage, Message m, Usuario u) throws Exception {
		
	
		
		MessageDTO response = new MessageDTO();
		response.setIdGrupo(m.getMessageId().getGrupo().getIdGrupo()+"");
		response.setText(m.getText());
		

		response.setUsuarioCreacion(comps.common().mapper().doit(m.getUserCreation()));
		
		response.setIdMessage(m.getMessageId().getIdMessage()+"");
		response.setMessagesDetailDTO(new MessageDetailDTO[1]);
		
		Set<MessageDetail> details = m.getMessagesDetail();
		
		response.setBlackMessage(m.isBlackMessage());
		response.setAnonimo(m.isAnonimo());
		response.setTimeMessage(m.getTimeMessage());
		response.setSystemMessage(m.isSystemMessage());
		
		Media media = m.getMedia();
		MediaDTO mediaDTO = comps.common().mapper().doit(media,true);
		response.setMediaDTO(mediaDTO);
		
		if (u.getUsername().equals(m.getUserCreation())) {
			
			response.setMessagesDetailDTO(new MessageDetailDTO[details.size()]);
			int i=0;
			for ( MessageDetail d : details) {
				
				response.getMessagesDetailDTO()[i] = new MessageDetailDTO();

				
				response.getMessagesDetailDTO()[i].setIdGrupo(m.getMessageId().getGrupo().getIdGrupo()+"");
				response.getMessagesDetailDTO()[i].setIdMessage(m.getMessageId().getIdMessage()+"");

				response.getMessagesDetailDTO()[i].setUsuarioDestino(comps.common().mapper().doit(d.getMessageDetailId().getUserDestino()));
				
				response.getMessagesDetailDTO()[i].setEstado(d.getState().toString());

				i++;
			}

			return response;
		}else {
			response.setUsuarioCreacion(null);
			response.setMessagesDetailDTO(new MessageDetailDTO[1]);
			//int i=0;
			for ( MessageDetail d : details) {
				
				if (d.getMessageDetailId().getUserDestino().getIdUser().equals(u.getIdUser())){
					response.getMessagesDetailDTO()[0] = new MessageDetailDTO();
					response.getMessagesDetailDTO()[0].setIdGrupo(m.getMessageId().getGrupo().getIdGrupo()+"");
					response.getMessagesDetailDTO()[0].setIdMessage(m.getMessageId().getIdMessage()+"");
					response.getMessagesDetailDTO()[0].setUsuarioDestino(comps.common().mapper().doit(d.getMessageDetailId().getUserDestino()));
					response.getMessagesDetailDTO()[0].setEstado(d.getState().toString());
				
				}
				//i++;
			}

			return response;
			
		}

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

			response.getMessagesDetailDTO()[i].setEstado(d.getState().toString());

			i++;
		}
		
		privacityLogguer.write(response);
		
		Media media = m.getMedia();
		MediaDTO mediaDTO = comps.common().mapper().doit(media,true);
		response.setMediaDTO(mediaDTO);
		if (media != null) {
			System.out.println(media.toString());
		}
		
		
		return response;
	}
	
	public MessageDTO[] getHistorialId(IdMessageDTO id) throws Exception {
		
		if ( id.getIdMessage() == null ) {
			id.setIdMessage("9123372036854775807");
		}
		
		Usuario u = getUser();
		List<MessageDetail> l = comps.repo().messageDetail().getHistorial(u.getIdUser(),id.getIdGrupoLong(),id.getIdMessageLong(), 
				 PageRequest.of(0, 10));
		
		
		MessageDTO[] r = new MessageDTO[l.size()];
		for (int i= 0 ; i < l.size() ; i++) {
			MessageDTO dto = new MessageDTO();
			dto.setIdMessage(l.get(i).getMessageDetailId().getMessage().getMessageId().getIdMessage().toString());
			dto.setIdGrupo(l.get(i).getMessageDetailId().getMessage().getMessageId().getGrupo().getIdGrupo().toString());
			
			r[i] = dto;
		}
		
		return r;
	}
	
	public MessageDTO[] getAllidMessageUnreadMessages() throws Exception {
		Usuario u = getUser();
		List<MessageDetail> l = comps.repo().messageDetail().getAllidMessageUnreadMessages(u.getIdUser());
		
		MessageDTO[] r = new MessageDTO[l.size()];
		for (int i= 0 ; i < l.size() ; i++) {
			MessageDTO dto = new MessageDTO();
			dto.setIdMessage(l.get(i).getMessageDetailId().getMessage().getMessageId().getIdMessage().toString());
			dto.setIdGrupo(l.get(i).getMessageDetailId().getMessage().getMessageId().getGrupo().getIdGrupo().toString());
			
			r[i] = dto;
		}
		
		return r;
	}
	
	public void emptyList(String idGrupo) throws Exception {
		Usuario u = getUser();
		
		comps.repo().messageDetail().deleteAllMessageDetailByGrupoAndUser(Long.parseLong(idGrupo), u.getIdUser());
		
	}
	
	public void deleteAllMyMessageForEverybodyByGrupo(String idGrupo) throws Exception {
		Usuario u = getUser();
		
		comps.repo().messageDetail().deleteAllMyMessageDetailForEverybodyByGrupo(Long.parseLong(idGrupo), u.getIdUser());
		comps.repo().message().deleteAllMyMessageForEverybodyByGrupo(Long.parseLong(idGrupo), u.getIdUser());
	}

	public MessageDetailDTO changeState(Usuario u, Message m, MessageDetail md, MessageState state, MessageDetailDTO request) throws Exception {

	
		if (m.isTimeMessage() && state.equals(MessageState.DESTINY_READED)) {
			md.setDeleted(true);
		}
		md.setState(state);
		
		MessageDetailDTO retornoWS = comps.common().mapper().doit(md);
		MessageDetailDTO retornoServer = comps.common().mapper().doit(md);
		
		Thread saveT = new Thread() {

			public void run() {
				comps.repo().messageDetail().save(md);		

			}
		};
		
		saveT.start();
		
		ProtocoloDTO p = comps.webSocket().sender().buildProtocoloDTO(
				ConstantProtocolo.PROTOCOLO_COMPONENT_MESSAGE,
		        ConstantProtocolo.PROTOCOLO_ACTION_MESSAGE_CHANGE_STATE,
		        retornoWS);


		Thread t = new Thread() {
		
			public void run() {
				
				try {



					comps.webSocket().sender().senderToGrupoMinusCreator(u.getIdUser(), m.getMessageId().getGrupo().getIdGrupo(), p);
					
					
				} catch (PrivacityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		};
		t.start();
		return retornoServer;
		
	}
	public MessageDTO[] loadMessages(MessageDTO request) throws Exception {
		return null;
	}
	
	public MessageDTO send(Long idUsuario, Message request, Long idGrupo) throws Exception {
	
		/*if (request.isAnonimo()) {
			return sendAnonimo(request);
		}else {*/
			return sendNormal(idUsuario, request, idGrupo);
		//}
		
	}
//	private MessageDTO sendAnonimo(Message m) throws Exception {
//
//		Set<MessageDetail> details = m.getMessagesDetail();
//
//		m.setMessagesDetail(null);
//		Media media = m.getMedia();
//		m.setMedia(null);
//		comps.repo().message().save(m);
//		
//		//comps.repo().message().insertMedia(media.getData(), media.getMediaType().ordinal(), media.getMessage().getMessageId().getGrupo().getIdGrupo(), media.getMessage().getMessageId().getIdMessage());
//		comps.repo().messageDetail().saveAll(details);
//		
//		//comps.repo().message().save(m);
//		
//		new Runnable() {
//
//			@Override
//			public void run() {
//				m.setMedia(media);
//				comps.repo().message().save(m);
//				
//			}}.run();
//		
//
//		m.setMessagesDetail(details);
//
//		for (MessageDetail md : details) {
//			
//			if (!md.getMessageDetailId().getUserDestino().getIdUser().equals(m.getUserCreation().getIdUser())
//			 && (comps.service().usuarioSessionInfo().isOnline(md.getMessageDetailId().getUserDestino().getUsername())) 
//					
//					) {
//
//			
//			new Runnable() {
//
//				@Override
//				public void run() {
//					try {
//						IdDTO response = new IdDTO(m.getMessageId().getIdMessage());
//						
//						
//						ProtocoloDTO p = comps.webSocket().sender().buildProtocoloDTO(
//								ConstantProtocolo.PROTOCOLO_COMPONENT_MESSAGE,
//								ConstantProtocolo.PROTOCOLO_ACTION_MESSAGE_RECIVIED);
//						
//						p.setObjectDTO(new Gson().toJson(p));
//				
//						comps.webSocket().sender().sender(p,md.getMessageDetailId().getUserDestino());
//				
//			} catch (ProcessException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (PrivacityException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	
//			
//				}}.run();
//			}
//		}
//		return comps.common().mapper().doitWithOutTextAndMedia(m);
//
//    }
	
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
		Thread allT = new Thread() {

			@Override
			public void run() {

//				Thread messageSaveT = new Thread() {
//
//					@Override
//					public void run() {
//
////						comps.repo().message().save(m);
//
//					}};
//					messageSaveT.start();


					

					ProtocoloDTO p = comps.webSocket().sender().buildProtocoloDTO(
								ConstantProtocolo.PROTOCOLO_COMPONENT_MESSAGE,
								ConstantProtocolo.PROTOCOLO_ACTION_MESSAGE_RECIVIED);
						p.setMessageDTO(responseWs);
						
					if (objecto != null) {
						if (objecto instanceof GrupoDTO) {
							p.setGrupoDTO((GrupoDTO)objecto);
						}else if(objecto instanceof SaveGrupoGralConfLockResponseDTO) {
							p.setSaveGrupoGralConfLockResponseDTO((SaveGrupoGralConfLockResponseDTO) objecto);
						}
						
						
					}
					

					try {
						comps.webSocket().sender().senderMessageToGrupoMinusCreator( idUsuario+"", idGrupo+"",  ConstantProtocolo.PROTOCOLO_COMPONENT_MESSAGE, ConstantProtocolo.PROTOCOLO_ACTION_MESSAGE_RECIVIED, responseWs);
					} catch (PrivacityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}			
					

			}};
			allT.start();

			return comps.common().mapper().doitWithOutTextAndMedia(responseServer);

	}	
	
	public MessageDTO deleteForEveryone(Long idUsuario, Message message) throws PrivacityException {
		
		
			MessageDTO mRemoveWS = new MessageDTO();
			mRemoveWS.setIdGrupo(message.getMessageId().getGrupo().getIdGrupo()+"");
			mRemoveWS.setIdMessage(message.getMessageId().getIdMessage()+"");
	
			MessageDTO mRemoveReturn = new MessageDTO();
			mRemoveReturn.setIdGrupo(message.getMessageId().getGrupo().getIdGrupo()+"");
			mRemoveReturn.setIdMessage(message.getMessageId().getIdMessage()+"");
			

			comps.repo().messageDetail().deleteLogicByMessageDetailIdMessage(message.getMessageId().getGrupo().getIdGrupo(), message.getMessageId().getIdMessage());
			comps.repo().message().deleteLogic(message.getMessageId().getGrupo().getIdGrupo(), message.getMessageId().getIdMessage());

			comps.webSocket().sender().senderMessageToGrupoMinusCreator( idUsuario+"",message.getMessageId().getGrupo().getIdGrupo()+"",  ConstantProtocolo.PROTOCOLO_COMPONENT_MESSAGE, "/message/deleteForEveryone", mRemoveWS);
		
		mRemoveReturn.setIdGrupo(message.getMessageId().getGrupo().getIdGrupo()+"");
		mRemoveReturn.setIdMessage(message.getMessageId().getIdMessage()+"");
		
		return mRemoveReturn;

	}    	


	private Usuario getUser() {
		Authentication auth = SecurityContextHolder
	            .getContext()
	            .getAuthentication();
	    UserDetails userDetail = (UserDetails) auth.getPrincipal();
	    
		Usuario u = comps.repo().user().findByUsername(userDetail.getUsername()).get();
		return u;
	}    
}
