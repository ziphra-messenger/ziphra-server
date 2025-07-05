package com.privacity.core.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.privacity.core.repository.EncryptKeysRepository;
import com.privacity.core.repository.GrupoInvitationRepository;
import com.privacity.core.repository.GrupoRepository;
import com.privacity.core.repository.MediaRepository;
import com.privacity.core.repository.MessageDetailRepository;
import com.privacity.core.repository.MessageRepository;
import com.privacity.core.repository.RoleRepository;
import com.privacity.core.repository.UserForGrupoRepository;
import com.privacity.core.repository.UserInvitationCodeRepository;
import com.privacity.core.repository.UsuarioRepository;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Component
@NoArgsConstructor
@Accessors(fluent = true, chain = false)
@Getter
public class RepositoryFacade {
	@Autowired @Lazy	
	private EncryptKeysRepository  encryptKeys;
	@Autowired @Lazy
	private GrupoInvitationRepository  grupoInvitation;
	@Autowired @Lazy
	private GrupoRepository  grupo;
	@Autowired @Lazy
	private MediaRepository  media;
	@Autowired @Lazy
	private MessageDetailRepository  messageDetail;
	@Autowired @Lazy
	private MessageRepository  message;
	@Autowired @Lazy
	private RoleRepository  role;
	@Autowired @Lazy
	private UserForGrupoRepository  userForGrupo;
	@Autowired @Lazy
	private UserInvitationCodeRepository  userInvitationCode;
	@Autowired @Lazy
	private UsuarioRepository  usuario;

}
