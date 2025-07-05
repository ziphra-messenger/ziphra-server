package ar.ziphra.messaging.services;

import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import ar.ziphra.common.dto.MembersQuantityDTO;
import ar.ziphra.common.dto.MessageDTO;
import ar.ziphra.common.dto.ProtocoloDTO;
import ar.ziphra.common.enumeration.ProtocoloActionsEnum;
import ar.ziphra.common.enumeration.ProtocoloComponentsEnum;
import ar.ziphra.common.exceptions.ZiphraException;
import ar.ziphra.commonback.common.enumeration.ServerUrls;
import ar.ziphra.core.model.Grupo;
import ar.ziphra.core.model.Usuario;
import ar.ziphra.core.util.UtilsStringComponent;
import ar.ziphra.messaging.model.WsMessage;
import ar.ziphra.messaging.pool.WsQueue;
import ar.ziphra.messaging.util.UtilFacade;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WebSocketSenderService {


	private static final String WEBSOCKET_CHANNEL = "/topic/reply";
	@Autowired
	@Lazy
	private UtilsStringComponent utilStrings;
	@Autowired
	@Lazy
	private UtilFacade uf;
	@Autowired
	@Lazy
	private SimpMessagingTemplate simpMessagingTemplate;
   @Autowired 
   @Lazy
   private WsQueue q;
   

//	public void sender(Usuario usuario, ProtocoloDTO protocoloDTP) throws ZiphraException {
//		sender(usuario.getUsername(),protocoloDTP);
//	}
//
//	public void sender(String username, ProtocoloDTO protocoloDTO) throws ZiphraException {
//
//		if (encryptIds) {
//			//username = ziphraIdServices.getAES(username);
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
//	public void sender(ProtocoloDTO p, Usuario destino) throws ZiphraException {
//		sentToUser(destino.getUsername(), WEBSOCKET_CHANNEL , new Gson().toJson(p));	
//	}
//
////	public void sender(Message m, String p) throws ZiphraException {
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
	
	private void sentToUser(String user, String urlDestino, String mensaje) throws ZiphraException {
		log.trace("SERVER CORE - Salida WS");
		log.trace("user: " + user);
		log.trace("urlDestino: " + urlDestino);
		log.trace("mensaje: " + mensaje);
		if (uf.socketSessionRegistry().getSessionIds(user).size() >0) {
			
		try {
		
			//String retornoFuncionEncriptado =comps.service().usuarioSessionInfo().encryptSessionAESServerWS(user,mensaje);


			simpMessagingTemplate.convertAndSendToUser(user, urlDestino , mensaje);	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

				
		}
		
	}
	
	public void senderToGrupoMinusCreator(String username, long idGrupo, ProtocoloDTO p) throws ZiphraException {
		
		senderToGrupo(p,idGrupo, username, true);
		
	}

	public void senderToUser(ProtocoloDTO p, Usuario usuario) throws ZiphraException {
		senderToUser(p,usuario.getUsername());
	}
	
	public void senderToUser(ProtocoloDTO p, String username) throws ZiphraException {
		if (uf.socketSessionRegistry().isOnline(username)){
			log.trace("Encriptando: " + username);
			String protocoloEncr = uf.usuarioSessionInfo().encryptProtocoloWS(username, p, getUrl().name());
	
			WsMessage m = new WsMessage();
			m.setUsername(username);
			m.setProtocolo(protocoloEncr);
			sender( m);
		}else {
			log.trace("Usuario Off Line - no enviado: " + username);
		}

	}
	public void senderToUserNoEncrypt(String username,String protocoloEncr) throws ZiphraException {
		if (uf.socketSessionRegistry().isOnline(username)){
			log.trace("Encriptando: " + username);
	
			WsMessage m = new WsMessage();
			m.setUsername(username);
			m.setProtocolo(protocoloEncr);
			sender( m);
		}else {
			log.trace("Usuario Off Line - no enviado: " + username);
		}

	}	
	
	
	public void senderToGrupo(ProtocoloDTO p, Long idGrupo, String username, boolean minusCreator) throws ZiphraException {
		log.debug("entrada - senderToGrupo" );
		log.debug("idGrupo: " + idGrupo );
		log.debug("username: " + username );
		log.debug("minusCreator: " + minusCreator );
		log.debug("ProtocoloDTO: " + uf.utilsString().cutStringToGson(p));
		List<Usuario> lista = uf.userForGrupoRepository().findByUsuariosForGrupoDeletedFalse(idGrupo);
		//List<String> listaToSend=new ArrayList<String>();
		Queue<String> listaToSend = new ConcurrentLinkedQueue<String>();
		lista.stream().forEach( destino -> {
			
			if (minusCreator && destino.getUsername().equals(username)) {
				log.trace("usuario creador excluido: " + username);
			}else {
				try {
					if (uf.socketSessionRegistry().isOnline(destino.getUsername())){
						log.trace("adding to: " + destino.getUsername());
						listaToSend.add(destino.getUsername());
					}else {
						log.trace("Usuario Off Line - no enviado: " + destino.getUsername());
					}
					//log.trace("sending to: " + destino.getUsername());
					//senderToUser(p,  destino );
				} catch (Exception e) {
					log.error("entrada - senderToGrupo" );
					log.error("idGrupo: " + idGrupo );
					log.error("username: " + destino.getUsername() );
					log.error("minusCreator: " + minusCreator );
					log.error("ProtocoloDTO: " + uf.utilsString().cutStringToGson(p));

					e.printStackTrace();
				}	
			}
	});
		
		if (listaToSend.size() > 0) {
			Map<String, String> map = uf.usuarioSessionInfo().encryptProtocoloWS(listaToSend, p, getUrl().name());
			
			map.entrySet()
			   .parallelStream()
			   .forEach(entry -> {
				try {
					senderToUserNoEncrypt(entry.getKey(),entry.getValue());
				} catch (ZiphraException e) {
					// TODO Auto-generated catch block
					log.error("entrada - map senderToGrupo" );
					log.error("idGrupo: " + idGrupo );
					log.error("username: " + entry.getKey() );
	
				}
			});
		}
	
	}

	
//	public void senderToGrupo(ProtocoloDTO p, Long idGrupo, String username, boolean minusCreator) throws ZiphraException {
//		log.debug("entrada - senderToGrupo" );
//		log.debug("idGrupo: " + idGrupo );
//		log.debug("username: " + username );
//		log.debug("minusCreator: " + minusCreator );
//		log.debug("ProtocoloDTO: " + uf.utilsString().cutStringToGson(p));
//		List<Usuario> lista = uf.userForGrupoRepository().findByUsuariosForGrupoDeletedFalse(idGrupo);
//		
//		lista.parallelStream().forEach( destino -> {
//			
//			if (minusCreator &&destino.getUsername().equals(username)) {
//				log.trace("usuario creador excluido: " + username);
//			}else {
//				try {
//					log.trace("sending to: " + username);
//					senderToUser(p,  destino );
//				} catch (ZiphraException e) {
//					log.error("entrada - senderToGrupo" );
//					log.error("idGrupo: " + idGrupo );
//					log.error("username: " + username );
//					log.error("minusCreator: " + minusCreator );
//					log.error("ProtocoloDTO: " + uf.utilsString().cutStringToGson(p));
//
//					e.printStackTrace();
//				}	
//			}
//	});
//		
//	
//	}
	
	public void sender(WsMessage msg) {
		
		String destino;
		
		destino = msg.getUsername();
		try {	
	        
			sentToUser(destino, WEBSOCKET_CHANNEL , msg.getProtocolo());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ServerUrls getUrl() {
		return ServerUrls.CONSTANT_URL_PATH_PRIVATE_WS;
	}
	public ProtocoloDTO buildProtocoloDTO(ProtocoloComponentsEnum comp, ProtocoloActionsEnum action,
			Object obj) throws ZiphraException  {
		
		ProtocoloDTO p = new ProtocoloDTO(comp,action);
		p.setObjectDTO(utilStrings.gsonToSend(obj));
		return p;
	}
	public ProtocoloDTO buildProtocoloDTO(ProtocoloComponentsEnum comp, ProtocoloActionsEnum action,
			MessageDTO m) {
		ProtocoloDTO p = new ProtocoloDTO(comp,action);
		p.setMessage(m);
		return p;
	}
	public  MembersQuantityDTO getMembersOnlineByGrupo(String idGrupoS) throws Exception {
		
		Long idGrupo = Long.parseLong(idGrupoS);

		List<Usuario> users = uf.userForGrupoRepository().findByUsuariosForGrupoDeletedFalse(idGrupo);

		int count = 0;
		for (Usuario u : users) {

			Set<String> online = uf.socketSessionRegistry().getSessionIds(u.getUsername());

			if (online.size() > 0) {
				count++;
			}
		}

		MembersQuantityDTO r = new MembersQuantityDTO();
		r.setQuantityOnline(count);
		r.setTotalQuantity(users.size());
		return r;
	}
}
