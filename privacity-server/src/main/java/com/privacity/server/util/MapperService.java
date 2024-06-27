package com.privacity.server.util;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.privacity.common.dto.AESDTO;
import com.privacity.common.dto.EncryptKeysDTO;
import com.privacity.common.dto.GrupoDTO;
import com.privacity.common.dto.GrupoGralConfDTO;
import com.privacity.common.dto.GrupoGralConfPasswordDTO;
import com.privacity.common.dto.GrupoInvitationDTO;
import com.privacity.common.dto.GrupoUserConfDTO;
import com.privacity.common.dto.IdMessageDTO;
import com.privacity.common.dto.LockDTO;
import com.privacity.common.dto.MediaDTO;
import com.privacity.common.dto.MessageDTO;
import com.privacity.common.dto.MessageDetailDTO;
import com.privacity.common.dto.MyAccountConfDTO;
import com.privacity.common.dto.RequestIdDTO;
import com.privacity.common.dto.UserForGrupoDTO;
import com.privacity.common.dto.UsuarioDTO;
import com.privacity.common.dto.response.SaveGrupoGralConfLockResponseDTO;
import com.privacity.common.enumeration.ConfigurationStateEnum;
import com.privacity.common.enumeration.GrupoUserConfEnum;
import com.privacity.server.component.common.service.facade.FacadeComponent;
import com.privacity.server.dao.factory.MessageIdSequenceFactory;
import com.privacity.server.exceptions.ProcessException;
import com.privacity.server.exceptions.ValidationException;
import com.privacity.server.model.AES;
import com.privacity.server.model.EncryptKeys;
import com.privacity.server.model.Grupo;
import com.privacity.server.model.GrupoInvitation;
import com.privacity.server.model.GrupoUserConf;
import com.privacity.server.model.GrupoUserConfId;
import com.privacity.server.model.Media;
import com.privacity.server.model.MediaId;
import com.privacity.server.model.Message;
import com.privacity.server.model.MessageDetail;
import com.privacity.server.model.MessageId;
import com.privacity.server.model.MyAccountConf;
import com.privacity.server.model.UserForGrupo;
import com.privacity.server.model.UserForGrupoId;
import com.privacity.server.security.Usuario;

import lombok.extern.java.Log;

@Service
@Log
public class MapperService {
	
	@Autowired
	@Lazy
	private FacadeComponent comps;
	@Autowired @Lazy
	private MessageIdSequenceFactory messageIdSequenceFactory;

	public UserForGrupoDTO getUserForGrupoDTOPropio(UserForGrupo userForGrupo) {
		UserForGrupoDTO ufgDTO = new UserForGrupoDTO();
//		ufgDTO.setIdGrupo(userForGrupo.getUserForGrupoId().getGrupo().getIdGrupo()+"");
//		ufgDTO.setUsuario(doitForGrupo(userForGrupo.getUserForGrupoId().getGrupo(),userForGrupo.getUserForGrupoId().getUser()));
		ufgDTO.setRole(userForGrupo.getRole());
		ufgDTO.setAesDTO( doit(userForGrupo.getAes()));
		
		ufgDTO.setAlias(userForGrupo.getAlias());
		ufgDTO.setNickname(userForGrupo.getNickname());
		
		return ufgDTO;
	}	
	public UserForGrupoDTO getUserForGrupoDTO(UserForGrupo userForGrupo) {
		UserForGrupoDTO ufgDTO = new UserForGrupoDTO();
		ufgDTO.setIdGrupo(userForGrupo.getUserForGrupoId().getGrupo().getIdGrupo()+"");
		ufgDTO.setUsuario(doitForGrupo(userForGrupo.getUserForGrupoId().getGrupo(),userForGrupo.getUserForGrupoId().getUser()));
		ufgDTO.setRole(userForGrupo.getRole());
		//ufgDTO.setAesDTO( doit(userForGrupo.getAes()));
		
		ufgDTO.setAlias(userForGrupo.getAlias());
		ufgDTO.setNickname(userForGrupo.getNickname());
		
		return ufgDTO;
	}	
	public GrupoDTO getGrupoDTOInvitation(Grupo grupo) {
		GrupoDTO g = new GrupoDTO();
		g.setIdGrupo(grupo.getIdGrupo().toString());
		g.setName(grupo.getName());
	
		return g;
	}	
	
	public GrupoDTO getGrupoDTOPropio(Grupo grupo) {
		GrupoDTO g = new GrupoDTO();
		g.setIdGrupo(grupo.getIdGrupo().toString());
		g.setName(grupo.getName());
		
		g.setGralConfDTO(new GrupoGralConfDTO());
		g.getGralConfDTO().setAnonimo(grupo.getGralConf().getAnonimo());
		g.getGralConfDTO().setAudiochat(grupo.getGralConf().getAudiochat());
		g.getGralConfDTO().setAudiochatMaxTime(grupo.getGralConf().getAudiochatMaxTime());
		g.getGralConfDTO().setBlackMessageAttachMandatory(grupo.getGralConf().isBlackMessageAttachMandatory());
		g.getGralConfDTO().setChangeNicknameToNumber(grupo.getGralConf().isChangeNicknameToNumber());
		g.getGralConfDTO().setDownloadAllowAudio(ConfigurationStateEnum.ALLOW);
		g.getGralConfDTO().setDownloadAllowImage(grupo.getGralConf().getDownloadAllowImage());
		g.getGralConfDTO().setDownloadAllowVideo(ConfigurationStateEnum.ALLOW);
		g.getGralConfDTO().setExtraEncrypt(grupo.getGralConf().getExtraEncrypt());
		g.getGralConfDTO().setHideMessageDetails(grupo.getGralConf().isHideMessageDetails());
		g.getGralConfDTO().setHideMessageState(grupo.getGralConf().isHideMessageState());
		g.getGralConfDTO().setHideMemberList(grupo.getGralConf().isHideMemberList());
		g.getGralConfDTO().setResend(grupo.getGralConf().isResend());
		g.getGralConfDTO().setTimeMessageMandatory(grupo.getGralConf().isTimeMessageMandatory());
		g.getGralConfDTO().setTimeMessageMaxTimeAllow(grupo.getGralConf().getTimeMessageMaxTimeAllow());
	

		g.setPassword(new GrupoGralConfPasswordDTO());
		g.getPassword().setEnabled(grupo.getPassword().isEnabled());
		g.getPassword().setExtraEncryptDefaultEnabled(grupo.getPassword().isExtraEncryptDefaultEnabled());
		g.getPassword().setDeleteExtraEncryptEnabled(grupo.getPassword().isDeleteExtraEncryptEnabled());		
		g.getPassword().setPassword(grupo.getPassword().getPassword());
		g.getPassword().setPasswordExtraEncrypt(grupo.getPassword().getPasswordExtraEncrypt());

		g.setLock(new LockDTO());
		g.getLock().setEnabled(grupo.getLock().isEnabled());
		g.getLock().setSeconds(grupo.getLock().getSeconds());
		return g;
	}		
	
	public GrupoInvitationDTO getGrupoInvitationDTOPropio(GrupoInvitation gi) {
	 return new GrupoInvitationDTO(
				comps.common().mapper().getUsuarioDTOOtro(gi.getGrupoInvitationId().getUsuarioInvitante()), 
				gi.getRole(),
				comps.common().mapper().doit(gi.getAes()),
				gi.getPrivateKey()
				);
	}
	public GrupoUserConfDTO doit(GrupoUserConf d) {
		
		GrupoUserConfDTO r = new GrupoUserConfDTO();
		
		r.setAnonimoAlways(d.getAnonimoAlways());
		r.setAnonimoRecived(d.getAnonimoRecived());
		r.setBlackMessageAlways(d.getBlackMessageAlways());
		r.setBlackMessageRecived(d.getBlackMessageRecived());
		r.setPermitirReenvio(d.getPermitirReenvio());
		r.setExtraAesAlways(d.getSecretKeyPersonalAlways());
		r.setTimeMessageAlways(d.getTimeMessageAlways());
		r.setTimeMessageSeconds(d.getTimeMessageSeconds());
		
		r.setDownloadAllowAudio(d.getDownloadAllowAudio());
		r.setDownloadAllowImage(d.getDownloadAllowImage());
		r.setDownloadAllowVideo(d.getDownloadAllowVideo());
		//r.setIdGrupo(d.getGrupoUserConfId().getGrupo().getIdGrupo()+"");
		//r.setChangeNicknameToNumber(d.getChangeNicknameToNumber());
		return r;
	}
	
	public AESDTO doit(AES e) {
		AESDTO r = new AESDTO();
		r.setSecretKeyAES(e.getSecretKeyAES());
		r.setSaltAES(e.getSaltAES());
		r.setIteration(e.getIteration().toString());
		return r;
	}
	
	public UsuarioDTO getUsuarioDTOOtro(Usuario u) {
		// configurar el nickname
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		//usuarioDTO.setIdUsuario(u.getIdUser()+"");
		usuarioDTO.setNickname(u.getNickname());
//		usuarioDTO.setAlias(u.getAlias());
		return usuarioDTO;
	}
	
	//old
	public RequestIdDTO doitClientRequestIdDTO(RequestIdDTO serverRequestIdDTO) {
		RequestIdDTO r = new RequestIdDTO();
		r.setDate(serverRequestIdDTO.getDate());
		r.setRequestIdServerSide(serverRequestIdDTO.getRequestIdServerSide());
		
		return r;
		
	}
	

	
	public GrupoDTO doit(Grupo grupo, UserForGrupo ufg) {
		
		GrupoDTO g = new GrupoDTO();
		g.setIdGrupo(grupo.getIdGrupo().toString());
		g.setName(grupo.getName());
		

		g.setAlias(ufg.getAlias());	
		g.setGralConfDTO(new GrupoGralConfDTO());
		
		g.getGralConfDTO().setAnonimo(grupo.getGralConf().getAnonimo());
		g.getGralConfDTO().setAudiochat(grupo.getGralConf().getAudiochat());
		g.getGralConfDTO().setAudiochatMaxTime(grupo.getGralConf().getAudiochatMaxTime());
		g.getGralConfDTO().setBlackMessageAttachMandatory(grupo.getGralConf().isBlackMessageAttachMandatory());
		g.getGralConfDTO().setChangeNicknameToNumber(grupo.getGralConf().isChangeNicknameToNumber());
		g.getGralConfDTO().setDownloadAllowAudio(ConfigurationStateEnum.ALLOW);
		g.getGralConfDTO().setDownloadAllowImage(grupo.getGralConf().getDownloadAllowImage());
		g.getGralConfDTO().setDownloadAllowVideo(ConfigurationStateEnum.ALLOW);
		g.getGralConfDTO().setExtraEncrypt(grupo.getGralConf().getExtraEncrypt());
		g.getGralConfDTO().setHideMessageDetails(grupo.getGralConf().isHideMessageDetails());
		g.getGralConfDTO().setHideMessageState(grupo.getGralConf().isHideMessageState());
		g.getGralConfDTO().setHideMemberList(grupo.getGralConf().isHideMemberList());
		g.getGralConfDTO().setResend(grupo.getGralConf().isResend());
		g.getGralConfDTO().setTimeMessageMandatory(grupo.getGralConf().isTimeMessageMandatory());
		g.getGralConfDTO().setTimeMessageMaxTimeAllow(grupo.getGralConf().getTimeMessageMaxTimeAllow());
	
		
		return g;
	}
	public MediaDTO doit(Media m) throws ProcessException {
		return doit(m, false);
	}
	
	public MediaDTO doit(Media m, boolean fillMediaData) throws ProcessException {
		if (m == null) return null;
		
		MediaDTO mediaDTO = new MediaDTO();

		if (fillMediaData && m.getData() != null) {
			//mediaDTO.setData(services.getZipUtilService().decompress(m.getData()));
			mediaDTO.setData(m.getData());

		}
		
		mediaDTO.setMiniatura(m.getMiniatura());
		mediaDTO.setDownloadable(m.isDownloadable());
		mediaDTO.setIdGrupo(m.getMediaId().getMessage().getMessageId().getGrupo().getIdGrupo()+"");
		mediaDTO.setIdMessage(m.getMediaId().getMessage().getMessageId().getIdMessage()+"");
		mediaDTO.setMediaType(m.getMediaType());

		return mediaDTO;
	}


	public UsuarioDTO doitForGrupo(Grupo grupo, Usuario u) {

		
		if (u.getUsername().equals("SYSTEM")){
			UsuarioDTO usuarioDTO = new UsuarioDTO();
			usuarioDTO.setIdUsuario(u.getIdUser()+"");
			usuarioDTO.setNickname(u.getNickname()+"");
			
			return usuarioDTO;
		}
		UserForGrupo ugr = comps.repo().userForGrupo().findById(new UserForGrupoId(u, grupo)).get();
		String nicknameForGrupo = ""; 
		
		if (ugr.getNickname() != null && !ugr.getNickname().equals("")) {
			nicknameForGrupo=ugr.getNickname();
		}else {
			nicknameForGrupo= u.getNickname();
		}

		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setIdUsuario(u.getIdUser()+"");
		usuarioDTO.setNickname(nicknameForGrupo);


		return usuarioDTO;
	}
	
	public UsuarioDTO doit(Usuario u) {
		
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		usuarioDTO.setIdUsuario(u.getIdUser()+"");
		usuarioDTO.setNickname(u.getNickname());

		return usuarioDTO;
	}
	


	public Message doit(MessageDTO dto, Usuario usuarioCreacion, Grupo g) throws ValidationException, ProcessException {
		List<UserForGrupo> usersForGrupo = comps.repo().userForGrupo().findByForGrupo(g.getIdGrupo());
		return doit(dto, usuarioCreacion, g, usersForGrupo, false);
	}
	public Message doit(MessageDTO dto, Usuario usuarioCreacion, Grupo g, List<UserForGrupo> usersForGrupo,  boolean newId) throws ValidationException, ProcessException {
		
		
		
	

		
		Message m = new Message();
		//m.setDateCreation(new Date());
		m.setUserCreation(usuarioCreacion);
		m.setText(dto.getText());
		m.setBlackMessage(dto.isBlackMessage());
		m.setAnonimo(dto.isAnonimo());
		m.setTimeMessage(dto.getTimeMessage());
		m.setSystemMessage(dto.isSystemMessage());
		m.setSecretKeyPersonal(dto.isSecretKeyPersonal());
		m.setPermitirReenvio(dto.isPermitirReenvio());
		
		//Grupo g = comps.util().grupo().getGrupoByIdValidation(dto.getIdGrupo());
		
		MessageId idm = new MessageId();
		idm.setGrupo(g);
		
		if (dto.getIdMessage() == null || newId) {
			idm.setIdMessage(messageIdSequenceFactory.getMessgeIdInterfaceDAO().getNextMessageId(g.getIdGrupo()));
		}
		
		m.setMessageId(idm);
		
		m.setMedia(doit(dto.getMediaDTO(), m,true));
		m.setMessagesDetail( comps.util().messageDetail().generateMessagesDetail(g.getIdGrupo(),m,usersForGrupo));
		
		if ( dto.getParentReply() != null) {
			
			Message mr = comps.repo().message().findById( new MessageId (g, Long.parseLong(dto.getParentReply().getIdMessage()))).get();
			m.setParentReply(mr);
		}
		return m;
	}

	public Media doit(MediaDTO dto, Message message) throws ValidationException, ProcessException {
		return doit(dto,message, false);
	}
	public Media doit(MediaDTO dto, Message message, boolean fillMediaData) throws ValidationException, ProcessException {
		if (dto == null) return null;
		Media media = new Media();
		

		if (fillMediaData && dto.getData() != null) {
			byte[] compress = dto.getData(); //services.getZipUtilService().compress(dto.getData());
			//byte[] compress = zipUtilService.compress("123");
			media.setData(compress);
		}
		media.setMiniatura(dto.getMiniatura());
		media.setMediaType(dto.getMediaType());
		media.setDownloadable(dto.isDownloadable());
		media.setMediaId(new MediaId());
		media.getMediaId().setMessage(message);

		
		return media;
	}
	
	public MessageDTO doitWithOutMediaData(Message m, String mediaReplace) throws ProcessException {
		MessageDTO r = new MessageDTO();
		r.setIdGrupo(m.getMessageId().getGrupo().getIdGrupo()+"");
		r.setText(m.getText());
		r.setUsuarioCreacion(doitForGrupo(m.getMessageId().getGrupo(), m.getUserCreation()));
		r.setIdMessage(m.getMessageId().getIdMessage()+"");
		r.setMessagesDetailDTO(new MessageDetailDTO[1]);
		r.setSecretKeyPersonal(m.isSecretKeyPersonal());
		Set<MessageDetail> details = m.getMessagesDetail();
		r.setBlackMessage(m.isBlackMessage());
		r.setAnonimo(m.isAnonimo());
		r.setTimeMessage(m.getTimeMessage());
		r.setSystemMessage(m.isSystemMessage());
		r.setPermitirReenvio(m.isPermitirReenvio());
		r.setMessagesDetailDTO(new MessageDetailDTO[details.size()]);
		
		Media media = m.getMedia();
		MediaDTO mediaDTO = doit(media);
		r.setMediaDTO(mediaDTO);
		
		

		int i=0;
		for ( MessageDetail d : details) {
			
			r.getMessagesDetailDTO()[i] = new MessageDetailDTO();
			
			r.getMessagesDetailDTO()[i].setIdGrupo(m.getMessageId().getGrupo().getIdGrupo()+"");
			r.getMessagesDetailDTO()[i].setIdMessage(m.getMessageId().getIdMessage()+"");
			//			r.getMessagesDetailDTO()[i].setIdMessageDetail(d.getMessageDetailId().getIdMessageDetail()+"");
			
			
			if (d.getMessageDetailId().getUserDestino().getIdUser() == m.getUserCreation().getIdUser()) {
				r.getMessagesDetailDTO()[i].setUsuarioDestino(r.getUsuarioCreacion());
			}else {
				r.getMessagesDetailDTO()[i].setUsuarioDestino( doitForGrupo(m.getMessageId().getGrupo(),d.getMessageDetailId().getUserDestino()));
			}
			
			//response.getMessagesDetailDTO()[i].setUserDestino(d.getMessageDetailId().getUserDestino().getUsername());
			
			r.getMessagesDetailDTO()[i].setEstado(d.getState().toString());

			i++;
		}
		return r;
	}
	
	public MessageDTO doitWithOutTextAndMedia(MessageDTO m) throws ProcessException {
		MessageDTO r = new MessageDTO();
		r.setIdGrupo(m.getIdGrupo());
		
		r.setUsuarioCreacion(m.getUsuarioCreacion());
		r.setIdMessage(m.getIdMessage());
		
		r.setSecretKeyPersonal(m.isSecretKeyPersonal());
		
		r.setBlackMessage(m.isBlackMessage());
		r.setAnonimo(m.isAnonimo());
		r.setTimeMessage(m.getTimeMessage());
		r.setSystemMessage(m.isSystemMessage());
		r.setPermitirReenvio(m.isPermitirReenvio());
		r.setMessagesDetailDTO(m.getMessagesDetailDTO());
		
		
		if (m.getMediaDTO() != null) {
			MediaDTO media = new MediaDTO();
			media.setDownloadable(m.getMediaDTO().isDownloadable());
			media.setIdGrupo(m.getIdGrupo());
			media.setIdMessage(m.getMediaDTO().getIdMessage());
			media.setMediaType(m.getMediaDTO().getMediaType());
			
			r.setMediaDTO(media);
		}

		return r;
	}
	
	public MessageDTO doitWithoutMediaData(Message m) throws ProcessException {
		MessageDTO r = new MessageDTO();
		r.setIdGrupo(m.getMessageId().getGrupo().getIdGrupo()+"");
		r.setText(m.getText());
		r.setUsuarioCreacion(doitForGrupo(m.getMessageId().getGrupo(), m.getUserCreation()));
		r.setIdMessage(m.getMessageId().getIdMessage()+"");
		r.setMessagesDetailDTO(new MessageDetailDTO[1]);
		r.setSecretKeyPersonal(m.isSecretKeyPersonal());
		Set<MessageDetail> details = m.getMessagesDetail();
		r.setBlackMessage(m.isBlackMessage());
		r.setAnonimo(m.isAnonimo());
		r.setTimeMessage(m.getTimeMessage());
		r.setSystemMessage(m.isSystemMessage());
		r.setPermitirReenvio(m.isPermitirReenvio());
		r.setMessagesDetailDTO(new MessageDetailDTO[details.size()]);
		
		Media media = m.getMedia();
		MediaDTO mediaDTO = doit(media,false);
		r.setMediaDTO(mediaDTO);
		

		int i=0;
		for ( MessageDetail d : details) {
			
			r.getMessagesDetailDTO()[i] = new MessageDetailDTO();
			
			r.getMessagesDetailDTO()[i].setIdGrupo(m.getMessageId().getGrupo().getIdGrupo()+"");
			r.getMessagesDetailDTO()[i].setIdMessage(m.getMessageId().getIdMessage()+"");
			//			r.getMessagesDetailDTO()[i].setIdMessageDetail(d.getMessageDetailId().getIdMessageDetail()+"");
			
			
			if (d.getMessageDetailId().getUserDestino().getIdUser() == m.getUserCreation().getIdUser()) {
				r.getMessagesDetailDTO()[i].setUsuarioDestino(r.getUsuarioCreacion());
			}else {
				r.getMessagesDetailDTO()[i].setUsuarioDestino( doitForGrupo(m.getMessageId().getGrupo(),d.getMessageDetailId().getUserDestino()));
			}
			
			//response.getMessagesDetailDTO()[i].setUserDestino(d.getMessageDetailId().getUserDestino().getUsername());
			
			r.getMessagesDetailDTO()[i].setEstado(d.getState().toString());

			i++;
		}
		return r;
	}
	
	public MessageDTO doit(Message m) throws ProcessException {
		String idGrupo = m.getMessageId().getGrupo().getIdGrupo()+"";
		String idMessage = m.getMessageId().getIdMessage() +"";
		
		MessageDTO r = new MessageDTO();
		r.setIdGrupo(idGrupo+"");
		r.setText(m.getText()+"");
		r.setUsuarioCreacion(doitForGrupo(m.getMessageId().getGrupo(), m.getUserCreation()));
		r.setIdMessage(idMessage+"");
		r.setMessagesDetailDTO(new MessageDetailDTO[1]);
		r.setSecretKeyPersonal(m.isSecretKeyPersonal());
		Set<MessageDetail> details = m.getMessagesDetail();
		r.setBlackMessage(m.isBlackMessage());
		r.setAnonimo(m.isAnonimo());
		r.setTimeMessage(m.getTimeMessage());
		r.setSystemMessage(m.isSystemMessage());
		r.setPermitirReenvio(m.isPermitirReenvio());
		
		if (m.getParentReply() != null) {
			r.setParentReply(new IdMessageDTO( 
					idGrupo+"",
					m.getParentReply().getMessageId().getIdMessage() +""
					));
			
		}

		r.setMessagesDetailDTO(new MessageDetailDTO[details.size()]);
		
		Media media = m.getMedia();
		MediaDTO mediaDTO = doit(media,false);
		r.setMediaDTO(mediaDTO);
		

		int i=0;
		for ( MessageDetail d : details) {
			
			r.getMessagesDetailDTO()[i] = new MessageDetailDTO();
			
			r.getMessagesDetailDTO()[i].setIdGrupo(idGrupo+"");
			r.getMessagesDetailDTO()[i].setIdMessage(idMessage);
			//			r.getMessagesDetailDTO()[i].setIdMessageDetail(d.getMessageDetailId().getIdMessageDetail()+"");
			
//			if (d.getMessageDetailId().getUserDestino().getIdUser() == m.getUserCreation().getIdUser()) {
//				r.getMessagesDetailDTO()[i].setUsuarioDestino(r.getUsuarioCreacion());
//			}else {
				r.getMessagesDetailDTO()[i].setUsuarioDestino( doitForGrupo(m.getMessageId().getGrupo(),d.getMessageDetailId().getUserDestino()));
//			}
			
			//response.getMessagesDetailDTO()[i].setUserDestino(d.getMessageDetailId().getUserDestino().getUsername());
			
			r.getMessagesDetailDTO()[i].setEstado(d.getState().toString());

			i++;
		}
		return r;
	}

	

	public MessageDTO doitAnonimoToSend(Message m, Long idUser) throws ProcessException {
		MessageDTO r = new MessageDTO();
		r.setIdGrupo(m.getMessageId().getGrupo().getIdGrupo()+"");
		r.setText(m.getText());
		//r.setUserDTOCreation();
		r.setIdMessage(m.getMessageId().getIdMessage()+"");
		r.setMessagesDetailDTO(new MessageDetailDTO[1]);
		Set<MessageDetail> details = m.getMessagesDetail();
		r.setBlackMessage(m.isBlackMessage());
		r.setAnonimo(m.isAnonimo());
		r.setTimeMessage(m.getTimeMessage());
		r.setSystemMessage(m.isSystemMessage());
		r.setSecretKeyPersonal(m.isSecretKeyPersonal());
		r.setPermitirReenvio(m.isPermitirReenvio());
		r.setMessagesDetailDTO(new MessageDetailDTO[1]);
		
		Media media = m.getMedia();
		MediaDTO mediaDTO = doit(media,true);
		r.setMediaDTO(mediaDTO);
		

		int i=0;
		for ( MessageDetail d : details) {
			
			if (d.getMessageDetailId().getUserDestino().getIdUser().equals(idUser)){
				r.getMessagesDetailDTO()[i] = new MessageDetailDTO();
				
				r.getMessagesDetailDTO()[i].setIdGrupo(m.getMessageId().getGrupo().getIdGrupo()+"");
				r.getMessagesDetailDTO()[i].setIdMessage(m.getMessageId().getIdMessage()+"");
				//r.getMessagesDetailDTO()[i].setIdMessageDetail(d.getMessageDetailId().getIdMessageDetail()+"");
				

				r.getMessagesDetailDTO()[i].setUsuarioDestino( doit(d.getMessageDetailId().getUserDestino()));
				
				//response.getMessagesDetailDTO()[i].setUserDestino(d.getMessageDetailId().getUserDestino().getUsername());
				
				r.getMessagesDetailDTO()[i].setEstado(d.getState().toString());
			}
			
		}
		return r;
	}
	public EncryptKeysDTO doit(EncryptKeys e) {
		EncryptKeysDTO r = new EncryptKeysDTO();
		r.setPrivateKey(e.getPrivateKey());
		r.setPublicKey(e.getPublicKey());
		return r;
	}
	
	public EncryptKeysDTO doitPublicKeyNoEncrypt(EncryptKeys e) {
		EncryptKeysDTO r = new EncryptKeysDTO();
		
		r.setPublicKeyNoEncrypt(e.getPublicKeyNoEncrypt());
		return r;
	}
	
	public EncryptKeys doit(EncryptKeysDTO e) {
		EncryptKeys r = new EncryptKeys();
		r.setPrivateKey(e.getPrivateKey());
		r.setPublicKey(e.getPublicKey());
		r.setPublicKeyNoEncrypt(e.getPublicKeyNoEncrypt());
		return r;
	}
	public AES doit(AESDTO e) {
		AES r = new AES();
		r.setSecretKeyAES(e.getSecretKeyAES().toString());
		r.setSaltAES(e.getSaltAES().toString());
		r.setIteration(e.getIteration().toString());

		return r;
	}


	public MessageDetailDTO doit(MessageDetail md) {
		MessageDetailDTO r = new MessageDetailDTO();
		r.setEstado(md.getState().name());
		r.setIdGrupo(md.getMessageDetailId().getMessage().getMessageId().getGrupo().getIdGrupo()+"");
		r.setIdMessage(md.getMessageDetailId().getMessage().getMessageId().getIdMessage()+"");
		r.setUsuarioDestino( doit(md.getMessageDetailId().getUserDestino()));
		return r;
	}
	
	public MessageDetailDTO doitChangeState(String state, String idGrupo, String idMessage, UsuarioDTO destino) {
		MessageDetailDTO r = new MessageDetailDTO();
		r.setEstado(state);
		r.setIdGrupo(idGrupo);
		r.setIdMessage(idMessage);
		r.setUsuarioDestino(destino);
		return r;
	}	
	public GrupoUserConf doit(GrupoUserConfDTO d) {

		GrupoUserConf r = new GrupoUserConf();
		r.setAnonimoAlways(d.getAnonimoAlways());
		r.setAnonimoRecived(d.getAnonimoRecived());
		r.setBlackMessageAlways(d.getBlackMessageAlways());
		r.setBlackMessageRecived(d.getBlackMessageRecived());
		r.setPermitirReenvio(d.getPermitirReenvio());
		r.setSecretKeyPersonalAlways(d.getExtraAesAlways());
		r.setTimeMessageAlways(d.getTimeMessageAlways());
		r.setTimeMessageSeconds(d.getTimeMessageSeconds());
		r.setGrupoUserConfId(new GrupoUserConfId());
		
		r.setDownloadAllowAudio(d.getDownloadAllowAudio());
		r.setDownloadAllowImage(d.getDownloadAllowImage());
		r.setDownloadAllowVideo(d.getDownloadAllowVideo());
		r.setDownloadAllowAudio(GrupoUserConfEnum.GENERAL_VALUE);
		r.setDownloadAllowVideo(GrupoUserConfEnum.GENERAL_VALUE);
		return r;
	}

	

	public void doit(Grupo grupo, GrupoGralConfDTO d) {
		
		grupo.getGralConf().setAnonimo(d.getAnonimo());
		grupo.getGralConf().setAudiochat(d.getAudiochat());
		grupo.getGralConf().setAudiochatMaxTime(d.getAudiochatMaxTime());
		grupo.getGralConf().setBlackMessageAttachMandatory(d.isBlackMessageAttachMandatory());
		grupo.getGralConf().setChangeNicknameToNumber(d.isChangeNicknameToNumber());
		grupo.getGralConf().setDownloadAllowAudio(ConfigurationStateEnum.ALLOW);
		grupo.getGralConf().setDownloadAllowImage(d.getDownloadAllowImage());
		grupo.getGralConf().setDownloadAllowVideo(ConfigurationStateEnum.ALLOW);
		grupo.getGralConf().setExtraEncrypt(d.getExtraEncrypt());
		grupo.getGralConf().setHideMessageDetails(d.isHideMessageDetails());
		grupo.getGralConf().setHideMessageState(d.isHideMessageState());
		grupo.getGralConf().setHideMemberList(d.isHideMemberList());
		grupo.getGralConf().setResend(d.isResend());
		grupo.getGralConf().setTimeMessageMandatory(d.isTimeMessageMandatory());
		grupo.getGralConf().setTimeMessageMaxTimeAllow(d.getTimeMessageMaxTimeAllow());
		
	
	}


	public void doit(Usuario usuario, MyAccountConfDTO d) {
		
		usuario.getMyAccountConf().setBlackMessageAttachMandatory(d.isBlackMessageAttachMandatory());

		usuario.getMyAccountConf().setDownloadAttachAllowImage(d.isDownloadAttachAllowImage());
		usuario.getMyAccountConf().setHideMyMessageState(d.isHideMyMessageState());
		usuario.getMyAccountConf().setResend(d.isResend());
		usuario.getMyAccountConf().setTimeMessageAlways(d.isTimeMessageAlways());
		usuario.getMyAccountConf().setTimeMessageDefaultTime(d.getTimeMessageDefaultTime());
		usuario.getMyAccountConf().setLoginSkip(d.isLoginSkip());	
	}
	public MyAccountConfDTO doit(MyAccountConf d) {
		MyAccountConfDTO r = new MyAccountConfDTO();
		r.setBlackMessageAttachMandatory(d.isBlackMessageAttachMandatory());

		r.setDownloadAttachAllowImage(d.isDownloadAttachAllowImage());
		r.setHideMyMessageState(d.isHideMyMessageState());
		r.setResend(d.isResend());
		r.setTimeMessageAlways(d.isTimeMessageAlways());
		r.setTimeMessageDefaultTime(d.getTimeMessageDefaultTime());
		
		r.setLock(new LockDTO());
		r.getLock().setEnabled(d.getLock().isEnabled());
		r.getLock().setSeconds(d.getLock().getSeconds());
		
		r.setLoginSkip(d.isLoginSkip());
		return r;
	}	
	
	public SaveGrupoGralConfLockResponseDTO doit(Grupo c) {
		SaveGrupoGralConfLockResponseDTO g = new SaveGrupoGralConfLockResponseDTO();
		g.setIdGrupo(c.getIdGrupo()+"");
		g.setPassword(new GrupoGralConfPasswordDTO());
		g.getPassword().setEnabled(c.getPassword().isEnabled());
		g.getPassword().setExtraEncryptDefaultEnabled(c.getPassword().isExtraEncryptDefaultEnabled());
		g.getPassword().setDeleteExtraEncryptEnabled(c.getPassword().isDeleteExtraEncryptEnabled());		
		//g.getPassword().setPassword(c.getPassword().getPassword());
		g.getPassword().setPasswordExtraEncrypt(c.getPassword().getPasswordExtraEncrypt());
		
		g.setLock(new LockDTO());
		g.getLock().setEnabled(c.getLock().isEnabled());
		g.getLock().setSeconds(c.getLock().getSeconds());

		return g;
	}		
	
}
