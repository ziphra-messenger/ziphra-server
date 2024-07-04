package com.privacity.server.component.grupo;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.privacity.common.enumeration.ProtocoloComponentsEnum;import com.privacity.common.enumeration.ProtocoloActionsEnum;
import com.privacity.common.dto.GrupoDTO;
import com.privacity.common.dto.GrupoInvitationDTO;
import com.privacity.common.dto.GrupoUserConfDTO;
import com.privacity.common.dto.IdDTO;
import com.privacity.common.dto.MessageDTO;
import com.privacity.common.dto.MessageDetailDTO;
import com.privacity.common.dto.ProtocoloDTO;
import com.privacity.common.dto.UserForGrupoDTO;
import com.privacity.common.dto.UsuarioDTO;
import com.privacity.common.dto.response.GrupoRemoveMeResponseDTO;
import com.privacity.common.dto.response.InitGrupoResponse;
import com.privacity.common.enumeration.ExceptionReturnCode;
import com.privacity.common.enumeration.GrupoRolesEnum;
import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.exceptions.PrivacityException;
import com.privacity.server.exceptions.ValidationException;
import com.privacity.server.model.AES;
import com.privacity.server.model.Grupo;
import com.privacity.server.model.GrupoGralConf;
import com.privacity.server.model.GrupoGralConfLock;
import com.privacity.server.model.GrupoGralConfPassword;
import com.privacity.server.model.GrupoInvitation;
import com.privacity.server.model.GrupoInvitationId;
import com.privacity.server.model.GrupoUserConf;
import com.privacity.server.model.GrupoUserConfId;
import com.privacity.server.model.Message;
import com.privacity.server.model.MessageDetail;
import com.privacity.server.model.UserForGrupo;
import com.privacity.server.model.UserForGrupoId;
import com.privacity.server.security.Usuario;
import com.privacity.server.websocket.WsMessage;
import com.privacity.server.websocket.WsQueue;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class GrupoProcessService  {

	@Autowired @Lazy
	private FacadeComponent comps;
	
	   @Autowired 
	   @Lazy
	   private WsQueue q;	
	
	public IdDTO[] getIdsMisGrupos(Usuario u) {
		List<Long> l = comps.repo().userForGrupo().findIdGrupoByUserForGrupoIdUser(u.getIdUser());
		List<Long> gi = comps.repo().grupoInvitation().findIdGrupoByGrupoInvitationUsuarioGrupo(u);

	
		
		
		IdDTO[] r = new IdDTO[l.size() + gi.size()];
		
		int i = 0;
		for ( ; i < l.size() ; i++) {
			r[i] = new IdDTO(l.get(i));
		}

		for ( int j = 0 ; j < gi.size() ; j++) {
			r[j+i] = new IdDTO(gi.get(j));
		}

		return r;
	}
	
	public GrupoDTO getGrupoDTOInvitation(Usuario u, GrupoInvitation gi,Grupo grupo) {
		GrupoDTO g = comps.common().mapper().getGrupoDTOInvitation(grupo);
		
		GrupoInvitationDTO invDTO = comps.common().mapper().getGrupoInvitationDTOPropio(gi);
		g.setGrupoInvitationDTO(invDTO);

		//GrupoUserConfDTO grupoUserConf = comps.util().grupoUserConf().getGrupoUserConf(u, grupo);
		//g.setUserConfDTO(grupoUserConf);
		
		return g;
	}
	
	
	public GrupoDTO getGrupoDTO(Usuario u, UserForGrupo v) throws ValidationException {
		Grupo grupo = v.getUserForGrupoId().getGrupo();
		
		GrupoDTO g = comps.common().mapper().getGrupoDTOPropio(grupo);
		
		UserForGrupoDTO ufg = comps.common().mapper().getUserForGrupoDTOPropio(v);
		g.setUserForGrupoDTO(ufg);
	
		GrupoUserConfDTO grupoUserConf = comps.util().grupoUserConf().getGrupoUserConf(u, grupo);
		g.setUserConfDTO(grupoUserConf);
		
		return g;
	} 

	public GrupoDTO newGrupo(Usuario u, Grupo g, AES aesdto) throws Exception {
		
		//aescomps.repo().grupo().save(g.getAesGrupo());
		
		//g.setEncryptPublicKey(encrypt.getPublicKey());
		//comps.repo().grupo().save(g);
		
		g.setPassword(new GrupoGralConfPassword());
		g.getPassword().setEnabled(false);
		g.getPassword().setExtraEncryptDefaultEnabled(false);
		g.getPassword().setGrupo(g);
		
		//comps.repo().grupo().save(g);
		
		g.setLock(new GrupoGralConfLock());

		g.getLock().setEnabled(false);
		g.getLock().setSeconds(900);
		g.getLock().setGrupo(g);
		

		comps.repo().grupo().save(g);
		
		//g.getGralConf().setGrupo(g);
		//comps.repo().grupo().save(g);
		
		UserForGrupo ug = new UserForGrupo();
		ug.setUserForGrupoId( new UserForGrupoId(u, g));
		ug.setRole(GrupoRolesEnum.ADMIN);
//		aesdto.setUserForGrupoId(null);
		ug.setAlias(comps.common().randomGenerator().alias());
		ug.setAes(aesdto);
		
		comps.repo().userForGrupo().save(ug);
		
//		g.getGralConf().setPassword(new GrupoGralConfPassword());
//		g.getGralConf().getPassword().setEnabled(false);
//		g.getGralConf().getPassword().setGrupoGralConf(g.getGralConf());
		comps.service().grupoUserConf().saveDefaultGrupoUserConf(g, u);
		
		return getGrupoDTO(u,ug);
	
	}  

	
	// olds

	

//	private GrupoDTO getGrupo(String idGrupo, Usuario logU) throws ValidationException {
//		return getGrupo(Long.parseLong(idGrupo),logU);
//	}
//	private GrupoDTO getGrupo(Long idGrupo, Usuario logU) throws ValidationException {
//		
//		//Grupo g = comps.util().grupo().getGrupoById(idGrupo);
//		return getGrupo(null,logU);
//	}
//	private GrupoDTO getGrupo(Grupo grupo, Usuario logU) throws ValidationException {
//		return getGrupo(grupo,logU,false);
//	}
//	private GrupoDTO getGrupo(Grupo grupo, Usuario logU, boolean isInvitation) throws ValidationException {
//
//		List<UserForGrupo> ufgList = comps.repo().userForGrupo().findByForGrupo(grupo.getIdGrupo());
//		
//		UserForGrupo miUserForGrupo = null;
//
//		UserForGrupoDTO[] arr = new UserForGrupoDTO[ufgList.size()];
//		int i = 0;
//		for (UserForGrupo ufgElement : ufgList ) {
//			ufgElement.getUserForGrupoId().getUser();
//			
//			UserForGrupoDTO ufgElementDTO = comps.common().mapper().doit(ufgElement,logU);
//			arr[i] = ufgElementDTO;
//			i++;
//			
//			if (ufgElement.getUserForGrupoId().getUser().getIdUser().equals(logU.getIdUser())){
//				miUserForGrupo=ufgElement;
//			}
//		}
//	
//		GrupoDTO r = comps.common().mapper().doit(grupo,miUserForGrupo);
//		if (!isInvitation) r.setUserConfDTO(getGrupoUserConf(logU,grupo));
//		//r.setUsersForGrupoDTO(arr);
//		return r;
//
//	}
	
	public void  sentInvitation(Grupo grupo, GrupoRolesEnum role, Usuario logU,Usuario UserInvitationCode, AES aes) throws PrivacityException {
		Grupo g = new Grupo();
		g.setIdGrupo(grupo.getIdGrupo());
		g.setName(grupo.getName());
		
		GrupoInvitation gi = new GrupoInvitation();
		gi.setAes(aes);
		gi.setRole(role);
		gi.setGrupoInvitationId(new GrupoInvitationId(UserInvitationCode, logU, g));
		gi.setPrivateKey(UserInvitationCode.getEncryptKeys().getPrivateKey());

		comps.repo().grupoInvitation().save(gi);
		
		GrupoDTO ginfo = getGrupoDTOInvitation(UserInvitationCode, gi, g);
		//ginfo.setGrupoInvitation(true);
		ginfo.setGrupoInvitationDTO(new GrupoInvitationDTO(
				comps.common().mapper().doit(gi.getGrupoInvitationId().getUsuarioInvitante()), 
				gi.getRole(),
				comps.common().mapper().doit(gi.getAes()),
				gi.getPrivateKey()
				));

		// enviar invitacion
		
		ProtocoloDTO p = comps.webSocket().sender().buildProtocoloDTO(
				ProtocoloComponentsEnum.GRUPO,
				ProtocoloActionsEnum.GRUPO_INVITATION_RECIVED, 
				ginfo);
		
		
			q.put(new WsMessage(gi.getGrupoInvitationId().getUsuarioInvitado().getUsername() ,p));

		
		
	}
	
	public GrupoDTO acceptInvitation(GrupoInvitation gi, AES aes) throws Exception  {
		
		
		UserForGrupo ug = new UserForGrupo();
		ug.setUserForGrupoId( new UserForGrupoId(gi.getGrupoInvitationId().getUsuarioInvitado(), gi.getGrupoInvitationId().getGrupo()));
		ug.setRole(gi.getRole());
		ug.setAlias(comps.common().randomGenerator().alias());
		
		GrupoUserConf gconf = comps.util().grupoUserConf().getDefaultGrupoUserConf(gi.getGrupoInvitationId().getGrupo(), gi.getGrupoInvitationId().getUsuarioInvitado());
		
		comps.repo().grupoUserConf().save(gconf);
		
		
		ug.setAes(aes);
		
		
		
		
		comps.repo().userForGrupo().save(ug);
		
		// aviso a todos para q lo agreguen al grupo menos al invitado
		//GrupoDTO dto = getGrupo(gi.getGrupoInvitationId().getGrupo(),gi.getGrupoInvitationId().getUsuarioInvitado());
		{
//			ProtocoloDTO p = comps.webSocket().sender().buildProtocoloDTO(
//											Constant.PROTOCOLO_COMPONENT_GRUPOS,
//					                        Constant.PROTOCOLO_ACTION_GRUPO_ADDUSER_ADDME,
//					                        dto);
//	
//			comps.webSocket().sender().sender(gi.getGrupoInvitationId().getUsuarioInvitado(), p);
		}
		
		//ACA DEBE INFORMAR A TODOS LOS SUSCRIPTORES EL INGRESO DEL NUEVO MIEMBRO
		{

			MessageDTO mensajeD = comps.webSocket().sender().buildSystemMessage(gi.getGrupoInvitationId().getGrupo(), "SE HA AGREGADO EL USUARIO : " + gi.getGrupoInvitationId().getUsuarioInvitado().getNickname() + " AL GRUPO " + gi.getGrupoInvitationId().getGrupo().getName() + " POR " + gi.getGrupoInvitationId().getUsuarioInvitante().getNickname());
			Message mensaje = comps.common().mapper().doit(mensajeD, comps.util().usuario().getUsuarioSystem(), gi.getGrupoInvitationId().getGrupo());
			comps.process().message().sendNormal(comps.util().usuario().getUsuarioSystem().getIdUser(), mensaje, gi.getGrupoInvitationId().getGrupo().getIdGrupo());
			//esto debe estar activo
			comps.repo().grupoInvitation().delete(gi);
		}
		{


			//GrupoUserConfDTO conf = comps.common().mapper().doit(gconf);
			
			//GrupoDTO dto = getGrupo(comps.util().grupo().getGrupoById(ug.getUserForGrupoId().getGrupo().getIdGrupo()), ug.getUserForGrupoId().getUser());
			//dto.setUserConfDTO(conf);
			return getGrupoDTO(gi.getGrupoInvitationId().getUsuarioInvitado(), ug); //dto; 
		}
	}  	


	
	public GrupoDTO[] listarMisGrupos(Usuario u) throws Exception {
		
//		UserForGrupo ug = new UserForGrupo();
//		
//		ug.setUserForGrupoId( new UserForGrupoId(u));
//		
//		List<GrupoInvitation> lgi = comps.repo().grupoInvitation().findByGrupoInvitationIdUsuarioInvitado(u);
//		
//		List<UserForGrupo> l = comps.repo().userForGrupo().findByUserForGrupoIdUser(u.getIdUser());
//		
//		GrupoDTO[] r = new GrupoDTO[l.size() + lgi.size()];
//		int i =0;
//		for ( GrupoInvitation e : lgi ) {
//			GrupoDTO ginfo = getGrupo(e.getGrupoInvitationId().getGrupo(), u, true);
//			ginfo.setGrupoInvitation(true);
//			ginfo.setGrupoInvitationDTO(new GrupoInvitationDTO(
//					comps.common().mapper().doit(e.getGrupoInvitationId().getUsuarioInvitante()), 
//					e.getRole().name(),
//					comps.common().mapper().doit(e.getAes()),
//					e.getPrivateKey()
//					));
//			r[i] = ginfo;
//			i ++;
//		}
//		
//		for ( UserForGrupo e : l ) {
//			GrupoDTO ginfo = getGrupo(e.getUserForGrupoId().getGrupo(), u, false);
//			ginfo.setGrupoInvitation(false);
//			r[i] = ginfo;
//			i ++;
//		}

		return null; //r;
		
		
	}

	public InitGrupoResponse initGrupo(Usuario u, Grupo g) throws Exception {

		List<Usuario> usuarios = comps.repo().userForGrupo().findByUsuariosForGrupoDeletedFalse(g.getIdGrupo());

		InitGrupoResponse response = new InitGrupoResponse();
		response.setUsersDTO(new UsuarioDTO[usuarios.size()]); 
	
		for (int i = 0 ; i < usuarios.size() ; i++ ) {
			
			
			response.getUsersDTO()[i] = comps.common().mapper().doit(usuarios.get(i));

		}
		
		
		List<Message> mensajes = comps.repo().message().findByMessageIdGrupoIdGrupo(g.getIdGrupo());

		MessageDTO[] mensajesDTO = new MessageDTO[mensajes.size()];
		
		
		for (int i = 0 ; i < mensajes.size() ; i++ ) {
			Message m = mensajes.get(i);
	    	
			MessageDTO mensaje = new MessageDTO();
	    	
			mensaje.setIdGrupo(m.getMessageId().getGrupo().getIdGrupo().toString());
			mensaje.setIdMessage(m.getMessageId().getIdMessage().toString());
			mensaje.setText(m.getText());
			mensaje.setUsuarioCreacion(comps.common().mapper().doit(m.getUserCreation()));
	    	
	    	List<MessageDetail> detalles = comps.repo().messageDetail().findByMessageUser(m.getMessageId().getIdMessage(),u.getIdUser());
	    	
			mensaje.setMessagesDetailDTO(new MessageDetailDTO[detalles.size()]);

			int j=0;
	    	for (MessageDetail d : detalles) {
	    		MessageDetailDTO dto = new MessageDetailDTO();
	    		//dto.setIdMessageDetail(d.getMessageDetailId().getIdMessageDetail().toString());
	    		
	    		dto.setEstado(d.getState());
	    		
				dto.setUsuarioDestino(comps.common().mapper().doit(d.getMessageDetailId().getUserDestino()));
				
	    		//dto.setUserDestino(d.getMessageDetailId().getUserDestino().getUsername());
	    		
	    		
	    		dto.setIdGrupo(m.getMessageId().getGrupo().getIdGrupo().toString());
	    		dto.setIdMessage(m.getMessageId().getIdMessage().toString());
	    		
	    		mensaje.getMessagesDetailDTO()[j] = dto;
				j++;
	  
	    	}
	    	
	    	mensajesDTO[i] = mensaje;
	    	
		}
		response.setMessagesDTO(mensajesDTO);
		return response;

	}

	public Usuario getUser() {
		Authentication auth = SecurityContextHolder
	            .getContext()
	            .getAuthentication();
	    UserDetails userDetail = (UserDetails) auth.getPrincipal();
	    
		Usuario u = comps.repo().user().findByUsername(userDetail.getUsername()).get();
		return u;
	}

//
//	private void removeMeAnonimo(Usuario usuarioLogged, Grupo grupo) throws PrivacityException {
//		List<Message> l = comps.repo().message().findByMessageIdGrupoUserAnonimo(grupo, usuarioLogged);
//		
////		for (Message m : l) {
////			comps.process().message().deleteForEveryone(m);
////		}
//	}


	public void removeMe(Usuario usuarioLogged, Usuario usuarioSystem, Grupo grupo, UserForGrupo userForGrupo) throws Exception {
		//Grupo grupo = userForGrupo.getUserForGrupoId().getGrupo();
		//removeMeAnonimo(usuarioLogged, grupo);
		
		//mediaRepository.deleteAllMyMediaByGrupo(grupo, usuarioLogged);
		//comps.repo().media().deleteAllMyMediasByGrupo(grupo.getIdGrupo(), usuarioLogged.getIdUser());
		comps.repo().messageDetail().deleteLogicAllMyMessagesDetailByGrupo(grupo, usuarioLogged);
		//comps.repo().grupoUserConf().deleteById(new GrupoUserConfId(usuarioLogged,grupo));
		
		comps.repo().message().deleteLogicAllMyMessagesByGrupo(grupo, usuarioLogged);
		
		userForGrupo.setDeleted(true);
		comps.repo().userForGrupo().save(userForGrupo);

		List<Usuario> usuarios = comps.repo().userForGrupo().findByUsuariosForGrupoDeletedFalse(grupo.getIdGrupo());
		//avisar q se fue, borrar todos los mensajes y sacarlo del grupo
		
		GrupoRemoveMeResponseDTO r = new GrupoRemoveMeResponseDTO();
		
		
		GrupoDTO grupoRemove = new GrupoDTO();
		grupoRemove.setIdGrupo(grupo.getIdGrupo()+"");
		
		r.setGrupoDTO(grupoRemove);
		
		UsuarioDTO usuarioRemove = new UsuarioDTO();
		usuarioRemove.setIdUsuario(usuarioLogged.getIdUser()+"");
		r.setUsuariosDTO(usuarioRemove);
		
		comps.util().grupo().senderToGrupoMinusCreator(ProtocoloComponentsEnum.GRUPO,
				ProtocoloActionsEnum.GRUPO_REMOVE_USER,  grupo.getIdGrupo(), grupoRemove);
	

		//ACA DEBE INFORMAR A TODOS LOS SUSCRIPTORES EL INGRESO DEL NUEVO MIEMBRO
	

			MessageDTO mensaje = comps.webSocket().sender().buildSystemMessage(grupo, "USUARIO " + usuarioLogged.getNickname() + " HA DEJADO EL GRUPO " + grupo.getName());
			comps.process().message().sendNormal(comps.util().usuario().getUsuarioSystem().getIdUser(), comps.common().mapper().doit(mensaje, usuarioSystem,grupo), grupo.getIdGrupo());

	}


	public void saveGrupoUserConf(GrupoUserConf conf) {
		comps.repo().grupoUserConf().save(conf);
	}


	public GrupoUserConfDTO getGrupoUserConf(Usuario usuarioLogged, Grupo grupo) {
		
		Optional<GrupoUserConf> o = comps.repo().grupoUserConf().findById(new GrupoUserConfId(usuarioLogged, grupo));
		
		if ( o.isPresent() ) {
			return comps.common().mapper().doit(o.get());
		}
		
		GrupoUserConfDTO r = new GrupoUserConfDTO();
		r.setIdGrupo(grupo.getIdGrupo()+"");
		return r;
	}


	public Object getGrupoUserConf(GrupoGralConf c) {
		// TODO Auto-generated method stub
		return null;
	}


	public void saveGrupoGeneralConfiguration(Grupo grupo) {
		comps.repo().grupo().save(grupo);
	}

	public boolean loginGrupo(Grupo g, String pw) throws ValidationException {


		//if ( comps.util().passwordEncoder().encode(pw).equals(g.getPassword().getPassword())){
		if ( 
				comps.util().passwordEncoder().matches(pw, g.getPassword().getPassword())
				){		
			return true;
		}
		
		return false;
	}

	public void delete(Usuario usuarioLogged, Usuario usuarioSystem, Grupo grupo) throws Exception {
		/*
		grupo
		
		GrupoDTO grupoRemove = new GrupoDTO();
		grupoRemove.setIdGrupo(grupo.getIdGrupo()+"");
		
		r.setGrupoDTO(grupoRemove);
		
		UsuarioDTO usuarioRemove = new UsuarioDTO();
		usuarioRemove.setIdUsuario(usuarioLogged.getIdUser()+"");
		r.setUsuariosDTO(usuarioRemove);
		
		ProtocoloDTO p = comps.webSocket().sender().buildProtocoloDTO(
				ProtocoloComponentsEnum.GRUPO,
				ProtocoloActionsEnum.GRUPO_DELETE_GRUPO, 
				grupoRemove);
		
		for (Usuario usuarioToAvisarRemove : usuarios){
			q.put(new WsMessage(usuarioToAvisarRemove.getUsername() ,p));
		}
		//ACA DEBE INFORMAR A TODOS LOS SUSCRIPTORES EL INGRESO DEL NUEVO MIEMBRO
	

			MessageDTO mensaje = comps.webSocket().sender().buildSystemMessage(grupo, "USUARIO " + usuarioLogged.getNickname() + " HA DEJADO EL GRUPO " + grupo.getName());
			comps.process().message().sendNormal(comps.util().usuario().getUsuarioSystem().getIdUser(), comps.common().mapper().doit(mensaje, usuarioSystem,grupo), grupo.getIdGrupo());
*/
	}




	


    	
}
