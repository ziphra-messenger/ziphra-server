package com.privacity.server.websocket;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.privacity.common.config.ConstantProtocolo;
import com.privacity.common.dto.IdDTO;
import com.privacity.common.dto.MessageDTO;
import com.privacity.common.dto.ProtocoloDTO;
import com.privacity.common.enumeration.ExceptionReturnCode;
import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.encrypt.PrivacityIdServices;
import com.privacity.server.encrypt.UsuarioSessionInfoService;
import com.privacity.server.exceptions.PrivacityException;
import com.privacity.server.exceptions.ProcessException;
import com.privacity.server.exceptions.ValidationException;
import com.privacity.server.main.AESToUse;
import com.privacity.server.model.Grupo;
import com.privacity.server.model.Message;
import com.privacity.server.model.MessageDetail;
import com.privacity.server.model.UserForGrupo;
import com.privacity.server.security.Usuario;
import com.privacity.server.util.LocalDateAdapter;

@Service
public class WebSocketSenderService {

	
	@Value("${serverconf.privacityIdAESOn}")
	private boolean encryptIds;
	
	@Autowired
	@Lazy
	
	private PrivacityIdServices privacityIdServices;

	private static final String WEBSOCKET_CHANNEL = "/topic/reply";
	
	@Autowired
	@Lazy
	private FacadeComponent comps;
	
   @Autowired 
   @Lazy
   private WsQueue q;

//	public void sender(Usuario usuario, ProtocoloDTO protocoloDTP) throws PrivacityException {
//		sender(usuario.getUsername(),protocoloDTP);
//	}
//
//	public void sender(String username, ProtocoloDTO protocoloDTO) throws PrivacityException {
//
//		if (encryptIds) {
//			//username = privacityIdServices.getAES(username);
//		}
//
//		
//		sentToUser(username, WEBSOCKET_CHANNEL , new Gson().toJson(protocoloDTO));
//	}
//
	public MessageDTO buildSystemMessage(Grupo grupo, String text) {

		MessageDTO mensaje = new MessageDTO();
		mensaje.setIdGrupo(grupo.getIdGrupo()+"");
		mensaje.setBlackMessage(false);
		mensaje.setTimeMessage(0);
		mensaje.setAnonimo(false);
		mensaje.setSystemMessage(true);
		mensaje.setText(text);

		return mensaje;

	}
//
//
//
//	public void sender(ProtocoloDTO p, Usuario destino) throws PrivacityException {
//		sentToUser(destino.getUsername(), WEBSOCKET_CHANNEL , new Gson().toJson(p));	
//	}
//
////	public void sender(Message m, String p) throws PrivacityException {
////		
////
////		for ( MessageDetail md : m.getMessagesDetail() ) {
////			
////			if (!md.getMessageDetailId().getUserDestino().getIdUser().equals(m.getUserCreation().getIdUser())){
//// 
////				
////				sentToUser(md.getMessageDetailId().getUserDestino().getUsername(), WEBSOCKET_CHANNEL , p);	
////			}
////
////			
////
////		}
//	
////}
//
	private void sentToUser(String user, String urlDestino, String mensaje) throws PrivacityException {
		
		if (comps.webSocket().socketSessionRegistry().getSessionIds(user).size() >0) {
			
		AESToUse c;
		try {
			c = comps.service().usuarioSessionInfo().get(user).getSessionAESToUse();
		
			String retornoFuncionEncriptado = c.getAES(mensaje);


			comps.webSocket().simpMessagingTemplate().convertAndSendToUser(user, urlDestino , retornoFuncionEncriptado);	
		} catch (ValidationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
						
						
				
		}
		
	}
	 
	public ProtocoloDTO buildProtocoloDTO(String component, String action, Object dto) throws ProcessException {
		return buildProtocoloDTO(component, action, dto, null);
	}
	
	public ProtocoloDTO buildProtocoloDTO(String component, String action, MessageDTO messageDTO) throws ProcessException {
		return buildProtocoloDTO(component, action, null, messageDTO);
	}
	
	public ProtocoloDTO buildProtocoloDTO(String component, String action, Object dto, MessageDTO messageDTO) throws ProcessException {
		ProtocoloDTO p = new ProtocoloDTO();
		p.setComponent(component);
		p.setAction(action);
		
		if (encryptIds) {
			try {
				comps.common().privacityId().transformarEncriptarOutOrder(dto);
				comps.common().privacityId().transformarEncriptarOut(dto);
			} catch (Exception e) {
				e.printStackTrace();
				throw new ProcessException(ExceptionReturnCode.ENCRYPT_PROCESS);	
			} 
		}
		p.setObjectDTO(new Gson().toJson(dto));

		return p;
	}

	public ProtocoloDTO buildProtocoloDTO(String component, String action) {
		ProtocoloDTO p = new ProtocoloDTO();
		p.setComponent(component);
		p.setAction(action);

		return p;
	}
//	/*
//	 * @param U ignora ese usuario
//	 */
//
//
	public void senderPing(String username) throws PrivacityException {
		ProtocoloDTO p = comps.webSocket().sender().buildProtocoloDTO("PING_COMPONENT", "PING ACTION");
			addMessageCola(p, username);
	}
	

		
	public void senderMessageToGrupoMinusCreator(String idUsuario, String idGrupo, String componente, String action, MessageDTO messageDTO ) throws PrivacityException {

		if(encryptIds) {
			try {
				privacityIdServices.transformarEncriptarOutOrder(messageDTO);
				privacityIdServices.transformarEncriptarOut(messageDTO);
			} catch (Exception e) {
				throw new PrivacityException(ExceptionReturnCode.ENCRYPT_PROCESS);
			}
			
		}
		
		List<String> lista;
		
		if (messageDTO.isSystemMessage() ) {
			lista = comps.repo().userForGrupo().findByForGrupoAll(Long.parseLong(idGrupo));
		}else {
			lista = comps.repo().userForGrupo().findByForGrupoMinusCreator(Long.parseLong(idGrupo), Long.parseLong( idUsuario));	
		}
		
		
		lista.stream().forEach( username -> 
		senderMessageToGrupoMinusCreatorThread(username,componente, action, messageDTO)
	);
		
	}
	private void senderMessageToGrupoMinusCreatorThread (String username, String componente, String action, MessageDTO messageDTO ){
		
		ProtocoloDTO p = comps.webSocket().sender().buildProtocoloDTO(
				componente,
				action);
		
		p.setMessageDTO(messageDTO);
		addMessageCola(p, username);
	}
	
	public void senderToGrupoMinusCreator(Long idUsuario, long idGrupo, ProtocoloDTO p) throws PrivacityException {
		
		List<String> lista = comps.repo().userForGrupo().findByForGrupoMinusCreator(idGrupo, idUsuario);
		
		
	
		if(encryptIds) {
			try {
				privacityIdServices.transformarEncriptarOutOrder(p.getGrupoDTO());
				privacityIdServices.transformarEncriptarOut(p.getGrupoDTO());
				
				privacityIdServices.transformarEncriptarOutOrder(p.getSaveGrupoGralConfLockResponseDTO());
				privacityIdServices.transformarEncriptarOut(p.getSaveGrupoGralConfLockResponseDTO());
				
			} catch (Exception e) {
				throw new PrivacityException(ExceptionReturnCode.ENCRYPT_PROCESS);
			}
		}
		
		lista.stream().forEach( username -> 
		
		
			addMessageCola(p, username)
		);
		
	}

	private void addMessageCola(ProtocoloDTO p, String username) {
		WsMessage m = new WsMessage();
		m.setUsername(username);
		m.setProtocolo(p);
		q.put(m);
	}

	public void senderToGrupoAllMember(Grupo grupo, ProtocoloDTO p) throws PrivacityException {
		
		List<UserForGrupo> lista = comps.repo().userForGrupo().findByForGrupo(grupo.getIdGrupo());
		//poner paralel stream
		
		for ( int k = 0 ; k < lista.size() ; k++ ) {

			Usuario destino = lista.get(k).getUserForGrupoId().getUser(); 
			
			WsMessage m = new WsMessage();
			m.setUsername(destino.getUsername());
			m.setProtocolo(p);
			// esta mal corregir
			new Runnable() {
				@Override
				public void run() {
					q.put(m);
				}}.run();
		}
		
	}
	
	public void sender(WsMessage msg) {
		String destino;
		
		destino = msg.getUsername();
		try {	
//		if(encryptIds) {
//			privacityIdServices.transformarEncriptarOutOrder(msg.getProtocolo().getMessageDTO());
//			privacityIdServices.transformarEncriptarOut(msg.getProtocolo().getMessageDTO());
//		}


	        Gson gson = new GsonBuilder()
	                .setPrettyPrinting()
	                .registerTypeAdapter(LocalDateTime.class, new LocalDateAdapter())
	                .create();
	        
			sentToUser(destino, WEBSOCKET_CHANNEL , gson.toJson(msg.getProtocolo()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
