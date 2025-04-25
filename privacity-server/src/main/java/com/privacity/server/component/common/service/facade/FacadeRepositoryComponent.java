package com.privacity.server.component.common.service.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.privacity.core.repository.AESRepository;
import com.privacity.core.repository.EncryptKeysRepository;
import com.privacity.core.repository.GrupoGralConfRepository;
import com.privacity.core.repository.GrupoInvitationRepository;
import com.privacity.core.repository.GrupoRepository;
import com.privacity.core.repository.MediaDataRepository;
import com.privacity.core.repository.MediaRepository;
import com.privacity.core.repository.MessageDetailRepository;
import com.privacity.core.repository.MessageRepository;
import com.privacity.core.repository.RoleRepository;
import com.privacity.core.repository.UserForGrupoRepository;
import com.privacity.core.repository.UsuarioRepository;
import com.privacity.server.component.grupouserconf.GrupoUserConfRepository;

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
