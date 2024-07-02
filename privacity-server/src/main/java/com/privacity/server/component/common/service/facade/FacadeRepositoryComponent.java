package com.privacity.server.component.common.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.privacity.server.common.repositories.UsuarioRepository;
import com.privacity.server.component.grupo.GrupoRepository;
import com.privacity.server.component.grupoinvitation.GrupoInvitationRepository;
import com.privacity.server.component.grupouserconf.GrupoUserConfRepository;
import com.privacity.server.component.media.MediaRepository;
import com.privacity.server.component.message.MessageRepository;
import com.privacity.server.component.messagedetail.MessageDetailDeletedRepository;
import com.privacity.server.component.messagedetail.MessageDetailRepository;
import com.privacity.server.component.userforgrupo.UserForGrupoRepository;
import com.privacity.server.security.RoleRepository;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class FacadeRepositoryComponent {
	
	@Autowired @Lazy
	
	private UsuarioRepository user;
	
	@Autowired @Lazy
	
	private GrupoUserConfRepository grupoUserConf;
	
	@Autowired @Lazy
	
	private UserForGrupoRepository userForGrupo;
	
	@Autowired @Lazy
	
	private MediaRepository media;
	
	@Autowired @Lazy
	
	private MessageRepository message;
	
	@Autowired @Lazy
	
	private MessageDetailRepository messageDetail;
	
	@Autowired @Lazy
	
	private	MessageDetailDeletedRepository messageDetailDeleted;
	
	
	@Autowired @Lazy
	
	private RoleRepository role;
	
	@Autowired @Lazy
	
	private GrupoRepository grupo;
	
	
	@Autowired @Lazy
	
	private GrupoInvitationRepository grupoInvitation;


	public UsuarioRepository user() {
		return user;
	}


	public GrupoUserConfRepository grupoUserConf() {
		return grupoUserConf;
	}


	public UserForGrupoRepository userForGrupo() {
		return userForGrupo;
	}


	public MediaRepository media() {
		return media;
	}


	public MessageRepository message() {
		return message;
	}


	public MessageDetailRepository messageDetail() {
		return messageDetail;
	}


	public MessageDetailDeletedRepository messageDetailDeleted() {
		return messageDetailDeleted;
	}
	
	public RoleRepository role() {
		return role;
	}


	public GrupoRepository grupo() {
		return grupo;
	}


	public GrupoInvitationRepository grupoInvitation() {
		return grupoInvitation;
	}



}
