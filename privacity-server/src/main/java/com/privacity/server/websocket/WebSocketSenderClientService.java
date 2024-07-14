package com.privacity.server.websocket;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.privacity.common.dto.GrupoDTO;
import com.privacity.common.dto.MessageDTO;
import com.privacity.common.dto.ProtocoloDTO;
import com.privacity.common.enumeration.ProtocoloActionsEnum;
import com.privacity.common.enumeration.ProtocoloComponentsEnum;
import com.privacity.server.common.enumeration.Urls;
import com.privacity.server.common.utils.UtilsString;
import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.exceptions.PrivacityException;
import com.privacity.server.exceptions.ProcessException;
import com.privacity.server.model.Grupo;
import com.privacity.server.security.JwtUtils;
import com.privacity.server.security.Usuario;

@Service
public class WebSocketSenderClientService {


	@Value("${com.privacity.server.websocket.server}")
	private String server;
	
	private static final String WEBSOCKET_CHANNEL = "/topic/reply";
	
	@Autowired
	@Lazy
	private FacadeComponent comps;
	
	@Autowired @Lazy
	private JwtUtils jwtUtils;
   


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
	public MessageDTO buildSystemMessage(Grupo grupo, String text) throws ProcessException {

		MessageDTO mensaje = new MessageDTO();
		mensaje.setIdGrupo(grupo.getIdGrupo()+"");
		mensaje.setBlackMessage(false);
		mensaje.setTimeMessage(0);
		mensaje.setAnonimo(false);
		mensaje.setSystemMessage(true);
		mensaje.setText(text);
		mensaje.setSystemMessage(true);
		mensaje.setUsuarioCreacion(comps.util().usuario().getUsuarioSystemDTO());
		
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
//	private void sentToUser(String user, String urlDestino, String mensaje) throws PrivacityException {
//		System.out.println(" sentToUser(String user, String urlDestino, String mensaje)");
//		System.out.println("SERVER CORE - Salida WS");
//		System.out.println("user: " + user);
//		System.out.println("urlDestino: " + urlDestino);
//		System.out.println("mensaje: " + mensaje);
//		if (comps.webSocket().socketSessionRegistry().getSessionIds(user).size() >0) {
//			
//		try {
//		
//			//String retornoFuncionEncriptado =comps.service().usuarioSessionInfo().encryptSessionAESServerWS(user,mensaje);
//
//
//			comps.webSocket().simpMessagingTemplate().convertAndSendToUser(user, urlDestino , mensaje);	
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//						
//						
//				
//		}
		
	
//	 
//	public ProtocoloDTO buildProtocoloDTO(ProtocoloComponentsEnum component, ProtocoloActionsEnum action, Object dto) throws ProcessException {
//		return buildProtocoloDTO(component, action, dto, null);
//	}
//	
//	public ProtocoloDTO buildProtocoloDTO(ProtocoloComponentsEnum component, ProtocoloActionsEnum action, MessageDTO messageDTO) throws ProcessException {
//		return buildProtocoloDTO(component, action, null, messageDTO);
//	}
//	
//	public ProtocoloDTO buildProtocoloDTO(ProtocoloComponentsEnum component, ProtocoloActionsEnum action, Object dto, MessageDTO messageDTO) throws ProcessException {
//		ProtocoloDTO p = new ProtocoloDTO();
//		p.setComponent(component);
//		p.setAction(action);
//		
//
//		p.setObjectDTO(new Gson().toJson(dto));
//
//		return p;
//	}

//	public ProtocoloDTO buildProtocoloDTO(ProtocoloComponentsEnum component, ProtocoloActionsEnum action) {
//		ProtocoloDTO p = new ProtocoloDTO();
//		p.setComponent(component);
//		p.setAction(action);
//
//		return p;
//	}
//	/*
//	 * @param U ignora ese usuario
//	 */
//
//
//	public void senderPing(String username) throws PrivacityException {
//		ProtocoloDTO p = comps.webSocket().sender().buildProtocoloDTO(ProtocoloComponentsEnum.PING, ProtocoloActionsEnum.PING_ACTION);
//			addMessageCola(p, username);
//	}
	

		
//	public void senderMessageToGrupoMinusCreator(String idUsuario, String idGrupo, ProtocoloComponentsEnum componente, ProtocoloActionsEnum action, MessageDTO messageDTO ) throws PrivacityException {
//
//
//		
//		List<String> lista;
//		
//		if (messageDTO.isSystemMessage() ) {
//			lista = comps.repo().userForGrupo().findByForGrupoAll(Long.parseLong(idGrupo));
//		}else {
//			lista = comps.repo().userForGrupo().findByForGrupoMinusCreator(Long.parseLong(idGrupo), Long.parseLong( idUsuario));	
//		}
//		
//		
//		lista.stream().forEach( username -> 
//		senderMessageToGrupoMinusCreatorThread(username,componente, action, messageDTO)
//	);
//		
//	}
//	private void senderMessageToGrupoMinusCreatorThread (String username, ProtocoloComponentsEnum componente, ProtocoloActionsEnum action, MessageDTO messageDTO ){
//		
//		ProtocoloDTO p = comps.webSocket().sender().buildProtocoloDTO(
//				componente,
//				action);
//		
//		p.setMessageDTO(messageDTO);
//		addMessageCola(p, username);
//	}
	
	public void senderToGrupoMinusCreator(String username, long idGrupo, ProtocoloDTO p) throws PrivacityException {
		
		senderToGrupo(p,idGrupo, username, true);
		
	}


	
	public void senderToUser(ProtocoloDTO p, Usuario usuario) throws PrivacityException {
		System.out.println("senderToUser(ProtocoloDTO p, Usuario usuario");
		String protocoloEncr = comps.service().usuarioSessionInfo().encryptProtocoloWS(usuario.getUsername(), p, getUrl().name());

		WsMessage m = new WsMessage();
		m.setUsername(usuario.getUsername());
		m.setProtocolo(protocoloEncr);
		sender( m);

	}
	
	public void senderToGrupo(ProtocoloDTO p, Long idGrupo, String username, boolean minusCreator) throws PrivacityException {
		System.out.println("senderToGrupo(ProtocoloDTO p, boolean minusCreator, boolean onlyOnline)");
		List<Usuario> lista = comps.repo().userForGrupo().findByUsuariosForGrupoDeletedFalse(idGrupo);
		//poner paralel stream
		
		for ( int k = 0 ; k < lista.size() ; k++ ) {

			Usuario destino = lista.get(k); 
			
			if (destino.getUsername().equals(username) && minusCreator) {
				System.out.println("usuario creador excluido: " + username);
			}else {
				senderToUser(p,  destino );	
			}
				
			
	
		}
		
	}
	
	public void sender(WsMessage msg) {
  	  RestTemplate rest = new RestTemplate();

  	  MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
  	  map.add("msg",UtilsString.gsonToSend(msg));


  	  rest.postForObject(server + "/entry/ws", map,String.class);
  	  
		
	}
	
	public Urls getUrl() {
		return Urls.CONSTANT_URL_PATH_PRIVATE_WS;
	}
	public ProtocoloDTO buildProtocoloDTO(ProtocoloComponentsEnum comp, ProtocoloActionsEnum action,
			Object obj) {
		
		ProtocoloDTO p = new ProtocoloDTO(comp,action);
		p.setObjectDTO(UtilsString.gsonToSend(obj));
		return p;
	}
	public ProtocoloDTO buildProtocoloDTO(ProtocoloComponentsEnum comp, ProtocoloActionsEnum action,
			MessageDTO m) {
		ProtocoloDTO p = new ProtocoloDTO(comp,action);
		p.setMessageDTO(m);
		return p;
	}
	
	public int getMembersOnline(GrupoDTO request) throws PrivacityException {

		Authentication auth = SecurityContextHolder
		        .getContext()
		        .getAuthentication();
		String token = jwtUtils.generateJwtToken(auth);
		
		 
		RestTemplate rest = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer "+token);

		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
  	  MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
  	  //map.add("idGrupo", "idGrupo=");

  	  Integer idMessage = rest.postForObject(server + "/entry/ws/get/online/grupo/" + request.getIdGrupo(), entity, Integer.class);
  	  if ( idMessage == null) {
  		  return 0;
  	  }
      return idMessage;

	}
}
