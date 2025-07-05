package ar.ziphra.core.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import ar.ziphra.core.repository.EncryptKeysRepository;
import ar.ziphra.core.repository.GrupoInvitationRepository;
import ar.ziphra.core.repository.GrupoRepository;
import ar.ziphra.core.repository.MediaRepository;
import ar.ziphra.core.repository.MessageDetailRepository;
import ar.ziphra.core.repository.MessageRepository;
import ar.ziphra.core.repository.RoleRepository;
import ar.ziphra.core.repository.UserForGrupoRepository;
import ar.ziphra.core.repository.UserInvitationCodeRepository;
import ar.ziphra.core.repository.UsuarioRepository;

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
