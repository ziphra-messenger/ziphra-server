package ar.ziphra.appserver.component.common.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import ar.ziphra.core.repository.AESRepository;
import ar.ziphra.core.repository.EncryptKeysRepository;
import ar.ziphra.core.repository.GrupoGralConfRepository;
import ar.ziphra.core.repository.GrupoInvitationRepository;
import ar.ziphra.core.repository.GrupoRepository;
import ar.ziphra.core.repository.MediaDataRepository;
import ar.ziphra.core.repository.MediaRepository;
import ar.ziphra.core.repository.MessageDetailRepository;
import ar.ziphra.core.repository.MessageRepository;
import ar.ziphra.core.repository.RoleRepository;
import ar.ziphra.core.repository.UserForGrupoRepository;
import ar.ziphra.core.repository.UsuarioRepository;
import ar.ziphra.appserver.component.grupouserconf.GrupoUserConfRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Component
@NoArgsConstructor
@Accessors(fluent = true, chain = false)
@Getter
public class FacadeRepositoryComponent {
	
	

	@Autowired @Lazy
	
	private AESRepository aes;
	
	@Autowired @Lazy
	
	private UsuarioRepository user;
	
	@Autowired @Lazy
	
	private GrupoUserConfRepository grupoUserConf;
	
	@Autowired @Lazy
	
	private UserForGrupoRepository userForGrupo;
	
	@Autowired @Lazy
	
	private MediaRepository media;
	
	@Autowired @Lazy
	
	private MediaDataRepository mediaData;
	
	@Autowired @Lazy
	
	private MessageRepository message;
	
	@Autowired @Lazy
	
	private MessageDetailRepository messageDetail;

	
	@Autowired @Lazy
	
	private RoleRepository role;
	
	@Autowired @Lazy
	
	private GrupoRepository grupo;
	
	
	@Autowired @Lazy
	
	private GrupoInvitationRepository grupoInvitation;

	@Autowired @Lazy
	
	private GrupoGralConfRepository grupoGralConf;
	@Autowired @Lazy
	
	private EncryptKeysRepository encryptKeys;

	
}
